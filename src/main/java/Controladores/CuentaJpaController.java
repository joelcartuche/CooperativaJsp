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
        Rol idRolOrphanCheck = cuenta.getIdRol();
        if (idRolOrphanCheck != null) {
            Cuenta oldCuentaOfIdRol = idRolOrphanCheck.getCuenta();
            if (oldCuentaOfIdRol != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Rol " + idRolOrphanCheck + " already has an item of type Cuenta whose idRol column cannot be null. Please make another selection for the idRol field.");
            }
        }
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
            Rol idRol = cuenta.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                cuenta.setIdRol(idRol);
            }
            Usuario idUsuario = cuenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cuenta.setIdUsuario(idUsuario);
            }
            em.persist(cuenta);
            if (idRol != null) {
                idRol.setCuenta(cuenta);
                idRol = em.merge(idRol);
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
            Rol idRolOld = persistentCuenta.getIdRol();
            Rol idRolNew = cuenta.getIdRol();
            Usuario idUsuarioOld = persistentCuenta.getIdUsuario();
            Usuario idUsuarioNew = cuenta.getIdUsuario();
            List<String> illegalOrphanMessages = null;
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                Cuenta oldCuentaOfIdRol = idRolNew.getCuenta();
                if (oldCuentaOfIdRol != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Rol " + idRolNew + " already has an item of type Cuenta whose idRol column cannot be null. Please make another selection for the idRol field.");
                }
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
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                cuenta.setIdRol(idRolNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cuenta.setIdUsuario(idUsuarioNew);
            }
            cuenta = em.merge(cuenta);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.setCuenta(null);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.setCuenta(cuenta);
                idRolNew = em.merge(idRolNew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Rol idRol = cuenta.getIdRol();
            if (idRol != null) {
                idRol.setCuenta(null);
                idRol = em.merge(idRol);
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
        List<Cuenta> cuentas = findCuentaEntities();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getUsuario().equals(usuario)) {
                return cuenta;
            }
        }
        return null;
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
