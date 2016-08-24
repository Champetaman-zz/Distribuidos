/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import Entities.Serverinfo;
import Entities.ServerinfoPK;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 *
 * @author david
 */
public class Server {

    private static int port = 1010;
    public static int BALANCERPORT = 1111;
    public static String IP = "localhost";
    public static void main(String args[]) {
        try {
            ServerinfoPK serverinfoPK = new ServerinfoPK("localhost", port);
            Serverinfo serverinfo  = new Serverinfo(serverinfoPK, "DESCIFRAR", false);
            Socket socket = new Socket(IP, BALANCERPORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(serverinfo);
            socket.close();
            SocketListener socketListener = new SocketListener(port);
            socketListener.start();
        } catch (IOException e) {
            System.out.println("Socket error: " + e.getMessage());
        }
    }
    
}
