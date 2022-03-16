<%-- 
    Document   : addParnet
    Created on : 15 mar 2022, 21:27:14
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../Template/layout.jsp"></jsp:include>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 fw-bold fs-3 text-center">Agregar Nuevo Socio</div>
            </div>
            <div class="row mt-4 mb-5 justify-content-center">
                <div class="col-12 col-md-6">
                    <div class="card p-4">
                        <form>
                            <div class="mb-3">
                                <label for="inputName" class="form-label">Nombre</label>
                                <input type="text" class="form-control form-control-sm is-invalid" id="inputName" aria-describedby="emailHelp" required>
                                <div class="invalid-feedback">
                                    Por favor, elija un nombre de socio
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="inputLatName" class="form-label">Apellido</label>
                                <input type="text" class="form-control form-control-sm" id="inputLatName" aria-describedby="emailHelp" required>
                            </div>
                            <div class="mb-3">
                                <label for="inputCi" class="form-label">Número Cédula</label>
                                <input type="text" class="form-control form-control-sm" id="inputCi" aria-describedby="emailHelp" required>
                            </div>
                            <div class="mb-3">
                                <label for="inputPhone" class="form-label">Número de Teléfono</label>
                                <input type="text" class="form-control form-control-sm" id="inputPhone" aria-describedby="emailHelp" required>
                            </div>
                            <div class="mb-3">
                                <label for="inputAddress" class="form-label">Dirección</label>
                                <textarea class="form-control" id="inputAddress" rows="3"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Agregar Nuevo Socio</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
<jsp:include page="../Template/footer.jsp"></jsp:include>