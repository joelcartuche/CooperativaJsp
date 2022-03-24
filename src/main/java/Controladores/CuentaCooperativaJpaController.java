/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.CuentaCooperativa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.Usuario;
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
public class CuentaCooperativaJpaController implements Serializable {

    public CuentaCooperativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public CuentaCooperativaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaCooperativa cuentaCooperativa) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = cuentaCooperativa.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            CuentaCooperativa oldCuentaCooperativaOfIdUsuario = idUsuarioOrphanCheck.getCuentaCooperativa();
            if (oldCuentaCooperativaOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type CuentaCooperativa whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = cuentaCooperativa.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cuentaCooperativa.setIdUsuario(idUsuario);
            }
            Reportes reportes = cuentaCooperativa.getReportes();
            if (reportes != null) {
                reportes = em.getReference(reportes.getClass(), reportes.getIdReportes());
                cuentaCooperativa.setReportes(reportes);
            }
            em.persist(cuentaCooperativa);
            if (idUsuario != null) {
                idUsuario.setCuentaCooperativa(cuentaCooperativa);
                idUsuario = em.merge(idUsuario);
            }
            if (reportes != null) {
                CuentaCooperativa oldIdCuentaOfReportes = reportes.getIdCuenta();
                if (oldIdCuentaOfReportes != null) {
                    oldIdCuentaOfReportes.setReportes(null);
                    oldIdCuentaOfReportes = em.merge(oldIdCuentaOfReportes);
                }
                reportes.setIdCuenta(cuentaCooperativa);
                reportes = em.merge(reportes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaCooperativa cuentaCooperativa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCooperativa persistentCuentaCooperativa = em.find(CuentaCooperativa.class, cuentaCooperativa.getIdCuentaCooperativa());
            Usuario idUsuarioOld = persistentCuentaCooperativa.getIdUsuario();
            Usuario idUsuarioNew = cuentaCooperativa.getIdUsuario();
            Reportes reportesOld = persistentCuentaCooperativa.getReportes();
            Reportes reportesNew = cuentaCooperativa.getReportes();
            List<String> illegalOrphanMessages = null;
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                CuentaCooperativa oldCuentaCooperativaOfIdUsuario = idUsuarioNew.getCuentaCooperativa();
                if (oldCuentaCooperativaOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type CuentaCooperativa whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (reportesOld != null && !reportesOld.equals(reportesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Reportes " + reportesOld + " since its idCuenta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cuentaCooperativa.setIdUsuario(idUsuarioNew);
            }
            if (reportesNew != null) {
                reportesNew = em.getReference(reportesNew.getClass(), reportesNew.getIdReportes());
                cuentaCooperativa.setReportes(reportesNew);
            }
            cuentaCooperativa = em.merge(cuentaCooperativa);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setCuentaCooperativa(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setCuentaCooperativa(cuentaCooperativa);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (reportesNew != null && !reportesNew.equals(reportesOld)) {
                CuentaCooperativa oldIdCuentaOfReportes = reportesNew.getIdCuenta();
                if (oldIdCuentaOfReportes != null) {
                    oldIdCuentaOfReportes.setReportes(null);
                    oldIdCuentaOfReportes = em.merge(oldIdCuentaOfReportes);
                }
                reportesNew.setIdCuenta(cuentaCooperativa);
                reportesNew = em.merge(reportesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaCooperativa.getIdCuentaCooperativa();
                if (findCuentaCooperativa(id) == null) {
                    throw new NonexistentEntityException("The cuentaCooperativa with id " + id + " no longer exists.");
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
            CuentaCooperativa cuentaCooperativa;
            try {
                cuentaCooperativa = em.getReference(CuentaCooperativa.class, id);
                cuentaCooperativa.getIdCuentaCooperativa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaCooperativa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Reportes reportesOrphanCheck = cuentaCooperativa.getReportes();
            if (reportesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CuentaCooperativa (" + cuentaCooperativa + ") cannot be destroyed since the Reportes " + reportesOrphanCheck + " in its reportes field has a non-nullable idCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = cuentaCooperativa.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setCuentaCooperativa(null);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(cuentaCooperativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaCooperativa> findCuentaCooperativaEntities() {
        return findCuentaCooperativaEntities(true, -1, -1);
    }

    public List<CuentaCooperativa> findCuentaCooperativaEntities(int maxResults, int firstResult) {
        return findCuentaCooperativaEntities(false, maxResults, firstResult);
    }

    private List<CuentaCooperativa> findCuentaCooperativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaCooperativa.class));
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

    public CuentaCooperativa findCuentaCooperativa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaCooperativa.class, id);
        } finally {
            em.close();
        }
    }
    
    public CuentaCooperativa findNumeroCuentaCooperativa (String numCuenta) {
        EntityManager em = getEntityManager();
        Query buscar = em.createNamedQuery("CuentaCooperativa.findByNumeroCuenta");
        buscar.setParameter("numeroCuenta", numCuenta);
        List<CuentaCooperativa> cuentaLista = buscar.getResultList();
        try {
            return cuentaLista.get(0);
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
    
    public CuentaCooperativa findBySocioId (int idSocio) {
        EntityManager em = getEntityManager();
        Query buscar = em.createNamedQuery("CuentaCooperativa.findByIdSocio");
        buscar.setParameter("idSocio", idSocio);
        List<CuentaCooperativa> cuentaLista = buscar.getResultList();
        try {
            return cuentaLista.get(0);
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int getCuentaCooperativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaCooperativa> rt = cq.from(CuentaCooperativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
