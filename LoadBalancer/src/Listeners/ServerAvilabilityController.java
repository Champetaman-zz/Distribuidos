/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Controllers.exceptions.NonexistentEntityException;
import Entities.ServerMessage;
import Entities.Serverinfo;
import Persistence.ServerDirectory;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TG1604
 */
public class ServerAvilabilityController extends Thread {

    @Override
    public void run() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<Serverinfo> servers = ServerDirectory.getInstance().getServerdirectoryJpaController().findServerinfoEntities();
                for (Serverinfo server : servers) {
                    Integer low=0, up=0;
                    try {
                        String IP = server.getServerinfoPK().getIp();
                        Socket socket = new Socket(server.getServerinfoPK().getIp(), 4040);
                        socket.close();
                        System.out.println("Revisado: " + server.getServerinfoPK().getIp());
                    } catch (IOException ex) {
                        //ex.printStackTrace();
                        try {
                            System.out.println(">>Servidor desconectado: " + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort());
                            if (server.getBusy() == true) {
                                int cont = 100;
                                int aux = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers().size();
                                if(server.getLowerdata()!=null){
                                    low = Integer.parseInt(server.getLowerdata());
                                    up = Integer.parseInt(server.getUpperdata());
                                    System.out.println(">>Procediendo a repartir cargas: LOW = " + low + " UP: " + up);
                                }
                                ClientListener.decrypt(low, up);
                            }
                            ServerDirectory.getInstance().getServerdirectoryJpaController().destroy(server.getServerinfoPK());
                        } catch (NonexistentEntityException ex1) {
                            Logger.getLogger(ServerAvilabilityController.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }
}
