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
    
    private String password;
    private String IP;
    private int port;

    public ClientInfo(String password) {
        this.password = password;
    }
    public ClientInfo(String password, String IP, int port) {
        this.password = password;
        this.IP = IP;
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    
}
