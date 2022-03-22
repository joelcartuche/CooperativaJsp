/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.TasaAmortizacion;
import java.io.Serializable;
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
public class TasaAmortizacionJpaController implements Serializable {

    public TasaAmortizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TasaAmortizacionJpaController() {
    }
    
    

    public void create(TasaAmortizacion tasaAmortizacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tasaAmortizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TasaAmortizacion tasaAmortizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tasaAmortizacion = em.merge(tasaAmortizacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tasaAmortizacion.getIdTasaAmortizacion();
                if (findTasaAmortizacion(id) == null) {
                    throw new NonexistentEntityException("The tasaAmortizacion with id " + id + " no longer exists.");
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
            TasaAmortizacion tasaAmortizacion;
            try {
                tasaAmortizacion = em.getReference(TasaAmortizacion.class, id);
                tasaAmortizacion.getIdTasaAmortizacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tasaAmortizacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(tasaAmortizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TasaAmortizacion> findTasaAmortizacionEntities() {
        return findTasaAmortizacionEntities(true, -1, -1);
    }

    public List<TasaAmortizacion> findTasaAmortizacionEntities(int maxResults, int firstResult) {
        return findTasaAmortizacionEntities(false, maxResults, firstResult);
    }

    private List<TasaAmortizacion> findTasaAmortizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TasaAmortizacion.class));
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

    public TasaAmortizacion findTasaAmortizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TasaAmortizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTasaAmortizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TasaAmortizacion> rt = cq.from(TasaAmortizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
