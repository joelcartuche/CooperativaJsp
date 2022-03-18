package Modelos;

import Modelos.Credito;
import Modelos.Cuota;
import Modelos.Pago;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-17T17:51:09")
@StaticMetamodel(TasaAmortizacion.class)
public class TasaAmortizacion_ { 

    public static volatile SingularAttribute<TasaAmortizacion, Double> montoPagar;
    public static volatile SingularAttribute<TasaAmortizacion, Cuota> idCuota;
    public static volatile SingularAttribute<TasaAmortizacion, Pago> idPago;
    public static volatile SingularAttribute<TasaAmortizacion, Integer> idTasaAmortizacion;
    public static volatile SingularAttribute<TasaAmortizacion, Credito> credito;
    public static volatile SingularAttribute<TasaAmortizacion, Integer> numeroPagos;
    public static volatile SingularAttribute<TasaAmortizacion, Boolean> esEliminado;

}