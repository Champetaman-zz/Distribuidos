/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ConnectionListener extends Thread{

    private ServerSocket socket;
    
    @Override
    public void run() {
        try {
            socket = new ServerSocket(1);
            while(true){
                Socket serverSocket = socket.accept();
                ServerInfo server = new ServerInfo(serverSocket);
                ServerDirectory.getInstance().addServer(server);
                System.out.println(">>Conected new server to port: " + serverSocket.getPort());
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
