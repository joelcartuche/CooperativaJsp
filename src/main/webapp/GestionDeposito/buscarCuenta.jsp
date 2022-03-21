<%-- 
    Document   : deposito
    Created on : 21 mar 2022, 12:11:21
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

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-6">
                <div id="errorlAlert" class="alert alert-danger" style="display: none" role="alert">
                    A simple danger alert
                </div>
                <div id="successAlert" class="alert alert-success" style="display: none" role="alert">
                    A simple success alert
                </div>
                <div class="card p-4">
                    <form id="formdata">
                        <div class="mb-3 d-flex">
                            <input name="numCuenta" id="inputSearch" class="form-control me-2" type="search" placeholder="Ingresar Número de Cuenta" aria-label="Search">
                            <button id="btnSearch" class="btn btn-outline-success" type="button">Buscar</button>
                        </div>
                    </form>
                    <div id="errorSearch" class="invalid-feedback" style="display: none">
                        Por favor, ingrese el número de cuenta del socio
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4 mb-5 justify-content-center">
            <div class="col-12 col-md-11">
                <table class="table" id="tableSearch">
                    <thead>
                        <tr>
                            <th scope="col">N° Cuenta</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Apellido</th>
                            <th scope="col">Cédula</th>
                            <th scope="col">Estado de Cuenta</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<script>
    $(document).ready(function () {

        $("#btnSearch").click(function () {     // Con esto establecemos la acción por defecto de nuestro botón de enviar.
            if (validaForm()) {                               // Primero validará el formulario.
                $.post("<%=dom.getDominio()%>Deposito?accion=buscar", $("#formdata").serialize(), function (res) {
                    if (res.error) {
                        $('#errorlAlert').show();
                        $('#errorlAlert').text(res.error);
                    } else {
                        console.log(res[0]);
                        console.log(res[1]);
                        let estadoCuenta = "";
                        if (!res[0].esEliminado) {
                            estadoCuenta = '<span class="my-estado-activo">Activo</span>';
                        } else {
                            estadoCuenta = '<span class="my-estado-inactivo">Inactivo</span>';
                        }
                        
                        let opciones = "";
                        if (!res[0].esEliminado) {
                            opciones = '<a type="button" href="<%=dom.getDominio()%>Deposito?accion=irDeposito&id='+res[0].idCuentaCooperativa+'" class="btn btn-success">Hacer Depósito</a>';
                        } else {
                            opciones = '<a type="button" href="<%=dom.getDominio()%>Cuenta?accion=editar&id='+res[1].idSocios+'" class="btn btn-primary">Activar Cuenta<i class="fa fa-pencil" aria-hidden="true"></i></a>';
                        }

                        $('#tableSearch>tbody').append(
                            "<tr>\n\
                            <td>" + res[0].numeroCuenta + "</td>\n\
                            <td>" + res[1].nombreSocio + "</td>\n\
                            <td>" + res[1].apellidoSocio + "</td>\n\
                            <td>" + res[1].cedulaSocio + "</td>\n\
                            <td>" + estadoCuenta + "</td>\n\
                            <td>" + opciones + "</td>\n\
                            </tr>"
                        );

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
        if ($("#inputSearch").val() == "") {
            $('#errorSearch').show();
            $('#inputSearch').addClass("is-invalid");
            $("#inputSearch").focus();
            return false;
        }
        return true; // Si todo está correcto
    }
</script>
<jsp:include page="../Template/footer.jsp"></jsp:include>
