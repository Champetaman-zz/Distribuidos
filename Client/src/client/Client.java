/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Entities.Message;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // arguments supply message and hostname
            Socket socket = new Socket("localhost", 1112);
            Message msg = new Message("CONSUME", "DESCIFRAR", "localhost", 1113);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(msg);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            do{
                msg = (Message)objectInputStream.readObject();
            }while(!msg.getType().equals("ACK"));
            socket.close();
            ServerSocket serverSocket = new ServerSocket(1113);
            socket = serverSocket.accept();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            msg = (Message)objectInputStream.readObject();
            System.out.println("RESULTADO: " + msg.getData());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}