/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Server extends UnicastRemoteObject implements ServerSkeleton{

    private String serverName;

    public Server(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
    }
    
    @Override
    public String getServerName() throws RemoteException {
        return this.serverName;
    }
    
    public static void main(String[] args) {
        try {
            String serverName = "Directory";
            Registry r = LocateRegistry.createRegistry(1099);
            r.rebind(serverName, new Server(serverName));
            System.out.println("Server running");
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
