
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
                            <span>${numCuentaCooperativa}</span>
                        </div>
                    </div>
                    <div class="row mb-3" hidden>
                        <div class="col-12 col-md-6">
                            <span class="fw-bold">Saldo Actual:</span>
                        </div>
                        <div class="col-12 col-md-6">
                            <span class="my-estado-activo">$ ${saldoActual}</span>
                        </div>
                    </div>


                    <form id="formdata">
                        <input type="hidden" name="id_cuenta" value="${idCuentaCooperativa}"/>
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

        <div id="cardSuccess" class="row mt-4 mb-3 justify-content-center" style="display: none">
            <div class="col-12 col-md-11">
                <div class="alert alert-success" role="alert">
                    Depósito realizado con éxito
                </div>
            </div>
        </div>
        <div id="cardSuccessInfo" class="row mb-5 justify-content-center" style="display: none">
            <div class="col-12 col-md-11">
                <div class="card">
                    <div class="card-header">
                        <div class="row mb-3">
                            <div class="col-12 col-md-6">
                                <span class="fw-bold">N° de Cuenta: </span><span id="textNumCuenta">111</span>
                            </div>
                            <div class="col-12 col-md-6">
                                <span class="fw-bold">Código de Cuenta: </span><span id="textCodCuenta">222</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-12">
                                <span class="fw-bold">Nombres: </span><span id="textNombre">Primer Segundo</span> <span id="textApellido">Tercero Cuarto</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-12">
                                <span class="fw-bold">Teléfono: </span><span id="textTelefono">1111111111</span>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table" id="tableSuccess">
                            <thead>
                                <tr>
                                    <th scope="col">FECHA</th>
                                    <th scope="col">TIPO DE OPERACIÓN</th>
                                    <th scope="col">MONTO</th>
                                    <th scope="col">SALDO</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>

    </div>
</main>
<script>
    $(document).ready(function () {
        // obtenemos la fecha actual y le damos formato
        let date = new Date();
        let dia = date.getDate();
        let mes = ("0" + (date.getMonth() + 1));
        let anio = date.getFullYear();
        let fechaTotal = anio + "-" + mes + "-" + dia;
        // ingresamos la fecha en el input date del formulario
        $("#inputDate").val(fechaTotal);
        // Con esto establecemos la acción por defecto de nuestro botón de enviar.
        $("#btnSubmit").click(function () {
            // Primero validará el formulario.
            if (validaForm()) {
                // enviamos la peticion por el metodo POST
                $.post("Deposito?accion=depositar", $("#formdata").serialize(), function (res) {
                    // si existe un error en los datos enviados, se presenta un alert            
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error);
                    }
                    // si la peticion fue un exito, se presenta un mensaje y se oculta el formulario del deposito
                    if (res.message) {
                        // limpiamos el formulario y lo ocultamos
                        $("#inputMonto").val("");
                        $("#panel").hide();
                        // presentamos un mensaje
                        $('#cardSuccess').show();
                        // presentamos un card de informacion con respecto a la transacción
                        $('#cardSuccessInfo').show();
                        $('#textNumCuenta').text(res.cuentaNumero);
                        $('#textCodCuenta').text(res.cuentaCodigo);
                        $('#textNombre').text(res.socioNombre);
                        $('#textApellido').text(res.socioApellido);
                        $('#textTelefono').text(res.socioTelefono);
                        $('#tableSuccess>tbody').append(
                                "<tr>\n\
                            <td>" + res.depositoFecha + "</td>\n\
                            <td>" + "Depósito" + "</td>\n\
                            <td>" + res.depositoMonto + "</td>\n\
                            <td>" + res.saldo + "</td>\n\
                            </tr>"
                                );
                    }
                }).fail(function (error) { // si existe un error del servidor, presentamos un alert
                    $('#errorlAlert').show();
                    $('#errorlAlert').text("Error " + error.status + ": " + error.responseText);
                });
            }
        });
    });

    function validaForm() {
        // Campos de texto
        if ($("#inputMonto").val() === "") {
            $('#errorMonto').show();
            $('#inputMonto').addClass("is-invalid");
            $("#inputMonto").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>
