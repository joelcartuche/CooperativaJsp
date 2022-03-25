<%-- 
    Document   : addParnet
    Created on : 15 mar 2022, 21:27:14
    Author     : jede
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../Template/layout.jsp"></jsp:include>
<%
    //HttpSession sesion2 = request.getSession();
    //if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
    //        response.sendRedirect("../login.jsp");
    //}
%>

<main>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Agregar Nuevo Socio</div>
        </div>
        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-6">

                <div id="errorlAlert" class="alert alert-danger" role="alert" style="display: none">
                    A simple danger alert
                </div>
                <div id="successAlert" class="alert alert-success" role="alert" style="display: none">
                    <div class="row">
                        <div class="col-12" id="messageRes">Mensaje</div>
                    </div>
                    <div class="row">
                        <div class="col-12 col-md-6 fw-bold">Número de Cuenta:</div>
                        <div class="col-12 col-md-6 fw-bold" id="messageNum">11</div>
                    </div>
                </div>

                <div class="card p-4">
                    <form id="formdata">
                        <div class="mb-3">
                            <label for="inputName" class="form-label">Nombre</label>
                            <input type="text" name="nombre_socio" class="form-control form-control-sm" id="inputName" aria-describedby="nameHelp" required>
                            <div id="errorName" class="invalid-feedback" style="display: none">
                                Por favor, ingrese un nombre de socio
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="inputLatName" class="form-label">Apellido</label>
                            <input type="text" name="apellido_socio" class="form-control form-control-sm" id="inputLatName" aria-describedby="emailHelp" required>
                            <div id="errorLastName" class="invalid-feedback" style="display: none">
                                Por favor, ingrese un apellido de socio
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="inputCi" class="form-label">Número Cédula</label>
                            <input type="text" name="cedula_socio" class="form-control form-control-sm" id="inputCi" aria-describedby="emailHelp" required>
                            <div id="errorCi" class="invalid-feedback" style="display: none">
                                Por favor, ingrese el número de cédula del socio
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="inputPhone" class="form-label">Número de Teléfono</label>
                            <input type="text" name="telefono_socio" class="form-control form-control-sm" id="inputPhone" aria-describedby="emailHelp" required>
                            <div id="errorPhone" class="invalid-feedback" style="display: none">
                                Por favor, ingrese el número de celular del socio
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="inputAddress" class="form-label">Dirección</label>
                            <textarea class="form-control" name="direccion_socio" id="inputAddress" rows="3"></textarea>
                        </div>
                        <button type="button" id="botonenviar" class="btn btn-primary">Agregar Nuevo Socio</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
        <div id="liveToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body" id="message">
                    Cuenta de Socio Creado con éxito.
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
            // Primero validará el formulario.
            if (validaForm()) {
                // enviamos la peticion por el metodo POST
                // como respuesta, recibimos un JSON con la informacion requerida
                $.post("Socio?accion=agregar", $("#formdata").serialize(), function (res) {
                    // si en el JSON existe el parametro error, significa que existe un error de los datos. 
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error); // se presenta el mensaje en un alert
                    }
                    // si existe el parametro messagge en el JSON, significa que la operacion tuvo exito
                    if (res.message) {
                        // se presenta un toast de confirmacion
                        $('#liveToast').toast('show');
                        // se presenta el mensaje en un alert
                        $('#errorlAlert').hide();
                        $('#successAlert').show();
                        $('#messageRes').text(res.message);
                        $('#messageNum').text(res.numeroCuenta);
                        // se limpia el formulario
                        $('input[type="text"]').val('');
                        $('#inputAddress').val('');
                        // se oculta los mensajes de error
                        $("#errorName").hide();
                        $("#errorLastName").hide();
                        $("#errorCi").hide();
                        $("#errorPhone").hide();
                        $('input[type="text"]').removeClass('is-invalid');
                    }
                }).fail(function (error) { // este error corresponde al servidor
                    $('#errorlAlert').show();
                    $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                });
            }
        });
    });

    function validaForm() {
        if ($("#inputName").val() === "") {
            $('#errorName').show();
            $('#inputName').addClass("is-invalid");
            $("#inputName").focus();
            return false;
        }
        if ($("#inputLatName").val() === "") {
            $('#errorLastName').show();
            $('#inputLatName').addClass("is-invalid");
            $("#inputLatName").focus();
            return false;
        }
        if ($("#inputCi").val() === "") {
            $('#errorCi').show();
            $('#inputCi').addClass("is-invalid");
            $("#inputCi").focus();
            return false;
        }
        if ($("#inputPhone").val() === "") {
            $('#errorPhone').show();
            $('#inputPhone').addClass("is-invalid");
            $("#inputPhone").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>