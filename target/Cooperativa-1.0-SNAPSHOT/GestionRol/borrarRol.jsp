<%-- 
    Document   : borrarRol
    Created on : 14 mar. 2022, 19:13:17
    Author     : joelc
--%>

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
    <div class="modal fade" id="exampleModal" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Eliminar rol</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ¿Desea eliminar el rol?
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
                $.get('../BorrarRol',{
                    id:<%=id%>,
                    esEliminado:<%=esEliminado%>,
                    si:1,
                    no:0
                },function(resultado) {
                    var data = JSON.parse(resultado);
                    var si = data.data.si;
                    if(si!=null){
                        if(si==1){
                            window.location.replace("listarRol.jsp");
                        }
                    }
                });
            });
            $('#no').click(function (event){
                $.get('../BorrarRol',{
                    si:0,
                    no:1
                },function(resultado) {
                    var data = JSON.parse(resultado);
                    var no = data.data.no;
                    if(no!=null){
                        if(no==1){
                            window.location.replace("listarRol.jsp");
                        }
                    }
                });
            });
        });
        
    </script>
    <jsp:include page="../Template/footer.jsp"></jsp:include>
</html>
