package Modelos;

import Modelos.Cuenta;
import Modelos.CuentaCooperativa;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T10:50:42")
=======
@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T19:22:42")
>>>>>>> Developer
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> cedulaUsuario;
    public static volatile SingularAttribute<Usuario, String> telefonoUsuario;
    public static volatile SingularAttribute<Usuario, String> apellidoUsuario;
    public static volatile SingularAttribute<Usuario, Integer> idUsuario;
    public static volatile SingularAttribute<Usuario, Cuenta> cuenta;
    public static volatile SingularAttribute<Usuario, CuentaCooperativa> cuentaCooperativa;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile SingularAttribute<Usuario, Boolean> esEliminado;

}