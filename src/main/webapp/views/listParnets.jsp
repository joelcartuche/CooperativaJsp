<%-- 
    Document   : listParnets
    Created on : 15 mar 2022, 18:57:30
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../Template/layout.jsp"></jsp:include>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Lista de Socios</div>
            </div>
            <div class="row mt-5 justify-content-center">
                <div class="col-12 col-md-11">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Cédula</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Apellido</th>
                                <th scope="col">Teléfono</th>
                                <th scope="col">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>111111111</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>2222222222</td>
                                <td>
                                    <a type="button" href="#" class="btn btn-info"><i class="bi bi-eye text-white"></i></a>
                                    <a type="button" href="#" class="btn btn-primary"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                    <a type="button" href="#" class="btn btn-danger"><i class="fa fa-trash-o fa-lg"></i> </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
<jsp:include page="../Template/footer.jsp"></jsp:include>
