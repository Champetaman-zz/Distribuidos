/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.Agenda;
import Entities.AgendaItem;
import Entities.ClientInfo;
import Entities.Message;
import Entities.PasswordTuple;
import Entities.Rainbowtable;
import Entities.ResponseMessage;
import Entities.ServerMessage;
import Entities.Serverinfo;
import Persistence.RainbowTableContainer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TG1604
 */
public class ServerResponseListener extends Thread{
    private ServerSocket serverSocket;
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1113);
            while(true){
                Socket socket = this.serverSocket.accept();
                ObjectInputStream inputChannel = new ObjectInputStream(socket.getInputStream());
                ResponseMessage msg = (ResponseMessage)inputChannel.readObject();
                System.out.println(">>Mensaje recibido: " + msg.getPassword());
                socket.close();
                // Guardar contraseña en DB
                if(msg.getType().equals("RESULT")){
                    Rainbowtable rainbowtable = new Rainbowtable(msg.getPassword(), msg.getPasswordHASH());
                    RainbowTableContainer.getInstance().getRainbowtableJpaController().create(rainbowtable);
                    AgendaItem item = Agenda.getInstance().getAgenda().element();
                    if(item != null){
                        ClientInfo info = item.getClientInfo();
                        Message response = new Message("RESULT", msg.getPassword());
                        Socket responseSocket = new Socket(info.getIP(), info.getPort());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(responseSocket.getOutputStream());
                        objectOutputStream.writeObject(response);
                        objectOutputStream.close();
                        responseSocket.close();
                        // TODO AVISAR A LOS DEMAS SERVIDORES QUE PAREN
                        ServerMessage stopMsg = new ServerMessage("STOP","", "", "");
                        for(Serverinfo server: item.getServers()){
                            System.out.println(">>Enviando mensaje de parar a :" + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort());
                            socket = new Socket(server.getServerinfoPK().getIp(), server.getServerinfoPK().getPort());
                            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                            outputStream.writeObject(stopMsg);
                            outputStream.close();
                            socket.close();
                        }
                        Agenda.getInstance().getAgenda().remove();
                        // CONTINUAR CON OTROS TRABAJOS PENDIENTES SI HAY
                        ClientListener.decrypt();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
