<%-- 
    Document   : index.jsp
    Created on : 13 mar. 2022, 16:11:21
    Author     : joelc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("logueado") == null || sesion.getAttribute("logueado").equals("0")) {
            response.sendRedirect("login.jsp");
        }
    %>
    <jsp:include page="/Template/cabecera.jsp"></jsp:include>
    <%
        String id = request.getParameter("id");
        String nombre = request.getParameter("user");
        
    %>
        <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Bienvenido <%=nombre%></div>
            </div>
        </div>
    </main>
    
    
    <jsp:include page="/Template/footer.jsp"></jsp:include>
</html>
