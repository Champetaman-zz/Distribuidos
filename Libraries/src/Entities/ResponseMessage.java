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
public class ResponseMessage implements Serializable{
    private String type;
    private String msg;
    private String password;
    private String passwordHASH;

    public ResponseMessage(String type, String msg, String password, String passwordHASH) {
        this.type = type;
        this.msg = msg;
        this.password = password;
        this.passwordHASH = passwordHASH;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
