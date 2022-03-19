<%-- 
    Document   : listParnets
    Created on : 15 mar 2022, 18:57:30
    Author     : LENOVO
--%>

<%@page import="Utilidades.Dominio"%>
<%@page import="Modelos.Socios"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.SociosJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    HttpSession sesion2 = request.getSession();
    if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
    }

    Dominio dom = new Dominio();
%>

<%
    SociosJpaController sociosJpaController = new SociosJpaController();
    List<Socios> listaSocio = sociosJpaController.findSociosEntities();

%>

<main>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Lista de Socios</div>
        </div>
        <div class="row mt-5 justify-content-center">
            <div class="col-12 col-md-11">
                <%                    if (listaSocio.isEmpty()) {
                %>
                <div class="alert alert-primary" role="alert">
                    No hay Socios registrados.
                    <a href="<%=dom.getDominio()%>Socio?accion=agregar" class="btn btn-primary">Agregar Nuevo Socio</a>
                </div>
                <%
                } else {
                %>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Apellido</th>
                            <th scope="col">Cédula</th>
                            <th scope="col">Teléfono</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Socios socio : listaSocio) {
                        %>
                        <tr>
                            <td><%=socio.getIdSocios()%></th>
                            <td><%=socio.getNombreSocio()%></td>
                            <td><%=socio.getApellidoSocio()%></td>
                            <td><%=socio.getCedulaSocio()%></td>
                            <td><%=socio.getTelefonoSocio()%></td>
                            <td>
                                <% if (!socio.getEsEliminado()) {%>
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
                                <a type="button" href="<%=dom.getDominio()%>Socio?accion=ver&id=<%=socio.getIdSocios()%>" class="btn btn-info"><i class="bi bi-eye text-white"></i></a>
                                <a type="button" href="<%=dom.getDominio()%>Socio?accion=editar&id=<%=socio.getIdSocios()%>" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                    
                                <% if (!socio.getEsEliminado()) {%>
                                <a type="button" href="<%=dom.getDominio()%>Socio?accion=eliminar&id=<%=socio.getIdSocios()%>&esEliminado=<%=socio.getEsEliminado()%>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i> </a>
                                <%
                                } else {
                                %>
                                <a type="button" href="<%=dom.getDominio()%>Socio?accion=eliminar&id=<%=socio.getIdSocios()%>&esEliminado=<%=socio.getEsEliminado()%>" class="btn btn-success">Activar </a>
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
