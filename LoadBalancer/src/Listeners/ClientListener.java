/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.Agenda;
import Entities.AgendaItem;
import Entities.ServerMessage;
import Entities.ClientInfo;
import Entities.Message;
import Entities.MessageRainbowTable;
import Entities.Rainbowtable;
import Entities.Serverinfo;
import Persistence.RainbowTableContainer;
import Persistence.ServerDirectory;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.Hack.HashGeneratorUtils;

/**
 *
 * @author salabd
 */
public class ClientListener extends Thread {

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1112);
            System.out.println(">>Esperando mensajes de clientes");
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) objectInputStream.readObject();
                System.out.println("Nueva petición: " + msg.getData());
                String type = msg.getType();
                ClientInfo info = new ClientInfo((String) msg.getData(), msg.getIP(), msg.getPort());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                switch (type) {
                    case "DELETE_RAINBOWTABLE":
                        String result = RainbowTableContainer.getInstance().getRainbowtableJpaController().deleteAll();
                        if (result.contains("EXITO")) {
                            msg = new Message("RESULT", result);
                        } else {
                            msg = new Message("ERROR", result);
                        }
                        objectOutputStream.writeObject(msg);
                        break;
                    case "GET_RAINBOWTABLE":
                        MessageRainbowTable msgRainbow = new MessageRainbowTable("ERROR", "NO SE PROCESO NADA");
                        ;
                        try {
                            System.out.println(">>Devolviendo rainbow table");
                            List<Rainbowtable> table = RainbowTableContainer.getInstance().getRainbowtableJpaController().findRainbowtableEntities();
                            msgRainbow = new MessageRainbowTable("RESULT", table);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgRainbow = new MessageRainbowTable("ERROR", ex.getMessage());
                        } finally {
                            objectOutputStream.writeObject(msgRainbow);
                        }
                        break;
                    case "CRYPT":
                        System.out.println(">>Encriptando");
                        try {
                            String hash = RainbowTableContainer.getInstance().getRainbowtableJpaController().getHash(info.getData());
                            if (hash.compareTo("") == 0) {
                                hash = HashGeneratorUtils.generateSHA256(info.getData());
                                Rainbowtable rainbowtable = new Rainbowtable(info.getData(), hash);
                                RainbowTableContainer.getInstance().getRainbowtableJpaController().create(rainbowtable);
                            }
                            msg = new Message("RESULT", hash);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msg = new Message("ERROR", ex.getMessage());
                        } finally {
                            objectOutputStream.writeObject(msg);
                        }
                        break;
                    case "DECRYPT":
                        String password = RainbowTableContainer.getInstance().getRainbowtableJpaController().getPassword(info.getData());
                        if (!password.equals("")) {
                            msg = new Message("RESULT", password);
                            objectOutputStream.writeObject(msg);
                        } else {
                            int cont = 10000;
                            List<Serverinfo> freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
                            System.out.println("Cantidad de Servers:=" + freeServers.size());
                            while (freeServers.size() == 0) {
                                if (cont-- == 0) {
                                    System.out.println(">>Esperando que se conecten servidores");
                                    cont = 1000000;
                                    freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
                                }
                            }
                            if (freeServers.size() != 0) {
                                msg = new Message("ACK", "");
                                objectOutputStream.writeObject(msg);
                                AgendaItem agendaItem = new AgendaItem(info, freeServers);
                                Agenda.getInstance().getAgenda().add(agendaItem);
                                ClientListener.decrypt(0, 255);
                            } else {
                                System.out.println(">>Enviando mensaje de error");
                                msg = new Message("ERROR", "No hay servidores disponibles");
                                objectOutputStream.writeObject(msg);
                            }
                        }
                        break;
                }
                objectOutputStream.close();
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSubscriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void decrypt(int llow, int lup) {
        try {
            System.out.println("Entro a decriptar...");
            if (!Agenda.getInstance().getAgenda().isEmpty()) {
                AgendaItem agendaItem = Agenda.getInstance().getAgenda().element();

                // DEFINE LIMITS
                Integer limit, res = 0, machine, low = 1, up, range = lup - llow;
                machine = agendaItem.getServers().size();
                
                if (range % machine == 0) {
                    limit = range / machine;
                } else {
                    limit = (range / machine) + 1;
                    res = range % machine;
                }
                List<Serverinfo> freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();

                while(freeServers.size()==0){
                    System.out.println(">>No servers available");
                    freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
                    if(freeServers.size() != 0){
                        try{
                            Thread.sleep(2000);
                        }catch(Exception e){
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }
                System.out.println("Maquinas:="+freeServers.size());
                agendaItem.setServers(freeServers);
                for (Serverinfo server : freeServers) {
                    up = low + limit;
                    System.out.println("Server: " + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort());
                    // GENERATE LIMITS
                    String lowerData = low.toString();
                    String upperData = up.toString();
                    ServerMessage msg = new ServerMessage("ASSIGNATION", lowerData, upperData, agendaItem.getClientInfo().getData());
                    Socket socket = new Socket(server.getServerinfoPK().getIp(), server.getServerinfoPK().getPort());
                    server.setBusy(true);
                    server.setLowerdata(lowerData);
                    server.setUpperdata(upperData);
                    ServerDirectory.getInstance().getServerdirectoryJpaController().set_busy(server);
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(msg);
                    outputStream.close();
                    socket.close();
                    res = res - 1;
                    if (res == 0) {
                        limit = limit - 1;
                    }
                    low = up;
                }
                System.out.println("Desencriptando...");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
