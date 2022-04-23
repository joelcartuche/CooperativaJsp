

<%@page import="Modelos.Cuenta"%>
<%@page import="Controladores.CuentaJpaController"%>
<%@page import="Modelos.Rol"%>
<%@page import="java.util.List"%>
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

    <%
        CuentaJpaController cuentaJpaController = new CuentaJpaController();
        List<Cuenta> listaCuenta = cuentaJpaController.findCuentaEntities();        
    %>
    
    
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Cuenta</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 col-md-9">
                        <div class="card p-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" >
                                            <a href="crearCuenta.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th scope="col">N°</th>
                                        <th scope="col">Usuario</th>
                                        <th scope="col">Password</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Funciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Cuenta cuenta : listaCuenta) {
                                    %>
                                    <tr>
                                        <th scope="row"><%=cuenta.getIdCuenta()%></th>
                                        <td><%= cuenta.getUsuario() %></td>
                                        <td><%= cuenta.getPassword()%></td>
                                        <%
                                            if (cuenta.getEsEliminado()) {
                                        %>
                                        <td><i class="fa fa-ban" aria-hidden="true"></i> Inactivo</td>
                                        <td>
                                            <a type="button" href="editarCuenta.jsp?id=<%=cuenta.getIdCuenta()%>"  class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                            <a type="button" href="borrarCuenta.jsp?id=<%=cuenta.getIdCuenta()%>&esEliminado=<%=cuenta.getEsEliminado()%>" class="btn btn-success"><i class="fa fa-check-circle" aria-hidden="true"></i> Activar </a>

                                        </td>
                                        <%
                                        } else {
                                        %>
                                        <td><i class="fa fa-check" aria-hidden="true"></i> Activo</td>
                                        <td>
                                            <a type="button" href="editarCuenta.jsp?id=<%=cuenta.getIdCuenta() %>" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>

                                            <a type="button" href="borrarCuenta.jsp?id=<%=cuenta.getIdCuenta()%>&esEliminado=<%=cuenta.getEsEliminado() %>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i>Desactivar</a>
                                        </td>
                                        <%
                                            }
                                        %>

                                    </tr>
                                    <%                }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>


    
    
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
