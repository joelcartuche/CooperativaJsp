<%-- 
    Document   : crearRol
    Created on : 15 mar. 2022, 21:43:16
    Author     : joelc
--%>

<%@page import="Controladores.RolJpaController"%>
<%@page import="Modelos.Rol"%>
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
    
    
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Rol</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 col-md-12">
                        <div class="card p-4">
                            <div class="row">
                                <div class="col-sm">
                                    <form  action="crearRol.jsp" method="post">
                                        <div class="form-group mb-3">
                                            <label for="nombre">Tipo de rol</label>
                                            <input type="text" class="form-control" id="tipoRol" name="tipoRol" placeholder="Nombre" required="required">
                                        </div>
                                        <button type="submit" name="enviar" class="btn btn-primary mt-5">Guardar <i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </main>

    <div class="container mt-5">
        
    </div>
    
    <%
        if (request.getParameter("enviar") != null) {
            String tipoRol = request.getParameter("tipoRol");
            Rol rol = new Rol();
            rol.setTipoRol(tipoRol);
            RolJpaController rolJpaController = new RolJpaController();
            rolJpaController.create(rol);
            request.getRequestDispatcher("listarRol.jsp").forward(request, response);
        }
    %>
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
