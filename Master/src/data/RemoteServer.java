/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.HashMap;
import java.util.Map;
import skeleton.ServerSkeleton;

/**
 *
 * @author david
 */
public class RemoteServer {
    
    private String serverName;
    private int load;

    public RemoteServer(String serverName) {
        this.serverName = serverName;
        this.load = 0;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    
}

