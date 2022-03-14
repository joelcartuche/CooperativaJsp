/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Aportes;
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
public class AportesJpaController implements Serializable {

    public AportesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public AportesJpaController() {
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aportes aportes) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Socios idSociosOrphanCheck = aportes.getIdSocios();
        if (idSociosOrphanCheck != null) {
            Aportes oldAportesOfIdSocios = idSociosOrphanCheck.getAportes();
            if (oldAportesOfIdSocios != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Socios " + idSociosOrphanCheck + " already has an item of type Aportes whose idSocios column cannot be null. Please make another selection for the idSocios field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios idSocios = aportes.getIdSocios();
            if (idSocios != null) {
                idSocios = em.getReference(idSocios.getClass(), idSocios.getIdSocios());
                aportes.setIdSocios(idSocios);
            }
            em.persist(aportes);
            if (idSocios != null) {
                idSocios.setAportes(aportes);
                idSocios = em.merge(idSocios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aportes aportes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aportes persistentAportes = em.find(Aportes.class, aportes.getIdAportes());
            Socios idSociosOld = persistentAportes.getIdSocios();
            Socios idSociosNew = aportes.getIdSocios();
            List<String> illegalOrphanMessages = null;
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                Aportes oldAportesOfIdSocios = idSociosNew.getAportes();
                if (oldAportesOfIdSocios != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Socios " + idSociosNew + " already has an item of type Aportes whose idSocios column cannot be null. Please make another selection for the idSocios field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSociosNew != null) {
                idSociosNew = em.getReference(idSociosNew.getClass(), idSociosNew.getIdSocios());
                aportes.setIdSocios(idSociosNew);
            }
            aportes = em.merge(aportes);
            if (idSociosOld != null && !idSociosOld.equals(idSociosNew)) {
                idSociosOld.setAportes(null);
                idSociosOld = em.merge(idSociosOld);
            }
            if (idSociosNew != null && !idSociosNew.equals(idSociosOld)) {
                idSociosNew.setAportes(aportes);
                idSociosNew = em.merge(idSociosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aportes.getIdAportes();
                if (findAportes(id) == null) {
                    throw new NonexistentEntityException("The aportes with id " + id + " no longer exists.");
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
            Aportes aportes;
            try {
                aportes = em.getReference(Aportes.class, id);
                aportes.getIdAportes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aportes with id " + id + " no longer exists.", enfe);
            }
            Socios idSocios = aportes.getIdSocios();
            if (idSocios != null) {
                idSocios.setAportes(null);
                idSocios = em.merge(idSocios);
            }
            em.remove(aportes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aportes> findAportesEntities() {
        return findAportesEntities(true, -1, -1);
    }

    public List<Aportes> findAportesEntities(int maxResults, int firstResult) {
        return findAportesEntities(false, maxResults, firstResult);
    }

    private List<Aportes> findAportesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aportes.class));
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

    public Aportes findAportes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aportes.class, id);
        } finally {
            em.close();
        }
    }

    public int getAportesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aportes> rt = cq.from(Aportes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
