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
import Modelos.Cuenta;
import Modelos.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joelc
 */
public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RolJpaController() {
    }
    

    public void create(Rol rol) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Cuenta idCuentaOrphanCheck = rol.getIdCuenta();
        if (idCuentaOrphanCheck != null) {
            Rol oldRolOfIdCuenta = idCuentaOrphanCheck.getRol();
            if (oldRolOfIdCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cuenta " + idCuentaOrphanCheck + " already has an item of type Rol whose idCuenta column cannot be null. Please make another selection for the idCuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta idCuenta = rol.getIdCuenta();
            if (idCuenta != null) {
                idCuenta = em.getReference(idCuenta.getClass(), idCuenta.getIdCuenta());
                rol.setIdCuenta(idCuenta);
            }
            em.persist(rol);
            if (idCuenta != null) {
                idCuenta.setRol(rol);
                idCuenta = em.merge(idCuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
            Cuenta idCuentaOld = persistentRol.getIdCuenta();
            Cuenta idCuentaNew = rol.getIdCuenta();
            List<String> illegalOrphanMessages = null;
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                Rol oldRolOfIdCuenta = idCuentaNew.getRol();
                if (oldRolOfIdCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cuenta " + idCuentaNew + " already has an item of type Rol whose idCuenta column cannot be null. Please make another selection for the idCuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCuentaNew != null) {
                idCuentaNew = em.getReference(idCuentaNew.getClass(), idCuentaNew.getIdCuenta());
                rol.setIdCuenta(idCuentaNew);
            }
            rol = em.merge(rol);
            if (idCuentaOld != null && !idCuentaOld.equals(idCuentaNew)) {
                idCuentaOld.setRol(null);
                idCuentaOld = em.merge(idCuentaOld);
            }
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                idCuentaNew.setRol(rol);
                idCuentaNew = em.merge(idCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            Cuenta idCuenta = rol.getIdCuenta();
            if (idCuenta != null) {
                idCuenta.setRol(null);
                idCuenta = em.merge(idCuenta);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
