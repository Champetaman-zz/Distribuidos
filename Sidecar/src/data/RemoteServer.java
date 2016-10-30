/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import skeleton.ServerSkeleton;

/**
 *
 * @author david
 */
public class RemoteServer {
    
    private String serverID;
    private ServerSkeleton serverSkeleton;
    List<String> proyectos;

    public RemoteServer(String serverID, ServerSkeleton serverSkeleton) {
        this.serverID = serverID;
        this.serverSkeleton = serverSkeleton;
        this.proyectos = new ArrayList<>();
    }

    public ServerSkeleton getServerSkeleton() {
        return serverSkeleton;
    }

    public void setServerSkeleton(ServerSkeleton serverSkeleton) {
        this.serverSkeleton = serverSkeleton;
    }

    public List<String> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<String> proyectos) {
        this.proyectos = proyectos;
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

