package Modelos;

import Modelos.TasaAmortizacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-17T17:51:09")
@StaticMetamodel(Cuota.class)
public class Cuota_ { 

    public static volatile SingularAttribute<Cuota, Double> montoMensual;
    public static volatile SingularAttribute<Cuota, TasaAmortizacion> tasaAmortizacion;
    public static volatile SingularAttribute<Cuota, Integer> idCuota;
    public static volatile SingularAttribute<Cuota, Boolean> esEliminado;

}