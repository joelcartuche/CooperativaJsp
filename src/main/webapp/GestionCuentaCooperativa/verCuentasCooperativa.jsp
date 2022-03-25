<%-- 
    Document   : verCuentasCooperativa
    Created on : 24 mar 2022, 18:02:21
    Author     : jede
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    //HttpSession sesion2 = request.getSession();
    //if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
    //        response.sendRedirect("../login.jsp");
    //}

    boolean esEliminadoCuenta = (Boolean) request.getAttribute("esEliminadoCuenta");
    boolean esEliminadoSocio = (Boolean) request.getAttribute("esEliminadoSocio");
    boolean esEliminadoUsuario = (Boolean) request.getAttribute("esEliminadoUsuario");
%>

<main>
    <div class="container-fluid">

        <div class="row mt-2 mb-5 justify-content-center">
            <div class="col-12 col-md-9">
                <div class="card">
                    <div class="card-header fw-bold">
                        Información de Cuenta Cooperativa
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">N° de Cuenta:</span><span>${cuentaCooperativa.getNumeroCuenta()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center mt-2">
                                <span class="fw-bold me-2 text-muted">Estado:</span>
                                <% if (!esEliminadoCuenta) {%>
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
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Nombre de la Cuenta:</span><span>${cuentaCooperativa.getNombreCuenta()}</span>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Código de la Cuenta:</span><span>${cuentaCooperativa.getCodigoCuenta()}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4 mb-4 justify-content-center">
            <div class="col-12 col-md-9">
                <div class="card">
                    <div class="card-header fw-bold">
                        Información del Socio
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Nombres:</span><span>${socio.getNombreSocio()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center mt-2">
                                <span class="fw-bold me-2 text-muted">Estado:</span>
                                <% if (!esEliminadoSocio) {%>
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
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Apellidos:</span><span>${socio.getApellidoSocio()}</span>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Cédula:</span><span>${socio.getCedulaSocio()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Teléfono:</span><span>${socio.getTelefonoSocio()}</span>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-12 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Dirección:</span><span>${socio.getDireccionSocio()}</span>
                            </div>
                        </div>
                        <div class="mt-4">
                            <a type="button" href="Socio?accion=editar&id=${socio.getIdSocios()}" class="btn btn-primary">Editar información <i class="fa fa-pencil" aria-hidden="true"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4 mb-4 justify-content-center">
            <div class="col-12 col-md-9">
                <div class="card">
                    <div class="card-header fw-bold">
                        Información del Usuario
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Nombres:</span><span>${usuario.getNombreUsuario()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center mt-2">
                                <span class="fw-bold me-2 text-muted">Estado:</span>
                                <% if (!esEliminadoUsuario) {%>
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
                        <div class="row">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Apellidos:</span><span>${usuario.getApellidoUsuario()}</span>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Cédula:</span><span>${usuario.getCedulaUsuario()}</span>
                            </div>
                            <div class="col-md-6 d-flex align-items-center">
                                <span class="fw-bold me-2 text-muted">Teléfono:</span><span>${usuario.getTelefonoUsuario()}</span>
                            </div>
                        </div>
                        <div class="mt-4">
                            <a type="button" href="EditarUsuario?id=${usuario.getIdUsuario()}" class="btn btn-primary">Editar información <i class="fa fa-pencil" aria-hidden="true"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>

</main>

<jsp:include page="../Template/footer.jsp"></jsp:include>
