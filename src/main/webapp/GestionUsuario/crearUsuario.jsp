<%-- 
    Document   : editarUsuario
    Created on : 16 mar. 2022, 16:48:00
    Author     : joelc
--%>

<%@page import="Modelos.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
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
        function removerErrores(){
            
            $('#nombre').removeClass("is-invalid");
            $('#nombre').removeAttr("aria-describedby","errorNombre")
            $('#apellido').removeClass("is-invalid");
            $('#apellido').removeAttr("aria-describedby","errorApellido")
            $('#cedula').removeClass("is-invalid");
            $('#cedula').removeAttr("aria-describedby","errorCedula")
            $('#telefono').removeClass("is-invalid");
            $('#telefono').removeAttr("aria-describedby","errorTelefono")
            $('#errorNombre').hide();
            $('#errorApellido').hide();
            $('#errorCedula').hide();
            $('#errorTelefono').hide();
            $('#errorNombreVacio').hide();
            $('#errorApellidoVacio').hide();
            $('#errorCedulaVacio').hide();
            $('#errorTelefonoVacio').hide();
        }
            $(document).ready(function(event) {
                $('#guardar').click(function(){
                    //removemos los errores
                    removerErrores();
                    //instanciamos los datos
                    var nombre = $('#nombre').val();
                    var apellido = $('#apellido').val();
                    var cedula = $('#cedula').val();
                    var telefono = $('#telefono').val();
                    var esEliminado = $('#esEliminado').val();
                    console.log(nombre);
                    $.post('../CrearUsuario',
                    {
                        nombre:nombre,
                        apellido:apellido,
                        cedula:cedula,
                        telefono:telefono,
                        esEliminado:esEliminado
                        
                    },function(resultado) {
                        var data = JSON.parse(resultado);
                        var succes = data.success;
                        var error= data.error;
                        console.log(data);
                        console.log(error);
                        console.log("succes"+succes);
                        if(error ==1){
                            $('#errorNombre').show();
                            $('#nombre').addClass("is-invalid");
                            $('#nombre').attr("aria-describedby","errorNombre")
                        }else if(error == 2){
                            $('#errorApellido').show();
                            $('#apellido').addClass("is-invalid");
                            $('#apellido').attr("aria-describedby","errorApellido")
                        }else if(error ==3){
                            $('#errorCedula').show();
                            $('#cedula').addClass("is-invalid");
                            $('#cedula').attr("aria-describedby","errorCedula")
                        }else if(error ==4){
                            $('#errorTelefono').show();
                            $('#telefono').addClass("is-invalid");
                            $('#telefono').attr("aria-describedby","errorTelefono")
                        }else if(error ==5){
                            $('#errorNombreVacio').show();
                            $('#nombre').addClass("is-invalid");
                            $('#nombre').attr("aria-describedby","errorNombreVacio")
                        }else if(error == 6){
                            $('#errorApellidoVacio').show();
                            $('#apellido').addClass("is-invalid");
                            $('#apellido').attr("aria-describedby","errorApellidoVacio")
                        }else if(error == 7){
                            $('#errorCedulaVacio').show();
                            $('#cedula').addClass("is-invalid");
                            $('#cedula').attr("aria-describedby","errorCedulaVacio")
                        }else if(error ==8){
                            $('#errorTelefonoVacio').show();
                            $('#telefono').addClass("is-invalid");
                            $('#telefono').attr("aria-describedby","errorTelefonoVacio")
                        }else if(succes ==1){
                            window.console.log("Registro actualizado");
                            window.location.replace("listarUsuario.jsp");
                        }else if(succes ==0){
                            removerErrores();
                            window.console.log("Error en la actualizacion");
                        }
                        
                    });
                });
                $('#cancelar').click(function(){
                window.location.replace("listarUsuario.jsp");
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
                            <label for="staticEmail" class="col-sm-2 col-form-label">Nombre</label>
                            <input id="nombre" name="nombre" class="form-control" placeholder="Ingrese nombre" id="flexCheckDefault" required/>
                            <div id="errorNombre" class="invalid-feedback">
                                El nombre no puede contener números
                            </div>
                            <div id="errorNombreVacio" class="invalid-feedback">
                                Ingrese nombre
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="staticEmail" class="col-sm-2 col-form-label">Apellido</label>
                            <input id="apellido" name="apellido" class="form-control" placeholder="Ingrese apellido" id="flexCheckDefault" required/>
                            <div id="errorApellido" class="invalid-feedback">
                                El apellido no puede contener números
                            </div>
                            <div id="errorApellidoVacio" class="invalid-feedback">
                                Ingrese apellido
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="staticEmail" class="col-sm-2 col-form-label">Cédula</label>
                            <input id="cedula" name="cedula" class="form-control" placeholder="Ingrese cédula" id="flexCheckDefault" required/>
                            <div id="errorCedula" class="invalid-feedback">
                                Cédula no válida
                            </div>
                            <div id="errorCedulaVacio" class="invalid-feedback">
                                Ingrese cédula
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="staticEmail" class="col-sm-2 col-form-label">Teléfono</label>
                            <input id="telefono" name="telefono" class="form-control" placeholder="Ingrese teléfono" id="flexCheckDefault" required/>
                            <div id="errorTelefono" class="invalid-feedback">
                                Teléfono no válido.
                            </div>
                            <div id="errorTelefonoVacio" class="invalid-feedback">
                                Ingrese teléfono
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