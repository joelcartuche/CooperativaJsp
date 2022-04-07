

<%@page import="Modelos.TasaAmortizacion"%>
<%@page import="Controladores.TasaAmortizacionJpaController"%>
<%@page import="Modelos.Credito"%>
<%@page import="Controladores.CreditoJpaController"%>
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
        String id = request.getParameter("id");
        CreditoJpaController creditoJpaController = new CreditoJpaController();
        Credito credito = creditoJpaController.findCredito(Integer.parseInt(id));
        TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
        List<TasaAmortizacion>  tasaAmortizacionList = tasaAmortizacionJpaController.findTasaAmortizacionIdCredito(credito);
        String dataTasaAmortizacion=  "\"";
        for (int i = 0; i < tasaAmortizacionList.size(); i++) {
            dataTasaAmortizacion += tasaAmortizacionList.get(i).getSaldoDeuda()+";";
            dataTasaAmortizacion += tasaAmortizacionList.get(i).getCuota()+";";
            dataTasaAmortizacion += tasaAmortizacionList.get(i).getInteres()+";";
            dataTasaAmortizacion += tasaAmortizacionList.get(i).getAmortizacion();
            
            if (i<tasaAmortizacionList.size()-1) {
                    dataTasaAmortizacion +=";";
            }
            
        }
        dataTasaAmortizacion+=  "\"";

        

    %>

    <jsp:include page="../Template/cabecera.jsp"></jsp:include>

<script>        
$(document).ready(function(event) {
    
    //cargamos los datos
    $('#montoCredito').val(<%=credito.getMontoCredito()%>);
    
    $('#plazoMeses').val(<%=tasaAmortizacionList.size() %>);
    var totalInteres =0;
    var totalPagar=0;
    window.console.log("///");
    
    var dataTasaAmortizacionAux= <%=dataTasaAmortizacion%>;
    window.console.log(dataTasaAmortizacionAux+"///");
    var dataTasaAmortizacion = dataTasaAmortizacionAux.split(";");
    var cuerpoTabla ="";
    var cont =0;
    
    //leemos los dataos de la tabla de amortizacion
    for (var i = 0; i < dataTasaAmortizacion.length; i+=4) {
        cuerpoTabla+="<tr>";
        cont+=1;

        cuerpoTabla+="<td>"+cont+"</td>"; //primera columna
        //retorna lee los datos asata solo asta la cuarta columna
        for (var j = i; j<i+4; j++) {
            
            cuerpoTabla+="<td>"+dataTasaAmortizacion[j]+"</td>";
            if(j==i+2){
                totalInteres = totalInteres+ parseFloat(dataTasaAmortizacion[j]);
            }
            if(j==i+3){
                totalPagar = totalPagar+ parseFloat(dataTasaAmortizacion[j]);
            }
        }   
        cuerpoTabla+="</tr>"; 
    }
    
    //cargamos los datos en los id correspondientes
    $('#totalInteres').html(totalInteres);
    $('#totalPagado').html(totalPagar);
    $('#generacionTasaAmortizacion').html(cuerpoTabla);
    
    //quitamos el alert de la tasa de amortizacion
    $('#alertaTasaAmortizacion').removeClass("alert-danger");
    $('#alertaTasaAmortizacion').addClass("alert-success");
    $('#alertaTasaAmortizacion').html("Se selecciono la tasa de amortización");
    
    
    //cargamos los datos del socio
    $('#idSocioActual').val('<%=credito.getIdCodigoSocio().getIdSocios().toString() %>');
    $('#socio').val('<%=credito.getIdCodigoSocio().getNombreSocio()%>' +' '+'<%=credito.getIdCodigoSocio().getApellidoSocio() %>');
    
    
    //cargamos los datos del socio en el modal
    var tBody="";
    var datoIdNombreApellido ='<%=credito.getIdCodigoSocio().getIdSocios().toString() %>' +";"+'<%=credito.getIdCodigoSocio().getNombreSocio()%>'+" "+'<%=credito.getIdCodigoSocio().getApellidoSocio() %>';

    tBody=tBody+"<tr>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getIdSocios() %>'+"</td>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getNombreSocio()%>'+"</td>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getApellidoSocio()%>'+"</td>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getCedulaSocio()%>'+"</td>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getTelefonoSocio()%>'+"</td>";
    tBody=tBody+"<td>"+'<%=credito.getIdCodigoSocio().getDireccionSocio()%>'+"</td>";
    tBody=tBody+'<td> <input id="idSocio'+'<%=credito.getIdCodigoSocio().getIdSocios() %>'+'" type="button" onclick="actualizacionIdSocio('+datoIdNombreApellido+')" class="boton btn btn-secondary" data-bs-dismiss="modal" value="Seleccionar" input/> </td>';
    tBody=tBody+"</tr>";                    

    $('#coicidenciaBussquedaSocio').html(tBody);
    
    

    function ocultarToast(){
        //ocultamos el toast
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
            window.console.log(result);
            
            if(result== "errorMontoVacio"){
                $('#montoCredito').addClass("is-invalid");
                $('#mensajeErrorToast').html("Ingrese un monto para calcular la tasa de amortización");
                $('#modalTazaAmortizacion').modal('hide');

            }else if(result == "errorPlazoVacio"){
                $('#plazoMeses').addClass("is-invalid");
                $('#mensajeErrorToast').html("Ingrese un plazo para calcular la tasa de amortización");

            }else if(result.length >0){
                dataTasaAmortizacion= result; //lo pasamos a la variable global
                var tBody ="";
                totalInteres =0;
                totalPagar=0;
                for (var i = 0; i < result.length; i++) {
                    tBody+="<tr>";
                    tBody+="<td>"+(i+1)+"</td>";
                    for (var j = 0; j<4; j++) {
                        tBody+="<td>"+result[i][j]+"</td>";
                        if(j==2){
                            totalInteres = totalInteres+result[i][j];
                        }
                        if(j==3){
                            totalPagar = totalPagar+result[i][j];
                        }
                    }   
                    tBody+="</tr>"; 
                }
                $('#totalInteres').html(totalInteres);
                $('#totalPagado').html(totalPagar);
                $('#generacionTasaAmortizacion').html(tBody);
            }
        });
    });
    
    $('#guardarTasaAmortizacion').click(function(){
       
        if(totalPagar>0){
            $('#alertaTasaAmortizacion').removeClass("alert-danger");
            $('#alertaTasaAmortizacion').addClass("alert-success");
            $('#alertaTasaAmortizacion').html("Se selecciono la tasa de amortización");
            
        }else{
            $('#alertaTasaAmortizacion').removeClass("alert-success");
            $('#alertaTasaAmortizacion').addClass("alert-danger");
            $('#alertaTasaAmortizacion').html("No se ha generado ninguna tasa de amortización");

        }
    });
   
    
    $('#btnBuscarSocio').click(function (){
        $('#errorBuscarSocio').attr("hidden",true);
        $('#coicidenciaBussquedaSocio').html("");
        var cedula = $('#cedulaSocioBuscar').val();
        $.get('../BuscarSocioCedula',{
            cedula:cedula
        },function(resultado){
            window.console.log(resultado.toString());
            if(resultado == "error"){
                $('#errorBuscarSocio').attr("hidden",false);
            }else if(resultado.length >0){
                var data = resultado[0];
                var tBody="";
                var i =0;
                var datoIdNombreApellido ="'"+data[i] +";"+data[i+1]+" "+data[i+2]+"'";
                
                tBody=tBody+"<tr>";
                tBody=tBody+"<td>"+data[i]+"</td>";
                tBody=tBody+"<td>"+data[i+1]+"</td>";
                tBody=tBody+"<td>"+data[i+2]+"</td>";
                tBody=tBody+"<td>"+data[i+3]+"</td>";
                tBody=tBody+"<td>"+data[i+4]+"</td>";
                tBody=tBody+"<td>"+data[i+5]+"</td>";
                tBody=tBody+'<td> <input id="idSocio'+data[i]+'" type="button" onclick="actualizacionIdSocio('+datoIdNombreApellido+')" class="boton btn btn-secondary" data-bs-dismiss="modal" value="Seleccionar" input/> </td>';
                tBody=tBody+"</tr>";                    
                
                $('#coicidenciaBussquedaSocio').html(tBody);
                
            }
            
        });
    });
    
    $('#cancelar').click(function (){
        window.location.replace("listarCreditos.jsp");
    });
    
    function ocultarCampos(){
        $('#montoCredito').removeClass("is-invalid");
        $('#mensajeError').attr("hidden",true);
        $('#mensajeSuccess').attr("hidden",true);

    }
    $('#guardar').click(function (){
        ocultarCampos();
        var monto = $('#montoCredito').val();
        var idSocio = $('#idSocioActual').val();
        $.post('../EditarCredito',{
            idCredito: <%=credito.getIdCredito()%>,
            monto: monto,
            idSocio: idSocio,
            totalPagar: totalPagar,
            dataTasaAmortizacion: dataTasaAmortizacion.toString(),
        },function(result){
            if(result=="errorMonto"){
                $('#montoCredito').addClass("is-invalid");
            }else if(result=="errorTasa"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error no se ha generado una tasa de amortización");
            }else if(result=="errorSocio"){
                $('#mensajeError').attr("hidden",false);
                $('#mensajeError').html("Error no se ha seleccionado ningun socio");
            }if(result=="success"){
                $('#mensajeSuccess').attr("hidden",false);
                window.location.replace("listarCreditos.jsp");
            }
        });
    });    
});



</script>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Editar crédito</div>
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
                            <div id="alertaTasaAmortizacion" class="alert alert-danger" role="alert">No se ha generado ninguna tasa de amortización</div>
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
                                    <th  class="text-center" colspan="3">
                                        <input id="btnGenerarTasaAmortizacionModal" class="btn btn-primary" value="Generar"/>
                                    </th>
                                </tr>
                                <tr>
                                    <th scope="col"  class="text-center" ></th>
                                    <th scope="col" class="text-center" ></th>
                                    <th scope="col" class="text-center" ></th>
                                    <th scope="col" class="text-center" >Total interes</th>
                                    <th scope="col" class="text-center" >Total pagado</th>
                                </tr>
                                <tr>
                                    <td class="text-center" ></td>
                                    <td class="text-center" ></td>
                                    <td class="text-center" ></td>
                                    <td class="text-center" id="totalInteres"></td>
                                    <td class="text-center" id="totalPagado"></td>
                                </tr>
                                <tr>
                                    <th scope="col">Periodo</th>
                                    <th scope="col">Saldo deuda</th>
                                    <th scope="col">Cuota</th>
                                    <th scope="col">Interes</th>
                                    <th scope="col">Amortizacion</th>
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
  <div class="modal-dialog modal-fullscreen">
    <div class="modal-content ">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Seleccionar socio</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
            <div id="errorBuscarSocio" class="alert alert-danger" role="alert" hidden>
                No se encontraron coincidencias
            </div>
            <div class="row">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col" colspan="2" >
                                <input id="cedulaSocioBuscar" name="cedulaSocio" class="form-control " placeholder="Ingrese número de cédula del socio" id="flexCheckDefault" />
                            </th>
                            <th scope="col" class="text-center" >
                                <input id="btnBuscarSocio" class="btn btn-primary" value="Buscar"/>
                            </th>
                        </tr>
                        <tr>
                            <th scope="col">N°</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Apellido</th>
                            <th scope="col">Cedula</th>
                            <th scope="col">Teléfono</th>
                            <th scope="col">Dirección</th>
                        </tr>
                    </thead>
                    <tbody id="coicidenciaBussquedaSocio">

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
    
    function actualizacionIdSocio(socioIdNombreApellido){
        var socioYnombre = socioIdNombreApellido.split(";");
        idDado = socioYnombre[0];
        nombreDado = socioYnombre[1];
        
        $('#idSocioActual').removeAttr("value");
        $('#idSocioActual').attr("value",idDado);
        $('#socio').val(nombreDado);

    };
</script>

        
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
