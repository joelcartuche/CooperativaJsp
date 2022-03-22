<%-- 
    Document   : crearCredito
    Created on : 21 mar. 2022, 17:19:24
    Author     : joelc
--%>

<%-- 
    Document   : crearCuenta
    Created on : 16 mar. 2022, 21:19:36
    Author     : joelc
--%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.AbstractList"%>
<%@page import="java.util.List"%>
<%@page import="Modelos.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
<%@page import="Modelos.Cuenta"%>
<%@page import="Controladores.CuentaJpaController"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
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

    function ocultarToast(){
        //ocultamos el toast
        toast.hide();
        $('#mensajeErrorToast').html("");
        $('#montoCredito').removeClass("is-invalid");
        $('#plazoMeses').removeClass("is-invalid");
    }
    
    $('#btnGenerarTasaAmortizacionModal').click(function(){
        var monto= $('#montoCredito').val();
        var plazo = $('#plazoMeses').val();
        ocultarToast();
        
        $.get('../GenerarTasaAmortizacion',{
            monto:monto,
            plazo:plazo
        },function(result){
            if(result== "errorMontoVacio"){
                $('#montoCredito').addClass("is-invalid");
                $('#mensajeErrorToast').html("Ingrese un monto para calcular la tasa de amortización");
                $('#modalTazaAmortizacion').modal('hide');

            }else if(result == "errorPlazoVacio"){
                $('#plazoMeses').addClass("is-invalid");
                $('#mensajeErrorToast').html("Ingrese un plazo para calcular la tasa de amortización");

            }
        });
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
                <div class="col-md-12 fw-bold fs-3 text-center">Crear crédito</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-6">
                    <div class="card p-4">
                        <div id="mensajeError" class="alert alert-danger" role="alert" hidden>
                        </div>
                        <div id="mensajeSuccess" class="alert alert-success" role="alert" hidden>
                            La cuenta ha sido creada con éxito
                        </div>
                        <div class="mb-3 row">
                            <label class="col-md-6 col-form-label">Monto del crédito</label>
                            <input id="montoCredito" name="montoCredito" class="form-control " placeholder="Ingrese monto del credito límite 1000" aria-describedby="validarMontoCredito"/>
                            <div id="validarMontoCredito" class="invalid-feedback" >
                                Ingrese monto.
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label  class="col-sm-6 col-form-label">Interes del crédito</label>
                            <input class="form-control" type="text" value="0.8" disabled/>                        
                        </div>
                        <div class="mb-3 row">
                            <label  class="col-sm-6 col-form-label">Tasa de amortización</label>
                            <div class="alert alert-danger" role="alert">No se ha generado ninguna tasa de amortización</div>
                            <input id="generarTasaAmortizacion" class="btn btn-outline-success " data-bs-toggle="modal" data-bs-target="#modalTazaAmortizacion" value="Generar taza de amortización"/>
                            <input id="generoTazaAmortizacion" value="0" hidden >
                        </div>
                        <div class="mb-3 row ">
                            <label  class="col-sm-2 col-form-label">Socio</label>
                            <input id="socio" name="socio" class="form-control "  disabled/>
                            <input id="buscarSocio" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#modalSocio" value="Buscar socio"/>
                            <input id="idSocioActual" value="" hidden >
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



<!-- Modal Taza de amortizacion-->
<div class="modal fade" id="modalTazaAmortizacion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Generar tasa amortización</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div id="salida"></div>
                    <div class="row">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center" colspan="2">
                                        <input id="plazoMeses" class="form-control" placeholder="Ingrese plazo del prestamo en meses"/>
                                    </th>
                                    <th scope="col" class="text-center" colspan="4">
                                        <input id="btnGenerarTasaAmortizacionModal" class="btn btn-primary" value="Generar taza de amortización"/>
                                    </th>
                                </tr>
                                <tr>
                                    <th scope="col">Periodo</th>
                                    <th scope="col">Saldo deuda</th>
                                    <th scope="col">Cuota</th>
                                    <th scope="col">Interes</th>
                                    <th scope="col">Amortizacion</th>
                                    <th scope="col">Valor a pagar cada mes</th>
                                </tr>
                            </thead>
                            <tbody id="generacionTasaAmortizacion">
                                
                            </tbody>
                        </table>
                    </div>
                    
                </div>
            </div>

            <div class="modal-footer">
                <input id="guardarTasaAmortizacion"type="button" class="btn btn-primary" data-bs-dismiss="modal" value="Guardar"/>
            </div>
        </div>
    </div>
</div>


<!-- Modal socio-->
<div class="modal fade" id="modalSocio" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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

<!-- Toast -->
<div id="toastError" role="alert" aria-live="assertive" aria-atomic="true" class="toast position-fixed bottom-0 end-0 p-3" data-bs-autohide="false">
  <div class="toast-header">
    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
    <strong class="me-auto">Error</strong>
    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
  </div>
  <div id="mensajeErrorToast" class="toast-body">
    
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