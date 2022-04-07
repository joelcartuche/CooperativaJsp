

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.AbstractList"%>
<%@page import="java.util.List"%>
<%@page import="Modelos.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
<%@page import="Modelos.Cuenta"%>
<%@page import="Controladores.CuentaJpaController"%>
<%@page import="Modelos.Rol"%>
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

<script>        
$(document).ready(function(event) {
    
    $('#cancelar').click(function(){
        window.location.replace("listarCuenta.jsp");
    });
    
    $('#btnBuscarUsuario').click(function(){
        $('#errorBuscarUsuario').attr("hidden",true);
        var usuario = $('#usuarioBuscar').val();
        $.get('../BuscarUsuarioNombre',{
            nombreApellido: usuario
        },function(resultado){
            var data = resultado;
            if(data == "error"){
                $('#errorBuscarUsuario').attr("hidden",false);
            }else{
                var tBody="";
                data = data.split(';');
                window.console.log(data.length);
                for(var i=0;i<data.length-1;i=i+5){
                    tBody=tBody+"<tr>";
                    tBody=tBody+"<td>"+data[i]+"</td>";
                    tBody=tBody+"<td>"+data[i+1]+"</td>";
                    tBody=tBody+"<td>"+data[i+2]+"</td>";
                    tBody=tBody+"<td>"+data[i+3]+"</td>";
                    tBody=tBody+"<td>"+data[i+4]+"</td>";
                    tBody=tBody+'<td> <input id="id'+data[i]+'" type="button" onclick="actualizacionIdUsuario('+data[i]+')" class="boton btn btn-secondary" data-bs-dismiss="modal" value="Seleccionar" input/> </td>';
                    tBody=tBody+"</tr>";                    
                }
                $('#coicidenciaBussquedaUsuario').html(tBody);
                
            }
            
        });
    });
    
    $('#btnBuscarRol').click(function (){
        $('#errorBuscarRol').attr("hidden",true);
        $('#coicidenciaBussquedaRol').html("");
        var rol = $('#tipoRolBuscar').val();
        $.get('../BuscarRolNombre',{
            rolTipo:rol
        },function(resultado){
            var data = resultado;
            if(data == "error"){
                $('#errorBuscarRol').attr("hidden",false);
            }else{
                var tBody="";
                data = data.split(';');
                window.console.log(data.length);
                for(var i=0;i<data.length-1;i=i+2){
                    tBody=tBody+"<tr>";
                    tBody=tBody+"<td>"+data[i]+"</td>";
                    tBody=tBody+"<td>"+data[i+1]+"</td>";
                    tBody=tBody+'<td> <input id="idRolLista'+data[i]+'" type="button" onclick="actualizacionIdRol('+data[i]+')" class="boton btn btn-secondary" data-bs-dismiss="modal" value="Seleccionar" input/> </td>';
                    tBody=tBody+"</tr>";                    
                }
                $('#coicidenciaBussquedaRol').html(tBody);
                
            }
            
        });
    });
    
    function ocultarCampos(){
        $('#usuario').removeClass("is-invalid");
        $('#clave1').removeClass("is-invalid");
        $('#clave2').removeClass("is-invalid");
        $('#mensajeError').attr("hidden",true);
        $('#mensajeSuccess').attr("hidden",true);
        $('#usuarioActual').removeClass("is-invalid");
    }
    $('#guardar').click(function (){
        ocultarCampos();
        var usuario = $('#usuario').val();
        var clave1 = $('#clave1').val();
        var clave2 = $('#clave2').val();
        var idUsuario =$('#idUsuarioActual').val();
        var idRol = $('#idRolActual').val();
        $.post('../CrearCuenta',{
            usuario: usuario,
            clave1: clave1,
            clave2: clave2,
            idUsuario: idUsuario,
            idRol: idRol
        },function(result){
            if(result=="errorNombreUsuario"){
                $('#usuario').addClass("is-invalid");
            }else if(result=="errorClaveNoIngresada"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error no se ha ingresado clave");
            }else if(result=="errorClaveNoCoincide"){
                $('#clave1').addClass("is-invalid");
                $('#clave2').addClass("is-invalid");
            }if(result=="errorUsuario"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error usuario no seleccionado");

            }else if(result=="errorIdRol"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error con el rol");
                
            }else if(result=="errorCuentaNoCreada"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error con el usuario que ha sido seleccionado debido a que ya existe una cuenta para este usuario");
                $('#usuarioActual').addClass("is-invalid");
            }else if(result=="success"){
                $('#mensajeSuccess').attr("hidden",false);
                window.location.replace("listarCuenta.jsp");
            }
        });
    });    
});



</script>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Cuenta</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-6">
                    <div class="card p-4">
                        <div class="alert alert-primary d-flex align-items-center" role="alert">
                            <div>
                               <i class="fa fa-info-circle" aria-hidden="true"></i> Si no desea configurar la clave deje sus campos correspondientes vacios
                            </div>
                        </div>
                        <div id="mensajeError" class="alert alert-danger" role="alert" hidden>
                        </div>
                        <div id="mensajeSuccess" class="alert alert-success" role="alert" hidden>
                            La cuenta ha sido creada con éxito
                        </div>
                        <div class="mb-3 row">
                            <label class="col-md-6 col-form-label">Nombre de usuario</label>
                            <input id="usuario" name="usuario" class="form-control " placeholder="Ingrese nombre del usuario" aria-describedby="validacionUsuario"/>
                            <div id="validacionUsuario" class="invalid-feedback" >
                                Ingrese usuario
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Clave</label>
                            <input id="clave1" name="clave1" placeholder="Ingrese clave del usuario" class="form-control" aria-describedby="validacionClave1"/>                        
                            <div id="validacionClave1" class="invalid-feedback" >
                                No coinciden las claves
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="col-md-6 col-form-label">Confirmar clave</label>
                            <input id="clave2" name="clave2" placeholder="Ingrese clave del usuario"  class="form-control" aria-describedby="validacionClave2"/>
                            <div id="validacionClave2" class="invalid-feedback" >
                                No coinciden las claves
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Usuario</label>
                            <input id="usuarioActual" name="usuarioActual" class="form-control "   disabled/>
                            <input id="buscarUsuario" class="btn btn-outline-success " data-bs-toggle="modal" data-bs-target="#modalUsuario" value="Buscar usuario"/>
                            <input id="idUsuarioActual" value="" hidden >
                        </div>
                        <div class="mb-3 row ">
                            <label  class="col-sm-2 col-form-label">Rol</label>
                            <input id="rol" name="rol" class="form-control "  disabled/>
                            <input id="buscarRol" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#modalRol" value="Buscar rol"/>
                            <input id="idRolActual" value="" hidden >
                        </div>
                        <div class="mb-3 row">
                            <input id="cancelar" class="btn btn-danger" value="Cancelar"/>
                        </div>
                        <div class="mb-3 row">
                            <input id="guardar" class="btn btn-primary" value="Guardar"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>



<!-- Modal Usuario-->
<div class="modal fade" id="modalUsuario" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Buscar Usuario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div id="salida"></div>
                    <div id="errorBuscarUsuario" class="alert alert-danger" role="alert" hidden>
                        No se encontraron coincidencias
                    </div>
                    <div class="row">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col" colspan="4">
                                        <input id="usuarioBuscar" name="usaurioBuscar" class="form-control " placeholder="Ingrese nombre y apellido del usuario"  />
                                    </th>
                                    <th scope="col" class="text-center" colspan="2">
                                        <input id="btnBuscarUsuario" class="btn btn-primary" value="Buscar"/>
                                    </th>
                                </tr>
                                <tr>
                                    <th scope="col">N°</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellido</th>
                                    <th scope="col">Cedula</th>
                                    <th scope="col">telefono</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody id="coicidenciaBussquedaUsuario">
                                
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Modal Rol-->
<div class="modal fade" id="modalRol" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
            <div id="errorBuscarRol" class="alert alert-danger" role="alert" hidden>
                No se encontraron coincidencias
            </div>
            <div class="row">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col" colspan="2" >
                                <input id="tipoRolBuscar" name="tipoRolBuscar" class="form-control " placeholder="Ingrese rol" id="flexCheckDefault" />
                            </th>
                            <th scope="col" class="text-center" >
                                <input id="btnBuscarRol" class="btn btn-primary" value="Buscar"/>
                            </th>
                        </tr>
                        <tr>
                            <th scope="col">N°</th>
                            <th scope="col">Tipo Rol</th>
                        </tr>
                    </thead>
                    <tbody id="coicidenciaBussquedaRol">

                    </tbody>
                </table>
            </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
    function actualizacionIdUsuario(idDada){
        $('#idUsuarioActual').removeAttr("value");
        $('#idUsuarioActual').attr("value",idDada);
        
        $.get('../BuscarUsuarioId',{
           idUsuario:idDada 
        },function(result){
            var data = JSON.parse(result);
            $('#usuarioActual').val(data.nombreUsuario);
        });

    };
    
    function actualizacionIdRol(idDado){
        $('#idRolActual').removeAttr("value");
        $('#idRolActual').attr("value",idDado);
        
        $.get('../BuscarRolId',{
           idRol:idDado 
        },function(result){
            var data = JSON.parse(result);
            $('#rol').val(data.rol);
        });
    };
</script>

        
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>