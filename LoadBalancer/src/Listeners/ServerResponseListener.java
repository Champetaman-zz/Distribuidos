/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Entities.Agenda;
import Entities.AgendaItem;
import Entities.ClientInfo;
import Entities.Message;
import Entities.PasswordTuple;
import Entities.ResponseMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TG1604
 */
public class ServerResponseListener extends Thread{
    private ServerSocket serverSocket;
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1113);
            while(true){
                Socket socket = this.serverSocket.accept();
                ObjectInputStream inputChannel = new ObjectInputStream(socket.getInputStream());
                ResponseMessage msg = (ResponseMessage)inputChannel.readObject();
                AgendaItem item = Agenda.getInstance().getAgenda().get(msg.getPasswordHASH());
                if(item != null){
                    ClientInfo info = item.getClientInfo();
                    Message response = new Message("RESULT", msg.getPassword());
                    Socket responseSocket = new Socket(info.getIP(), info.getPort());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(responseSocket.getOutputStream());
                    objectOutputStream.writeObject(response);
                    objectOutputStream.close();
                    responseSocket.close();
                    // TODO AVISAR A LOS DEMAS SERVIDORES QUE PAREN
                    Agenda.getInstance().getAgenda().remove(msg.getPasswordHASH());
                }
                socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
