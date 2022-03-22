<%-- 
    Document   : crearCredito
    Created on : 21 mar. 2022, 16:26:02
    Author     : joelc
--%>

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
        CreditoJpaController creditoJpaController = new CreditoJpaController();
        List<Credito> listaCredito = creditoJpaController.findCreditoEntities();
            
    %>
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  créditos</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 col-md-9">
                        <div class="card p-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" >
                                            <a href="crearCredito.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th scope="col">N°</th>
                                        <th scope="col">Monto crédito</th>
                                        <th scope="col">Interes crédito</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Funciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Credito credito : listaCredito) {
                                    %>
                                    <tr>
                                        <th scope="row"><%=credito.getIdCredito()%></th>
                                        <td><%= credito.getMontoCredito() %></td>
                                        <td><%= credito.getInteresCredito() %></td>
                                        <%
                                            if (credito.getEsEliminado()) {
                                        %>
                                        <td><i class="fa fa-ban" aria-hidden="true"></i> Inactivo</td>
                                        <td>
                                            <a type="button" href="verTasaAmortizacion.jsp?id="  class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                            <a type="button" href="borrarCredito.jsp?id=<%=credito.getIdCredito()%>&esEliminado=<%=credito.getEsEliminado()%>" class="btn btn-success"><i class="fa fa-check-circle" aria-hidden="true"></i> Activar </a>

                                        </td>
                                        <%
                                        } else {
                                        %>
                                        <td><i class="fa fa-check" aria-hidden="true"></i> Activo</td>
                                        <td>
                                            <a type="button" href="verTasaAmortizacion.jsp?id=" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                            <a type="button" href="borrarCuenta.jsp?id=<%=credito.getIdCredito()%>&esEliminado=<%=credito.getEsEliminado() %>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i>Desactivar</a>
                                        </td>
                                        <%
                                            }
                                        %>

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