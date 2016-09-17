/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Controllers.ServerinfoJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author salabd
 */
public class ServerDirectory {
    
    private static ServerDirectory instance;
    private EntityManagerFactory emf;
    private ServerinfoJpaController serverdirectoryJpaController;

    private ServerDirectory() {
        emf = Persistence.createEntityManagerFactory("LibrariesPU");
        serverdirectoryJpaController = new ServerinfoJpaController(emf);
            
    }
    
    public static ServerDirectory getInstance(){
        if(instance == null)
            instance = new ServerDirectory();
        return instance;
    }

    public ServerinfoJpaController getServerdirectoryJpaController() {
        return serverdirectoryJpaController;
    }
}
