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
import Modelos.Cuota;
import Modelos.Pago;
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
public class TasaAmortizacionJpaController implements Serializable {

    public TasaAmortizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TasaAmortizacionJpaController() {
    }

    public void create(TasaAmortizacion tasaAmortizacion) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Cuota idCuotaOrphanCheck = tasaAmortizacion.getIdCuota();
        if (idCuotaOrphanCheck != null) {
            TasaAmortizacion oldTasaAmortizacionOfIdCuota = idCuotaOrphanCheck.getTasaAmortizacion();
            if (oldTasaAmortizacionOfIdCuota != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cuota " + idCuotaOrphanCheck + " already has an item of type TasaAmortizacion whose idCuota column cannot be null. Please make another selection for the idCuota field.");
            }
        }
        Pago idPagoOrphanCheck = tasaAmortizacion.getIdPago();
        if (idPagoOrphanCheck != null) {
            TasaAmortizacion oldTasaAmortizacionOfIdPago = idPagoOrphanCheck.getTasaAmortizacion();
            if (oldTasaAmortizacionOfIdPago != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pago " + idPagoOrphanCheck + " already has an item of type TasaAmortizacion whose idPago column cannot be null. Please make another selection for the idPago field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuota idCuota = tasaAmortizacion.getIdCuota();
            if (idCuota != null) {
                idCuota = em.getReference(idCuota.getClass(), idCuota.getIdCuota());
                tasaAmortizacion.setIdCuota(idCuota);
            }
            Pago idPago = tasaAmortizacion.getIdPago();
            if (idPago != null) {
                idPago = em.getReference(idPago.getClass(), idPago.getIdPago());
                tasaAmortizacion.setIdPago(idPago);
            }
            em.persist(tasaAmortizacion);
            if (idCuota != null) {
                idCuota.setTasaAmortizacion(tasaAmortizacion);
                idCuota = em.merge(idCuota);
            }
            if (idPago != null) {
                idPago.setTasaAmortizacion(tasaAmortizacion);
                idPago = em.merge(idPago);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TasaAmortizacion tasaAmortizacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TasaAmortizacion persistentTasaAmortizacion = em.find(TasaAmortizacion.class, tasaAmortizacion.getIdTasaAmortizacion());
            Cuota idCuotaOld = persistentTasaAmortizacion.getIdCuota();
            Cuota idCuotaNew = tasaAmortizacion.getIdCuota();
            Pago idPagoOld = persistentTasaAmortizacion.getIdPago();
            Pago idPagoNew = tasaAmortizacion.getIdPago();
            List<String> illegalOrphanMessages = null;
            if (idCuotaNew != null && !idCuotaNew.equals(idCuotaOld)) {
                TasaAmortizacion oldTasaAmortizacionOfIdCuota = idCuotaNew.getTasaAmortizacion();
                if (oldTasaAmortizacionOfIdCuota != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cuota " + idCuotaNew + " already has an item of type TasaAmortizacion whose idCuota column cannot be null. Please make another selection for the idCuota field.");
                }
            }
            if (idPagoNew != null && !idPagoNew.equals(idPagoOld)) {
                TasaAmortizacion oldTasaAmortizacionOfIdPago = idPagoNew.getTasaAmortizacion();
                if (oldTasaAmortizacionOfIdPago != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pago " + idPagoNew + " already has an item of type TasaAmortizacion whose idPago column cannot be null. Please make another selection for the idPago field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCuotaNew != null) {
                idCuotaNew = em.getReference(idCuotaNew.getClass(), idCuotaNew.getIdCuota());
                tasaAmortizacion.setIdCuota(idCuotaNew);
            }
            if (idPagoNew != null) {
                idPagoNew = em.getReference(idPagoNew.getClass(), idPagoNew.getIdPago());
                tasaAmortizacion.setIdPago(idPagoNew);
            }
            tasaAmortizacion = em.merge(tasaAmortizacion);
            if (idCuotaOld != null && !idCuotaOld.equals(idCuotaNew)) {
                idCuotaOld.setTasaAmortizacion(null);
                idCuotaOld = em.merge(idCuotaOld);
            }
            if (idCuotaNew != null && !idCuotaNew.equals(idCuotaOld)) {
                idCuotaNew.setTasaAmortizacion(tasaAmortizacion);
                idCuotaNew = em.merge(idCuotaNew);
            }
            if (idPagoOld != null && !idPagoOld.equals(idPagoNew)) {
                idPagoOld.setTasaAmortizacion(null);
                idPagoOld = em.merge(idPagoOld);
            }
            if (idPagoNew != null && !idPagoNew.equals(idPagoOld)) {
                idPagoNew.setTasaAmortizacion(tasaAmortizacion);
                idPagoNew = em.merge(idPagoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tasaAmortizacion.getIdTasaAmortizacion();
                if (findTasaAmortizacion(id) == null) {
                    throw new NonexistentEntityException("The tasaAmortizacion with id " + id + " no longer exists.");
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
            TasaAmortizacion tasaAmortizacion;
            try {
                tasaAmortizacion = em.getReference(TasaAmortizacion.class, id);
                tasaAmortizacion.getIdTasaAmortizacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tasaAmortizacion with id " + id + " no longer exists.", enfe);
            }
            Cuota idCuota = tasaAmortizacion.getIdCuota();
            if (idCuota != null) {
                idCuota.setTasaAmortizacion(null);
                idCuota = em.merge(idCuota);
            }
            Pago idPago = tasaAmortizacion.getIdPago();
            if (idPago != null) {
                idPago.setTasaAmortizacion(null);
                idPago = em.merge(idPago);
            }
            em.remove(tasaAmortizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TasaAmortizacion> findTasaAmortizacionEntities() {
        return findTasaAmortizacionEntities(true, -1, -1);
    }

    public List<TasaAmortizacion> findTasaAmortizacionEntities(int maxResults, int firstResult) {
        return findTasaAmortizacionEntities(false, maxResults, firstResult);
    }

    private List<TasaAmortizacion> findTasaAmortizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TasaAmortizacion.class));
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

    public TasaAmortizacion findTasaAmortizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TasaAmortizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTasaAmortizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TasaAmortizacion> rt = cq.from(TasaAmortizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
