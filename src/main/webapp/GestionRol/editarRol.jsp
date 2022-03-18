<%-- 
    Document   : editarRol
    Created on : 15 mar. 2022, 15:42:58
    Author     : joelc
--%>

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
        
        String id = request.getParameter("id");
        RolJpaController rolJpaController = new RolJpaController();
        Rol rol = rolJpaController.findRol(Integer.parseInt(id));
    %>

    <jsp:include page="../Template/cabecera.jsp"></jsp:include>
    
    <script>
            $(document).ready(function(event) {
                
                $('#guardar').click(function(){
                    var tipoRol = $('#tipoRol').val();
                    var esEliminado = $('#esEliminado').val();
                    $.post('../EditarRol',
                    {
                        id:<%= rol.getIdRol() %>,
                        tipoRol:tipoRol,
                        esEliminado:esEliminado
                        
                    },function(resultado) {
                        var data = JSON.parse(resultado);
                        var succes = data.success;
                        if(succes ==1){
                            window.console.log("Registro actualizado");
                            window.location.replace("listarRol.jsp");
                        }
                        if(succes ==0){
                            window.console.log("Error en la actualizacion");
                        }
                    });
                });
                $('#cancelar').click(function(){
                window.location.replace("listarRol.jsp");
                });
            });
    </script>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Rol</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-12">
                    <div class="card p-4">
                        <div class="mb-3 row">
                            <label for="staticEmail" class="col-sm-2 col-form-label">Tipo de rol</label>
                            <input id="tipoRol" name="tipoRol" class="form-input" value="<%=rol.getTipoRol()%>" id="flexCheckDefault"/>
                        </div>
                        <div class="mb-3 row">
                            <div class="form-check">
                                <label class="form-check-label" for="flexCheckDefault">
                                    Desactivado
                                </label>
                                <input id="esEliminado" name="esEliminado" class="form-check-input" type="checkbox" value="<%=rol.getEsEliminado()%>" id="flexCheckDefault"/>
                            </div> 
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

        
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
