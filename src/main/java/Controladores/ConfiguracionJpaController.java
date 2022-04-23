/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.Configuracion;
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
public class ConfiguracionJpaController implements Serializable {

    public ConfiguracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public ConfiguracionJpaController() {
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Configuracion configuracion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(configuracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Configuracion configuracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            configuracion = em.merge(configuracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configuracion.getIdConfiguracion();
                if (findConfiguracion(id) == null) {
                    throw new NonexistentEntityException("The configuracion with id " + id + " no longer exists.");
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
            Configuracion configuracion;
            try {
                configuracion = em.getReference(Configuracion.class, id);
                configuracion.getIdConfiguracion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configuracion with id " + id + " no longer exists.", enfe);
            }
            em.remove(configuracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Configuracion> findConfiguracionEntities() {
        return findConfiguracionEntities(true, -1, -1);
    }

    public List<Configuracion> findConfiguracionEntities(int maxResults, int firstResult) {
        return findConfiguracionEntities(false, maxResults, firstResult);
    }

    private List<Configuracion> findConfiguracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configuracion.class));
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

    public Configuracion findConfiguracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Configuracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfiguracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Configuracion> rt = cq.from(Configuracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
