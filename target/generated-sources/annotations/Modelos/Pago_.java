package Modelos;

import Modelos.TasaAmortizacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-18T10:50:42")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, TasaAmortizacion> tasaAmortizacion;
    public static volatile SingularAttribute<Pago, String> formasPago;
    public static volatile SingularAttribute<Pago, Integer> idPago;
    public static volatile SingularAttribute<Pago, Boolean> esEliminado;

}