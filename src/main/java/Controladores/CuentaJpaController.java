/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.Rol;
import Modelos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joelc
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CuentaJpaController() {
    }
    
    public void create(Cuenta cuenta) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = cuenta.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            Cuenta oldCuentaOfIdUsuario = idUsuarioOrphanCheck.getCuenta();
            if (oldCuentaOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type Cuenta whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol = cuenta.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getIdRol());
                cuenta.setRol(rol);
            }
            Usuario idUsuario = cuenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cuenta.setIdUsuario(idUsuario);
            }
            em.persist(cuenta);
            if (rol != null) {
                Cuenta oldIdCuentaOfRol = rol.getIdCuenta();
                if (oldIdCuentaOfRol != null) {
                    oldIdCuentaOfRol.setRol(null);
                    oldIdCuentaOfRol = em.merge(oldIdCuentaOfRol);
                }
                rol.setIdCuenta(cuenta);
                rol = em.merge(rol);
            }
            if (idUsuario != null) {
                idUsuario.setCuenta(cuenta);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getIdCuenta());
            Rol rolOld = persistentCuenta.getRol();
            Rol rolNew = cuenta.getRol();
            Usuario idUsuarioOld = persistentCuenta.getIdUsuario();
            Usuario idUsuarioNew = cuenta.getIdUsuario();
            List<String> illegalOrphanMessages = null;
            if (rolOld != null && !rolOld.equals(rolNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Rol " + rolOld + " since its idCuenta field is not nullable.");
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                Cuenta oldCuentaOfIdUsuario = idUsuarioNew.getCuenta();
                if (oldCuentaOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type Cuenta whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getIdRol());
                cuenta.setRol(rolNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cuenta.setIdUsuario(idUsuarioNew);
            }
            cuenta = em.merge(cuenta);
            if (rolNew != null && !rolNew.equals(rolOld)) {
                Cuenta oldIdCuentaOfRol = rolNew.getIdCuenta();
                if (oldIdCuentaOfRol != null) {
                    oldIdCuentaOfRol.setRol(null);
                    oldIdCuentaOfRol = em.merge(oldIdCuentaOfRol);
                }
                rolNew.setIdCuenta(cuenta);
                rolNew = em.merge(rolNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setCuenta(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setCuenta(cuenta);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getIdCuenta();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getIdCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Rol rolOrphanCheck = cuenta.getRol();
            if (rolOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Rol " + rolOrphanCheck + " in its rol field has a non-nullable idCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = cuenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setCuenta(null);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }
    
    public Cuenta findCuentaUsuario(String usuario) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, usuario);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
