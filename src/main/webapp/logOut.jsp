<%-- 
    Document   : logOut
    Created on : 15 mar. 2022, 21:33:59
    Author     : joelc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log out</title>
    </head>
    <body>
        <%
            HttpSession sesion=request.getSession();
            sesion.invalidate();
            response.sendRedirect("login.jsp");
        %>
    </body>
</html>
