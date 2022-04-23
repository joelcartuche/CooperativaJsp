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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "aportes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aportes.findAll", query = "SELECT a FROM Aportes a"),
    @NamedQuery(name = "Aportes.findByIdAportes", query = "SELECT a FROM Aportes a WHERE a.idAportes = :idAportes"),
    @NamedQuery(name = "Aportes.findByMensualidades", query = "SELECT a FROM Aportes a WHERE a.mensualidades = :mensualidades"),
    @NamedQuery(name = "Aportes.findByCantidadAportes", query = "SELECT a FROM Aportes a WHERE a.cantidadAportes = :cantidadAportes"),
    @NamedQuery(name = "Aportes.findByNumeroAportes", query = "SELECT a FROM Aportes a WHERE a.numeroAportes = :numeroAportes"),
    @NamedQuery(name = "Aportes.findByFechaAporte", query = "SELECT a FROM Aportes a WHERE a.fechaAporte = :fechaAporte"),
    @NamedQuery(name = "Aportes.findByEsEliminado", query = "SELECT a FROM Aportes a WHERE a.esEliminado = :esEliminado")})
public class Aportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aportes")
    private Integer idAportes;
    @Size(max = 200)
    @Column(name = "mensualidades")
    private String mensualidades;
    @Column(name = "cantidad_aportes")
    private Integer cantidadAportes;
    @Column(name = "numero_aportes")
    private Integer numeroAportes;
    @Column(name = "fecha_aporte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAporte;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "codigo_socio", referencedColumnName = "codigo_socio")
    @ManyToOne(optional = false)
    private Socios codigoSocio;

    public Aportes() {
    }

    public Aportes(Integer idAportes) {
        this.idAportes = idAportes;
    }

    public Integer getIdAportes() {
        return idAportes;
    }

    public void setIdAportes(Integer idAportes) {
        this.idAportes = idAportes;
    }

    public String getMensualidades() {
        return mensualidades;
    }

    public void setMensualidades(String mensualidades) {
        this.mensualidades = mensualidades;
    }

    public Integer getCantidadAportes() {
        return cantidadAportes;
    }

    public void setCantidadAportes(Integer cantidadAportes) {
        this.cantidadAportes = cantidadAportes;
    }

    public Integer getNumeroAportes() {
        return numeroAportes;
    }

    public void setNumeroAportes(Integer numeroAportes) {
        this.numeroAportes = numeroAportes;
    }

    public Date getFechaAporte() {
        return fechaAporte;
    }

    public void setFechaAporte(Date fechaAporte) {
        this.fechaAporte = fechaAporte;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public Socios getCodigoSocio() {
        return codigoSocio;
    }

    public void setCodigoSocio(Socios codigoSocio) {
        this.codigoSocio = codigoSocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAportes != null ? idAportes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aportes)) {
            return false;
        }
        Aportes other = (Aportes) object;
        if ((this.idAportes == null && other.idAportes != null) || (this.idAportes != null && !this.idAportes.equals(other.idAportes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Aportes[ idAportes=" + idAportes + " ]";
    }
    
}
