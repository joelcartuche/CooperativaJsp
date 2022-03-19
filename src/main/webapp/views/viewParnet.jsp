<%-- 
    Document   : viewParnet
    Created on : 19 mar 2022, 6:29:45
    Author     : LENOVO
--%>

<%@page import="Utilidades.Dominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    HttpSession sesion2 = request.getSession();
    if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
    }

    Dominio dom = new Dominio();
    boolean estadoSocio = (Boolean) request.getAttribute("estado");
%>

<main>
    <div class="container-fluid">

        <div class="row mt-4 mb-4 justify-content-center">
            <div class="col-12 col-md-9">
                <div class="card">
                    <div class="card-header">
                        Información de Socio
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2">Nombres y Apellidos:</span><span>${socio.getNombreSocio()} ${socio.getApellidoSocio()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center mt-2">
                                <span class="fw-bold me-2">Estado:</span>
                                <% if (!estadoSocio) {%>
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
                                %>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2">Cédula:</span><span>${socio.getCedulaSocio()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2">Teléfono:</span><span>${socio.getTelefonoSocio()}</span>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-12 d-flex align-items-center">
                                <span class="fw-bold me-2">Dirección:</span><span>${socio.getDireccionSocio()}</span>
                            </div>
                        </div>
                        <div class="mt-4">
                            <a type="button" href="<%=dom.getDominio()%>Socio?accion=editar&id=${socio.getIdSocios()}" class="btn btn-primary">Editar información <i class="fa fa-pencil" aria-hidden="true"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-2 mb-5 justify-content-center">
            <div class="col-12 col-md-9">
                <div class="card">
                    <div class="card-header">
                        Información de Cuenta de Socio
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>
        </div>

    </div>

</main>

<jsp:include page="../Template/footer.jsp"></jsp:include>
