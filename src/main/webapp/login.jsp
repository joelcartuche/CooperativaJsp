<%-- 
    Document   : login
    Created on : 13 mar. 2022, 16:09:13
    Author     : joelc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        
        <title>Login</title>
    </head>
    <script>
        $(document).ready(function() {
            function ocultarCampos(){
                //ocultamos los divs de alertas
                $('#errorPassword').attr("hidden",true);
                $('#errorUsuario').attr("hidden",true);
                $('#success').attr("hidden",true);

            };
            
                $('#submit').click(function(event) {//le damos evento al boton
                    ocultarCampos();
                        var usuario = $('#usuario').val(); //almacena el nombre de usuario
                        var password = $('#password').val(); //almacena el password
                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('Login', {
                                user : usuario,
                                password: password 
                        }, function(responseText) {
                            //$('#resultado').html(responseText);
                            var result = JSON.parse(responseText);//recogemos el JSON generado
                            //recogemos las variables enviadas desde el servelet
                            var logueado = result.logueado;
                            var id =result.id;
                            var usuario =result.user;
                            var esContraIncorrecta =result.esContraIncorrecta;
                            var noExisteUsuario = result.noExisteUsuario;
                            
                            if(logueado!=null){
                                if(logueado=="1"){
                                    $('#success').removeAttr("hidden");
                                    //redireccionamos a index.jsp
                                    window.location.replace("admin.jsp?id="+id+"&user="+usuario);
                                }
                            }
                            if(esContraIncorrecta!=null){
                                if(esContraIncorrecta=="1")
                                {
                                    $('#errorPassword').removeAttr("hidden");

                                }
                            }
                            if(noExisteUsuario!=null){
                                if(noExisteUsuario=="1"){
                                    $('#errorUsuario').removeAttr("hidden");
                                }
                            }
                            //console.log(result.data.logueado);
                        });
                });
        });
        
    </script>
    <body>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
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
                    <form>
                        <div class="form-group">
                            <label>Usuario</label>
                            <input type="text" class="form-control" id ="usuario" placeholder="Ingrese el nombre de usuario">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" id="password" class="form-control" placeholder="Ingrese su contraseña">
                        </div>
                        <input type="button"  id="submit" class="btn btn-primary" value="Iniciar sesión" />
                    </form> 
                </div>
            </div>               
        </div>
    </body>
    
</html>


