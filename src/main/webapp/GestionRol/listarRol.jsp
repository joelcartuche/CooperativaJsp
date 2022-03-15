<%-- 
    Document   : crearRol
    Created on : 14 mar. 2022, 17:37:31
    Author     : joelc
--%>

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
    <div class="container mt-5" >
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Rol</th>
                    <th scope="col">Tipo de rol</th>
                    <th scope="col">Está eliminado</th>
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
                    <td><i class="fa fa-check" aria-hidden="true"></i></td>
                    <td>
                        <a type="button" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                        <a type="button" href="borrarRol.jsp?id=<%=rol.getIdRol()%>&esEliminado=<%=rol.getEsEliminado() %>" class="btn btn-danger"><i class="fa fa-check-circle" aria-hidden="true"></i> </a>
                    </td>
                    <%
                    } else {
                    %>
                    <td><i class="fa fa-ban" aria-hidden="true"></i></td>
                    <td>
                        <a type="button" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                        <a type="button" href="borrarRol.jsp?id=<%=rol.getIdRol()%>&esEliminado=<%=rol.getEsEliminado() %>" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i> </a>
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

    
    
    
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
