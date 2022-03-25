<%-- 
    Document   : designPage
    Created on : 15 mar 2022, 13:02:16
    Author     : jede
--%>

<%@page import="Utilidades.Dominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion2 = request.getSession();
    //if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
    //        response.sendRedirect("../login.jsp");
    //}

    Dominio dom = new Dominio();
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg myBgNavbar">
    <div class="container-fluid">
        <!-- offcanvas trigger -->
        <button class="navbar-toggler me-2 btnTogle" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list burger" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
            </svg>
        </button>
        <!-- offcanvas trigger -->
        <!-- Navbar -->
        <a class="navbar-brand fw-bold text-uppercase me-auto myLogo" href="#">Cooperativa</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-down-fill" viewBox="0 0 16 16">
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
            </svg>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle nameUserNavbar" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <span><%=sesion2.getAttribute("user")%></span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                            <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                        </svg>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end " aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                </svg> Perfil</a></li>
                        <li><a class="dropdown-item" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">
                                    <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>
                                    <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>
                                </svg> Opciones</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=dom.getDominio()%>logOut.jsp"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box-arrow-right" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
                                    <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                                </svg> Cerrar Sesión</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- offCanvas -->
<div class="offcanvas offcanvas-start sidebar-nav myOffCanvas" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">

    <div class="offcanvas-body p-0 hidenScroll">
        <nav class="navbar-light">
            <ul class="navbar-nav">
                <li class="mt-2">
                    <div class="text-muted small fw-bold text-uppercase px-3">CORE</div>
                </li>
                <li class="offcanvasItems">
                    <a href="/Cooperativa" class="nav-link px-3 active">
                        <span class="myIconText me-2"><i class="bi bi-collection"></i></span>
                        <span class="myOffCanvaSubtitle">Dashboard</span>
                    </a>
                </li>
                <li class="my-2">
                </li>
                <!-- Cash Management -->
                <li>
                    <div class="text-muted small fw-bold text-uppercase px-3">Caja</div>
                </li>
                <li class="offcanvasItems">
                    <a href="Deposito?accion=buscarcuenta" class="nav-link px-3 active">
                        <span class="myIconText me-2"><i class="bi bi-currency-dollar"></i></span>
                        <span class="myOffCanvaSubtitle">Depositar</span>
                    </a>
                </li>
                <li class="offcanvasItems">
                    <a href="Retiro?accion=buscarcuenta" class="nav-link px-3 active">
                        <span class="myIconText me-2"><i class="bi bi-cash-coin"></i></span>
                        <span class="myOffCanvaSubtitle">Retirar</span>
                    </a>
                </li>
                <li class="offcanvasItems">
                    <a href="GestionCredito/listarCreditos.jsp" class="nav-link px-3 active">
                        <span class="myIconText me-2"><i class="bi bi-credit-card-2-back-fill"></i></span>
                        <span class="myOffCanvaSubtitle">Credits</span>
                    </a>
                </li>
                <li class="offcanvasItems">
                    <a href="#" class="nav-link px-3 active">
                        <span class="myIconText me-2"><i class="bi bi-piggy-bank-fill"></i></span>
                        <span class="myOffCanvaSubtitle">Contributions</span>
                    </a>
                </li>
                <li class="my-2">
                </li>

                <!-- User and account management -->

                <li>
                    <div class="text-muted small fw-bold text-uppercase px-3">Usuarios Y Cuentas</div>
                </li>

                <!-- Rol LIST -->
                <li>
                    <li class="offcanvasItems">
                        <a href="<%=dom.getDominio()%>GestionRol/listarRol.jsp" class="nav-link px-3 active">
                            <span class="me-3 myIconText">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-heading" viewBox="0 0 16 16">
                                    <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
                                    <path d="M3 8.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5zm0-5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5v-1z"/>
                                </svg>
                            </span>
                            <span class="myOffCanvaSubtitle">Gestion Rol</span>
                        </a>
                    </li>
                </li>

                <!-- CUENTA GESION -->
                <li>
                    <li class="offcanvasItems">
                        <a href="<%=dom.getDominio()%>GestionCuenta/listarCuenta.jsp" class="nav-link px-3 active">
                            <span class="me-3 myIconText">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                </svg>
                            </span>
                            <span class="myOffCanvaSubtitle">Gestion Cuenta</span>
                        </a>
                    </li>
                </li>

                <!-- Gestion usuarios -->
                <li>
                    <li class="offcanvasItems">
                        <a href="<%=dom.getDominio()%>GestionUsuario/listarUsuario.jsp" class="nav-link px-3 active">
                            <span class="me-3 myIconText">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-person-fill" viewBox="0 0 16 16">
                                    <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm-1 7a3 3 0 1 1-6 0 3 3 0 0 1 6 0zm-3 4c2.623 0 4.146.826 5 1.755V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1v-1.245C3.854 11.825 5.377 11 8 11z"/>
                                </svg>
                            </span>
                            <span class="myOffCanvaSubtitle">Gestion Usuarios</span>
                        </a>
                    </li>
                </li>


                <!-- Partner Accounts -->
                <li>
                    <button class="btn-link nav-link sidebar-link px-3 offcanvasItems" type="button" data-bs-toggle="collapse" data-bs-target="#collapsePartnerAccounts" aria-expanded="false" aria-controls="collapseExample">
                        <span class="me-3 myIconText">
                            <i class="bi bi-person-bounding-box"></i>
                        </span>
                        <span class="myOffCanvaSubtitle">Cuentas Cooperativa</span>
                        <span class="right-icon ms-auto">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-down" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                            </svg>
                        </span>
                    </button>
                    <div class="collapse" id="collapsePartnerAccounts">
                        <div>
                            <ul class="navbar-nav ps-3">
                                <li>
                                    <a href="CuentasCooperativa?accion=buscarCuenta" class="nav-link px-3">
                                        <span class="me-2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                            </svg>
                                        </span>
                                        <span>Buscar Cuenta Cooperativa</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="CuentasCooperativa?accion=listar" class="nav-link px-3">
                                        <span class="me-2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-list" viewBox="0 0 16 16">
                                                <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
                                                <path d="M5 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 5 8zm0-2.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0 5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-1-5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zM4 8a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zm0 2.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0z"/>
                                            </svg>
                                        </span>
                                        <span>Listar Cuentas Cooperativa</span>
                                    </a>
                                </li>
                                
                            </ul>
                        </div>
                    </div>
                </li>

                <!-- Partners -->
                <li>
                    <button class="btn-link nav-link sidebar-link px-3 offcanvasItems" type="button" data-bs-toggle="collapse" data-bs-target="#collapsePartners" aria-expanded="false" aria-controls="collapseExample">
                        <span class="me-3 myIconText">
                            <i class="bi bi-person"></i>
                        </span>
                        <span class="myOffCanvaSubtitle">Socios</span>
                        <span class="right-icon ms-auto">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-down" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                            </svg>
                        </span>
                    </button>
                    <div class="collapse" id="collapsePartners">
                        <div>
                            <ul class="navbar-nav ps-3">
                                <li>
                                    <a href="Socio?accion=buscarSocio" class="nav-link px-3">
                                        <span class="me-2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                            </svg>
                                        </span>
                                        <span>Buscar Socio</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="Socio?accion=listar" class="nav-link px-3">
                                        <span class="me-2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-list" viewBox="0 0 16 16">
                                                <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
                                                <path d="M5 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 5 8zm0-2.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0 5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-1-5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zM4 8a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zm0 2.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0z"/>
                                            </svg>
                                        </span>
                                        <span>Listar Socios</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="Socio?accion=agregar" class="nav-link px-3">
                                        <span class="me-2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-plus" viewBox="0 0 16 16">
                                                <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                                <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                                            </svg>
                                        </span>
                                        <span>Agregar Nuevo Socio</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <li class="my-2"></li>

                <!-- Reports -->
                <li>
                    <div class="text-muted small fw-bold text-uppercase px-3">Reportes</div>
                </li>
                <li class="offcanvasItems">
                    <a href="#" class="nav-link px-3 active">
                        <span class="myIconText me-2">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-graph-up-arrow" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M0 0h1v15h15v1H0V0Zm10 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V4.9l-3.613 4.417a.5.5 0 0 1-.74.037L7.06 6.767l-3.656 5.027a.5.5 0 0 1-.808-.588l4-5.5a.5.5 0 0 1 .758-.06l2.609 2.61L13.445 4H10.5a.5.5 0 0 1-.5-.5Z"/>
                            </svg>
                        </span>
                        <span class="myOffCanvaSubtitle">Reporte</span>
                    </a>
                </li>
                <li class="my-2"></li>

                <!-- User -->
                <li>
                    <div class="text-muted small fw-bold text-uppercase px-3">Usuario</div>
                </li>
                <li class="offcanvasItems">
                    <a href="#" class="nav-link px-3 active">
                        <span class="myIconText me-2">
                            <i class="bi bi-box-arrow-in-right"></i>
                        </span>
                        <span class="myOffCanvaSubtitle">Perfil</span>
                    </a>
                </li>
                <li class="offcanvasItems">
                    <a href="<%=dom.getDominio()%>logOut.jsp" class="nav-link px-3 active">
                        <span class="myIconText me-2">
                            <i class="bi bi-box-arrow-right"></i>
                        </span>
                        <span class="myOffCanvaSubtitle">Cerrar Sesión</span>
                    </a>
                </li>

            </ul>
        </nav>
    </div>
</div>

