package Modelos;

import Modelos.Credito;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-25T23:27:17")
@StaticMetamodel(TasaAmortizacion.class)
public class TasaAmortizacion_ { 

    public static volatile SingularAttribute<TasaAmortizacion, Double> cuota;
    public static volatile SingularAttribute<TasaAmortizacion, Credito> codigoCredito;
    public static volatile SingularAttribute<TasaAmortizacion, Integer> idTasaAmortizacion;
    public static volatile SingularAttribute<TasaAmortizacion, Date> fechaCreacion;
    public static volatile SingularAttribute<TasaAmortizacion, Double> saldoDeuda;
    public static volatile SingularAttribute<TasaAmortizacion, Integer> numeroPagos;
    public static volatile SingularAttribute<TasaAmortizacion, Boolean> esPagado;
    public static volatile SingularAttribute<TasaAmortizacion, Boolean> esEliminado;
    public static volatile SingularAttribute<TasaAmortizacion, String> formaPago;
    public static volatile SingularAttribute<TasaAmortizacion, Double> amortizacion;
    public static volatile SingularAttribute<TasaAmortizacion, Date> fechaPago;
    public static volatile SingularAttribute<TasaAmortizacion, Double> interes;

}