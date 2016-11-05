/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Comparator;

/**
 *
 * @author TG1604
 */
public class RemoteServerComparator implements Comparator<RemoteServer> {
    
  private String requestServer;

    public RemoteServerComparator(String requestServer) {
        this.requestServer = requestServer;
    }
  
  @Override
  public int compare(RemoteServer x, RemoteServer y) {
    if(x.getServerName().equals(requestServer)){
        return 1;
    }
    if(y.getServerName().equals(requestServer)){
        return -1;
    }
    
    if(x.getLoad() > y.getLoad()){
        return 1;
    }else{
        return -1;
    }
  }
}