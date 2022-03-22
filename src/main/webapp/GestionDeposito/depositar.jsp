<%-- 
    Document   : depositar
    Created on : 21 mar 2022, 17:11:05
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
    boolean esEliminado = false;
%>

<main>
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12 fw-bold fs-3 text-center">Hacer Depósito</div>
        </div>

        <div id="panel" class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-7">
                <div id="errorlAlert" class="alert alert-danger" style="display: none" role="alert">
                    A simple danger alert
                </div>
                <div class="card p-4">

                    <div class="row mb-3">
                        <div class="col-12 col-md-6">
                            <span class="fw-bold">Número de Cuenta:</span>
                        </div>
                        <div class="col-12 col-md-6">
                            <span>111</span>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-12 col-md-6">
                            <span class="fw-bold">Saldo Actual:</span>
                        </div>
                        <div class="col-12 col-md-6">
                            <span class="my-estado-activo">$ 3000.00</span>
                        </div>
                    </div>


                    <form id="formdata">
                        <input type="hidden" name="id_cuenta" value="999"/>
                        <div class="input-group mb-3">
                            <div class="col-12 col-md-6">
                                <label for="bday" class="form-label fw-bold">Fecha de la transacción:</label>
                            </div>
                            <div class="col-12 col-md-6">
                                <input type="date" id="inputDate" name="fecha_deposito" class="form-control" readonly >
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <div class="col-12 col-md-6">
                                <label class="form-label fw-bold">Ingresar el monto:</label>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="input-group mb-3">
                                    <span class="input-group-text">$</span>
                                    <input id="inputMonto" name="monto" type="number" min="0" class="form-control" aria-label="Amount (to the nearest dollar)">
                                    <div id="errorMonto" class="invalid-feedback" style="display: none">
                                        Por favor, ingrese el monto a depositar
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button id="btnSubmit" class="btn btn-outline-success" type="button">Hacer Depósito</button>
                    </form>

                </div>
            </div>
        </div>

        <div id="cardSuccess" class="row mt-4 mb-5 justify-content-center" style="display: none">
            <div class="col-12 col-md-11">
                <div class="alert alert-success" role="alert">
                    Depósito realizado con éxito
                </div>
            </div>
        </div>

    </div>
</main>
<script>
    $(document).ready(function () {

        let date = new Date();
        let dia = date.getDate();
        let mes = ("0" + (date.getMonth() + 1));
        let anio = date.getFullYear();
        let fechaTotal = anio + "-" + mes + "-" + dia;
        $("#inputDate").val(fechaTotal);

        $("#btnSubmit").click(function () {     // Con esto establecemos la acción por defecto de nuestro botón de enviar.
            if (validaForm()) {                               // Primero validará el formulario.
                $.post("<%=dom.getDominio()%>Deposito?accion=depositar", $("#formdata").serialize(), function (res) {
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error);
                    }
                    if (res.message) {
                        $("#inputMonto").val("");
                        $("#panel").hide();
                        $('#cardSuccess').show();
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
        if ($("#inputMonto").val() == "") {
            $('#errorMonto').show();
            $('#inputMonto').addClass("is-invalid");
            $("#inputMonto").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>
