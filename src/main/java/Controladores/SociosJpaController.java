/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.Credito;
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
public class SociosJpaController implements Serializable {

    public SociosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SociosJpaController() {
    }

    public void create(Socios socios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credito credito = socios.getCredito();
            if (credito != null) {
                credito = em.getReference(credito.getClass(), credito.getIdCredito());
                socios.setCredito(credito);
            }
            em.persist(socios);
            if (credito != null) {
                Socios oldIdSociosOfCredito = credito.getIdSocios();
                if (oldIdSociosOfCredito != null) {
                    oldIdSociosOfCredito.setCredito(null);
                    oldIdSociosOfCredito = em.merge(oldIdSociosOfCredito);
                }
                credito.setIdSocios(socios);
                credito = em.merge(credito);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Socios socios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios persistentSocios = em.find(Socios.class, socios.getIdSocios());
            Credito creditoOld = persistentSocios.getCredito();
            Credito creditoNew = socios.getCredito();
            List<String> illegalOrphanMessages = null;
            if (creditoOld != null && !creditoOld.equals(creditoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Credito " + creditoOld + " since its idSocios field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (creditoNew != null) {
                creditoNew = em.getReference(creditoNew.getClass(), creditoNew.getIdCredito());
                socios.setCredito(creditoNew);
            }
            socios = em.merge(socios);
            if (creditoNew != null && !creditoNew.equals(creditoOld)) {
                Socios oldIdSociosOfCredito = creditoNew.getIdSocios();
                if (oldIdSociosOfCredito != null) {
                    oldIdSociosOfCredito.setCredito(null);
                    oldIdSociosOfCredito = em.merge(oldIdSociosOfCredito);
                }
                creditoNew.setIdSocios(socios);
                creditoNew = em.merge(creditoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = socios.getIdSocios();
                if (findSocios(id) == null) {
                    throw new NonexistentEntityException("The socios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios socios;
            try {
                socios = em.getReference(Socios.class, id);
                socios.getIdSocios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The socios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Credito creditoOrphanCheck = socios.getCredito();
            if (creditoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Credito " + creditoOrphanCheck + " in its credito field has a non-nullable idSocios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(socios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Socios> findSociosEntities() {
        return findSociosEntities(true, -1, -1);
    }

    public List<Socios> findSociosEntities(int maxResults, int firstResult) {
        return findSociosEntities(false, maxResults, firstResult);
    }

    private List<Socios> findSociosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Socios.class));
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

    public Socios findSocios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Socios.class, id);
        } finally {
            em.close();
        }
    }

    public int getSociosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Socios> rt = cq.from(Socios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
