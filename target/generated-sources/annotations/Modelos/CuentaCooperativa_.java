package Modelos;

import Modelos.Reportes;
import Modelos.Socios;
import Modelos.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-25T23:27:17")
@StaticMetamodel(CuentaCooperativa.class)
public class CuentaCooperativa_ { 

    public static volatile SingularAttribute<CuentaCooperativa, Integer> idCuentaCooperativa;
    public static volatile SingularAttribute<CuentaCooperativa, Socios> idSocios;
    public static volatile SingularAttribute<CuentaCooperativa, String> nombreCuenta;
    public static volatile SingularAttribute<CuentaCooperativa, Usuario> idUsuario;
    public static volatile SingularAttribute<CuentaCooperativa, Reportes> reportes;
    public static volatile SingularAttribute<CuentaCooperativa, String> numeroCuenta;
    public static volatile SingularAttribute<CuentaCooperativa, Boolean> esEliminado;
    public static volatile SingularAttribute<CuentaCooperativa, String> codigoCuenta;

}