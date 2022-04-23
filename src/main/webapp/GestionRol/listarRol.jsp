

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
        RolJpaController rolJpaController = new RolJpaController();
        List<Rol> listaRol = rolJpaController.findRolEntities();
    %>
    
    
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Rol</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 ">
                        <div class="card p-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" >
                                            <a href="crearRol.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th scope="col">Rol</th>
                                        <th scope="col">Tipo de rol</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Funciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Rol rol : listaRol) {
                                    %>
                                    <tr>
                                        <th scope="row"><%=rol.getIdRol()%></th>
                                        <td><%= rol.getTipoRol()%></td>
                                        <%
                                            if (rol.getEsEliminado()) {
                                        %>
                                        <td><i class="fa fa-ban" aria-hidden="true"></i> Inactivo</td>
                                        <td>
                                            <a type="button" href="editarRol.jsp?id=<%=rol.getIdRol()%>"  class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                            <a type="button" href="borrarRol.jsp?id=<%=rol.getIdRol()%>&esEliminado=<%=rol.getEsEliminado()%>" class="btn btn-success"><i class="fa fa-check-circle" aria-hidden="true"></i> Activar </a>

                                        </td>
                                        <%
                                        } else {
                                        %>
                                        <td><i class="fa fa-check" aria-hidden="true"></i> Activo</td>
                                        <td>
                                            <a type="button" href="editarRol.jsp?id=<%=rol.getIdRol()%>" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>

                                            <a type="button" href="borrarRol.jsp?id=<%=rol.getIdRol()%>&esEliminado=<%=rol.getEsEliminado()%>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i>Desactivar</a>
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
