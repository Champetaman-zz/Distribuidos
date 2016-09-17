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
public class AssignationMessage implements Serializable{
    
    private String lowerData;
    private String upperData;
    private String passwordHASH;

    public AssignationMessage(String lowerData, String upperData, String passwordHASH) {
        this.lowerData = lowerData;
        this.upperData = upperData;
        this.passwordHASH = passwordHASH;
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
