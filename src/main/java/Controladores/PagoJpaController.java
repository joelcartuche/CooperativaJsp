/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Pago;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class PagoJpaController implements Serializable {

    public PagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PagoJpaController() {
    }
    
    

    public void create(Pago pago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TasaAmortizacion tasaAmortizacion = pago.getTasaAmortizacion();
            if (tasaAmortizacion != null) {
                tasaAmortizacion = em.getReference(tasaAmortizacion.getClass(), tasaAmortizacion.getIdTasaAmortizacion());
                pago.setTasaAmortizacion(tasaAmortizacion);
            }
            em.persist(pago);
            if (tasaAmortizacion != null) {
                Pago oldIdPagoOfTasaAmortizacion = tasaAmortizacion.getIdPago();
                if (oldIdPagoOfTasaAmortizacion != null) {
                    oldIdPagoOfTasaAmortizacion.setTasaAmortizacion(null);
                    oldIdPagoOfTasaAmortizacion = em.merge(oldIdPagoOfTasaAmortizacion);
                }
                tasaAmortizacion.setIdPago(pago);
                tasaAmortizacion = em.merge(tasaAmortizacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pago pago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pago persistentPago = em.find(Pago.class, pago.getIdPago());
            TasaAmortizacion tasaAmortizacionOld = persistentPago.getTasaAmortizacion();
            TasaAmortizacion tasaAmortizacionNew = pago.getTasaAmortizacion();
            List<String> illegalOrphanMessages = null;
            if (tasaAmortizacionOld != null && !tasaAmortizacionOld.equals(tasaAmortizacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TasaAmortizacion " + tasaAmortizacionOld + " since its idPago field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tasaAmortizacionNew != null) {
                tasaAmortizacionNew = em.getReference(tasaAmortizacionNew.getClass(), tasaAmortizacionNew.getIdTasaAmortizacion());
                pago.setTasaAmortizacion(tasaAmortizacionNew);
            }
            pago = em.merge(pago);
            if (tasaAmortizacionNew != null && !tasaAmortizacionNew.equals(tasaAmortizacionOld)) {
                Pago oldIdPagoOfTasaAmortizacion = tasaAmortizacionNew.getIdPago();
                if (oldIdPagoOfTasaAmortizacion != null) {
                    oldIdPagoOfTasaAmortizacion.setTasaAmortizacion(null);
                    oldIdPagoOfTasaAmortizacion = em.merge(oldIdPagoOfTasaAmortizacion);
                }
                tasaAmortizacionNew.setIdPago(pago);
                tasaAmortizacionNew = em.merge(tasaAmortizacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pago.getIdPago();
                if (findPago(id) == null) {
                    throw new NonexistentEntityException("The pago with id " + id + " no longer exists.");
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
            Pago pago;
            try {
                pago = em.getReference(Pago.class, id);
                pago.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TasaAmortizacion tasaAmortizacionOrphanCheck = pago.getTasaAmortizacion();
            if (tasaAmortizacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pago (" + pago + ") cannot be destroyed since the TasaAmortizacion " + tasaAmortizacionOrphanCheck + " in its tasaAmortizacion field has a non-nullable idPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pago> findPagoEntities() {
        return findPagoEntities(true, -1, -1);
    }

    public List<Pago> findPagoEntities(int maxResults, int firstResult) {
        return findPagoEntities(false, maxResults, firstResult);
    }

    private List<Pago> findPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pago.class));
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

    public Pago findPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pago> rt = cq.from(Pago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
