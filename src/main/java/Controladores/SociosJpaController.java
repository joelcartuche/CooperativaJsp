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
import Modelos.Deposito;
import Modelos.Retiro;
import Modelos.Credito;
import Modelos.Aportes;
import Modelos.Socios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joelc
 */
public class SociosJpaController implements Serializable {

    public SociosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistece_cooperativa");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SociosJpaController() {
    }
    
    

    public void create(Socios socios) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Credito idCreditoOrphanCheck = socios.getIdCredito();
        if (idCreditoOrphanCheck != null) {
            Socios oldSociosOfIdCredito = idCreditoOrphanCheck.getSocios();
            if (oldSociosOfIdCredito != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Credito " + idCreditoOrphanCheck + " already has an item of type Socios whose idCredito column cannot be null. Please make another selection for the idCredito field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCooperativa cuentaCooperativa = socios.getCuentaCooperativa();
            if (cuentaCooperativa != null) {
                cuentaCooperativa = em.getReference(cuentaCooperativa.getClass(), cuentaCooperativa.getIdCuentaCooperativa());
                socios.setCuentaCooperativa(cuentaCooperativa);
            }
            Deposito deposito = socios.getDeposito();
            if (deposito != null) {
                deposito = em.getReference(deposito.getClass(), deposito.getIdDeposito());
                socios.setDeposito(deposito);
            }
            Retiro retiro = socios.getRetiro();
            if (retiro != null) {
                retiro = em.getReference(retiro.getClass(), retiro.getIdRetiro());
                socios.setRetiro(retiro);
            }
            Credito idCredito = socios.getIdCredito();
            if (idCredito != null) {
                idCredito = em.getReference(idCredito.getClass(), idCredito.getIdCredito());
                socios.setIdCredito(idCredito);
            }
            Aportes aportes = socios.getAportes();
            if (aportes != null) {
                aportes = em.getReference(aportes.getClass(), aportes.getIdAportes());
                socios.setAportes(aportes);
            }
            em.persist(socios);
            if (cuentaCooperativa != null) {
                Socios oldIdSociosOfCuentaCooperativa = cuentaCooperativa.getIdSocios();
                if (oldIdSociosOfCuentaCooperativa != null) {
                    oldIdSociosOfCuentaCooperativa.setCuentaCooperativa(null);
                    oldIdSociosOfCuentaCooperativa = em.merge(oldIdSociosOfCuentaCooperativa);
                }
                cuentaCooperativa.setIdSocios(socios);
                cuentaCooperativa = em.merge(cuentaCooperativa);
            }
            if (deposito != null) {
                Socios oldIdSociosOfDeposito = deposito.getIdSocios();
                if (oldIdSociosOfDeposito != null) {
                    oldIdSociosOfDeposito.setDeposito(null);
                    oldIdSociosOfDeposito = em.merge(oldIdSociosOfDeposito);
                }
                deposito.setIdSocios(socios);
                deposito = em.merge(deposito);
            }
            if (retiro != null) {
                Socios oldIdSociosOfRetiro = retiro.getIdSocios();
                if (oldIdSociosOfRetiro != null) {
                    oldIdSociosOfRetiro.setRetiro(null);
                    oldIdSociosOfRetiro = em.merge(oldIdSociosOfRetiro);
                }
                retiro.setIdSocios(socios);
                retiro = em.merge(retiro);
            }
            if (idCredito != null) {
                idCredito.setSocios(socios);
                idCredito = em.merge(idCredito);
            }
            if (aportes != null) {
                Socios oldIdSociosOfAportes = aportes.getIdSocios();
                if (oldIdSociosOfAportes != null) {
                    oldIdSociosOfAportes.setAportes(null);
                    oldIdSociosOfAportes = em.merge(oldIdSociosOfAportes);
                }
                aportes.setIdSocios(socios);
                aportes = em.merge(aportes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Socios socios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios persistentSocios = em.find(Socios.class, socios.getIdSocios());
            CuentaCooperativa cuentaCooperativaOld = persistentSocios.getCuentaCooperativa();
            CuentaCooperativa cuentaCooperativaNew = socios.getCuentaCooperativa();
            Deposito depositoOld = persistentSocios.getDeposito();
            Deposito depositoNew = socios.getDeposito();
            Retiro retiroOld = persistentSocios.getRetiro();
            Retiro retiroNew = socios.getRetiro();
            Credito idCreditoOld = persistentSocios.getIdCredito();
            Credito idCreditoNew = socios.getIdCredito();
            Aportes aportesOld = persistentSocios.getAportes();
            Aportes aportesNew = socios.getAportes();
            List<String> illegalOrphanMessages = null;
            if (cuentaCooperativaOld != null && !cuentaCooperativaOld.equals(cuentaCooperativaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain CuentaCooperativa " + cuentaCooperativaOld + " since its idSocios field is not nullable.");
            }
            if (depositoOld != null && !depositoOld.equals(depositoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Deposito " + depositoOld + " since its idSocios field is not nullable.");
            }
            if (retiroOld != null && !retiroOld.equals(retiroNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Retiro " + retiroOld + " since its idSocios field is not nullable.");
            }
            if (idCreditoNew != null && !idCreditoNew.equals(idCreditoOld)) {
                Socios oldSociosOfIdCredito = idCreditoNew.getSocios();
                if (oldSociosOfIdCredito != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Credito " + idCreditoNew + " already has an item of type Socios whose idCredito column cannot be null. Please make another selection for the idCredito field.");
                }
            }
            if (aportesOld != null && !aportesOld.equals(aportesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Aportes " + aportesOld + " since its idSocios field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentaCooperativaNew != null) {
                cuentaCooperativaNew = em.getReference(cuentaCooperativaNew.getClass(), cuentaCooperativaNew.getIdCuentaCooperativa());
                socios.setCuentaCooperativa(cuentaCooperativaNew);
            }
            if (depositoNew != null) {
                depositoNew = em.getReference(depositoNew.getClass(), depositoNew.getIdDeposito());
                socios.setDeposito(depositoNew);
            }
            if (retiroNew != null) {
                retiroNew = em.getReference(retiroNew.getClass(), retiroNew.getIdRetiro());
                socios.setRetiro(retiroNew);
            }
            if (idCreditoNew != null) {
                idCreditoNew = em.getReference(idCreditoNew.getClass(), idCreditoNew.getIdCredito());
                socios.setIdCredito(idCreditoNew);
            }
            if (aportesNew != null) {
                aportesNew = em.getReference(aportesNew.getClass(), aportesNew.getIdAportes());
                socios.setAportes(aportesNew);
            }
            socios = em.merge(socios);
            if (cuentaCooperativaNew != null && !cuentaCooperativaNew.equals(cuentaCooperativaOld)) {
                Socios oldIdSociosOfCuentaCooperativa = cuentaCooperativaNew.getIdSocios();
                if (oldIdSociosOfCuentaCooperativa != null) {
                    oldIdSociosOfCuentaCooperativa.setCuentaCooperativa(null);
                    oldIdSociosOfCuentaCooperativa = em.merge(oldIdSociosOfCuentaCooperativa);
                }
                cuentaCooperativaNew.setIdSocios(socios);
                cuentaCooperativaNew = em.merge(cuentaCooperativaNew);
            }
            if (depositoNew != null && !depositoNew.equals(depositoOld)) {
                Socios oldIdSociosOfDeposito = depositoNew.getIdSocios();
                if (oldIdSociosOfDeposito != null) {
                    oldIdSociosOfDeposito.setDeposito(null);
                    oldIdSociosOfDeposito = em.merge(oldIdSociosOfDeposito);
                }
                depositoNew.setIdSocios(socios);
                depositoNew = em.merge(depositoNew);
            }
            if (retiroNew != null && !retiroNew.equals(retiroOld)) {
                Socios oldIdSociosOfRetiro = retiroNew.getIdSocios();
                if (oldIdSociosOfRetiro != null) {
                    oldIdSociosOfRetiro.setRetiro(null);
                    oldIdSociosOfRetiro = em.merge(oldIdSociosOfRetiro);
                }
                retiroNew.setIdSocios(socios);
                retiroNew = em.merge(retiroNew);
            }
            if (idCreditoOld != null && !idCreditoOld.equals(idCreditoNew)) {
                idCreditoOld.setSocios(null);
                idCreditoOld = em.merge(idCreditoOld);
            }
            if (idCreditoNew != null && !idCreditoNew.equals(idCreditoOld)) {
                idCreditoNew.setSocios(socios);
                idCreditoNew = em.merge(idCreditoNew);
            }
            if (aportesNew != null && !aportesNew.equals(aportesOld)) {
                Socios oldIdSociosOfAportes = aportesNew.getIdSocios();
                if (oldIdSociosOfAportes != null) {
                    oldIdSociosOfAportes.setAportes(null);
                    oldIdSociosOfAportes = em.merge(oldIdSociosOfAportes);
                }
                aportesNew.setIdSocios(socios);
                aportesNew = em.merge(aportesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = socios.getIdSocios();
                if (findSocios(id) == null) {
                    throw new NonexistentEntityException("The socios with id " + id + " no longer exists.");
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
            Socios socios;
            try {
                socios = em.getReference(Socios.class, id);
                socios.getIdSocios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The socios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            CuentaCooperativa cuentaCooperativaOrphanCheck = socios.getCuentaCooperativa();
            if (cuentaCooperativaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the CuentaCooperativa " + cuentaCooperativaOrphanCheck + " in its cuentaCooperativa field has a non-nullable idSocios field.");
            }
            Deposito depositoOrphanCheck = socios.getDeposito();
            if (depositoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Deposito " + depositoOrphanCheck + " in its deposito field has a non-nullable idSocios field.");
            }
            Retiro retiroOrphanCheck = socios.getRetiro();
            if (retiroOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Retiro " + retiroOrphanCheck + " in its retiro field has a non-nullable idSocios field.");
            }
            Aportes aportesOrphanCheck = socios.getAportes();
            if (aportesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Aportes " + aportesOrphanCheck + " in its aportes field has a non-nullable idSocios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Credito idCredito = socios.getIdCredito();
            if (idCredito != null) {
                idCredito.setSocios(null);
                idCredito = em.merge(idCredito);
            }
            em.remove(socios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Socios> findSociosEntities() {
        return findSociosEntities(true, -1, -1);
    }

    public List<Socios> findSociosEntities(int maxResults, int firstResult) {
        return findSociosEntities(false, maxResults, firstResult);
    }

    private List<Socios> findSociosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Socios.class));
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

    public Socios findSocios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Socios.class, id);
        } finally {
            em.close();
        }
    }

    public int getSociosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Socios> rt = cq.from(Socios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
