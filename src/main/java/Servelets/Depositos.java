/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaCooperativaJpaController;
import Controladores.DepositoJpaController;
import Modelos.CuentaCooperativa;
import Modelos.Deposito;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;


@WebServlet(name = "Deposito", urlPatterns = {"/Deposito"})
public class Depositos extends HttpServlet {

    String buscarCuenta = "GestionDeposito/buscarCuenta.jsp";
    String irDeposito = "GestionDeposito/depositar.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String acceso = "";
        String action = request.getParameter("accion");

        if (action.equalsIgnoreCase("buscarCuenta")) {
            acceso = buscarCuenta;

        } else if (action.equalsIgnoreCase("irDeposito")) {
            int id = Integer.parseInt((String) request.getParameter("id"));
            CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
            CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);
            request.setAttribute("idCuentaCooperativa", cuentaCooperativa.getIdCuentaCooperativa());
            request.setAttribute("numCuentaCooperativa", cuentaCooperativa.getNumeroCuenta());
            request.setAttribute("saldoActual", 8000);
            acceso = irDeposito;
        }

        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("accion");

        // REALIZO LA BUSQUEDA DE LA CUENTA POR EL NUMERO DE CUENTA
        if (action.equalsIgnoreCase("buscar")) {
            try ( PrintWriter out = response.getWriter()) {
                // obtengo el numero de cuenta del formulario de busqueda
                String numCuenta = request.getParameter("numCuenta");
                try {
                    // Buscar cuenta cooperativa por el numero de cuenta en la base de datos
                    CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                    CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findNumeroCuentaCooperativa(numCuenta);;
                    // preparo los datos en un JSON
                    JSONObject jsonResultado = new JSONObject(); // convierto el objeto a json
                    jsonResultado.put("idCuentaCooperativa", cuentaCooperativa.getIdCuentaCooperativa());
                    jsonResultado.put("numeroCuentaCooperativa", cuentaCooperativa.getNumeroCuenta());
                    jsonResultado.put("estadoCuentaCooperativa", cuentaCooperativa.getEsEliminado());
                    jsonResultado.put("nombreSocio", cuentaCooperativa.getIdSocios().getNombreSocio());
                    jsonResultado.put("apellidoSocio", cuentaCooperativa.getIdSocios().getApellidoSocio());
                    jsonResultado.put("cedulaSocio", cuentaCooperativa.getIdSocios().getCedulaSocio());
                    // enviar resultado
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonResultado);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se encontró el número de cuenta " + numCuenta + "\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(salida);
                }
            }

            // REALIZAR EL DEPOSITO
        } else if (action.equalsIgnoreCase("depositar")) {

            try ( PrintWriter out = response.getWriter()) {
                // Obtengo loa parametros del formulario
                int id = Integer.parseInt((String) request.getParameter("id_cuenta"));
                String monto = request.getParameter("monto");
                //String fecha = request.getParameter("fecha_deposito");
                try {
                    // Buscar cuenta cooperativa por el id de cuenta en la base de datos
                    CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                    CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);
                    // creo un objeto deposito y agrego la informacion
                    Deposito deposito = new Deposito();
                    deposito.setMontoDeposito(Float.parseFloat((String) monto));
                    deposito.setFechaDeposito(new Date());
                    deposito.setCodigoSocio(cuentaCooperativa.getIdSocios());
                    // almaceno el deposito en la base de datos
                    DepositoJpaController depositoJpaController = new DepositoJpaController();
                    depositoJpaController.create(deposito);
                    // ceamos un objeto JSON para hacer la respuesta en la vista
                    JSONObject jsonRes = new JSONObject();
                    jsonRes.put("message", "Depósito realizado con éxito");
                    jsonRes.put("cuentaNumero", cuentaCooperativa.getNumeroCuenta());
                    jsonRes.put("cuentaCodigo", cuentaCooperativa.getCodigoCuenta());
                    jsonRes.put("socioNombre", cuentaCooperativa.getIdSocios().getNombreSocio());
                    jsonRes.put("socioApellido", cuentaCooperativa.getIdSocios().getApellidoSocio());
                    jsonRes.put("socioTelefono", cuentaCooperativa.getIdSocios().getTelefonoSocio());
                    jsonRes.put("depositoFecha", new SimpleDateFormat("dd/MM/yyyy").format(deposito.getFechaDeposito()));
                    jsonRes.put("depositoMonto", Math.round(deposito.getMontoDeposito()*100.0)/100.0);
                    // !: OBTENER EL SALDO VERDADERO
                    jsonRes.put("saldo", "---");
                    //System.out.println(jsonRes);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonRes);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se puede realizar el depósito. No se ha encontrado el número de cuenta\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(salida);
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
