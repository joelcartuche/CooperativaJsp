

<%@page import="Modelos.Rol"%>
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
                        Habilitar usuario
                        <%
                        } else {
                        %>
                        Eliminar usuario
                        <%
                            }
                        %>
                    </h5>
                </div>
                <div class="modal-body">
                    <%
                        if(Boolean.parseBoolean(esEliminado)){
                    %>
                        ¿Desea habilitar el usuario?
                    <%
                        }else{
                    %>
                        ¿Desea desactivar el usuario?
                    <%
                        }
                    %>
                    
                </div>
                <div class="modal-footer">
                    <input  type="button" class="btn btn-secondary" id="no" value="NO"/>
                    <input type="button" class="btn btn-primary" id="si" value="SI"/>
                </div>
            </div>
        </div>
    </div>
      <script>
        var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
        myModal.show();
        $(document).ready(function() {            
            $('#si').click(function (event){
                $.post('../BorrarUsuario',{
                    id:<%=id%>,
                    esEliminado:<%=esEliminado%>,
                    si:1,
                    no:0
                },function(resultado) {
                    var data = JSON.parse(resultado);
                    var si = data.si;
                    if(si!=null){
                        if(si==1){
                            window.location.replace("listarUsuario.jsp");
                        }
                    }
                });
            });
            $('#no').click(function (event){
                window.location.replace("listarUsuario.jsp");
            });
        });
        
    </script>
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
