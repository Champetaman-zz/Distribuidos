/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ServerDirectory {
    
    private static ServerDirectory instance;
    private static List<ServerInfo> servers;
    
    private ServerDirectory(){
        servers = new ArrayList<>();
    }
    
    public static ServerDirectory getInstance(){
        if(instance == null)
            instance = new ServerDirectory();
        return instance;
    }
    
    public static void addServer(ServerInfo server){
        servers.add(server);
    }
}
