package Modelos;

import Modelos.Socios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-25T23:27:17")
@StaticMetamodel(Retiro.class)
public class Retiro_ { 

    public static volatile SingularAttribute<Retiro, Float> montoRetiro;
    public static volatile SingularAttribute<Retiro, Socios> codigoSocio;
    public static volatile SingularAttribute<Retiro, Date> fechaRetiro;
    public static volatile SingularAttribute<Retiro, Boolean> esEliminado;
    public static volatile SingularAttribute<Retiro, Integer> idRetiro;

}