/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Credito;
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
public class CreditoJpaController implements Serializable {

    public CreditoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CreditoJpaController() {
    }

    public void create(Credito credito) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Socios idSociosOrphanCheck = credito.getIdSocios();
        if (idSociosOrphanCheck != null) {
            Credito oldCreditoOfIdSocios = idSociosOrphanCheck.getCredito();
            if (oldCreditoOfIdSocios != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Socios " + idSociosOrphanCheck + " already has an item of type Credito whose idSocios column cannot be null. Please make another selection for the idSocios field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios idSocios = credito.getIdSocios();
            if (idSocios != null) {
                idSocios = em.getReference(idSocios.getClass(), idSocios.getIdSocios());
                credito.setIdSocios(idSocios);
            }
            em.persist(credito);
            if (idSocios != null) {
                idSocios.setCredito(credito);
                idSocios = em.merge(idSocios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Credito credito) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credito persistentCredito = em.find(Credito.class, credito.getIdCredito());
            Socios idSociosOld = persistentCredito.getIdSocios();
            Socios idSociosNew = credito.getIdSocios();
            List<String> illegalOrphanMessages = null;
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                Credito oldCreditoOfIdSocios = idSociosNew.getCredito();
                if (oldCreditoOfIdSocios != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Socios " + idSociosNew + " already has an item of type Credito whose idSocios column cannot be null. Please make another selection for the idSocios field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSociosNew != null) {
                idSociosNew = em.getReference(idSociosNew.getClass(), idSociosNew.getIdSocios());
                credito.setIdSocios(idSociosNew);
            }
            credito = em.merge(credito);
            if (idSociosOld != null && !idSociosOld.equals(idSociosNew)) {
                idSociosOld.setCredito(null);
                idSociosOld = em.merge(idSociosOld);
            }
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                idSociosNew.setCredito(credito);
                idSociosNew = em.merge(idSociosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = credito.getIdCredito();
                if (findCredito(id) == null) {
                    throw new NonexistentEntityException("The credito with id " + id + " no longer exists.");
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
            Credito credito;
            try {
                credito = em.getReference(Credito.class, id);
                credito.getIdCredito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The credito with id " + id + " no longer exists.", enfe);
            }
            Socios idSocios = credito.getIdSocios();
            if (idSocios != null) {
                idSocios.setCredito(null);
                idSocios = em.merge(idSocios);
            }
            em.remove(credito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Credito> findCreditoEntities() {
        return findCreditoEntities(true, -1, -1);
    }

    public List<Credito> findCreditoEntities(int maxResults, int firstResult) {
        return findCreditoEntities(false, maxResults, firstResult);
    }

    private List<Credito> findCreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Credito.class));
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

    public Credito findCredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Credito.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Credito> rt = cq.from(Credito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
