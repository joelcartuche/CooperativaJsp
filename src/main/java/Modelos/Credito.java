/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credito.findAll", query = "SELECT c FROM Credito c"),
    @NamedQuery(name = "Credito.findByIdCredito", query = "SELECT c FROM Credito c WHERE c.idCredito = :idCredito"),
    @NamedQuery(name = "Credito.findByMontoCredito", query = "SELECT c FROM Credito c WHERE c.montoCredito = :montoCredito"),
    @NamedQuery(name = "Credito.findByInteresCredito", query = "SELECT c FROM Credito c WHERE c.interesCredito = :interesCredito"),
    @NamedQuery(name = "Credito.findByEsEliminado", query = "SELECT c FROM Credito c WHERE c.esEliminado = :esEliminado"),
    @NamedQuery(name = "Credito.findByCodigoCredito", query = "SELECT c FROM Credito c WHERE c.codigoCredito = :codigoCredito"),
@NamedQuery(name = "Credito.findBySocio", query = "SELECT c FROM Credito c WHERE c.idCodigoSocio = :socio")})
public class Credito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_credito")
    private Integer idCredito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_credito")
    private Float montoCredito;
    @Column(name = "interes_credito")
    private Double interesCredito;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_credito")
    private int codigoCredito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCredito")
    private Collection<TasaAmortizacion> tasaAmortizacionCollection;
    @JoinColumn(name = "id_codigo_socio", referencedColumnName = "codigo_socio")
    @ManyToOne(optional = false)
    private Socios idCodigoSocio;

    public Credito() {
    }

    public Credito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public Credito(Integer idCredito, int codigoCredito) {
        this.idCredito = idCredito;
        this.codigoCredito = codigoCredito;
    }

    public Integer getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public Float getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(Float montoCredito) {
        this.montoCredito = montoCredito;
    }

    public Double getInteresCredito() {
        return interesCredito;
    }

    public void setInteresCredito(Double interesCredito) {
        this.interesCredito = interesCredito;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public int getCodigoCredito() {
        return codigoCredito;
    }

    public void setCodigoCredito(int codigoCredito) {
        this.codigoCredito = codigoCredito;
    }

    @XmlTransient
    public Collection<TasaAmortizacion> getTasaAmortizacionCollection() {
        return tasaAmortizacionCollection;
    }

    public void setTasaAmortizacionCollection(Collection<TasaAmortizacion> tasaAmortizacionCollection) {
        this.tasaAmortizacionCollection = tasaAmortizacionCollection;
    }

    public Socios getIdCodigoSocio() {
        return idCodigoSocio;
    }

    public void setIdCodigoSocio(Socios idCodigoSocio) {
        this.idCodigoSocio = idCodigoSocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCredito != null ? idCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.idCredito == null && other.idCredito != null) || (this.idCredito != null && !this.idCredito.equals(other.idCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Credito[ idCredito=" + idCredito + " ]";
    }
    
}
