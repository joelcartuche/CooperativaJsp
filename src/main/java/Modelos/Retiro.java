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
@Table(name = "retiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retiro.findAll", query = "SELECT r FROM Retiro r"),
    @NamedQuery(name = "Retiro.findByIdRetiro", query = "SELECT r FROM Retiro r WHERE r.idRetiro = :idRetiro"),
    @NamedQuery(name = "Retiro.findByMontoRetiro", query = "SELECT r FROM Retiro r WHERE r.montoRetiro = :montoRetiro"),
    @NamedQuery(name = "Retiro.findByFechaRetiro", query = "SELECT r FROM Retiro r WHERE r.fechaRetiro = :fechaRetiro"),
    @NamedQuery(name = "Retiro.findByEsEliminado", query = "SELECT r FROM Retiro r WHERE r.esEliminado = :esEliminado")})
public class Retiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_retiro")
    private Integer idRetiro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_retiro")
    private float montoRetiro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_retiro")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "id_socios", referencedColumnName = "id_socios")
    @OneToOne(optional = false)
    private Socios idSocios;

    public Retiro() {
    }

    public Retiro(Integer idRetiro) {
        this.idRetiro = idRetiro;
    }

    public Retiro(Integer idRetiro, float montoRetiro, Date fechaRetiro) {
        this.idRetiro = idRetiro;
        this.montoRetiro = montoRetiro;
        this.fechaRetiro = fechaRetiro;
    }

    public Integer getIdRetiro() {
        return idRetiro;
    }

    public void setIdRetiro(Integer idRetiro) {
        this.idRetiro = idRetiro;
    }

    public float getMontoRetiro() {
        return montoRetiro;
    }

    public void setMontoRetiro(float montoRetiro) {
        this.montoRetiro = montoRetiro;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
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
        hash += (idRetiro != null ? idRetiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retiro)) {
            return false;
        }
        Retiro other = (Retiro) object;
        if ((this.idRetiro == null && other.idRetiro != null) || (this.idRetiro != null && !this.idRetiro.equals(other.idRetiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Retiro[ idRetiro=" + idRetiro + " ]";
    }
    
}
