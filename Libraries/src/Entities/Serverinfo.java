/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TG1604
 */
@Entity
@Table(name = "SERVERINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serverinfo.findAll", query = "SELECT s FROM Serverinfo s"),
    @NamedQuery(name = "Serverinfo.findByIp", query = "SELECT s FROM Serverinfo s WHERE s.serverinfoPK.ip = :ip"),
    @NamedQuery(name = "Serverinfo.findByPort", query = "SELECT s FROM Serverinfo s WHERE s.serverinfoPK.port = :port"),
    @NamedQuery(name = "Serverinfo.findByService", query = "SELECT s FROM Serverinfo s WHERE s.service = :service"),
    @NamedQuery(name = "Serverinfo.findByBusy", query = "SELECT s FROM Serverinfo s WHERE s.busy = :busy"),
    @NamedQuery(name = "Serverinfo.findByLowerdata", query = "SELECT s FROM Serverinfo s WHERE s.lowerdata = :lowerdata"),
    @NamedQuery(name = "Serverinfo.findByUpperdata", query = "SELECT s FROM Serverinfo s WHERE s.upperdata = :upperdata"),
    @NamedQuery(name = "Serverinfo.getFreeServers", query = "SELECT c FROM Serverinfo c where c.busy = false")
    })
public class Serverinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServerinfoPK serverinfoPK;
    @Basic(optional = false)
    @Column(name = "SERVICE")
    private String service;
    @Basic(optional = false)
    @Column(name = "BUSY")
    private Boolean busy;
    @Column(name = "LOWERDATA")
    private String lowerdata;
    @Column(name = "UPPERDATA")
    private String upperdata;

    public Serverinfo() {
    }

    public Serverinfo(ServerinfoPK serverinfoPK) {
        this.serverinfoPK = serverinfoPK;
    }

    public Serverinfo(ServerinfoPK serverinfoPK, String service, Boolean busy) {
        this.serverinfoPK = serverinfoPK;
        this.service = service;
        this.busy = busy;
    }

    public Serverinfo(String ip, int port) {
        this.serverinfoPK = new ServerinfoPK(ip, port);
    }

    public ServerinfoPK getServerinfoPK() {
        return serverinfoPK;
    }

    public void setServerinfoPK(ServerinfoPK serverinfoPK) {
        this.serverinfoPK = serverinfoPK;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public String getLowerdata() {
        return lowerdata;
    }

    public void setLowerdata(String lowerdata) {
        this.lowerdata = lowerdata;
    }

    public String getUpperdata() {
        return upperdata;
    }

    public void setUpperdata(String upperdata) {
        this.upperdata = upperdata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serverinfoPK != null ? serverinfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serverinfo)) {
            return false;
        }
        Serverinfo other = (Serverinfo) object;
        if ((this.serverinfoPK == null && other.serverinfoPK != null) || (this.serverinfoPK != null && !this.serverinfoPK.equals(other.serverinfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Serverinfo[ serverinfoPK=" + serverinfoPK + " ]";
    }
    
}
