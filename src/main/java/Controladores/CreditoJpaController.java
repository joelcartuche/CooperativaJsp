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
import Modelos.TasaAmortizacion;
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
        TasaAmortizacion idTasaAmortizacionOrphanCheck = credito.getIdTasaAmortizacion();
        if (idTasaAmortizacionOrphanCheck != null) {
            Credito oldCreditoOfIdTasaAmortizacion = idTasaAmortizacionOrphanCheck.getCredito();
            if (oldCreditoOfIdTasaAmortizacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TasaAmortizacion " + idTasaAmortizacionOrphanCheck + " already has an item of type Credito whose idTasaAmortizacion column cannot be null. Please make another selection for the idTasaAmortizacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios socios = credito.getSocios();
            if (socios != null) {
                socios = em.getReference(socios.getClass(), socios.getIdSocios());
                credito.setSocios(socios);
            }
            TasaAmortizacion idTasaAmortizacion = credito.getIdTasaAmortizacion();
            if (idTasaAmortizacion != null) {
                idTasaAmortizacion = em.getReference(idTasaAmortizacion.getClass(), idTasaAmortizacion.getIdTasaAmortizacion());
                credito.setIdTasaAmortizacion(idTasaAmortizacion);
            }
            em.persist(credito);
            if (socios != null) {
                Credito oldIdCreditoOfSocios = socios.getIdCredito();
                if (oldIdCreditoOfSocios != null) {
                    oldIdCreditoOfSocios.setSocios(null);
                    oldIdCreditoOfSocios = em.merge(oldIdCreditoOfSocios);
                }
                socios.setIdCredito(credito);
                socios = em.merge(socios);
            }
            if (idTasaAmortizacion != null) {
                idTasaAmortizacion.setCredito(credito);
                idTasaAmortizacion = em.merge(idTasaAmortizacion);
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
            Socios sociosOld = persistentCredito.getSocios();
            Socios sociosNew = credito.getSocios();
            TasaAmortizacion idTasaAmortizacionOld = persistentCredito.getIdTasaAmortizacion();
            TasaAmortizacion idTasaAmortizacionNew = credito.getIdTasaAmortizacion();
            List<String> illegalOrphanMessages = null;
            if (sociosOld != null && !sociosOld.equals(sociosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Socios " + sociosOld + " since its idCredito field is not nullable.");
            }
            if (idTasaAmortizacionNew != null && !idTasaAmortizacionNew.equals(idTasaAmortizacionOld)) {
                Credito oldCreditoOfIdTasaAmortizacion = idTasaAmortizacionNew.getCredito();
                if (oldCreditoOfIdTasaAmortizacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TasaAmortizacion " + idTasaAmortizacionNew + " already has an item of type Credito whose idTasaAmortizacion column cannot be null. Please make another selection for the idTasaAmortizacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sociosNew != null) {
                sociosNew = em.getReference(sociosNew.getClass(), sociosNew.getIdSocios());
                credito.setSocios(sociosNew);
            }
            if (idTasaAmortizacionNew != null) {
                idTasaAmortizacionNew = em.getReference(idTasaAmortizacionNew.getClass(), idTasaAmortizacionNew.getIdTasaAmortizacion());
                credito.setIdTasaAmortizacion(idTasaAmortizacionNew);
            }
            credito = em.merge(credito);
            if (sociosNew != null && !sociosNew.equals(sociosOld)) {
                Credito oldIdCreditoOfSocios = sociosNew.getIdCredito();
                if (oldIdCreditoOfSocios != null) {
                    oldIdCreditoOfSocios.setSocios(null);
                    oldIdCreditoOfSocios = em.merge(oldIdCreditoOfSocios);
                }
                sociosNew.setIdCredito(credito);
                sociosNew = em.merge(sociosNew);
            }
            if (idTasaAmortizacionOld != null && !idTasaAmortizacionOld.equals(idTasaAmortizacionNew)) {
                idTasaAmortizacionOld.setCredito(null);
                idTasaAmortizacionOld = em.merge(idTasaAmortizacionOld);
            }
            if (idTasaAmortizacionNew != null && !idTasaAmortizacionNew.equals(idTasaAmortizacionOld)) {
                idTasaAmortizacionNew.setCredito(credito);
                idTasaAmortizacionNew = em.merge(idTasaAmortizacionNew);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Socios sociosOrphanCheck = credito.getSocios();
            if (sociosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Credito (" + credito + ") cannot be destroyed since the Socios " + sociosOrphanCheck + " in its socios field has a non-nullable idCredito field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TasaAmortizacion idTasaAmortizacion = credito.getIdTasaAmortizacion();
            if (idTasaAmortizacion != null) {
                idTasaAmortizacion.setCredito(null);
                idTasaAmortizacion = em.merge(idTasaAmortizacion);
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
