/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.TaskContainer;
import Entities.ServerMessage;
import Entities.ResponseMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author TG1604
 */
public class LoadBalancerListener extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Server.MY_PORT);
            while (true) {
                System.out.println(">>Esperando mensaje del load balancer");
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ServerMessage msg = (ServerMessage) objectInputStream.readObject();
                socket.close();
                System.out.println(">>Recibido mensaje de: " + msg.getType());
                switch(msg.getType()){
                    case "ASSIGNATION":
                        System.out.println("Input values:= " + msg.getLowerData() + "-" + msg.getUpperData() + "-" + msg.getPasswordHASH());
                        TaskContainer.getInstance().setServerMessage(msg);
                        break;
                    case "STOP":
                        TaskContainer.getInstance().setServerMessage(null);
                        break;
                }    
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ResponseMessage result = new ResponseMessage("ERROR", ex.getMessage(), "", "");
                Socket socket = new Socket(Server.BALANCER_IP, Server.BALANCER_RESPONSE_PORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(result);
                objectOutputStream.close();
                socket.close();
            } catch (IOException ex1) {
                Logger.getLogger(LoadBalancerListener.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 
    }
    
}
