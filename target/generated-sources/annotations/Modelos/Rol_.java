package Modelos;

import Modelos.Cuenta;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-24T13:53:07")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Integer> idRol;
    public static volatile SingularAttribute<Rol, String> tipoRol;
    public static volatile SingularAttribute<Rol, Boolean> esEliminado;
    public static volatile CollectionAttribute<Rol, Cuenta> cuentaCollection;

}