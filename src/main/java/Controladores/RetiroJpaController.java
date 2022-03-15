/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Retiro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.Socios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    
    

    public void create(Retiro retiro) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Socios idSociosOrphanCheck = retiro.getIdSocios();
        if (idSociosOrphanCheck != null) {
            Retiro oldRetiroOfIdSocios = idSociosOrphanCheck.getRetiro();
            if (oldRetiroOfIdSocios != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Socios " + idSociosOrphanCheck + " already has an item of type Retiro whose idSocios column cannot be null. Please make another selection for the idSocios field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios idSocios = retiro.getIdSocios();
            if (idSocios != null) {
                idSocios = em.getReference(idSocios.getClass(), idSocios.getIdSocios());
                retiro.setIdSocios(idSocios);
            }
            em.persist(retiro);
            if (idSocios != null) {
                idSocios.setRetiro(retiro);
                idSocios = em.merge(idSocios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retiro retiro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retiro persistentRetiro = em.find(Retiro.class, retiro.getIdRetiro());
            Socios idSociosOld = persistentRetiro.getIdSocios();
            Socios idSociosNew = retiro.getIdSocios();
            List<String> illegalOrphanMessages = null;
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                Retiro oldRetiroOfIdSocios = idSociosNew.getRetiro();
                if (oldRetiroOfIdSocios != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Socios " + idSociosNew + " already has an item of type Retiro whose idSocios column cannot be null. Please make another selection for the idSocios field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSociosNew != null) {
                idSociosNew = em.getReference(idSociosNew.getClass(), idSociosNew.getIdSocios());
                retiro.setIdSocios(idSociosNew);
            }
            retiro = em.merge(retiro);
            if (idSociosOld != null && !idSociosOld.equals(idSociosNew)) {
                idSociosOld.setRetiro(null);
                idSociosOld = em.merge(idSociosOld);
            }
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                idSociosNew.setRetiro(retiro);
                idSociosNew = em.merge(idSociosNew);
            }
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
            Socios idSocios = retiro.getIdSocios();
            if (idSocios != null) {
                idSocios.setRetiro(null);
                idSocios = em.merge(idSocios);
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
