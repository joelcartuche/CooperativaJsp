package Modelos;

import Modelos.Credito;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-24T13:53:07")
@StaticMetamodel(Socios.class)
public class Socios_ { 

    public static volatile CollectionAttribute<Socios, Credito> creditoCollection;
    public static volatile SingularAttribute<Socios, String> apellidoSocio;
    public static volatile SingularAttribute<Socios, String> direccionSocio;
    public static volatile SingularAttribute<Socios, String> telefonoSocio;
    public static volatile SingularAttribute<Socios, Integer> codigoSocio;
    public static volatile SingularAttribute<Socios, Integer> idSocios;
    public static volatile SingularAttribute<Socios, String> cedulaSocio;
    public static volatile SingularAttribute<Socios, String> nombreSocio;
    public static volatile SingularAttribute<Socios, Boolean> esEliminado;

}