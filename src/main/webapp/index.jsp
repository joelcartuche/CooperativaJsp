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
            String id = request.getParameter("id");
            String nombre = request.getParameter("user");
            
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <title>Login</title>
    </head>
    <body>
        <h1>HOLA COMO ESTAS <%=nombre%> </h1>
    </body>    
</html>
