/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

import data.Project;
import data.RemoteServer;
import data.RemoteServerComparator;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import skeleton.MasterSkeleton;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Master extends UnicastRemoteObject implements MasterSkeleton{
    
    private List<RemoteServer> remoteServers;
    private List<Project> proyectos;
    private Registry registry;
    private int K = 2;
    
    public Master() throws RemoteException {
        super();
        this.remoteServers = new ArrayList<>();
        this.registry = LocateRegistry.createRegistry(1099);
        this.registry.rebind("Master", this);
        System.out.println("Master running");
    }
    
    
    private List<RemoteServer> getBestServers(){
        remoteServers.sort(new RemoteServerComparator());
        return remoteServers.subList(0, K);
    }
    
    @Override
    public boolean connect(String serverName) throws RemoteException {
        remoteServers.add(new RemoteServer(serverName));
        return true;
    }
    
    public static void main(String args[]) throws RemoteException {
        new Master();
    }

    @Override
    public boolean commitRequest(String fileName) throws RemoteException {
        
        return false;
    }

}
