/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.ClientInfo;
import Entities.Serverinfo;
import java.util.List;
/**
 *
 * @author TG1604
 */
public class AgendaItem {
    private ClientInfo clientInfo;
    private List<Serverinfo> servers;

    public AgendaItem(ClientInfo clientInfo, List<Serverinfo> servers) {
        this.clientInfo = clientInfo;
        this.servers = servers;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<Serverinfo> getServers() {
        return servers;
    }

    public void setServers(List<Serverinfo> servers) {
        this.servers = servers;
    }
    
    
}
