/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuota;
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
public class CuotaJpaController implements Serializable {

    public CuotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CuotaJpaController() {
    }

    
    
    public void create(Cuota cuota) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TasaAmortizacion tasaAmortizacion = cuota.getTasaAmortizacion();
            if (tasaAmortizacion != null) {
                tasaAmortizacion = em.getReference(tasaAmortizacion.getClass(), tasaAmortizacion.getIdTasaAmortizacion());
                cuota.setTasaAmortizacion(tasaAmortizacion);
            }
            em.persist(cuota);
            if (tasaAmortizacion != null) {
                Cuota oldIdCuotaOfTasaAmortizacion = tasaAmortizacion.getIdCuota();
                if (oldIdCuotaOfTasaAmortizacion != null) {
                    oldIdCuotaOfTasaAmortizacion.setTasaAmortizacion(null);
                    oldIdCuotaOfTasaAmortizacion = em.merge(oldIdCuotaOfTasaAmortizacion);
                }
                tasaAmortizacion.setIdCuota(cuota);
                tasaAmortizacion = em.merge(tasaAmortizacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuota cuota) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuota persistentCuota = em.find(Cuota.class, cuota.getIdCuota());
            TasaAmortizacion tasaAmortizacionOld = persistentCuota.getTasaAmortizacion();
            TasaAmortizacion tasaAmortizacionNew = cuota.getTasaAmortizacion();
            List<String> illegalOrphanMessages = null;
            if (tasaAmortizacionOld != null && !tasaAmortizacionOld.equals(tasaAmortizacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TasaAmortizacion " + tasaAmortizacionOld + " since its idCuota field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tasaAmortizacionNew != null) {
                tasaAmortizacionNew = em.getReference(tasaAmortizacionNew.getClass(), tasaAmortizacionNew.getIdTasaAmortizacion());
                cuota.setTasaAmortizacion(tasaAmortizacionNew);
            }
            cuota = em.merge(cuota);
            if (tasaAmortizacionNew != null && !tasaAmortizacionNew.equals(tasaAmortizacionOld)) {
                Cuota oldIdCuotaOfTasaAmortizacion = tasaAmortizacionNew.getIdCuota();
                if (oldIdCuotaOfTasaAmortizacion != null) {
                    oldIdCuotaOfTasaAmortizacion.setTasaAmortizacion(null);
                    oldIdCuotaOfTasaAmortizacion = em.merge(oldIdCuotaOfTasaAmortizacion);
                }
                tasaAmortizacionNew.setIdCuota(cuota);
                tasaAmortizacionNew = em.merge(tasaAmortizacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuota.getIdCuota();
                if (findCuota(id) == null) {
                    throw new NonexistentEntityException("The cuota with id " + id + " no longer exists.");
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
            Cuota cuota;
            try {
                cuota = em.getReference(Cuota.class, id);
                cuota.getIdCuota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuota with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TasaAmortizacion tasaAmortizacionOrphanCheck = cuota.getTasaAmortizacion();
            if (tasaAmortizacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuota (" + cuota + ") cannot be destroyed since the TasaAmortizacion " + tasaAmortizacionOrphanCheck + " in its tasaAmortizacion field has a non-nullable idCuota field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cuota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuota> findCuotaEntities() {
        return findCuotaEntities(true, -1, -1);
    }

    public List<Cuota> findCuotaEntities(int maxResults, int firstResult) {
        return findCuotaEntities(false, maxResults, firstResult);
    }

    private List<Cuota> findCuotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuota.class));
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

    public Cuota findCuota(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuota.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuota> rt = cq.from(Cuota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
