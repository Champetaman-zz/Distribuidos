/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import Controllers.exceptions.NonexistentEntityException;
import Entities.Serverinfo;
import Listeners.ClientListener;
import Listeners.ServerSubscriptionListener;
import Listeners.ServerAvilabilityController;
import Listeners.ServerResponseListener;
import Persistence.ServerDirectory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class LoadBalancer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // DELETE SERVER DIRECTORY
        List<Serverinfo> freeServers = ServerDirectory.getInstance().getServerdirectoryJpaController().getFreeServers();
        for(Serverinfo server: freeServers){
            try {
                System.out.println("Borrando: " + server.getServerinfoPK().getIp() + ":" + server.getServerinfoPK().getPort() );
                ServerDirectory.getInstance().getServerdirectoryJpaController().destroy(server.getServerinfoPK());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Start servers connection listener
        ServerSubscriptionListener connectionListener = new ServerSubscriptionListener();
        connectionListener.start();
        ClientListener clientListener = new ClientListener();
        clientListener.start();
        ServerAvilabilityController serverAvilabilityController = new ServerAvilabilityController();
        serverAvilabilityController.start();
        ServerResponseListener serverResponseListener = new ServerResponseListener();
        serverResponseListener.start();
        
    }
}
