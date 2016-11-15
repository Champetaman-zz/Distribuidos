/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class AvailabilityThread extends Thread{
    
    Master master;
    Timer timer;
    
    public AvailabilityThread(Master master) {
        this.master = master;
        timer = new Timer();
    }

    @Override
    public void run() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(String serverName: master.getServers().keySet()){
                    try {
                        ServerSkeleton serverSkeleton = master.getServers().get(serverName);
                        serverSkeleton.getServerName();
                    } catch (RemoteException ex) {
                        System.out.println("Server disconnected: " + serverName);
                        // REASSIGN FILES
                        master.reassign(serverName);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    
    
}
