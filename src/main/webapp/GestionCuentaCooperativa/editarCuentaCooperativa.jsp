
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    HttpSession sesion2 = request.getSession();
    if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
            response.sendRedirect("../login.jsp");
    }

    boolean esEliminado = (Boolean) request.getAttribute("esEliminado");
%>

<main>
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Editar Cuenta Cooperativa</div>
        </div>

        <div class="row">
            <div class="col-md-12 fs-3 text-center">N°: ${cuenta.getNumeroCuenta()} </div>
        </div>
        
        <div class="row">
            <div class="col-md-12 fs-3 text-center">Socio: ${socioNombre} ${socioApellido} </div>
        </div>

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-8">
                <div id="errorlAlert" class="alert alert-danger" role="alert" style="display: none">
                    A simple danger alert
                </div>
                <div class="card p-4 shadow rounded-3">

                    <form id="formdata">
                        <input type="hidden" name="id_cuenta_cooperativa" value="${cuenta.getIdCuentaCooperativa()}"/>
                        <div class="mb-3">
                            <label for="nombreCuenta" class="form-label">Nombre de la Cuenta: </label>
                            <select name="nombreCuenta" id="nombreCuenta" class="form-select" aria-label="Estado del Socio">
                                <option value="ahorros" class="my-estado-inactivo">Ahorro</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="estadoCuenta" class="form-label">Estado: </label>
                            <% if (!esEliminado) {%>
                            <span class="my-estado-activo">
                                Activo
                            </span>
                            <%
                            } else {
                            %>
                            <span class="my-estado-inactivo">
                                Inactivo
                            </span>
                            <%
                                }
                            %>
                            <select name="estadoCuenta" id="estadoCuenta" class="form-select" aria-label="Estado del Socio">
                                <option selected>Seleccione una opción</option>
                                <option value="true" class="my-estado-inactivo">Desactivar</option>
                                <option value="false" class="my-estado-activo">Activar</option>
                            </select>
                        </div>
                        <button type="button" id="botonenviar" class="btn btn-primary">Actualizar Datos</button>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
        <div id="liveToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body" id="message">
                    Hello, world! This is a toast message.
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>

</main>
<script>
    $(document).ready(function () {

        // Con esto establecemos la acción por defecto de nuestro botón de enviar.
        $("#botonenviar").click(function () {
            // enviamos la peticion por el metodo POST
            $.post("CuentasCooperativa?accion=editar", $("#formdata").serialize(), function (res) {
                // si existe un error en los datos, se presenta un alert        
                if (res.error) {
                    $('#errorlAlert').show();
                    $('#errorlAlert').text(res.error);
                }
                // si el proceso es exito, se presenta un toast
                if (res.message) {
                    // se oculta los mensajes de error
                    $('#errorlAlert').hide();
                    // se presenta un toast
                    $('#message').text(res.message);
                    $('#liveToast').toast('show');
                }
            }).fail(function (error) {// si existe un error de servidor, se presenta una alert
                $('#errorlAlert').show();
                $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
            });
        });
    });
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>