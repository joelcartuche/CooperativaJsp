<%-- 
    Document   : listarCuentasCooperativa
    Created on : 24 mar 2022, 17:26:45
    Author     : LENOVO
--%>

<%@page import="Controladores.CuentaCooperativaJpaController"%>
<%@page import="Modelos.CuentaCooperativa"%>
<%@page import="java.util.List"%>
<%@page import="Utilidades.Dominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    //HttpSession sesion2 = request.getSession();
    //if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
    //        response.sendRedirect("../login.jsp");
    //}

    Dominio dom = new Dominio();
    CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
    List<CuentaCooperativa> listaCuentas = cuentaCooperativaJpaController.findCuentaCooperativaEntities();
%>

<main>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Lista de Cuentas Cooperativa</div>
        </div>
        <div class="row mt-4 justify-content-center">
            <div class="col-12 col-md-11">
                <%                    if (listaCuentas.isEmpty()) {
                %>
                <div class="alert alert-primary" role="alert">
                    No hay Socios registrados.
                    <a href="<%=dom.getDominio()%>Socio?accion=agregar" class="btn btn-primary">Agregar Nuevo Socio</a>
                </div>
                <%
                } else {
                %>
                <table class="table">
                    <thead class="table-primary">
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Número Cuenta</th>
                            <th scope="col">Nombre Cuenta</th>
                            <th scope="col">Código Cuenta</th>
                            <th scope="col">Nombre Socio</th>
                            <th scope="col">Estado Cuenta</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CuentaCooperativa cuenta : listaCuentas) {
                        %>
                        <tr>
                            <td><%=cuenta.getIdCuentaCooperativa()%></th>
                            <td><%=cuenta.getNumeroCuenta()%></td>
                            <td><%=cuenta.getNombreCuenta()%></td>
                            <td><%=cuenta.getCodigoCuenta()%></td>
                            <td><%=cuenta.getIdSocios().getNombreSocio()%></td>
                            <td>
                                <% if (!cuenta.getEsEliminado()) {%>
                                <span class="my-estado-activo">
                                    Activo
                                </span>
                                <%
                                } else {
                                %>
                                <span class="my-estado-inactivo">
                                    Inactivo
                                </span>
                                <%
                                    }
                                %></td>
                            <td>
                                <a type="button" href="<%=dom.getDominio()%>CuentasCooperativa?accion=ver&id=<%=cuenta.getIdCuentaCooperativa()%>" class="btn btn-info"><i class="bi bi-eye text-white"></i></a>
                                <a type="button" href="<%=dom.getDominio()%>CuentasCooperativa?accion=editar&id=<%=cuenta.getIdCuentaCooperativa()%>" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>

                                <% if (!cuenta.getEsEliminado()) {%>
                                <a type="button" href="<%=dom.getDominio()%>CuentasCooperativa?accion=eliminar&id=<%=cuenta.getIdCuentaCooperativa()%>&esEliminado=<%=cuenta.getEsEliminado()%>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i> </a>
                                <%
                                } else {
                                %>
                                <a type="button" href="<%=dom.getDominio()%>CuentasCooperativa?accion=eliminar&id=<%=cuenta.getIdCuentaCooperativa()%>&esEliminado=<%=cuenta.getEsEliminado()%>" class="btn btn-success">Activar </a>
                                <%
                                    }
                                %></td>
                            </td>
                        </tr>
                        <%                }
                        %>
                    </tbody>
                </table>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../Template/footer.jsp"></jsp:include>
