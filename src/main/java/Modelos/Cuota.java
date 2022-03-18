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
@Table(name = "cuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuota.findAll", query = "SELECT c FROM Cuota c"),
    @NamedQuery(name = "Cuota.findByIdCuota", query = "SELECT c FROM Cuota c WHERE c.idCuota = :idCuota"),
    @NamedQuery(name = "Cuota.findByMontoMensual", query = "SELECT c FROM Cuota c WHERE c.montoMensual = :montoMensual"),
    @NamedQuery(name = "Cuota.findByEsEliminado", query = "SELECT c FROM Cuota c WHERE c.esEliminado = :esEliminado")})
public class Cuota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuota")
    private Integer idCuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_mensual")
    private Double montoMensual;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idCuota")
    private TasaAmortizacion tasaAmortizacion;

    public Cuota() {
    }

    public Cuota(Integer idCuota) {
        this.idCuota = idCuota;
    }

    public Integer getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(Integer idCuota) {
        this.idCuota = idCuota;
    }

    public Double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(Double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public TasaAmortizacion getTasaAmortizacion() {
        return tasaAmortizacion;
    }

    public void setTasaAmortizacion(TasaAmortizacion tasaAmortizacion) {
        this.tasaAmortizacion = tasaAmortizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuota != null ? idCuota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuota)) {
            return false;
        }
        Cuota other = (Cuota) object;
        if ((this.idCuota == null && other.idCuota != null) || (this.idCuota != null && !this.idCuota.equals(other.idCuota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Cuota[ idCuota=" + idCuota + " ]";
    }
    
}
