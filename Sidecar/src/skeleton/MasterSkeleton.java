
package skeleton;
;
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
public interface MasterSkeleton extends Remote {
    
    public boolean connect(String serverName, ServerSkeleton server) throws RemoteException;
    public boolean commitRequest(String serverName, String fileName, byte[] file) throws RemoteException;
}
