
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/layout.jsp"></jsp:include>

<%
    HttpSession sesion2 = request.getSession();
    if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
        response.sendRedirect("../login.jsp");
    }
%>

<main>
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Configuraciones</div>
        </div>

        <div id="cardSuccess" class="row mt-4 mb-3 justify-content-center" style="display: none">
            <div class="col-12 col-md-11">
                <div class="alert alert-success" role="alert">
                    Datos guardados con éxito.
                </div>
            </div>
        </div>

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-sm-6 col-md-4">
                <div id="errorlAlert" class="alert alert-danger" style="display: none" role="alert">
                    A simple danger alert
                </div>
                <div class="card p-4">
                    <form id="formdata">
                        <div class="mb-3">
                            <label for="interes_credito" class="form-label">Interés del crédito</label>
                            <input type="number" class="form-control" id="interes_credito" name="interes_credito" min="0" value="${interes_credito}">
                            <div id="error-interes_credito" class="invalid-feedback" style="display: none">
                                Ingresar el interés del crédito
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="monto_max_credito" class="form-label">Monto máximo del interés</label>
                            <input type="number" class="form-control" id="monto_max_credito" name="monto_max_credito" min="0" value="${monto_max_credito}">
                            <div id="error-monto_max_credito" class="invalid-feedback" style="display: none">
                                Ingresar el monto máximo del interés
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="plazo_max_meses" class="form-label">Monto máximo del interés</label>
                            <input type="number" class="form-control" id="plazo_max_meses" name="plazo_max_meses" min="0" value="${plazo_max_meses}">
                            <div id="error-plazo_max_meses" class="invalid-feedback" style="display: none">
                                Ingresar el plazo máximo del interés
                            </div>
                        </div>
                        <button id="btnSubmit" class="btn btn-outline-success" type="button">Guardar cambios realizados</button>
                    </form>

                </div>
            </div>
        </div>

    </div>
</main>
<script>
    $(document).ready(function () {
        // Con esto establecemos la acción por defecto de nuestro botón de enviar.
        $("#btnSubmit").click(function () {
            // ocultamos las alertas
            $('#cardSuccess').hide();
            // Primero validará el formulario.
            if (validaForm()) {
                // enviamos la peticion por el metodo POST
                let resultConfirm = confirm("¿Desea guardar los datos?")
                if (resultConfirm === true) {
                    // enviamos la peticion por el metodo POST
                    $.post("Configuraciones", $("#formdata").serialize(), function (res) {
                        // si existe un error en los datos enviados, se presenta un alert            
                        if (res.error) {
                            $('#errorlAlert').show();
                            $('#errorlAlert').text(res.error);
                        }
                        // si la peticion fue un exito, se presenta un mensaje y se oculta el formulario del deposito
                        if (res.message) {
                            // presentamos un mensaje
                            $('#cardSuccess').show();
                            // ocultamos los alertas de error
                            $('#interes_credito').removeClass("is-invalid");
                            $('#error-interes_credito').hide();
                            $('#monto_max_credito').removeClass("is-invalid");
                            $('#error-monto_max_credito').hide();
                            $('#plazo_max_meses').removeClass("is-invalid");
                            $('#error-plazo_max_meses').hide();
                        }
                    }).fail(function (error) { // si existe un error del servidor, presentamos un alert
                        $('#errorlAlert').show();
                        $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                    });
                }
            }
        });
    });

    function validaForm() {
        // Campos de texto
        if ($("#interes_credito").val() === "") {
            $('#error-interes_credito').show();
            $('#interes_credito').addClass("is-invalid");
            $("#interes_credito").focus();
            return false;
        }
        if ($("#monto_max_credito").val() === "") {
            $('#error-monto_max_credito').show();
            $('#monto_max_credito').addClass("is-invalid");
            $("#monto_max_credito").focus();
            return false;
        }
        if ($("#plazo_max_meses").val() === "") {
            $('#error-plazo_max_meses').show();
            $('#plazo_max_meses').addClass("is-invalid");
            $("#plazo_max_meses").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>

