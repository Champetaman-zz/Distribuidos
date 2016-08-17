/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import java.net.Socket;

/**
 *
 * @author david
 */
public class ServerInfo {
    private Socket socket;
    private boolean busy;

    public ServerInfo(Socket socket) {
        this.socket = socket;
        this.busy = false;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    
}
