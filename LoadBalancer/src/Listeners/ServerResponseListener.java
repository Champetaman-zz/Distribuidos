/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TG1604
 */
public class ServerResponseListener extends Thread{
    private ServerSocket socket;
    
    @Override
    public void run() {
        try {
            socket = new ServerSocket(1113);
            while(true){
                Socket serverSocket = socket.accept();
                ObjectInputStream inputChannel = new ObjectInputStream(serverSocket.getInputStream());
                Message msg = (Message)inputChannel.readObject();
                System.out.println("Resultado: " + msg.getData());
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
