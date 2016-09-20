/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import Entities.AssignationMessage;
import Entities.Message;
import Entities.PasswordTuple;
import Entities.ResponseMessage;
import Entities.Serverinfo;
import Entities.ServerinfoPK;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.Hack.HashGeneratorUtils;

/**
 *
 * @author david
 */
public class Server {

    private static int MY_PORT = 1010;
    public static int BALANCER_PORT = 1111;
    public static int BALANCER_RESPONSE_PORT = 1113;
    public static String BALANCER_IP = "localhost";
    //public static String BALANCER_IP = "10.192.10.17";
    public static String MY_IP = "10.192.10.23";
    public static void main(String args[]) {
        try {
            ServerinfoPK serverinfoPK = new ServerinfoPK(MY_IP, MY_PORT);
            Serverinfo serverinfo  = new Serverinfo(serverinfoPK, "DESCIFRAR", false);
            Socket socket = new Socket(BALANCER_IP, BALANCER_PORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(serverinfo);
            objectOutputStream.close();
            socket.close();
            ServerSocket serverSocket = new ServerSocket(MY_PORT);
            while(true){
                socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                AssignationMessage msg = (AssignationMessage) objectInputStream.readObject();
                socket.close();
                // TODO GENERATE STRINGS
                List<String> toProcess = new ArrayList<>();
                String password = "prueba";
                for(String s: toProcess){
                    String sha256Hash = HashGeneratorUtils.generateSHA256(s);
                    if(sha256Hash.compareTo(msg.getPasswordHASH()) == 0){
                        password = sha256Hash;
                        break;
                    }
                }
                System.out.println(">>Enviando respuesta");
                ResponseMessage result = new ResponseMessage("RESULT","",password, msg.getPasswordHASH());
                socket = new Socket(BALANCER_IP, BALANCER_RESPONSE_PORT);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(result);
                objectOutputStream.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
}
