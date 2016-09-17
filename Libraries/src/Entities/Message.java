/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author TG1604
 */
public class Message implements Serializable{
    
    private String type;
    private String data;
    private String IP;
    private int port;

    public Message(String type, String data) {
        this.type = type;
        this.data = data;
    }
    
    public Message(String type, String data, String IP, int port) {
        this.type = type;
        this.data = data;
        this.IP = IP;
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
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