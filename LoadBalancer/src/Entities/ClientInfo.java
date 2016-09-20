/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author TG1604
 */
public class ClientInfo {
    
    private String data;
    private String IP;
    private int port;

    
    public ClientInfo(String data) {
        this.data = data;
    }
    public ClientInfo(String data, String IP, int port) {
        this.data = data;
        this.IP = IP;
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
}
