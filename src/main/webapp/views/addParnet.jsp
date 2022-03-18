<%-- 
    Document   : addParnet
    Created on : 15 mar 2022, 21:27:14
    Author     : LENOVO
--%>

<%@page import="Utilidades.Dominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../Template/layout.jsp"></jsp:include>
<%
    //HttpSession sesion2 = request.getSession();
    //if (sesion2.getAttribute("logueado") == null || sesion2.getAttribute("logueado").equals("0")) {
    //        response.sendRedirect("../login.jsp");
    //}

    Dominio dom = new Dominio();
%>

<main>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Agregar Nuevo Socio</div>
        </div>
        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-6">

                <div id="errorlAlert" class="alert alert-danger" role="alert">
                    A simple danger alert
                </div>
                <div id="successAlert" class="alert alert-success" role="alert">
                    A simple success alert
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
</main>
<script>
    $(document).ready(function () {
        //ocultamos los divs de alertas
        //$('#errorName').hide();
        $('#errorlAlert').hide();
        $('#successAlert').hide();

        $("#botonenviar").click(function () {     // Con esto establecemos la acción por defecto de nuestro botón de enviar.
            if (validaForm()) {                               // Primero validará el formulario.
                $.post("<%=dom.getDominio()%>Socio", $("#formdata").serialize(), function (res) {
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error);
                    }
                    if (res.message) {
                        $('#errorlAlert').hide();
                        $('#successAlert').show();
                        $('#successAlert').text(res.message);
                        $('input[type="text"]').val('');
                        $('#inputAddress').val('');
                        $("#errorName").hide();
                        $("#errorLastName").hide();
                        $("#errorCi").hide();
                        $("#errorPhone").hide();
                        $('input[type="text"]').removeClass('is-invalid');
                    }
                }).fail(function (error) {
                    $('#errorlAlert').show();
                    $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                });
            }
        });
    });

    function validaForm() {
        // Campos de texto
        if ($("#inputName").val() == "") {
            $('#errorName').show();
            $('#inputName').addClass("is-invalid");
            $("#inputName").focus();
            return false;
        }
        if ($("#inputLatName").val() == "") {
            $('#errorLastName').show();
            $('#inputLatName').addClass("is-invalid");
            $("#inputLatName").focus();
            return false;
        }
        if ($("#inputCi").val() == "") {
            $('#errorCi').show();
            $('#inputCi').addClass("is-invalid");
            $("#inputCi").focus();
            return false;
        }
        if ($("#inputPhone").val() == "") {
            $('#errorPhone').show();
            $('#inputPhone').addClass("is-invalid");
            $("#inputPhone").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>