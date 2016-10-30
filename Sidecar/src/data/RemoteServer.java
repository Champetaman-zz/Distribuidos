/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import skeleton.ServerSkeleton;

/**
 *
 * @author david
 */
public class RemoteServer {
    
    private ServerSkeleton serverSkeleton;
    List<String> proyectos;

    public RemoteServer(ServerSkeleton serverSkeleton) {
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

    
}
