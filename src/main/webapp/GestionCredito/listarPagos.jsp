
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="Modelos.TasaAmortizacion"%>
<%@page import="Controladores.TasaAmortizacionJpaController"%>
<%@page import="Modelos.Credito"%>
<%@page import="Controladores.CreditoJpaController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.AbstractList"%>
<%@page import="java.util.List"%>
<%@page import="Modelos.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
<%@page import="Modelos.Cuenta"%>
<%@page import="Controladores.CuentaJpaController"%>

<%@page import="Modelos.Rol"%>
<%@page import="Controladores.RolJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("logueado") == null || sesion.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
        }
        String id = request.getParameter("id");
        CreditoJpaController creditoJpaController = new CreditoJpaController();
        Credito credito = creditoJpaController.findCredito(Integer.parseInt(id));
        TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
        List<TasaAmortizacion>  tasaAmortizacionList = tasaAmortizacionJpaController.findTasaAmortizacionIdCredito(credito);
        
        

    %>

    <jsp:include page="../Template/cabecera.jsp"></jsp:include>


    
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Pagar crédito</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-12">
                    <div class="card p-4">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Mes</th>
                                    <th scope="col">Cuota</th>
                                    <th scope="col">Deuda</th>
                                    <th scope="col">Forma de pago</th>
                                    <th scope="col">Pagado</th>
                                    <th scope="col">Fecha del pago</th>
                                    <th scope="col">Funciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    int cont=1;
                                    boolean esPagado=false;
                                    for (TasaAmortizacion tasaAmortizacion : tasaAmortizacionList) {
                                %>
                                <tr>
                                    <th scope="row"><%=cont %></th>
                                    <td><%= tasaAmortizacion.getCuota()%></td>
                                    <td><%= tasaAmortizacion.getSaldoDeuda() %></td>
                                    
                                    <%
                                        if(tasaAmortizacion.getFormaPago()==null){
                                        
                                    %>
                                        <td>No pagado</td>
                                    <%
                                        }else{
                                    %>
                                    
                                        <td><%= tasaAmortizacion.getFormaPago() %></td>
                                    
                                        
                                    <%

                                        }
                                    
                                    %>
                                    
                                    <%//controlamos el null en caso de que no realizo ningun pago
                                        if(tasaAmortizacion.getEsPagado()){
                                        
                                    %>
                                        <td>
                                            <i class="fa fa-check" aria-hidden="true"></i> Pagado 
                                        </td>
                                    <%
                                        }else{
                                    %>
                                    
                                        <td>
                                            <i class="fa fa-ban" aria-hidden="true"></i> No pagado
                                        </td>
                                    
                                    <%

                                        }
                                    
                                    %>
                                    
                                    <%//controlamos el null en caso de que no realizo ningun pago
                                        if(tasaAmortizacion.getFechaPago()==null){
                                        
                                    %>
                                        <td>
                                            No pagado 
                                        </td>
                                    <%
                                        }else{
                                    %>
                                    
                                        <td>
                                            <%=tasaAmortizacion.getFechaPago()%>
                                        </td>
                                    
                                    <%

                                        }
                                    
                                    %>
                                    
                                    <%//controlamos el null en caso de que no realizo ningun pago
                                        if(!tasaAmortizacion.getEsPagado() && !esPagado){
                                            esPagado =true;
                                    %>
                                        <td>
                                            <a id="pagar" class="btn btn-success" href="pagarLetra.jsp?id=<%=tasaAmortizacion.getIdTasaAmortizacion() %>"> <i class="fa fa-money" aria-hidden="true"></i> Pagar </a>
                                        </td>
                                    <%
                                        }else{
                                    %>
                                    
                                        <td>
                                            
                                        </td>
                                    
                                    <%

                                        }
                                    
                                    %>
                                    
                                    
                                </tr>
                                <%      
                                    cont++;
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>






    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
