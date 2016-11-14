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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import skeleton.MasterSkeleton;
import skeleton.ServerSkeleton;

/**
 *
 * @author TG1604
 */
public class Server extends UnicastRemoteObject implements ServerSkeleton {

    private final String serverName;
    Map<String, Project> proyectos;
    Map<String, Stack<File>> versiones;
    private Registry registry;
    private final String coordinatorIP = "localhost";
    private boolean commiting = false;
    private Project actualProyect;
    private MasterSkeleton master;

    public Server(String serverName, String ip) throws RemoteException, NotBoundException {
        super();
        this.serverName = serverName;
        this.proyectos = new HashMap<>();
        this.versiones = new HashMap<>();
        this.registry = LocateRegistry.getRegistry(ip);
        this.master = ((MasterSkeleton) this.registry.lookup("Master"));
        this.master.connect(serverName, this);
        System.out.println(serverName + " running");
    }

    public boolean addProject(String projectName) {
        Project proyecto = proyectos.get(projectName);
        if (proyecto != null) {
            return false;
        } else {
            Project nuevo = new Project(projectName);
            //Se asigna la fevha inicial y de modificacion
            java.util.Date date = new java.util.Date();
            Timestamp tinicial = new Timestamp(date.getTime());
            nuevo.setFechaCreacion(tinicial);
            nuevo.setFechaModificacion(tinicial);
            //Se asigna el nombre de la maquina creadora del projecto
            nuevo.setPropietario(serverName);
            proyectos.put(projectName, new Project(projectName));
            actualProyect = proyectos.get(projectName);

            return true;
        }
    }

    public boolean addFileToProject(String projectName, File file) {
        Project proyecto = proyectos.get(projectName);
        if (proyecto == null) {
            return false;
        } else {
            
                System.out.println(file.getFileName());
               versiones.put(file.getFileName(), new Stack<>());
                if(versiones.get(file.getFileName())==null)
                    System.out.println("No se encontró elemento en la pila...");
                else{
                    System.out.println("Accediendo a la pila");
                Stack<File> newVersion = versiones.get(file.getFileName());
                newVersion.push(file);
                }
            try {
                master.addFile(serverName, file.getFileName(), file.getBytes());
            } catch (RemoteException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return proyecto.addFile(file);
        }
    }

    public boolean saveFile(String nombreArchivo) throws IOException {
        File file = actualProyect.getFile(nombreArchivo);
        if (file != null) {
            return commit(nombreArchivo);
        }
        return true;
    }

    public String getFilePath(String nombreArchivo) {
        File file = actualProyect.getFile(nombreArchivo);
        if (file != null) {
            return file.getFilePath();
        }
        return "";
    }

    public boolean createCopy(String nombreArchivo) {
        File file = actualProyect.getFile(nombreArchivo);
        if (file != null) {
            try {
                Files.copy(Paths.get(file.getFilePath()), Paths.get(System.getProperty("user.dir") + "/temp/" + file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                file.setLocalCopy("./.temp/" + file.getFileName());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean commit(String nombreArchivo) throws RemoteException, IOException {
        File file = actualProyect.getFile(nombreArchivo);
        if (!this.master.commitRequest(this.serverName, nombreArchivo, Files.readAllBytes(Paths.get(file.getFilePath())))) {
            // Rollback
            System.out.println("Iniciando rollback " + nombreArchivo);
            Files.copy(Paths.get(System.getProperty("user.dir") + "/temp/" + file.getFileName()), Paths.get(file.getFilePath()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Terminando rollback" + nombreArchivo);
            return false;
        } else {
            return file.updateMetadata();
        }
    }

    public Project getProject(String nameP) {
        return proyectos.get(nameP);
    }

    public Map<String, Stack<File>> getVersiones() {
        return versiones;
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
            Path path = Paths.get(System.getProperty("user.dir") + "/backups/" + fileName);
            Files.write(path, file, StandardOpenOption.CREATE);
            data.File newFile = new data.File(fileName, path.toString());
            newFile.setBytes(Files.readAllBytes(path));
            System.out.println("Creando copia de := "+fileName);
                if(versiones.get(fileName)==null){
                      versiones.put(fileName, new Stack<>());
                }
                System.out.println("Accediendo a la pila");
                Stack<File> newVersion = versiones.get(fileName);
                newVersion.push(newFile);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
