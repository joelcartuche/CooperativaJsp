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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joelc
 */
@Entity
@Table(name = "socios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Socios.findAll", query = "SELECT s FROM Socios s"),
    @NamedQuery(name = "Socios.findByIdSocios", query = "SELECT s FROM Socios s WHERE s.idSocios = :idSocios"),
    @NamedQuery(name = "Socios.findByNombreSocio", query = "SELECT s FROM Socios s WHERE s.nombreSocio = :nombreSocio"),
    @NamedQuery(name = "Socios.findByApellidoSocio", query = "SELECT s FROM Socios s WHERE s.apellidoSocio = :apellidoSocio"),
    @NamedQuery(name = "Socios.findByCedulaSocio", query = "SELECT s FROM Socios s WHERE s.cedulaSocio = :cedulaSocio"),
    @NamedQuery(name = "Socios.findByTelefonoSocio", query = "SELECT s FROM Socios s WHERE s.telefonoSocio = :telefonoSocio"),
    @NamedQuery(name = "Socios.findByDireccionSocio", query = "SELECT s FROM Socios s WHERE s.direccionSocio = :direccionSocio"),
    @NamedQuery(name = "Socios.findByEsEliminado", query = "SELECT s FROM Socios s WHERE s.esEliminado = :esEliminado")})
public class Socios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_socios")
    private Integer idSocios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre_socio")
    private String nombreSocio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "apellido_socio")
    private String apellidoSocio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "cedula_socio")
    private String cedulaSocio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "telefono_socio")
    private String telefonoSocio;
    @Size(max = 300)
    @Column(name = "direccion_socio")
    private String direccionSocio;
    @Column(name = "es_eliminado")
    private Boolean esEliminado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idSocios")
    private Credito credito;

    public Socios() {
    }

    public Socios(Integer idSocios) {
        this.idSocios = idSocios;
    }

    public Socios(Integer idSocios, String nombreSocio, String apellidoSocio, String cedulaSocio, String telefonoSocio) {
        this.idSocios = idSocios;
        this.nombreSocio = nombreSocio;
        this.apellidoSocio = apellidoSocio;
        this.cedulaSocio = cedulaSocio;
        this.telefonoSocio = telefonoSocio;
    }

    public Integer getIdSocios() {
        return idSocios;
    }

    public void setIdSocios(Integer idSocios) {
        this.idSocios = idSocios;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getApellidoSocio() {
        return apellidoSocio;
    }

    public void setApellidoSocio(String apellidoSocio) {
        this.apellidoSocio = apellidoSocio;
    }

    public String getCedulaSocio() {
        return cedulaSocio;
    }

    public void setCedulaSocio(String cedulaSocio) {
        this.cedulaSocio = cedulaSocio;
    }

    public String getTelefonoSocio() {
        return telefonoSocio;
    }

    public void setTelefonoSocio(String telefonoSocio) {
        this.telefonoSocio = telefonoSocio;
    }

    public String getDireccionSocio() {
        return direccionSocio;
    }

    public void setDireccionSocio(String direccionSocio) {
        this.direccionSocio = direccionSocio;
    }

    public Boolean getEsEliminado() {
        return esEliminado;
    }

    public void setEsEliminado(Boolean esEliminado) {
        this.esEliminado = esEliminado;
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
        hash += (idSocios != null ? idSocios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Socios)) {
            return false;
        }
        Socios other = (Socios) object;
        if ((this.idSocios == null && other.idSocios != null) || (this.idSocios != null && !this.idSocios.equals(other.idSocios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Socios[ idSocios=" + idSocios + " ]";
    }
    
}
