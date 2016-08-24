/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author salabd
 */
@Entity
@Table(name = "SERVERINFO")
@NamedQueries({
    @NamedQuery(name = "Serverinfo.findAll", query = "SELECT s FROM Serverinfo s")})
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
