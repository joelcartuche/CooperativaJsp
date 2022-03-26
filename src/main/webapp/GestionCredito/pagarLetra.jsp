<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="Modelos.TasaAmortizacion"%>
<%@page import="Controladores.TasaAmortizacionJpaController"%>
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
        String fechaHoy = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

    %>
    
    <jsp:include page="../Template/cabecera.jsp"></jsp:include>
    
    <%
        String id = request.getParameter("id");
        TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
        TasaAmortizacion tasaAmortizacion = tasaAmortizacionJpaController.findTasaAmortizacion(Integer.parseInt(id));
        
    %>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                        Pagar letra
                    </h5>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col align-self-center">
                            ¿Desea realizar el pago?
                        </div>
                        
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            Fecha de pago (yyyy/MM/dd HH:mm:ss):
                        </div>
                    </div>   
                    <div class="row">
                        <div class="col-md-6 offset-md-3">
                            <%=fechaHoy%>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            Monto a pagar:
                        </div>
                    </div>   
                    <div class="row">
                        <div class="col-md-6 offset-md-3">
                            <%=tasaAmortizacion.getCuota() %>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label  class="col-sm-6 col-form-label">Interes forma de pago</label>
                            <input id="formaPago" class="form-control" type="text"/> 
                        </div>
                    </div> 
                </div>
                <div class="modal-footer">
                    <input  type="button" class="btn btn-secondary" id="no" value="NO"/>
                    <input type="button" class="btn btn-primary" id="si" value="SI"/>
                </div>
            </div>
        </div>
    </div>
      <script>
        var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
        myModal.show();
        $(document).ready(function() {
            $('#si').click(function (event){
                $.post('../PagarLetra',{
                    id:<%=id%>,
                    formaPago:$('#formaPago').val() 
                },function(resultado) {
                    if(resultado=="success"){
                        window.location.replace("listarCreditos.jsp");
                    }else if(resultado=="errorFormaPago"){
                        window.alert("Error no se ingreso una forma de pago ");
                    }else{
                        window.alert("Error al realizar el pago");
                    }
                });
            });
            $('#no').click(function (event){
                window.location.replace("listarCreditos.jsp");
            });
        });
        
    </script>
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
