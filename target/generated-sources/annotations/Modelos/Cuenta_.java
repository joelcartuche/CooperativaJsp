package Modelos;

import Modelos.Rol;
import Modelos.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T19:22:42")
@StaticMetamodel(Cuenta.class)
public class Cuenta_ { 

    public static volatile SingularAttribute<Cuenta, String> password;
    public static volatile SingularAttribute<Cuenta, Rol> idRol;
    public static volatile SingularAttribute<Cuenta, Usuario> idUsuario;
    public static volatile SingularAttribute<Cuenta, String> usuario;
    public static volatile SingularAttribute<Cuenta, Integer> idCuenta;
    public static volatile SingularAttribute<Cuenta, Boolean> esEliminado;

}