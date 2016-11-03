/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
public class RemoteServer {
    
    private String serverID;
    private Map<String,Project> proyectos;

    public RemoteServer(String serverID) {
        this.serverID = serverID;
        this.proyectos = new HashMap<>();
    }

    public int getLoad(){
        return this.proyectos.size();
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

}

