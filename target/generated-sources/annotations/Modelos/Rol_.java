package Modelos;

import Modelos.Cuenta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T10:50:42")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Integer> idRol;
    public static volatile SingularAttribute<Rol, String> tipoRol;
    public static volatile SingularAttribute<Rol, Cuenta> cuenta;
    public static volatile SingularAttribute<Rol, Boolean> esEliminado;

}