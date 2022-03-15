/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.CuentaCooperativa;
import Modelos.Reportes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joelc
 */
public class ReportesJpaController implements Serializable {

    public ReportesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ReportesJpaController() {
    }
    
    

    public void create(Reportes reportes) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        CuentaCooperativa idCuentaOrphanCheck = reportes.getIdCuenta();
        if (idCuentaOrphanCheck != null) {
            Reportes oldReportesOfIdCuenta = idCuentaOrphanCheck.getReportes();
            if (oldReportesOfIdCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The CuentaCooperativa " + idCuentaOrphanCheck + " already has an item of type Reportes whose idCuenta column cannot be null. Please make another selection for the idCuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCooperativa idCuenta = reportes.getIdCuenta();
            if (idCuenta != null) {
                idCuenta = em.getReference(idCuenta.getClass(), idCuenta.getIdCuentaCooperativa());
                reportes.setIdCuenta(idCuenta);
            }
            em.persist(reportes);
            if (idCuenta != null) {
                idCuenta.setReportes(reportes);
                idCuenta = em.merge(idCuenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReportes(reportes.getIdReportes()) != null) {
                throw new PreexistingEntityException("Reportes " + reportes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reportes reportes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reportes persistentReportes = em.find(Reportes.class, reportes.getIdReportes());
            CuentaCooperativa idCuentaOld = persistentReportes.getIdCuenta();
            CuentaCooperativa idCuentaNew = reportes.getIdCuenta();
            List<String> illegalOrphanMessages = null;
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                Reportes oldReportesOfIdCuenta = idCuentaNew.getReportes();
                if (oldReportesOfIdCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The CuentaCooperativa " + idCuentaNew + " already has an item of type Reportes whose idCuenta column cannot be null. Please make another selection for the idCuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCuentaNew != null) {
                idCuentaNew = em.getReference(idCuentaNew.getClass(), idCuentaNew.getIdCuentaCooperativa());
                reportes.setIdCuenta(idCuentaNew);
            }
            reportes = em.merge(reportes);
            if (idCuentaOld != null && !idCuentaOld.equals(idCuentaNew)) {
                idCuentaOld.setReportes(null);
                idCuentaOld = em.merge(idCuentaOld);
            }
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                idCuentaNew.setReportes(reportes);
                idCuentaNew = em.merge(idCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reportes.getIdReportes();
                if (findReportes(id) == null) {
                    throw new NonexistentEntityException("The reportes with id " + id + " no longer exists.");
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
            Reportes reportes;
            try {
                reportes = em.getReference(Reportes.class, id);
                reportes.getIdReportes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reportes with id " + id + " no longer exists.", enfe);
            }
            CuentaCooperativa idCuenta = reportes.getIdCuenta();
            if (idCuenta != null) {
                idCuenta.setReportes(null);
                idCuenta = em.merge(idCuenta);
            }
            em.remove(reportes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reportes> findReportesEntities() {
        return findReportesEntities(true, -1, -1);
    }

    public List<Reportes> findReportesEntities(int maxResults, int firstResult) {
        return findReportesEntities(false, maxResults, firstResult);
    }

    private List<Reportes> findReportesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reportes.class));
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

    public Reportes findReportes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reportes.class, id);
        } finally {
            em.close();
        }
    }

    public int getReportesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reportes> rt = cq.from(Reportes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
