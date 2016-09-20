/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TG1604
 */
@Entity
@Table(name = "RAINBOWTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rainbowtable.findAll", query = "SELECT r FROM Rainbowtable r"),
    @NamedQuery(name = "Rainbowtable.findByPassword", query = "SELECT r FROM Rainbowtable r WHERE r.password = :password"),
    @NamedQuery(name = "Rainbowtable.findByHash", query = "SELECT r FROM Rainbowtable r WHERE r.hash = :hash"),
    @NamedQuery(name = "Rainbowtable.deleteAll", query = "DELETE FROM Rainbowtable")})
public class Rainbowtable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "HASH")
    private String hash;

    public Rainbowtable() {
    }

    public Rainbowtable(String password) {
        this.password = password;
    }

    public Rainbowtable(String password, String hash) {
        this.password = password;
        this.hash = hash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (password != null ? password.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rainbowtable)) {
            return false;
        }
        Rainbowtable other = (Rainbowtable) object;
        if ((this.password == null && other.password != null) || (this.password != null && !this.password.equals(other.password))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Rainbowtable[ password=" + password + " ]";
    }
    
}
