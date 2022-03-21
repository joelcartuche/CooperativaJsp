/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tasa_amortizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TasaAmortizacion.findAll", query = "SELECT t FROM TasaAmortizacion t"),
    @NamedQuery(name = "TasaAmortizacion.findByIdTasaAmortizacion", query = "SELECT t FROM TasaAmortizacion t WHERE t.idTasaAmortizacion = :idTasaAmortizacion"),
    @NamedQuery(name = "TasaAmortizacion.findByMontoPagar", query = "SELECT t FROM TasaAmortizacion t WHERE t.montoPagar = :montoPagar"),
    @NamedQuery(name = "TasaAmortizacion.findByNumeroPagos", query = "SELECT t FROM TasaAmortizacion t WHERE t.numeroPagos = :numeroPagos"),
    @NamedQuery(name = "TasaAmortizacion.findByEsEliminado", query = "SELECT t FROM TasaAmortizacion t WHERE t.esEliminado = :esEliminado")})
public class TasaAmortizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tasa_amortizacion")
    private Integer idTasaAmortizacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_pagar")
    private Double montoPagar;
    @Column(name = "numero_pagos")
    private Integer numeroPagos;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "id_cuota", referencedColumnName = "id_cuota")
    @OneToOne(optional = false)
    private Cuota idCuota;
    @JoinColumn(name = "id_pago", referencedColumnName = "id_pago")
    @OneToOne(optional = false)
    private Pago idPago;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idTasaAmortizacion")
    private Credito credito;

    public TasaAmortizacion() {
        this.esEliminado=false;
    }

    public TasaAmortizacion(Integer idTasaAmortizacion) {
        this.idTasaAmortizacion = idTasaAmortizacion;
    }

    public Integer getIdTasaAmortizacion() {
        return idTasaAmortizacion;
    }

    public void setIdTasaAmortizacion(Integer idTasaAmortizacion) {
        this.idTasaAmortizacion = idTasaAmortizacion;
    }

    public Double getMontoPagar() {
        return montoPagar;
    }

    public void setMontoPagar(Double montoPagar) {
        this.montoPagar = montoPagar;
    }

    public Integer getNumeroPagos() {
        return numeroPagos;
    }

    public void setNumeroPagos(Integer numeroPagos) {
        this.numeroPagos = numeroPagos;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public Cuota getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(Cuota idCuota) {
        this.idCuota = idCuota;
    }

    public Pago getIdPago() {
        return idPago;
    }

    public void setIdPago(Pago idPago) {
        this.idPago = idPago;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTasaAmortizacion != null ? idTasaAmortizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TasaAmortizacion)) {
            return false;
        }
        TasaAmortizacion other = (TasaAmortizacion) object;
        if ((this.idTasaAmortizacion == null && other.idTasaAmortizacion != null) || (this.idTasaAmortizacion != null && !this.idTasaAmortizacion.equals(other.idTasaAmortizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TasaAmortizacion[ idTasaAmortizacion=" + idTasaAmortizacion + " ]";
    }
    
}
