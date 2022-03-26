<%-- 
    Document   : login
    Created on : 13 mar. 2022, 16:09:13
    Author     : joelc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("logueado") != null ) {
        if (sesion.getAttribute("rol").equals("Socio")) {
            response.sendRedirect("Home?accion=socio");
        } else if (sesion.getAttribute("rol").equals("Administrador") || sesion.getAttribute("rol").equals("Secretaria")) {
            response.sendRedirect("Home?accion=admin");
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

        <title>Login</title>
    </head>
    <body>
        <div class="container mt-5 pt-5">
            <div class="row">
                <div class="col-12 col-sm-8 col-md-4 m-auto">
                    <div class="card boder-0 shadow">
                        <div class="card-body">
                            <div id="errorPassword" hidden>
                                <div class="alert alert-danger" role="alert">
                                    <i class="fa fa-ban" aria-hidden="true"></i> Contraseña incorrecta
                                </div>
                            </div>
                            <div id="errorUsuario" hidden>
                                <div class="alert alert-danger" role="alert">
                                    <i class="fa fa-ban" aria-hidden="true"></i> No existe el usuario
                                </div>
                            </div>
                            <div id="success" hidden>
                                <div class="alert alert-success" role="alert">
                                    <i class="fa fa-check-circle" aria-hidden="true"></i> Datos correctos
                                </div>
                            </div>
                            <div id="resultado"></div>
                            <div class="d-flex">
                                <svg class="mx-auto my-3" xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                </svg>
                            </div>
                            <form>
                                <input type="text" id ="usuario" class="form-control my-4 py-2" placeholder="Usuario">
                                <input type="password" id="password" class="form-control my-4 py-2" placeholder="Contraseña">
                                <div class="text-center mt-3">
                                    <input type="button"  id="submit" class="btn btn-primary" value="Iniciar sesión" />
                                    <a href="/" class="nav-link">Volver al inicio</a>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>               
        </div>

        <script>
            $(document).ready(function () {
                function ocultarCampos() {
                    //ocultamos los divs de alertas
                    $('#errorPassword').attr("hidden", true);
                    $('#errorUsuario').attr("hidden", true);
                    $('#success').attr("hidden", true);

                }

                $('#submit').click(function (event) {//le damos evento al boton
                    ocultarCampos();
                    var usuario = $('#usuario').val(); //almacena el nombre de usuario
                    var password = $('#password').val(); //almacena el password
                    // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                    $.post('Login', {
                        user: usuario,
                        password: password
                    }, function (responseText) {
                        var result = JSON.parse(responseText);//recogemos el JSON generado
                        //recogemos las variables enviadas desde el servelet
                        var logueado = result.logueado;
                        var esContraIncorrecta = result.esContraIncorrecta;
                        var noExisteUsuario = result.noExisteUsuario;

                        if (logueado != null) {
                            if (logueado == "1") {
                                $('#success').removeAttr("hidden");
                                if (result.rol === "Administrador" || result.rol === "Secretaria") {
                                    window.location.replace("Home?accion=admin");
                                } else if (result.rol === "Socio") {
                                    window.location.replace("Home?accion=socio");
                                }
                                //redireccionamos a index.jsp
                                //window.location.replace("admin.jsp?id=" + id + "&user=" + usuario);
                            }
                        }
                        if (esContraIncorrecta != null) {
                            if (esContraIncorrecta == "1")
                            {
                                $('#errorPassword').removeAttr("hidden");

                            }
                        }
                        if (noExisteUsuario != null) {
                            if (noExisteUsuario == "1") {
                                $('#errorUsuario').removeAttr("hidden");
                            }
                        }

                    });
                });
            });

        </script>

    </body>
</html>


