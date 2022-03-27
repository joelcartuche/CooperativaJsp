/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.Deposito;
import Modelos.Retiro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author joelc
 */
public class RetiroJpaController implements Serializable {

    public RetiroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RetiroJpaController() {
    }

    public void create(Retiro retiro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(retiro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retiro retiro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            retiro = em.merge(retiro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retiro.getIdRetiro();
                if (findRetiro(id) == null) {
                    throw new NonexistentEntityException("The retiro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retiro retiro;
            try {
                retiro = em.getReference(Retiro.class, id);
                retiro.getIdRetiro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retiro with id " + id + " no longer exists.", enfe);
            }
            em.remove(retiro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Retiro> findRetiroEntities() {
        return findRetiroEntities(true, -1, -1);
    }

    public List<Retiro> findRetiroEntities(int maxResults, int firstResult) {
        return findRetiroEntities(false, maxResults, firstResult);
    }

    private List<Retiro> findRetiroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Retiro.class));
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

    public Retiro findRetiro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Retiro.class, id);
        } finally {
            em.close();
        }
    }
    //Retiro.findByFechaIncioFechaFin
    
    public List<Retiro> findRetiroFechaInicioFin(Date fechaInicio,Date fechaFin){
        EntityManager em = getEntityManager();
        Query buscar = em.createNamedQuery("Retiro.findByFechaIncioFechaFin");
        buscar.setParameter("fechaInicio", fechaInicio);
        buscar.setParameter("fechaFin", fechaFin);
        List<Retiro> retiroList = buscar.getResultList();
        if (!retiroList.isEmpty()) {

            try {
                return retiroList;
            } finally {
                em.close();
            }

        }else{
            return null;
        }

    }
    

    public int getRetiroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Retiro> rt = cq.from(Retiro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
