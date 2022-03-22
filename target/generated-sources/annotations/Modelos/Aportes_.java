package Modelos;

import Modelos.Socios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-21T16:21:06")
@StaticMetamodel(Aportes.class)
public class Aportes_ { 

    public static volatile SingularAttribute<Aportes, Socios> idSocios;
    public static volatile SingularAttribute<Aportes, String> mensualidades;
    public static volatile SingularAttribute<Aportes, Integer> idAportes;
    public static volatile SingularAttribute<Aportes, Integer> cantidadAportes;
    public static volatile SingularAttribute<Aportes, Integer> numeroAportes;
    public static volatile SingularAttribute<Aportes, Boolean> esEliminado;
    public static volatile SingularAttribute<Aportes, Date> fechaAporte;

}