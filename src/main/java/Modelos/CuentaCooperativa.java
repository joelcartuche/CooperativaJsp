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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joelc
 */
@Entity
@Table(name = "cuenta_cooperativa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaCooperativa.findAll", query = "SELECT c FROM CuentaCooperativa c"),
    @NamedQuery(name = "CuentaCooperativa.findByIdCuentaCooperativa", query = "SELECT c FROM CuentaCooperativa c WHERE c.idCuentaCooperativa = :idCuentaCooperativa"),
    @NamedQuery(name = "CuentaCooperativa.findByNumeroCuenta", query = "SELECT c FROM CuentaCooperativa c WHERE c.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "CuentaCooperativa.findByNombreCuenta", query = "SELECT c FROM CuentaCooperativa c WHERE c.nombreCuenta = :nombreCuenta"),
    @NamedQuery(name = "CuentaCooperativa.findByCodigoCuenta", query = "SELECT c FROM CuentaCooperativa c WHERE c.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "CuentaCooperativa.findByEsEliminado", query = "SELECT c FROM CuentaCooperativa c WHERE c.esEliminado = :esEliminado")})
public class CuentaCooperativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta_cooperativa")
    private Integer idCuentaCooperativa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_cuenta")
    private String nombreCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_cuenta")
    private String codigoCuenta;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "id_socios", referencedColumnName = "id_socios")
    @OneToOne(optional = false)
    private Socios idSocios;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idCuenta")
    private Reportes reportes;

    public CuentaCooperativa() {
        this.esEliminado=false;
    }

    public CuentaCooperativa(Integer idCuentaCooperativa) {
        this.idCuentaCooperativa = idCuentaCooperativa;
    }

    public CuentaCooperativa(Integer idCuentaCooperativa, String numeroCuenta, String nombreCuenta, String codigoCuenta) {
        this.idCuentaCooperativa = idCuentaCooperativa;
        this.numeroCuenta = numeroCuenta;
        this.nombreCuenta = nombreCuenta;
        this.codigoCuenta = codigoCuenta;
    }

    public Integer getIdCuentaCooperativa() {
        return idCuentaCooperativa;
    }

    public void setIdCuentaCooperativa(Integer idCuentaCooperativa) {
        this.idCuentaCooperativa = idCuentaCooperativa;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
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

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Reportes getReportes() {
        return reportes;
    }

    public void setReportes(Reportes reportes) {
        this.reportes = reportes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuentaCooperativa != null ? idCuentaCooperativa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaCooperativa)) {
            return false;
        }
        CuentaCooperativa other = (CuentaCooperativa) object;
        if ((this.idCuentaCooperativa == null && other.idCuentaCooperativa != null) || (this.idCuentaCooperativa != null && !this.idCuentaCooperativa.equals(other.idCuentaCooperativa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.CuentaCooperativa[ idCuentaCooperativa=" + idCuentaCooperativa + " ]";
    }
    
}
