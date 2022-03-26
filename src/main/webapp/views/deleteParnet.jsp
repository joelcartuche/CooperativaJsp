<%-- 
    Document   : deleteParnet
    Created on : 19 mar 2022, 4:56:59
    Author     : jede
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/cabecera.jsp"></jsp:include>

<%
    HttpSession sesion2 = request.getSession();
    if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
    }
    
    String id = request.getParameter("id");
    String esEliminado = request.getParameter("esEliminado");
%>

<!-- Modal -->
<div class="modal fade" id="exampleModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                    <%
                        if (Boolean.parseBoolean(esEliminado)) {
                    %>
                    Activar Socio
                    <%
                    } else {
                    %>
                    Eliminar Socio
                    <%
                        }
                    %>
                </h5>
            </div>
            <div class="modal-body">
                <%
                    if (Boolean.parseBoolean(esEliminado)) {
                %>
                ¿Desea Activar Socio?
                <%
                } else {
                %>
                ¿Desea Desactivar Socio?
                <%
                    }
                %>

            </div>
            <div class="modal-footer">
                <input  type="button" class="btn btn-secondary" id="no" value="Cancelar"/>
                <%
                    if (Boolean.parseBoolean(esEliminado)) {
                %>
                <input type="button" class="btn btn-success" id="si" value="Si, activar"/>
                <%
                } else {
                %>
                <input type="button" class="btn btn-success" id="si" value="Si, desactivar"/>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
<script>
    let myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
    myModal.show();
    $(document).ready(function () {
        $('#no').click(function (event) {
            window.location.replace("Socio?accion=listar");
        });
        $('#si').click(function (event) {
            $.post("Socio?accion=eliminar", {
                id:<%=id%>,
                esEliminado:<%=esEliminado%>
            }, function (res) {
                if (res.message) {
                    window.location.replace("Socio?accion=listar");
                }
            });
        });
    });

</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>
