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
public class PasswordTuple implements Serializable{
    private String password;
    private String passwordHASH;

    public PasswordTuple(String password, String passwordHASH) {
        this.password = password;
        this.passwordHASH = passwordHASH;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHASH() {
        return passwordHASH;
    }

    public void setPasswordHASH(String passwordHASH) {
        this.passwordHASH = passwordHASH;
    }
    
}
