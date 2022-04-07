

<%@page import="Modelos.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
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
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        List<Usuario> listaUsuario = usuarioJpaController.findUsuarioEntities();
        
    %>
    
    
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12 fw-bold fs-3 text-center">Gestión  Usuario</div>
                </div>
                <div class="row mt-4 mb-5 justify-content-center">
                    <div class="col-12 col-md-12 ">
                        <div class="card p-4">
                            <table class="table col-12">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" class="text-center"></th>
                                        <th scope="col" >
                                            <a href="crearUsuario.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i> Crear</a>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th scope="col">N°</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Apellido</th>
                                        <th scope="col">Cédula</th>
                                        <th scope="col">teléfono</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Usuario usuario : listaUsuario) {
                                    %>
                                    <tr>
                                        <th scope="row"><%=usuario.getIdUsuario()%></th>
                                        <td><%= usuario.getNombreUsuario()%></td>
                                        <td><%= usuario.getApellidoUsuario()%></td>
                                        <td><%= usuario.getCedulaUsuario()%></td>
                                        <td><%= usuario.getTelefonoUsuario()%></td>
                                        <%
                                            if (usuario.getEsEliminado()) {
                                        %>
                                        <td><i class="fa fa-ban" aria-hidden="true"></i> Inactivo</td>
                                        <td>
                                            <a type="button" href="editarUsuario.jsp?id=<%=usuario.getIdUsuario()%>"  class="btn btn-primary mb-2 col-12"><i class="fa fa-pencil" aria-hidden="true"></i> Editar</a>
                                            <a type="button" href="borrarUsuario.jsp?id=<%=usuario.getIdUsuario()%>&esEliminado=<%=usuario.getEsEliminado()%>" class="btn btn-success col-12"><i class="fa fa-check-circle" aria-hidden="true"></i> Activar </a>

                                        </td>
                                        <%
                                        } else {
                                        %>
                                        <td><i class="fa fa-check" aria-hidden="true"></i> Activo</td>
                                        <td>
                                            <a type="button" href="editarUsuario.jsp?id=<%=usuario.getIdUsuario()%>" class="btn btn-primary mb-2 col-12"><i class="fa fa-pencil" aria-hidden="true"></i> Editar</a>
                                            <a type="button" href="borrarUsuario.jsp?id=<%=usuario.getIdUsuario()%>&esEliminado=<%=usuario.getEsEliminado()%>" class="btn btn-danger col-12"><i class="fa fa-trash-o fa-lg"></i>Desactivar</a>
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
