/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import Listeners.ClientListener;
import Listeners.ServerSubscriptionListener;
import Listeners.ServerAvilabilityController;
import Listeners.ServerResponseListener;

/**
 *
 * @author david
 */
public class LoadBalancer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
