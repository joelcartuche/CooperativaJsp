package Modelos;

import Modelos.Socios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-14T22:43:50")
@StaticMetamodel(Deposito.class)
public class Deposito_ { 

    public static volatile SingularAttribute<Deposito, Float> montoDeposito;
    public static volatile SingularAttribute<Deposito, Socios> idSocios;
    public static volatile SingularAttribute<Deposito, Date> fechaDeposito;
    public static volatile SingularAttribute<Deposito, Integer> idDeposito;
    public static volatile SingularAttribute<Deposito, Boolean> esEliminado;

}