
package skeleton;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TG1604
 */
public interface ServerSkeleton extends Remote{
    
    public String getServerName() throws RemoteException;
}
