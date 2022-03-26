<%-- 
    Document   : index.jsp
    Created on : 13 mar. 2022, 16:11:21
    Author     : joelc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/Template/layout.jsp"></jsp:include>   
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("logueado") == null || sesion.getAttribute("logueado").equals("0")) {
        response.sendRedirect("login.jsp");
    }
%>
<main>
    <div class="container-fluid">
        <div class="row mt-4">
            <div class="col-md-12 fs-3 text-center">
                <h2>Bienvenido <span class="text-success"><%=sesion.getAttribute("nombre")%> <%=sesion.getAttribute("apellido")%></span></h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 fs-3 text-center">
                <span class="fw-bold">Rol: </span><span class="text-success"><%=sesion.getAttribute("rol")%></span>
            </div>
        </div>
    </div>
</main>


<jsp:include page="/Template/footer.jsp"></jsp:include>
