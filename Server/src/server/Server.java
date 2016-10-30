/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import data.File;
import data.Project;
import data.RemoteServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Server extends UnicastRemoteObject implements ServerSkeleton{

    private final String serverName;
    Map<String, RemoteServer> remoteServers;
    Map<String, Project> proyectos;
    
    public Server(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
        this.remoteServers = new HashMap<>();
        this.proyectos = new HashMap<>();
    }
    
    private void connectToNetwork() throws RemoteException, MalformedURLException, NotBoundException{
        String [] servers = Naming.list("//localhost/");
        for(String availableServerName: servers){
            System.out.println("AvailableServer: " + availableServerName);
            ServerSkeleton serverSkeleton = (ServerSkeleton) Naming.lookup(availableServerName);
            this.remoteServers.put(serverSkeleton.getServerName(), new RemoteServer(serverSkeleton));
        }
    }
    
    public boolean addProject(String projectName){
        Project proyecto = proyectos.get(projectName);
        if(proyecto != null){
            return false;
        }else{
            //TODO Load balancer
            proyectos.put(projectName, new Project(projectName));
            //TODO Send Broadcast adding server information
            return true;
        }
    }
 
    public boolean addFileToProject(String projectName, File file){
        Project proyecto = proyectos.get(projectName);
        if(proyecto == null){
            return false;
        }else{
            proyecto.addFile(file);
            // TODO generate bakcups
            return true;
        }
    }
    // Remote Services
    
    @Override
    public String getServerName() throws RemoteException {
        return this.serverName;
    }
}
