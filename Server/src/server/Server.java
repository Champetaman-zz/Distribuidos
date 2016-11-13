/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import data.File;
import data.Project;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import skeleton.MasterSkeleton;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Server extends UnicastRemoteObject implements ServerSkeleton{

    private final String serverName;
    Map<String, Project> proyectos;
    private Registry registry;
    private final String coordinatorIP = "192.168.0.12";
    private boolean commiting = false;
    private Project actualProyect;
    private MasterSkeleton master;
    
    public Server(String serverName) throws RemoteException, NotBoundException {
        super();
        this.serverName = serverName;
        this.proyectos = new HashMap<>();
        this.registry = LocateRegistry.getRegistry(coordinatorIP);
        this.master = ((MasterSkeleton)this.registry.lookup("Master"));
        this.master.connect(serverName,this);
        System.out.println(serverName + " running");
    }
    
    public boolean addProject(String projectName){
        Project proyecto = proyectos.get(projectName);
        if(proyecto != null){
            return false;
        }else{
            proyectos.put(projectName, new Project(projectName));
            actualProyect = proyectos.get(projectName);
            return true;
        }
    }
 
    public boolean addFileToProject(String projectName, File file){
        Project proyecto = proyectos.get(projectName);
        if(proyecto == null){
            return false;
        }else{
            return proyecto.addFile(file);
        }
    }
    
    public boolean saveFile(String nombreArchivo) throws IOException{
        File file = actualProyect.getFile(nombreArchivo);
        if(file != null){
            return commit(nombreArchivo);
        }
        return true;
    }
    
    public String getFilePath(String nombreArchivo){
        File file = actualProyect.getFile(nombreArchivo);
        if(file != null){
            return file.getFilePath();
        }
        return "";
    }
    
    public boolean createCopy(String nombreArchivo){
        File file = actualProyect.getFile(nombreArchivo);
        if(file != null){
            try {
                Files.copy(Paths.get(file.getFilePath()), Paths.get(System.getProperty("user.dir") + "/temp/" + file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                file.setLocalCopy("./.temp/" + file.getFileName());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    
    public boolean commit(String nombreArchivo) throws RemoteException, IOException{
        File file = actualProyect.getFile(nombreArchivo);
        if(!this.master.commitRequest(this.serverName,nombreArchivo, Files.readAllBytes(Paths.get(file.getFilePath())))){
            // Rollback
            System.out.println("Iniciando rollback " + nombreArchivo);
            Files.copy(Paths.get(System.getProperty("user.dir") + "/temp/" + file.getFileName()), Paths.get(file.getFilePath()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Terminando rollback" + nombreArchivo);
            return false;
        }else{
            return file.updateMetadata();
        }
    }
    
    public boolean isCommiting() {
        return commiting;
    }
    
    // Remote Services
    @Override
    public String getServerName() throws RemoteException {
        return this.serverName;
    }

    @Override
    public boolean canCommit(String fileName) throws RemoteException {
        return true;
    }

    @Override
    public boolean commitFile(String fileName, byte[] file) throws RemoteException {
        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "/backups/" + fileName), file, StandardOpenOption.CREATE);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
