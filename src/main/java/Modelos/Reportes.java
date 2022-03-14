/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "reportes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reportes.findAll", query = "SELECT r FROM Reportes r"),
    @NamedQuery(name = "Reportes.findByIdReportes", query = "SELECT r FROM Reportes r WHERE r.idReportes = :idReportes"),
    @NamedQuery(name = "Reportes.findByResumenAportes", query = "SELECT r FROM Reportes r WHERE r.resumenAportes = :resumenAportes"),
    @NamedQuery(name = "Reportes.findByBalanceGeneral", query = "SELECT r FROM Reportes r WHERE r.balanceGeneral = :balanceGeneral"),
    @NamedQuery(name = "Reportes.findByEsEliminado", query = "SELECT r FROM Reportes r WHERE r.esEliminado = :esEliminado")})
public class Reportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_reportes")
    private Integer idReportes;
    @Size(max = 200)
    @Column(name = "resumen_aportes")
    private String resumenAportes;
    @Size(max = 200)
    @Column(name = "balance_general")
    private String balanceGeneral;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta_cooperativa")
    @OneToOne(optional = false)
    private CuentaCooperativa idCuenta;

    public Reportes() {
        this.esEliminado=false;
    }

    public Reportes(Integer idReportes) {
        this.idReportes = idReportes;
    }

    public Integer getIdReportes() {
        return idReportes;
    }

    public void setIdReportes(Integer idReportes) {
        this.idReportes = idReportes;
    }

    public String getResumenAportes() {
        return resumenAportes;
    }

    public void setResumenAportes(String resumenAportes) {
        this.resumenAportes = resumenAportes;
    }

    public String getBalanceGeneral() {
        return balanceGeneral;
    }

    public void setBalanceGeneral(String balanceGeneral) {
        this.balanceGeneral = balanceGeneral;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
    }

    public CuentaCooperativa getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(CuentaCooperativa idCuenta) {
        this.idCuenta = idCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReportes != null ? idReportes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reportes)) {
            return false;
        }
        Reportes other = (Reportes) object;
        if ((this.idReportes == null && other.idReportes != null) || (this.idReportes != null && !this.idReportes.equals(other.idReportes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Reportes[ idReportes=" + idReportes + " ]";
    }
    
}
