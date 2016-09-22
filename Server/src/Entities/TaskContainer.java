/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.ServerMessage;

/**
 *
 * @author TG1604
 */
public class TaskContainer {
    
    private ServerMessage serverMessage = null;
    private static TaskContainer instance;
    
    private TaskContainer(){    
    }
    
    public static TaskContainer getInstance(){
        if(instance == null)
            instance = new TaskContainer();
        return instance;
    }

    public ServerMessage getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;
    }
}
