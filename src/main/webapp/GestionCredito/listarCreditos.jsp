<%-- 
    Document   : crearCredito
    Created on : 21 mar. 2022, 16:26:02
    Author     : joelc
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.AbstractList"%>
<%@page import="Modelos.TasaAmortizacion"%>
<%@page import="Controladores.TasaAmortizacionJpaController"%>
<%@page import="Modelos.Credito"%>
<%@page import="Controladores.CreditoJpaController"%>
<%@page import="Modelos.Cuenta"%>
<%@page import="Controladores.CuentaJpaController"%>
<%@page import="Modelos.Rol"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RolJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("logueado") == null || sesion.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
        }
    %>

    <jsp:include page="../Template/cabecera.jsp"></jsp:include>

    <%
        
        String idCredito = request.getParameter("idCredito");
        
        CreditoJpaController creditoJpaController = new CreditoJpaController();
        List<Credito> listaCredito = new ArrayList<>();
        TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
        if (idCredito == null || idCredito.equals("")) {
            listaCredito = creditoJpaController.findCreditoEntities();
            
        }else{
            listaCredito.clear();
            listaCredito.add(creditoJpaController.findCredito(Integer.parseInt(idCredito)));
        }

    %>
    <script>
    $(document).ready(function(event) {
        $('#buscarCredito').click(function(){
           $('#errorBuscarCuenta').removeAttr('hidden');
           $.get('../BuscarCredito',{
               cedulaSocio : $('#cedulaSocio').val()
           },function(result){
               if(result=="error"){
                   
                   $('#errorBuscarCuenta').attr('hidden',false);
               }else if(result>=0){
                   $('#errorBuscarCuenta').attr('hidden',true);
                   window.location.replace("listarCreditos.jsp?idCredito="+result);
               }
           });
        });
    });
    </script>
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  créditos</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 col-md-9">
                        <div id="errorBuscarCuenta" class="alert alert-danger" role="alert" hidden>
                            No se encontraron coincidencias
                        </div>
                        <div class="card p-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center" colspan="3">
                                            
                                            <label>Buscar crédito:</label>
                                            <input id='cedulaSocio' class="form-control" type="text" placeholder="Ingrese número de cedula del socio"/>  
                                            
                                        </th>
                                        <th>
                                            <input id="buscarCredito" type="button" class="btn btn-primary" value="Buscar"/>
                                        </th>
                                        <th scope="col" class="text-center" >
                                            <a href="crearCredito.jsp"><i class="fa fa-credit-card-alt" aria-hidden="true"></i><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th scope="col">N°</th>
                                        <th scope="col">Nombres del socio</th>
                                        <th scope="col">Cédula del socio</th>
                                        <th scope="col">Monto crédito</th>
                                        <th scope="col">Interes crédito</th>
                                        <th scope="col">Funciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<TasaAmortizacion> tasaAmortizacions;
                                        for (Credito credito : listaCredito) {
                                        tasaAmortizacions = tasaAmortizacionJpaController.findTasaAmortizacionIdCredito(credito);
                                    %>
                                    <tr>
                                        <th scope="row"><%=credito.getIdCredito()%></th>
                                        <td><%= credito.getIdCodigoSocio().getNombreSocio()%> <%= credito.getIdCodigoSocio().getApellidoSocio()%></td>
                                        <td><%= credito.getIdCodigoSocio().getCedulaSocio() %></td>
                                        <td><%= credito.getMontoCredito() %></td>
                                        <td><%= credito.getInteresCredito() %></td>
                                        <td>
                                            <% 
                                              
                                            if(!tasaAmortizacions.get(0).getEsPagado()){
                                            %>
                                            <a type="button" href="editarCredito.jsp?id=<%=credito.getIdCredito()%>"  class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                            <%
                                            
                                                } 
                                            %>
                                            <a type="button" href="listarPagos.jsp?id=<%=credito.getIdCredito()%>" class="btn btn-success"><i class="fa fa-money" aria-hidden="true"></i> Pagar letra </a>

                                        </td>
                                        

                                    </tr>
                                    <%                }
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