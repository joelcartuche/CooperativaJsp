/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Modelos.CuentaCooperativa;
import Modelos.Socios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "Retiro", urlPatterns = {"/Retiro"})
public class Retiro extends HttpServlet {

    String buscarCuenta = "GestionRetiro/buscarCuenta.jsp";
    String irRetiro = "GestionRetiro/retirar.jsp";

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

        } else if (action.equalsIgnoreCase("irRetiro")) {
            acceso = irRetiro;
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
        processRequest(request, response);
        String action = request.getParameter("accion");

        if (action.equalsIgnoreCase("buscar")) {
            try ( PrintWriter out = response.getWriter()) {

                int numCuenta = Integer.parseInt((String) request.getParameter("numCuenta"));

                try {

                    // Buscar cuenta cooperativa por el numero de cuenta en la base de datos
                    //CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                    //CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(numCuenta);
                    // !: BORRAR esta seccion, es solo para pruebas
                    CuentaCooperativa cuentaCooperativa = new CuentaCooperativa();
                    cuentaCooperativa.setIdCuentaCooperativa(numCuenta);
                    cuentaCooperativa.setNumeroCuenta(numCuenta + "");
                    // FIN BORRAR

                    JSONObject jsonCuenta = new JSONObject(cuentaCooperativa); // convierto el objeto a json

                    // Obtener el socio vinculado al numero de cuenta
                    Socios socios = new Socios();
                    socios.setIdSocios(1);
                    socios.setNombreSocio("Jonnathan");
                    socios.setApellidoSocio("Espinoza");
                    socios.setCedulaSocio("1234567890");
                    JSONObject jsonSocio = new JSONObject(socios); // convierto el objeto a json

                    System.out.println(jsonCuenta);
                    System.out.println(jsonSocio);

                    JSONArray jsonRes = new JSONArray();
                    jsonRes.put(jsonCuenta);
                    jsonRes.put(jsonSocio);

                    System.out.println(jsonRes);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonRes);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se encontró el número de cuenta ingresada.\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(salida);
                }
            }
        } else if (action.equalsIgnoreCase("retirar")) {

            try ( PrintWriter out = response.getWriter()) {

                int numCuenta = Integer.parseInt((String) request.getParameter("id_cuenta"));
                String fecha = request.getParameter("fecha_retiro");
                String monto = request.getParameter("monto");

                System.out.println(numCuenta);
                System.out.println(fecha);
                System.err.println(monto);

                try {

                    // Buscar cuenta cooperativa por el id de cuenta en la base de datos
                    //CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                    //CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(numCuenta);
                    // !: BORRAR esta seccion, es solo para pruebas
                    CuentaCooperativa cuentaCooperativa = new CuentaCooperativa();
                    cuentaCooperativa.setIdCuentaCooperativa(numCuenta);
                    cuentaCooperativa.setNumeroCuenta(numCuenta + "");
                    // FIN BORRAR

                    String salida = "{\"message\":\"Retiro realizado con éxito\"}";

                    //System.out.println(jsonRes);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(salida);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se puede realizar el retiro. No se ha encontrado el número de cuenta\"}";
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
