/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Deposito;
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
public class DepositoJpaController implements Serializable {

    public DepositoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DepositoJpaController() {
    }
    
    

    public void create(Deposito deposito) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Socios idSociosOrphanCheck = deposito.getIdSocios();
        if (idSociosOrphanCheck != null) {
            Deposito oldDepositoOfIdSocios = idSociosOrphanCheck.getDeposito();
            if (oldDepositoOfIdSocios != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Socios " + idSociosOrphanCheck + " already has an item of type Deposito whose idSocios column cannot be null. Please make another selection for the idSocios field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios idSocios = deposito.getIdSocios();
            if (idSocios != null) {
                idSocios = em.getReference(idSocios.getClass(), idSocios.getIdSocios());
                deposito.setIdSocios(idSocios);
            }
            em.persist(deposito);
            if (idSocios != null) {
                idSocios.setDeposito(deposito);
                idSocios = em.merge(idSocios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deposito deposito) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deposito persistentDeposito = em.find(Deposito.class, deposito.getIdDeposito());
            Socios idSociosOld = persistentDeposito.getIdSocios();
            Socios idSociosNew = deposito.getIdSocios();
            List<String> illegalOrphanMessages = null;
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                Deposito oldDepositoOfIdSocios = idSociosNew.getDeposito();
                if (oldDepositoOfIdSocios != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Socios " + idSociosNew + " already has an item of type Deposito whose idSocios column cannot be null. Please make another selection for the idSocios field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSociosNew != null) {
                idSociosNew = em.getReference(idSociosNew.getClass(), idSociosNew.getIdSocios());
                deposito.setIdSocios(idSociosNew);
            }
            deposito = em.merge(deposito);
            if (idSociosOld != null && !idSociosOld.equals(idSociosNew)) {
                idSociosOld.setDeposito(null);
                idSociosOld = em.merge(idSociosOld);
            }
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                idSociosNew.setDeposito(deposito);
                idSociosNew = em.merge(idSociosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deposito.getIdDeposito();
                if (findDeposito(id) == null) {
                    throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.");
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
            Deposito deposito;
            try {
                deposito = em.getReference(Deposito.class, id);
                deposito.getIdDeposito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.", enfe);
            }
            Socios idSocios = deposito.getIdSocios();
            if (idSocios != null) {
                idSocios.setDeposito(null);
                idSocios = em.merge(idSocios);
            }
            em.remove(deposito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deposito> findDepositoEntities() {
        return findDepositoEntities(true, -1, -1);
    }

    public List<Deposito> findDepositoEntities(int maxResults, int firstResult) {
        return findDepositoEntities(false, maxResults, firstResult);
    }

    private List<Deposito> findDepositoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deposito.class));
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

    public Deposito findDeposito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deposito.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepositoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deposito> rt = cq.from(Deposito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
