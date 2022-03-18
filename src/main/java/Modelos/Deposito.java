/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joelc
 */
@Entity
@Table(name = "deposito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deposito.findAll", query = "SELECT d FROM Deposito d"),
    @NamedQuery(name = "Deposito.findByIdDeposito", query = "SELECT d FROM Deposito d WHERE d.idDeposito = :idDeposito"),
    @NamedQuery(name = "Deposito.findByMontoDeposito", query = "SELECT d FROM Deposito d WHERE d.montoDeposito = :montoDeposito"),
    @NamedQuery(name = "Deposito.findByFechaDeposito", query = "SELECT d FROM Deposito d WHERE d.fechaDeposito = :fechaDeposito"),
    @NamedQuery(name = "Deposito.findByEsEliminado", query = "SELECT d FROM Deposito d WHERE d.esEliminado = :esEliminado")})
public class Deposito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deposito")
    private Integer idDeposito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_deposito")
    private float montoDeposito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_deposito")
    @Temporal(TemporalType.DATE)
    private Date fechaDeposito;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "id_socios", referencedColumnName = "id_socios")
    @OneToOne(optional = false)
    private Socios idSocios;

    public Deposito() {
    }

    public Deposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public Deposito(Integer idDeposito, float montoDeposito, Date fechaDeposito) {
        this.idDeposito = idDeposito;
        this.montoDeposito = montoDeposito;
        this.fechaDeposito = fechaDeposito;
    }

    public Integer getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public float getMontoDeposito() {
        return montoDeposito;
    }

    public void setMontoDeposito(float montoDeposito) {
        this.montoDeposito = montoDeposito;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public Socios getIdSocios() {
        return idSocios;
    }

    public void setIdSocios(Socios idSocios) {
        this.idSocios = idSocios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeposito != null ? idDeposito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deposito)) {
            return false;
        }
        Deposito other = (Deposito) object;
        if ((this.idDeposito == null && other.idDeposito != null) || (this.idDeposito != null && !this.idDeposito.equals(other.idDeposito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Deposito[ idDeposito=" + idDeposito + " ]";
    }
    
}
