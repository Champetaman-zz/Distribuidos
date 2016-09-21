/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Rainbowtable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author TG1604
 */
public class RainbowtableJpaController implements Serializable {

    public RainbowtableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rainbowtable rainbowtable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rainbowtable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRainbowtable(rainbowtable.getPassword()) != null) {
                throw new PreexistingEntityException("Rainbowtable " + rainbowtable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rainbowtable rainbowtable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rainbowtable = em.merge(rainbowtable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rainbowtable.getPassword();
                if (findRainbowtable(id) == null) {
                    throw new NonexistentEntityException("The rainbowtable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rainbowtable rainbowtable;
            try {
                rainbowtable = em.getReference(Rainbowtable.class, id);
                rainbowtable.getPassword();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rainbowtable with id " + id + " no longer exists.", enfe);
            }
            em.remove(rainbowtable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rainbowtable> findRainbowtableEntities() {
        return findRainbowtableEntities(true, -1, -1);
    }

    public List<Rainbowtable> findRainbowtableEntities(int maxResults, int firstResult) {
        return findRainbowtableEntities(false, maxResults, firstResult);
    }

    private List<Rainbowtable> findRainbowtableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rainbowtable.class));
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

    public Rainbowtable findRainbowtable(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rainbowtable.class, id);
        } finally {
            em.close();
        }
    }

    public int getRainbowtableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rainbowtable> rt = cq.from(Rainbowtable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String getPassword(String hash){
        EntityManager em = getEntityManager();
        Rainbowtable result;
        String pass;
        try{
            result = (Rainbowtable) em.createNamedQuery("Rainbowtable.findByHash").setParameter("hash", hash).getSingleResult();
            pass = result.getPassword();
        }catch(NoResultException ex){
            pass = "";
        }
        return pass;
    }
    public String getHash(String pass){
        EntityManager em = getEntityManager();
        Rainbowtable result;
        String hash;
        try{
            result = (Rainbowtable) em.createNamedQuery("Rainbowtable.findByPassword").setParameter("password", pass).getSingleResult();
            hash = result.getHash();
        }catch(NoResultException ex){
            hash = "";
        }
        return hash;
    }
    
    public String deleteAll(){
        EntityManager em = getEntityManager();
        String result;
        try{
            result = "EXITO: ";
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            result += em.createNamedQuery("Rainbowtable.deleteAll").executeUpdate();
            transaction.commit();
            result += " REGISTROS BORRADOS";
        }catch(NoResultException ex){
            result = ex.getMessage();
        }
        return result;
    }
}
