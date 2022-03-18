package Modelos;

import Modelos.Aportes;
import Modelos.Credito;
import Modelos.CuentaCooperativa;
import Modelos.Deposito;
import Modelos.Retiro;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T10:50:42")
@StaticMetamodel(Socios.class)
public class Socios_ { 

    public static volatile SingularAttribute<Socios, String> apellidoSocio;
    public static volatile SingularAttribute<Socios, String> direccionSocio;
    public static volatile SingularAttribute<Socios, String> telefonoSocio;
    public static volatile SingularAttribute<Socios, Integer> idSocios;
    public static volatile SingularAttribute<Socios, String> cedulaSocio;
    public static volatile SingularAttribute<Socios, Credito> credito;
    public static volatile SingularAttribute<Socios, CuentaCooperativa> cuentaCooperativa;
    public static volatile SingularAttribute<Socios, Deposito> deposito;
    public static volatile SingularAttribute<Socios, Retiro> retiro;
    public static volatile SingularAttribute<Socios, String> nombreSocio;
    public static volatile SingularAttribute<Socios, Boolean> esEliminado;
    public static volatile SingularAttribute<Socios, Aportes> aportes;

}