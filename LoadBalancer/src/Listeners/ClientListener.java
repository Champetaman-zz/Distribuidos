/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.Agenda;
import Entities.AgendaItem;
import Entities.AssignationMessage;
import Entities.ClientInfo;
import Entities.Message;
import Entities.Rainbowtable;
import Entities.Serverinfo;
import Persistence.RainbowTableContainer;
import Persistence.ServerDirectory;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salabd
 */
public class ClientListener extends Thread{
    
    private ServerSocket serverSocket;
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1112);
            while(true){
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) objectInputStream.readObject();
                System.out.println("Nueva petición: " + msg.getData());
                if(msg.getType().equals("CONSUME")){
                    ClientInfo info = new ClientInfo((String)msg.getData(), msg.getIP(), msg.getPort());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    Rainbowtable rainbowtable = RainbowTableContainer.getInstance().getRainbowtableJpaController().findRainbowtable(info.getPasswordHASH());
                    if(rainbowtable != null){
                        msg = new Message("RESULT", rainbowtable.getPassword()); 
                        objectOutputStream.writeObject(msg);
                    }else{
                        msg = new Message("ACK", ""); 
                        objectOutputStream.writeObject(msg);
                        process(info);
                    }
                }
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSubscriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void process(ClientInfo info){
        try {
            //TODO asssign to servers
            System.out.println("Procesando...");
            List<Serverinfo> freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
            AgendaItem agendaItem = new AgendaItem(info, freeServers);
            Agenda.getInstance().getAgenda().put(info.getPasswordHASH(), agendaItem);
            for(Serverinfo server: freeServers){
                // GENERATE LIMITS
                String lowerData = "";
                String upperData = "";
                AssignationMessage msg = new AssignationMessage(lowerData, upperData,info.getPasswordHASH());
                Socket serverSocket = new Socket(server.getServerinfoPK().getIp(), server.getServerinfoPK().getPort());
                ObjectOutputStream outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
                outputStream.writeObject(msg);
                outputStream.close();
                serverSocket.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
