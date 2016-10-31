/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import data.File;
import data.Project;
import data.RemoteServer;
import data.RemoteServerComparator;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Server extends UnicastRemoteObject implements ServerSkeleton{

    private final String serverName;
    List<RemoteServer> remoteServers;
    Map<String, Project> proyectos;
    private static int K = 2;
    private static Registry registry;
    private static final String directoryIP = "10.138.21.90";
    
    private static Registry getRegistry() throws RemoteException{
        if(Server.registry == null){
            Server.registry = LocateRegistry.getRegistry(directoryIP);
        }
        return Server.registry;
    }
    public Server(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
        this.remoteServers = new ArrayList<>();
        this.proyectos = new HashMap<>();
        Server.getRegistry().rebind(this.serverName, this);
        System.out.println(serverName + " running");
    }
    
    public void connectToNetwork() throws RemoteException, MalformedURLException, NotBoundException{
        String [] servers = Naming.list(directoryIP);
        for(String availableServerName: servers){
            System.out.println("AvailableServer: " + availableServerName);
            ServerSkeleton serverSkeleton = (ServerSkeleton) Naming.lookup(availableServerName);
            this.remoteServers.add(new RemoteServer(availableServerName, serverSkeleton));
        }
    }
    
    public boolean addProject(String projectName){
        Project proyecto = proyectos.get(projectName);
        if(proyecto != null){
            return false;
        }else{
            proyectos.put(projectName, new Project(projectName));
            return true;
        }
    }
 
    public boolean addFileToProject(String projectName, File file){
        Project proyecto = proyectos.get(projectName);
        if(proyecto == null){
            return false;
        }else{
            proyecto.addFile(file);
            return true;
        }
    }
    
    public boolean saveProject(String projectName){
        Project proyecto = proyectos.get(projectName);
        if(proyecto == null){
            return false;
        }else{
            List<RemoteServer> bestServers = getBestServers();
            proyecto.addBackups(bestServers);
            for(RemoteServer remoteServer: bestServers){
                try {
                    if(!remoteServer.getServerSkeleton().commit(proyecto)){
                        return false;
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            return true;
        }
    }
    
    private List<RemoteServer> getBestServers(){
        remoteServers.sort(new RemoteServerComparator());
        return remoteServers.subList(0, K);
    }
    
    // Remote Services
    @Override
    public String getServerName() throws RemoteException {
        return this.serverName;
    }

    @Override
    public boolean commit(Project project) {
        Project proyecto = proyectos.get(project.getProjectName());
        if(proyecto == null){
            proyectos.put(project.getProjectName(), project);
            if(!proyectos.get(project.getProjectName()).save()){
                return false;
            }
        }else{
            if(!proyectos.get(project.getProjectName()).update(project)){
                return false;
            }
        }
        return true;
    }
}
