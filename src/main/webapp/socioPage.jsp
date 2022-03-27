<%-- 
    Document   : socioPage
    Created on : 25 mar 2022, 15:46:04
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

        <!-- My CSS -->
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>

        <!-- JQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

        <title>TAXOPEL - Socios</title>
    </head>
    <body>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg myBgNavbar">
            <div class="container-fluid">
                <!-- Navbar -->
                <a class="navbar-brand fw-bold text-uppercase me-auto myLogo" href="#">TAXOPEL - SOCIOS</a>
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

        <div class="container">
            <div class="row text-center mt-3">
                <h2>Bienvenido <%=sesion2.getAttribute("nombre")%> <%=sesion2.getAttribute("apellido")%></h2>
            </div>
            <div class="row text-center fs-5">
                <p>Aquí puedes ver todas las transacciones que usted ha realizado.</p>
            </div>
            <div class="row fs-5">
                <p>Haz click en las liguientes opciones:</p>
            </div>
            <div class="row">
                <div class="col-12">
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <button id="btnDeposito" class="nav-link" aria-current="page">Depósitos</button>
                        </li>
                        <li class="nav-item">
                            <button id="btnRetiro" class="nav-link" aria-current="page">Retiros</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-12">
                    <table class="table" id="tableSuccess">
                        <thead>
                            <tr>
                                <th scope="col">FECHA</th>
                                <th scope="col" class="">TIPO DE OPERACIÓN</th>
                                <th scope="col">MONTO</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
            
            <div id="errorAlert" class="alert alert-danger" role="alert" style="display: none">
                A simple danger alert
            </div>
            
            <div id="infoAlert" class="alert alert-info" role="alert" style="display: none">
                A simple danger alert
            </div>
            
            <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
                <div id="liveToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="d-flex">
                        <div class="toast-body" id="message">
                            Búsqueda completada
                        </div>
                        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                
                // FUNCION PARA OBTENER LOS DEPOSITOS
                $("#btnDeposito").click(function () {
                    // agrego algunas clases a los btn depositar y retirar
                    $("#btnDeposito").addClass("active")
                    $("#btnRetiro").removeClass("active")
                    // remover las filas de la tabla
                    $('#tableSuccess>tbody').find("tr").remove();
                    // enviamos la peticion por el metodo POST
                    $.post("SocioInfo?accion=depositos", {
                        id: <%=sesion2.getAttribute("id")%>,
                    }, function (res) {
                        // si existe un error, se presenta un alert
                        if (res.error) {
                            $('#errorAlert').text(res.error);
                            $('#errorAlert').show();
                        }
                        // si existe una informacion por el servidor, se presenta un alert
                        if (res.info) {
                            $('#infoAlert').text(res.info);
                            $('#infoAlert').show();
                        }
                        // si la respuesta del servidor es OK
                        if (res.message) {
                            // se presenta un toast de confirmacion
                            $('#liveToast').toast('show');
                            // se borra cualquier alert
                            $('#errorAlert').hide();
                            $('#infoAlert').hide();
                            // se crea una variable para almacenar la estructura de las filas de la tabla
                            let datos = "";
                            // se itera la lista de depositos y se agreaga en una fila
                            res.depositos?.map(deposito => {
                                datos += "<tr>\n\
                            <td>" + deposito.fecha + "</td>\n\
                            <td>" + res.operacion + "</td>\n\
                            <td>$ " + deposito.monto + "</td>\n\
                            </tr>";
                            })
                            // se agrega las filas a la tabla
                            $('#tableSuccess>tbody').append(datos);
                        }
                    }).fail(function (error) { // si existe un error del servidor, presentamos un alert
                        $('#errorlAlert').show();
                        $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                    });
                    ;
                });
                
                
                // FUNCION PARA OBTENER LOS RETIROS
                $("#btnRetiro").click(function () {
                    // agrego algunas clases a los btn depositar y retirar
                    $("#btnRetiro").addClass("active")
                    $("#btnDeposito").removeClass("active")
                    // remover las filas de la tabla
                    $('#tableSuccess>tbody').find("tr").remove();
                    // enviamos la peticion por el metodo POST
                    $.post("SocioInfo?accion=retiros", {
                        id: <%=sesion2.getAttribute("id")%>,
                    }, function (res) {
                        // si existe un error, se presenta un alert
                        if (res.error) {
                            $('#errorAlert').text(res.error);
                            $('#errorAlert').show();
                        }
                        // si existe una informacion por el servidor, se presenta un alert
                        if (res.info) {
                            $('#infoAlert').text(res.info);
                            $('#infoAlert').show();
                        }
                        // si la respuesta del servidor es OK
                        if (res.message) {
                            // se presenta un toast de confirmacion
                            $('#liveToast').toast('show');
                            // se borra cualquier alert
                            $('#errorAlert').hide();
                            $('#infoAlert').hide();
                            // se crea una variable para almacenar la estructura de las filas de la tabla
                            let datos = "";
                            // se itera la lista de retiros y se agreaga en una fila
                            res.retiros?.map(deposito => {
                                datos += "<tr>\n\
                            <td>" + deposito.fecha + "</td>\n\
                            <td>" + res.operacion + "</td>\n\
                            <td>$ " + deposito.monto + "</td>\n\
                            </tr>";
                            })
                            // se agrega las filas a la tabla
                            $('#tableSuccess>tbody').append(datos);
                        }
                    }).fail(function (error) { // si existe un error del servidor, presentamos un alert
                        $('#errorlAlert').show();
                        $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                    });
                    ;
                });
                
                
            });
        </script>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
