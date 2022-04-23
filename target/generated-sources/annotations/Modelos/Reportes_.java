package Modelos;

import Modelos.CuentaCooperativa;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-04-07T10:37:30")
@StaticMetamodel(Reportes.class)
public class Reportes_ { 

    public static volatile SingularAttribute<Reportes, String> balanceGeneral;
    public static volatile SingularAttribute<Reportes, String> resumenAportes;
    public static volatile SingularAttribute<Reportes, Integer> idReportes;
    public static volatile SingularAttribute<Reportes, Boolean> esEliminado;
    public static volatile SingularAttribute<Reportes, CuentaCooperativa> idCuenta;

}