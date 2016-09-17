/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Controllers.RainbowtableJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author TG1604
 */
public class RainbowTableContainer {
    private static RainbowTableContainer instance;
    private EntityManagerFactory emf;
    private RainbowtableJpaController rainbowtableJpaController;

    private RainbowTableContainer() {
        emf = Persistence.createEntityManagerFactory("LibrariesPU");
        rainbowtableJpaController = new RainbowtableJpaController(emf);
            
    }
    
    public static RainbowTableContainer getInstance(){
        if(instance == null)
            instance = new RainbowTableContainer();
        return instance;
    }

    public RainbowtableJpaController getRainbowtableJpaController() {
        return rainbowtableJpaController;
    }
}
