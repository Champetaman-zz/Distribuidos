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
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.Hack.HashGeneratorUtils;
import server.Server;
import static server.Server.BALANCER_IP;
import static server.Server.BALANCER_RESPONSE_PORT;
import static server.Server.caracteres;

/**
 *
 * @author TG1604
 */
public class DecryptThread extends Thread{

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        while(true){
            System.out.println(">>Esperando mensaje para decriptar");
            while(TaskContainer.getInstance().getServerMessage() == null){
               
            }
            System.out.println(">>Comenzando a decriptar "+TaskContainer.getInstance().getServerMessage().getPasswordHASH());
            try {
                ServerMessage msg = TaskContainer.getInstance().getServerMessage();
                System.out.println("msg:="+msg.toString());
                String password = letters(msg);
                if(TaskContainer.getInstance().getServerMessage() != null){
                    System.out.println(">>Enviando respuesta");
                    ResponseMessage result;
                    if(password.equals("")){
                        result = new ResponseMessage("NO_RESULT", "", password, msg.getPasswordHASH());
                    }else{
                        result = new ResponseMessage("RESULT", "", password, msg.getPasswordHASH());
                    }
                    Socket socket = new Socket(Server.BALANCER_IP, Server.BALANCER_RESPONSE_PORT);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);
                    objectOutputStream.close();
                    socket.close();
                    TaskContainer.getInstance().setServerMessage(msg);
                }else{
                    System.out.println(">>Parando hilo");
                }
            } catch (IOException ex) {
                try {
                    ResponseMessage result = new ResponseMessage("ERROR", ex.getMessage(), "", "");
                    Socket socket = new Socket(Server.BALANCER_IP, Server.BALANCER_RESPONSE_PORT);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);
                    objectOutputStream.close();
                } catch (IOException ex1) {
                    Logger.getLogger(DecryptThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    public static String letters(ServerMessage msg) {
        int ini, fin;
        String hash = msg.getPasswordHASH();
        ini = Integer.parseInt(msg.getLowerData());
        fin = Integer.parseInt(msg.getUpperData());
    
        for (int i = ini; i < fin; i++) {
            if(TaskContainer.getInstance().getServerMessage() != null){
                String pass=strings(3, "" + (char) i, hash);
                if(!pass.equals(""))
                    return pass; 
            }
        }
        return "";
    }

    public static String strings(int n, String start, String hash) {
        //System.out.println("String:="+start);
        if(TaskContainer.getInstance().getServerMessage() == null){
            return "";
        }
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
