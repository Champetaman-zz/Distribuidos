/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.ClientInfo;
import Entities.Message;
import Entities.Serverinfo;
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
                if(msg.getType().equals("CONSUME")){
                    // TODO CONSUME SERVICES
                    ClientInfo info = new ClientInfo(msg.getData(), msg.getIP(), msg.getPort());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    msg = new Message("ACK", ""); 
                    objectOutputStream.writeObject(msg);
                    process(info);
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
            Socket socket = new Socket(info.getIP(), info.getPort());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message msg = new Message("RESULT", "holaaaa");
            objectOutputStream.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
