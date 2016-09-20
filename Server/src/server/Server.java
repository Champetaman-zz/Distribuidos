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
    public static String MY_IP = "localhost";
    public static List<Integer> caracteres = new ArrayList<>();

    public static void main(String args[]) {

        for (int i = 0; i < 255; i++) {
            caracteres.add(i);
        }

        try {

            //MY_IP
            InetAddress IP = InetAddress.getLocalHost();
            MY_IP = IP.getHostAddress();
            System.out.println("IP of my server is := " + IP.getHostAddress());

            ServerinfoPK serverinfoPK = new ServerinfoPK(MY_IP, MY_PORT);
            Serverinfo serverinfo = new Serverinfo(serverinfoPK, "DESCIFRAR", false);
            Socket socket = new Socket(BALANCER_IP, BALANCER_PORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(serverinfo);
            objectOutputStream.close();
            socket.close();
            ServerSocket serverSocket = new ServerSocket(MY_PORT);
            while (true) {
                socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                AssignationMessage msg = (AssignationMessage) objectInputStream.readObject();
                System.out.println("Input values:= " + msg.getLowerData() + "-" + msg.getUpperData() + "-" + msg.getPasswordHASH());
                socket.close();
                // TODO GENERATE STRINGS
                String password = letters(msg);
                
                if(password.equals(""))
                    password="No se encontro la contrasena";      
       
                System.out.println(">>Enviando respuesta "+password);
                ResponseMessage result = new ResponseMessage("RESULT", "", password, msg.getPasswordHASH());
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

    public static String letters(AssignationMessage msg) {
        int ini, fin;
        String hash = msg.getPasswordHASH();
        //ini = Integer.getInteger(msg.getLowerData());
        //fin = Integer.getInteger(msg.getUpperData());
        ini=96;
        fin=99;

        for (int i = ini; i < fin; i++) {
            String pass=strings(3, "" + (char) i, hash);
            if(!pass.equals(""))
                return pass; 
        }
        return "";
    }

    public static String strings(int n, String start, String hash) {
        System.out.println("String:="+start);
        if (compare(start,hash)) {
            System.out.println("RTA "+start);
            return start;
        } 
        String ret="";
        if( start.length() < n) {
            
            for (int x : caracteres) {
                ret= strings(n, start + (char) x, hash);
                if(!ret.equals(""))
                    return ret;
            }
        }
        return ret;
    }
    
    public static boolean compare (String s, String hash){
        try {
            String sha256Hash = HashGeneratorUtils.generateSHA256(s);
            //System.out.println("Real "+hash);
            //System.out.println("Otro "+sha256Hash);
            if(sha256Hash.equals(hash)){
                return true;
            }
           
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
}
