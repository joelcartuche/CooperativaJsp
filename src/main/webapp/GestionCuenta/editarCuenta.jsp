<%-- 
    Document   : editarCuenta
    Created on : 16 mar. 2022, 21:19:19
    Author     : joelc
--%>

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
        
        String id = request.getParameter("id");
        String idUsuario = request.getParameter("idUsuario");
        String idRol = request.getParameter("idRol");
        CuentaJpaController cuentaJpaController = new CuentaJpaController();
        Cuenta cuenta = cuentaJpaController.findCuenta(Integer.parseInt(id));
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        Usuario usuario =  usuarioJpaController.findUsuario(cuenta.getIdUsuario().getIdUsuario());
        RolJpaController rolJpaController = new RolJpaController();
        Rol rol = rolJpaController.findRol(cuenta.getIdRol().getIdRol());
        

    %>

    <jsp:include page="../Template/cabecera.jsp"></jsp:include>
    
    <script>
            $(document).ready(function(event) {
                
                $('#guardar').click(function(){
                    var usuario = $('#usuario').val();
                    var clave1 = $('#clave1').val();
                    var clave2 = $('#clave2').val();
                    $.post('../EditarCuenta',
                    {
                        id:<%= cuenta.getIdCuenta() %>,
                        usuario: usuario,
                        clave1: clave1,
                        clave2: clave2,
                        idUsuario:<%=idUsuario%>,
                        idRol: <%=idRol%>
                        
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
                            <label class="col-sm-2 col-form-label">Tipo de rol</label>
                            <input id="tipoRol" name="usuario" class="form-input" value="<%=cuenta.getUsuario() %>" id="flexCheckDefault"/>
                        </div>
                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Tipo de rol</label>
                            <input id="tipoRol" name="clave1" class="form-input"  id="flexCheckDefault"/>
                        </div>
                        <div class="mb-3 row">
                            <label class="col-sm-2 col-form-label">Tipo de rol</label>
                            <input id="tipoRol" name="clave2" class="form-input" id="flexCheckDefault"/>
                        </div>
                        <div class="mb-3 row possition-relative">
                            <label  class="col-sm-2 col-form-label">Usuario</label>
                            <input id="usuarioActual" name="usuarioActual" class="form-input position-absolute top-0 start-0" value="<%=usuario.getNombreUsuario() %> <%=usuario.getApellidoUsuario() %>" id="flexCheckDefault"/>
                            <input id="buscarUsuario" class="btn btn-outline-success position-absolute top-0 start-100" data-bs-toggle="modal" data-bs-target="#modalUsuario" value="Buscar usuario"/>
                        </div>
                        <div class="mb-3 row ">
                            <label  class="col-sm-2 col-form-label">Rol</label>
                            <input id="rol" name="rol" class="form-input position-absolute top-0 start-0" value="<%=rol.getTipoRol()%>" id="flexCheckDefault"/>
                            <input id="buscarRol" class="btn btn-outline-success position-absolute top-0 start-50" data-bs-toggle="modal" data-bs-target="#modalRol" value="Buscar rol"/>
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
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
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
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

        
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>