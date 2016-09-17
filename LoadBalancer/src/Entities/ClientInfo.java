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
    
    private String passwordHASH;
    private String IP;
    private int port;

    public ClientInfo(String password) {
        this.passwordHASH = password;
    }
    public ClientInfo(String password, String IP, int port) {
        this.passwordHASH = password;
        this.IP = IP;
        this.port = port;
    }

    public String getPasswordHASH() {
        return passwordHASH;
    }

    public void setPassword(String password) {
        this.passwordHASH = password;
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
