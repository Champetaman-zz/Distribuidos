/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Controllers.exceptions.NonexistentEntityException;
import Entities.Serverinfo;
import Persistence.ServerDirectory;
import java.io.IOException;
import java.net.InetAddress;
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
                for(Serverinfo server: servers){
                    try {
                        String IP = server.getServerinfoPK().getIp();
                        InetAddress inet = InetAddress.getByName(IP);
                        if(!inet.isReachable(500)){
                            System.out.println(">>Servidor desconectado: " + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort());
                            ServerDirectory.getInstance().getServerdirectoryJpaController().destroy(server.getServerinfoPK());
                            //TODO REASIGNAR TAREA
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerAvilabilityController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(ServerAvilabilityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }
        
}
