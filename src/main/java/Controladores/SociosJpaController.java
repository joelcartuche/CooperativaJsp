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
import java.util.Collection;
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
        if (socios.getCreditoCollection() == null) {
            socios.setCreditoCollection(new ArrayList<Credito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Credito> attachedCreditoCollection = new ArrayList<Credito>();
            for (Credito creditoCollectionCreditoToAttach : socios.getCreditoCollection()) {
                creditoCollectionCreditoToAttach = em.getReference(creditoCollectionCreditoToAttach.getClass(), creditoCollectionCreditoToAttach.getIdCredito());
                attachedCreditoCollection.add(creditoCollectionCreditoToAttach);
            }
            socios.setCreditoCollection(attachedCreditoCollection);
            em.persist(socios);
            for (Credito creditoCollectionCredito : socios.getCreditoCollection()) {
                Socios oldCodigoCreditoOfCreditoCollectionCredito = creditoCollectionCredito.getCodigoCredito();
                creditoCollectionCredito.setCodigoCredito(socios);
                creditoCollectionCredito = em.merge(creditoCollectionCredito);
                if (oldCodigoCreditoOfCreditoCollectionCredito != null) {
                    oldCodigoCreditoOfCreditoCollectionCredito.getCreditoCollection().remove(creditoCollectionCredito);
                    oldCodigoCreditoOfCreditoCollectionCredito = em.merge(oldCodigoCreditoOfCreditoCollectionCredito);
                }
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
            Collection<Credito> creditoCollectionOld = persistentSocios.getCreditoCollection();
            Collection<Credito> creditoCollectionNew = socios.getCreditoCollection();
            List<String> illegalOrphanMessages = null;
            for (Credito creditoCollectionOldCredito : creditoCollectionOld) {
                if (!creditoCollectionNew.contains(creditoCollectionOldCredito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Credito " + creditoCollectionOldCredito + " since its codigoCredito field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Credito> attachedCreditoCollectionNew = new ArrayList<Credito>();
            for (Credito creditoCollectionNewCreditoToAttach : creditoCollectionNew) {
                creditoCollectionNewCreditoToAttach = em.getReference(creditoCollectionNewCreditoToAttach.getClass(), creditoCollectionNewCreditoToAttach.getIdCredito());
                attachedCreditoCollectionNew.add(creditoCollectionNewCreditoToAttach);
            }
            creditoCollectionNew = attachedCreditoCollectionNew;
            socios.setCreditoCollection(creditoCollectionNew);
            socios = em.merge(socios);
            for (Credito creditoCollectionNewCredito : creditoCollectionNew) {
                if (!creditoCollectionOld.contains(creditoCollectionNewCredito)) {
                    Socios oldCodigoCreditoOfCreditoCollectionNewCredito = creditoCollectionNewCredito.getCodigoCredito();
                    creditoCollectionNewCredito.setCodigoCredito(socios);
                    creditoCollectionNewCredito = em.merge(creditoCollectionNewCredito);
                    if (oldCodigoCreditoOfCreditoCollectionNewCredito != null && !oldCodigoCreditoOfCreditoCollectionNewCredito.equals(socios)) {
                        oldCodigoCreditoOfCreditoCollectionNewCredito.getCreditoCollection().remove(creditoCollectionNewCredito);
                        oldCodigoCreditoOfCreditoCollectionNewCredito = em.merge(oldCodigoCreditoOfCreditoCollectionNewCredito);
                    }
                }
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
            Collection<Credito> creditoCollectionOrphanCheck = socios.getCreditoCollection();
            for (Credito creditoCollectionOrphanCheckCredito : creditoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Credito " + creditoCollectionOrphanCheckCredito + " in its creditoCollection field has a non-nullable codigoCredito field.");
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
    
    public Socios findByCedulaSocio (String cedula) {
        EntityManager em = getEntityManager();
        Query buscar = em.createNamedQuery("Socios.findByCedulaSocio");
        buscar.setParameter("cedulaSocio", cedula);
        List<Socios> socioLista = buscar.getResultList();
        try {
            return socioLista.get(0);
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
