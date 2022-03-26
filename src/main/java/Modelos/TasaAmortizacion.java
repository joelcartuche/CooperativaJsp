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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "TasaAmortizacion.findByNumeroPagos", query = "SELECT t FROM TasaAmortizacion t WHERE t.numeroPagos = :numeroPagos"),
    @NamedQuery(name = "TasaAmortizacion.findByEsEliminado", query = "SELECT t FROM TasaAmortizacion t WHERE t.esEliminado = :esEliminado"),
    @NamedQuery(name = "TasaAmortizacion.findByFormaPago", query = "SELECT t FROM TasaAmortizacion t WHERE t.formaPago = :formaPago"),
    @NamedQuery(name = "TasaAmortizacion.findByEsPagado", query = "SELECT t FROM TasaAmortizacion t WHERE t.esPagado = :esPagado"),
    @NamedQuery(name = "TasaAmortizacion.findByFechaPago", query = "SELECT t FROM TasaAmortizacion t WHERE t.fechaPago = :fechaPago"),
    @NamedQuery(name = "TasaAmortizacion.findByFechaCreacion", query = "SELECT t FROM TasaAmortizacion t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "TasaAmortizacion.findBySaldoDeuda", query = "SELECT t FROM TasaAmortizacion t WHERE t.saldoDeuda = :saldoDeuda"),
    @NamedQuery(name = "TasaAmortizacion.findByCuota", query = "SELECT t FROM TasaAmortizacion t WHERE t.cuota = :cuota"),
    @NamedQuery(name = "TasaAmortizacion.findByInteres", query = "SELECT t FROM TasaAmortizacion t WHERE t.interes = :interes"),
    @NamedQuery(name = "TasaAmortizacion.findByAmortizacion", query = "SELECT t FROM TasaAmortizacion t WHERE t.amortizacion = :amortizacion"),
@NamedQuery(name = "TasaAmortizacion.findByCodigoCredito", query = "SELECT t FROM TasaAmortizacion t WHERE t.codigoCredito = :codigoCredito AND t.esEliminado=:esEliminado"),

})

public class TasaAmortizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tasa_amortizacion")
    private Integer idTasaAmortizacion;
    @Column(name = "numero_pagos")
    private Integer numeroPagos;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "forma_pago")
    private String formaPago;
    @Column(name = "es_pagado")
    private boolean esPagado;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_deuda")
    private Double saldoDeuda;
    @Column(name = "cuota")
    private Double cuota;
    @Column(name = "interes")
    private Double interes;
    @Column(name = "amortizacion")
    private Double amortizacion;
    @JoinColumn(name = "codigo_credito", referencedColumnName = "codigo_credito")
    @ManyToOne(optional = false)
    private Credito codigoCredito;

    public TasaAmortizacion() {
    }

    public TasaAmortizacion(Integer idTasaAmortizacion) {
        this.idTasaAmortizacion = idTasaAmortizacion;
    }

    public TasaAmortizacion(Integer idTasaAmortizacion, String formaPago, boolean esPagado) {
        this.idTasaAmortizacion = idTasaAmortizacion;
        this.formaPago = formaPago;
        this.esPagado = esPagado;
    }

    public Integer getIdTasaAmortizacion() {
        return idTasaAmortizacion;
    }

    public void setIdTasaAmortizacion(Integer idTasaAmortizacion) {
        this.idTasaAmortizacion = idTasaAmortizacion;
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public boolean getEsPagado() {
        return esPagado;
    }

    public void setEsPagado(boolean esPagado) {
        this.esPagado = esPagado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Double getSaldoDeuda() {
        return saldoDeuda;
    }

    public void setSaldoDeuda(Double saldoDeuda) {
        this.saldoDeuda = saldoDeuda;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(Double amortizacion) {
        this.amortizacion = amortizacion;
    }

    public Credito getCodigoCredito() {
        return codigoCredito;
    }

    public void setCodigoCredito(Credito codigoCredito) {
        this.codigoCredito = codigoCredito;
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
