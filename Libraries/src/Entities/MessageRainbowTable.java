/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author TG1604
 */
public class MessageRainbowTable implements Serializable{
    
    private String type;
    private List<Rainbowtable> data;
    private String msg;
    private String IP;
    private int port;

    public MessageRainbowTable(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }
    
    public MessageRainbowTable(String type, List<Rainbowtable> data) {
        this.type = type;
        this.data = data;
    }
    
    public MessageRainbowTable(String type, List<Rainbowtable> data, String IP, int port) {
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

    public List<Rainbowtable> getData() {
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