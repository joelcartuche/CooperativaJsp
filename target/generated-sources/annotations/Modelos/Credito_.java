package Modelos;

import Modelos.Socios;
import Modelos.TasaAmortizacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-14T22:43:50")
@StaticMetamodel(Credito.class)
public class Credito_ { 

    public static volatile SingularAttribute<Credito, Integer> idCredito;
    public static volatile SingularAttribute<Credito, Float> montoCredito;
    public static volatile SingularAttribute<Credito, TasaAmortizacion> idTasaAmortizacion;
    public static volatile SingularAttribute<Credito, Boolean> esEliminado;
    public static volatile SingularAttribute<Credito, Double> interesCredito;
    public static volatile SingularAttribute<Credito, Socios> socios;

}