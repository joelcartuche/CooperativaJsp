/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.Credito;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.Socios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    
    

    public void create(Credito credito) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios idCodigoSocio = credito.getIdCodigoSocio();
            if (idCodigoSocio != null) {
                idCodigoSocio = em.getReference(idCodigoSocio.getClass(), idCodigoSocio.getIdSocios());
                credito.setIdCodigoSocio(idCodigoSocio);
            }
            em.persist(credito);
            if (idCodigoSocio != null) {
                idCodigoSocio.getCreditoCollection().add(credito);
                idCodigoSocio = em.merge(idCodigoSocio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Credito credito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credito persistentCredito = em.find(Credito.class, credito.getIdCredito());
            Socios idCodigoSocioOld = persistentCredito.getIdCodigoSocio();
            Socios idCodigoSocioNew = credito.getIdCodigoSocio();
            if (idCodigoSocioNew != null) {
                idCodigoSocioNew = em.getReference(idCodigoSocioNew.getClass(), idCodigoSocioNew.getIdSocios());
                credito.setIdCodigoSocio(idCodigoSocioNew);
            }
            credito = em.merge(credito);
            if (idCodigoSocioOld != null && !idCodigoSocioOld.equals(idCodigoSocioNew)) {
                idCodigoSocioOld.getCreditoCollection().remove(credito);
                idCodigoSocioOld = em.merge(idCodigoSocioOld);
            }
            if (idCodigoSocioNew != null && !idCodigoSocioNew.equals(idCodigoSocioOld)) {
                idCodigoSocioNew.getCreditoCollection().add(credito);
                idCodigoSocioNew = em.merge(idCodigoSocioNew);
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
            Socios idCodigoSocio = credito.getIdCodigoSocio();
            if (idCodigoSocio != null) {
                idCodigoSocio.getCreditoCollection().remove(credito);
                idCodigoSocio = em.merge(idCodigoSocio);
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
    
    public Credito findCreditoSocio(Socios socio) {
        EntityManager em = getEntityManager();
        Query buscar = em.createNamedQuery("Credito.findBySocio");
        buscar.setParameter("socio", socio);
        List<Credito> creditoList = buscar.getResultList();
        if (!creditoList.isEmpty()) {
            try {
                return creditoList.get(0);
            } finally {
                em.close();
            }

        }else{
            return null;
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
