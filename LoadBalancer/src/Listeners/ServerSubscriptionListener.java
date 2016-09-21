/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;


import Entities.Agenda;
import Entities.AgendaItem;
import Entities.Serverinfo;
import Persistence.ServerDirectory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ServerSubscriptionListener extends Thread{

    private ServerSocket socket;
    
    @Override
    public void run() {
        try {
            socket = new ServerSocket(1111);
            while(true){
                Socket serverSocket = socket.accept();
                ObjectInputStream inputChannel = new ObjectInputStream(serverSocket.getInputStream());
                Serverinfo serverinfo = (Serverinfo)inputChannel.readObject();
                if(ServerDirectory.getInstance().getServerdirectoryJpaController().findServerinfo(serverinfo.getServerinfoPK()) != null){
                    ServerDirectory.getInstance().getServerdirectoryJpaController().edit(serverinfo);
                }else{
                    ServerDirectory.getInstance().getServerdirectoryJpaController().create(serverinfo);
                }
                System.out.println(">>Subscribed new server to " + serverinfo.getServerinfoPK().getIp() + ":" + serverinfo.getServerinfoPK().getPort());
                List<Serverinfo> freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
                System.out.println("Available servers:");
                for(Serverinfo server: freeServers){
                    System.out.println("Server: " + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort() );
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSubscriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerSubscriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerSubscriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
