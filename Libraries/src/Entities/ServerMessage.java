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
public class ServerMessage implements Serializable{
    
    private String type;
    private String lowerData;
    private String upperData;
    private String passwordHASH;

    public ServerMessage(String type, String lowerData, String upperData, String passwordHASH) {
        this.type = type;
        this.lowerData = lowerData;
        this.upperData = upperData;
        this.passwordHASH = passwordHASH;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLowerData() {
        return lowerData;
    }

    public void setLowerData(String lowerData) {
        this.lowerData = lowerData;
    }

    public String getUpperData() {
        return upperData;
    }

    public void setUpperData(String upperData) {
        this.upperData = upperData;
    }

    public String getPasswordHASH() {
        return passwordHASH;
    }

    public void setPasswordHASH(String passwordHASH) {
        this.passwordHASH = passwordHASH;
    }
    
    
}
