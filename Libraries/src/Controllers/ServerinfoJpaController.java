/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Serverinfo;
import Entities.ServerinfoPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author TG1604
 */
public class ServerinfoJpaController implements Serializable {

    public ServerinfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Serverinfo serverinfo) throws PreexistingEntityException, Exception {
        if (serverinfo.getServerinfoPK() == null) {
            serverinfo.setServerinfoPK(new ServerinfoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(serverinfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServerinfo(serverinfo.getServerinfoPK()) != null) {
                throw new PreexistingEntityException("Serverinfo " + serverinfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Serverinfo serverinfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            serverinfo = em.merge(serverinfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ServerinfoPK id = serverinfo.getServerinfoPK();
                if (findServerinfo(id) == null) {
                    throw new NonexistentEntityException("The serverinfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ServerinfoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Serverinfo serverinfo;
            try {
                serverinfo = em.getReference(Serverinfo.class, id);
                serverinfo.getServerinfoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serverinfo with id " + id + " no longer exists.", enfe);
            }
            em.remove(serverinfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Serverinfo> findServerinfoEntities() {
        return findServerinfoEntities(true, -1, -1);
    }

    public List<Serverinfo> findServerinfoEntities(int maxResults, int firstResult) {
        return findServerinfoEntities(false, maxResults, firstResult);
    }

    private List<Serverinfo> findServerinfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Serverinfo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Serverinfo findServerinfo(ServerinfoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Serverinfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getServerinfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Serverinfo> rt = cq.from(Serverinfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Serverinfo> getFreeServers(){
        EntityManager em = getEntityManager();
        return em.createNamedQuery("Serverinfo.getFreeServers").getResultList();
    }
}
