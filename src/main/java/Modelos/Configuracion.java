/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joelc
 */
@Entity
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c"),
    @NamedQuery(name = "Configuracion.findByIdConfiguracion", query = "SELECT c FROM Configuracion c WHERE c.idConfiguracion = :idConfiguracion"),
    @NamedQuery(name = "Configuracion.findByInteresCredito", query = "SELECT c FROM Configuracion c WHERE c.interesCredito = :interesCredito"),
    @NamedQuery(name = "Configuracion.findByMontoMaxCredito", query = "SELECT c FROM Configuracion c WHERE c.montoMaxCredito = :montoMaxCredito"),
    @NamedQuery(name = "Configuracion.findByPlazoMaxMeses", query = "SELECT c FROM Configuracion c WHERE c.plazoMaxMeses = :plazoMaxMeses")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_configuracion")
    private Integer idConfiguracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "interes_credito")
    private Double interesCredito;
    @Column(name = "monto_max_credito")
    private Double montoMaxCredito;
    @Column(name = "plazo_max_meses")
    private Integer plazoMaxMeses;

    public Configuracion() {
    }

    public Configuracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Double getInteresCredito() {
        return interesCredito;
    }

    public void setInteresCredito(Double interesCredito) {
        this.interesCredito = interesCredito;
    }

    public Double getMontoMaxCredito() {
        return montoMaxCredito;
    }

    public void setMontoMaxCredito(Double montoMaxCredito) {
        this.montoMaxCredito = montoMaxCredito;
    }

    public Integer getPlazoMaxMeses() {
        return plazoMaxMeses;
    }

    public void setPlazoMaxMeses(Integer plazoMaxMeses) {
        this.plazoMaxMeses = plazoMaxMeses;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Configuracion[ idConfiguracion=" + idConfiguracion + " ]";
    }
    
}
