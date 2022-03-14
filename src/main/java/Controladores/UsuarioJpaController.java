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
import Modelos.CuentaCooperativa;
import Modelos.Cuenta;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UsuarioJpaController() {
    }
    
    
    
    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCooperativa cuentaCooperativa = usuario.getCuentaCooperativa();
            if (cuentaCooperativa != null) {
                cuentaCooperativa = em.getReference(cuentaCooperativa.getClass(), cuentaCooperativa.getIdCuentaCooperativa());
                usuario.setCuentaCooperativa(cuentaCooperativa);
            }
            Cuenta cuenta = usuario.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getIdCuenta());
                usuario.setCuenta(cuenta);
            }
            em.persist(usuario);
            if (cuentaCooperativa != null) {
                Usuario oldIdUsuarioOfCuentaCooperativa = cuentaCooperativa.getIdUsuario();
                if (oldIdUsuarioOfCuentaCooperativa != null) {
                    oldIdUsuarioOfCuentaCooperativa.setCuentaCooperativa(null);
                    oldIdUsuarioOfCuentaCooperativa = em.merge(oldIdUsuarioOfCuentaCooperativa);
                }
                cuentaCooperativa.setIdUsuario(usuario);
                cuentaCooperativa = em.merge(cuentaCooperativa);
            }
            if (cuenta != null) {
                Usuario oldIdUsuarioOfCuenta = cuenta.getIdUsuario();
                if (oldIdUsuarioOfCuenta != null) {
                    oldIdUsuarioOfCuenta.setCuenta(null);
                    oldIdUsuarioOfCuenta = em.merge(oldIdUsuarioOfCuenta);
                }
                cuenta.setIdUsuario(usuario);
                cuenta = em.merge(cuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            CuentaCooperativa cuentaCooperativaOld = persistentUsuario.getCuentaCooperativa();
            CuentaCooperativa cuentaCooperativaNew = usuario.getCuentaCooperativa();
            Cuenta cuentaOld = persistentUsuario.getCuenta();
            Cuenta cuentaNew = usuario.getCuenta();
            List<String> illegalOrphanMessages = null;
            if (cuentaCooperativaOld != null && !cuentaCooperativaOld.equals(cuentaCooperativaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain CuentaCooperativa " + cuentaCooperativaOld + " since its idUsuario field is not nullable.");
            }
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cuenta " + cuentaOld + " since its idUsuario field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentaCooperativaNew != null) {
                cuentaCooperativaNew = em.getReference(cuentaCooperativaNew.getClass(), cuentaCooperativaNew.getIdCuentaCooperativa());
                usuario.setCuentaCooperativa(cuentaCooperativaNew);
            }
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getIdCuenta());
                usuario.setCuenta(cuentaNew);
            }
            usuario = em.merge(usuario);
            if (cuentaCooperativaNew != null && !cuentaCooperativaNew.equals(cuentaCooperativaOld)) {
                Usuario oldIdUsuarioOfCuentaCooperativa = cuentaCooperativaNew.getIdUsuario();
                if (oldIdUsuarioOfCuentaCooperativa != null) {
                    oldIdUsuarioOfCuentaCooperativa.setCuentaCooperativa(null);
                    oldIdUsuarioOfCuentaCooperativa = em.merge(oldIdUsuarioOfCuentaCooperativa);
                }
                cuentaCooperativaNew.setIdUsuario(usuario);
                cuentaCooperativaNew = em.merge(cuentaCooperativaNew);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                Usuario oldIdUsuarioOfCuenta = cuentaNew.getIdUsuario();
                if (oldIdUsuarioOfCuenta != null) {
                    oldIdUsuarioOfCuenta.setCuenta(null);
                    oldIdUsuarioOfCuenta = em.merge(oldIdUsuarioOfCuenta);
                }
                cuentaNew.setIdUsuario(usuario);
                cuentaNew = em.merge(cuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            CuentaCooperativa cuentaCooperativaOrphanCheck = usuario.getCuentaCooperativa();
            if (cuentaCooperativaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the CuentaCooperativa " + cuentaCooperativaOrphanCheck + " in its cuentaCooperativa field has a non-nullable idUsuario field.");
            }
            Cuenta cuentaOrphanCheck = usuario.getCuenta();
            if (cuentaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cuenta " + cuentaOrphanCheck + " in its cuenta field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
