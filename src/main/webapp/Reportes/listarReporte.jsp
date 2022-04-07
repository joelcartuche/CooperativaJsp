

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
 //errorFechaInicio
//errorFechaFin
//errorFecha
//errorBusquedaNoEncontrada
$(document).ready(function(event) {
    
    $('#generar').click(function(){
        //ocultamos los datos de informacion
        $('#mensajeError').attr('hidden',true);
        $('#mensajeSuccess').attr('hidden',true);
        $('#generarCsv').removeAttr('hidden');
        
        //almacena los datos del radio button
        var datoDepoRetiro = "";
        //
        if($('#deposito:checked').val() == 'on'){
           datoDepoRetiro ="deposito"
        }else if($('#retiro:checked').val() == 'on'){
            datoDepoRetiro ="retiro"
        }
        
        
        var fechaInicio = $('#fechaInicio').val();
        var fechaFin = $('#fechaFin').val();
        $.post('../GenerarReporte',{
            fechaInicio:fechaInicio,
            fechaFin: fechaFin,
            depostioOretiro: datoDepoRetiro
        },function(result){
            
            if(result=="errorFechaInicio"){
                $('#mensajeError').html("Error en la fecha de inicio");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFechaFin'){
                $('#mensajeError').html("Error en la fecha final");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFecha'){
                $('#mensajeError').html("Error de fecha reingresela de nuevo");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFechaInicioerrorFechaFin'){
                $('#mensajeError').html("Ingrese una fecha de inicio y fin");
                $('#mensajeError').removeAttr('hidden');
            }else  if(result=='errorBusquedaNoEncontrada'){
                $('#mensajeError').html("Busqueda no encontrada");
                $('#mensajeError').removeAttr('hidden');
            }else if(result.length >0){
                window.console.log(result);
                window.console.log(result.length);
                var tBody ="";
                for (var i = 0; i < result.length; i++) {
                    tBody+="<tr>";
                    for (var j = 0; j < 6; j++) {
                        tBody+="<td>"+result[i][j]+"</td>";
                    }
                    tBody+="</tr>";
                }
                $('#cuerpoTablaReporte').html(tBody);
                $('#mensajeSuccess').html("Datos encontrados los datos mostrados han sido limitados para no generar carga en en el dispositivo pero al generar el csv los datos no se veran afectados ");
                $('#mensajeSuccess').removeAttr('hidden');
                $('#generarCsv').removeAttr('hidden');
                
                
            }
        });
    });
    
    
    $('#generarCsv').click(function(){
        //ocultamos los datos de informacion
        $('#mensajeError').attr('hidden',true);
        $('#mensajeSuccess').attr('hidden',true);
        $('#generarCsv').removeAttr('hidden');
        
        //almacena los datos del radio button
        var datoDepoRetiro = "";
        //
        if($('#deposito:checked').val() == 'on'){
           datoDepoRetiro ="deposito"
        }else if($('#retiro:checked').val() == 'on'){
            datoDepoRetiro ="retiro"
        }
        
        
        var fechaInicio = $('#fechaInicio').val();
        var fechaFin = $('#fechaFin').val();
        $.post('../GenerarReporte',{
            fechaInicio:fechaInicio,
            fechaFin: fechaFin,
            depostioOretiro: datoDepoRetiro,
            accion: "generarCsv"
        },function(result){
            
            if(result=="errorFechaInicio"){
                $('#mensajeError').html("Error en la fecha de inicio");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFechaFin'){
                $('#mensajeError').html("Error en la fecha final");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFecha'){
                $('#mensajeError').html("Error de fecha reingresela de nuevo");
                $('#mensajeError').removeAttr('hidden');
            }else if(result=='errorFechaInicioerrorFechaFin'){
                $('#mensajeError').html("Ingrese una fecha de inicio y fin");
                $('#mensajeError').removeAttr('hidden');
            }else  if(result=='errorBusquedaNoEncontrada'){
                $('#mensajeError').html("Busqueda no encontrada");
                $('#mensajeError').removeAttr('hidden');
            }else  if(result=='errorCsv'){
                $('#mensajeError').html("Error al generar Csv");
                $('#mensajeError').removeAttr('hidden');
            }else if(result.length >0){
                window.console.log(result);
                window.console.log(result.length);
                var tBody ="";
                for (var i = 0; i < result.length; i++) {
                    tBody+="<tr>";
                    for (var j = 0; j < 6; j++) {
                        tBody+="<td>"+result[i][j]+"</td>";
                    }
                    tBody+="</tr>";
                }
                $('#cuerpoTablaReporte').html(tBody);
                $('#mensajeSuccess').html("Datos encontrados los datos mostrados han sido limitados para no generar carga en en el dispositivo pero al generar el csv los datos no se veran afectados ");
                $('#mensajeSuccess').removeAttr('hidden');
                $('#generarCsv').removeAttr('hidden');
                
                
            }
        });
    });
    
    
    
})
 
    
    

</script>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Reportes</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-12">
                    <div class="card p-4">
                        <div id="mensajeError" class="alert alert-danger" role="alert" hidden>
                        </div>
                        <div id="mensajeSuccess" class="alert alert-success" role="alert" hidden>
                        </div>


                        <div class="row mt-5 mb-5">
                            <div class="col col-md-6">
                                <label for="bday">Seleccione fecha de inicio:</label>
                                <input type="date" id="fechaInicio" name="bday" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">
                                <span class="validity"></span>
                            </div>
                            <div class="col col-md-6">
                                <label for="bday">Seleccione fecha fin:</label>
                                <input type="date" id="fechaFin" name="bday" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">
                                <span class="validity"></span>
                            </div>
                        </div>
                        
                        <div class="row mb-5">
                            <label>¿El reporte a generar es de?</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="deposito" checked>
                                <label class="form-check-label" for="deposito">
                                    Deposito
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="retiro" >
                                <label class="form-check-label" for="retiro">
                                    Retiro
                                </label>
                            </div>
                        </div>
                        
                        <div class="mb-3 row">
                            <input id="generar" class="btn btn-primary" value="Generar Reporte"/>
                        </div>
                        <div class="mb-3 row">
                            <input id="generarCsv" class="btn btn-success" value="Generar Csv" hidden/>
                        </div>
                        
                        
                        <div class="row">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">TIPO DE OPERACIÓN</th>
                                        <th scope="col">Numero de cuenta</th>
                                        <th scope="col">Nombre del socio</th>
                                        <th scope="col">Cedula del socio</th>
                                        <th scope="col">FECHA</th>
                                        <th scope="col">MONTO</th>
                                    </tr>
                                </thead>
                                <tbody id="cuerpoTablaReporte">

                                </tbody>
                            </table>
                            
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </main>









    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>