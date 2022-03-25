<%-- 
    Document   : buscarCuentaCooperativa
    Created on : 25 mar 2022, 1:56:14
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
            <div class="col-md-12 fw-bold fs-3 text-center">Búsqueda de Cuenta Cooperativa</div>
        </div>

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-6">
                <div id="errorlAlert" class="alert alert-danger" style="display: none" role="alert">
                    A simple danger alert
                </div>
                <div class="card p-4">
                    <form id="formdata">
                        <div class="mb-3 d-flex">
                            <input name="numCuenta" id="inputSearch" class="form-control me-2" type="search" placeholder="Ingresar Número de Cuenta" aria-label="Search">
                            <button id="btnSearch" class="btn btn-outline-success" type="button">Buscar</button>
                        </div>
                    </form>
                    <div id="errorSearch" class="invalid-feedback" style="display: none">
                        Por favor, ingrese la cédula del socio
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-11">
                <table class="table" id="tableSearch">
                    <thead>
                        <tr class="table-primary">
                            <th scope="col">Id</th>
                            <th scope="col">Número Cuenta</th>
                            <th scope="col">Nombre Cuenta</th>
                            <th scope="col">Código Cuenta</th>
                            <th scope="col">Nombre Socio</th>
                            <th scope="col">Estado Cuenta</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
        <div id="liveToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body" id="message">
                    Cuenta Cooperativa Encontrado
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>

</main>
<script>
    $(document).ready(function () {

        // Con esto establecemos la acción por defecto de nuestro botón de enviar.
        $("#btnSearch").click(function () {
            // Primero validará el formulario.
            if (validaForm()) {
                // enviamos la peticion por el metodo POST
                $.post("CuentasCooperativa?accion=buscarCuenta", $("#formdata").serialize(), function (res) {
                    // si existe un error en los datos enviados, se presenta un alert            
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error);
                    } else {
                        // si la peticion fue un exito, se presenta los datos en una TABLA
                        // se presenta un toast de confirmacion
                        $('#liveToast').toast('show');
                        // se oculta los mensajes de error
                        $('#errorlAlert').hide();
                        // se almacena el mensaje de acuerdo al estado de la cuenta, si esta eliminada o no
                        let estadoCuenta = "";
                        if (!res.esEliminadoCuenta) {
                            estadoCuenta = '<span class="my-estado-activo">Activo</span>';
                        } else {
                            estadoCuenta = '<span class="my-estado-inactivo">Inactivo</span>';
                        }
                        // se almacena las obciones de acuerdo al estado de la cuenta, si esta eliminada o no
                        // si la cuenta esta eliminada, no se podra hacer un deposito y se presenta un boton para activa la cuenta
                        let opciones = "";
                        if (!res.esEliminadoCuenta) {
                            opciones = '<a type="button" href="CuentasCooperativa?accion=eliminar&id=' + res.idCuenta + '&esEliminado=' + res.esEliminadoCuenta + '" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i> </a>';
                        } else {
                            opciones = '<a type="button" href="CuentasCooperativa?accion=eliminar&id=' + res.idCuenta + '&esEliminado=' + res.esEliminadoCuenta + '" class="btn btn-success">Activar </a>';
                        }
                        let btnVer = '<a type="button" href="CuentasCooperativa?accion=ver&id=' + res.idCuenta + '" class="btn btn-info me-1"><i class="bi bi-eye text-white"></i></a>'
                        let btnEditar = '<a type="button" href="CuentasCooperativa?accion=editar&id=' + res.idCuenta + '" class="btn btn-primary me-1"><i class="fa fa-pencil" aria-hidden="true"></i></a>';
                        // se construye la tabla y se presenta en la vista
                        $('#tableSearch>tbody').append(
                                "<tr>\n\
                            <td>" + res.idCuenta + "</td>\n\
                            <td>" + res.numeroCuenta + "</td>\n\
                            <td>" + res.nombreCuenta + "</td>\n\
                            <td>" + res.codigoCuenta + "</td>\n\
                            <td>" + res.nombreSocio + "</td>\n\
                            <td>" + estadoCuenta + "</td>\n\
                            <td>" + btnVer + btnEditar + opciones + "</td>\n\
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
        if ($("#inputSearch").val() === "") {
            $('#errorSearch').show();
            $('#inputSearch').addClass("is-invalid");
            $("#inputSearch").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>