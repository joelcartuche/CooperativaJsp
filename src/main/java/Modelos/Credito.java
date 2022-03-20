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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joelc
 */
@Entity
@Table(name = "credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credito.findAll", query = "SELECT c FROM Credito c"),
    @NamedQuery(name = "Credito.findByIdCredito", query = "SELECT c FROM Credito c WHERE c.idCredito = :idCredito"),
    @NamedQuery(name = "Credito.findByMontoCredito", query = "SELECT c FROM Credito c WHERE c.montoCredito = :montoCredito"),
    @NamedQuery(name = "Credito.findByInteresCredito", query = "SELECT c FROM Credito c WHERE c.interesCredito = :interesCredito"),
    @NamedQuery(name = "Credito.findByEsEliminado", query = "SELECT c FROM Credito c WHERE c.esEliminado = :esEliminado")})
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
    @JoinColumn(name = "id_socios", referencedColumnName = "id_socios")
    @OneToOne(optional = false)
    private Socios idSocios;
    @JoinColumn(name = "id_tasa_amortizacion", referencedColumnName = "id_tasa_amortizacion")
    @OneToOne(optional = false)
    private TasaAmortizacion idTasaAmortizacion;

    public Credito() {
        this.esEliminado=false;
    }

    public Credito(Integer idCredito) {
        this.idCredito = idCredito;
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

    public Socios getIdSocios() {
        return idSocios;
    }

    public void setIdSocios(Socios idSocios) {
        this.idSocios = idSocios;
    }

    public TasaAmortizacion getIdTasaAmortizacion() {
        return idTasaAmortizacion;
    }

    public void setIdTasaAmortizacion(TasaAmortizacion idTasaAmortizacion) {
        this.idTasaAmortizacion = idTasaAmortizacion;
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
