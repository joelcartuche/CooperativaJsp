/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaCooperativaJpaController;
import Controladores.CuentaJpaController;
import Modelos.Cuenta;
import Modelos.CuentaCooperativa;
import Modelos.Deposito;
import Modelos.Retiro;
import Modelos.Socios;
import Modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;


@WebServlet(name = "SocioInfo", urlPatterns = {"/SocioInfo"})
public class SocioInfo extends HttpServlet {

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

        // REALIZO LA BUSQUEDA DE TODOS LOS DEPOSITOS
        if (action.equalsIgnoreCase("depositos")) {
            // obtengo el id de la cuenta del usuario
            int idCuenta = Integer.parseInt((String) request.getParameter("id"));
            // declaro un objeto json para las respuestas
            JSONObject jsonResultado = new JSONObject(); 
            try ( PrintWriter out = response.getWriter()) {
                try {
                    // realizo la busqueda de la cuenta por el id
                    CuentaJpaController cuentaJpaController = new CuentaJpaController();
                    Cuenta cuenta = cuentaJpaController.findCuenta(idCuenta);
                    // obtengo el usuario
                    Usuario usuario = cuenta.getIdUsuario();
                    // verifico si el usuario está registrado
                    if (usuario != null) {
                        // obtengo la cuenta cooperativa del usuario
                        CuentaCooperativa cuentaCooperativa = usuario.getCuentaCooperativa();
                        // verifico si la cuenta cooperativa está creada
                        if (cuentaCooperativa != null) {
                            // obtengo el socio
                            Socios socio = cuentaCooperativa.getIdSocios();
                            // verifico si el socio esta registrado
                            if (socio != null) {
                                // obtengo la lista de depositos
                                List<Deposito> listaDeposito = (List<Deposito>) socio.getDepositoCollection();
                                // verifico si el hay algun deposito
                                if (listaDeposito.size() > 0) {
                                    // creo un json array para guardar los depositos
                                    JSONArray ja = new JSONArray();
                                    // itero la lista y guardo el deposito en el json array
                                    for(Deposito deposito : listaDeposito) {
                                        JSONObject jsonDeposito = new JSONObject();
                                        jsonDeposito.put("monto", Math.round(deposito.getMontoDeposito()*100.0)/100.0);
                                        jsonDeposito.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(deposito.getFechaDeposito()));
                                        ja.put(jsonDeposito);
                                    }
                                    // presento la respuesta
                                    jsonResultado.put("message", "OK");
                                    jsonResultado.put("operacion", "Depósito");
                                    jsonResultado.put("depositos", ja);
                                } else {
                                    jsonResultado.put("info", "No ha realizado ningún depósito");
                                }
                            } else {
                                jsonResultado.put("error", "No está registrado como Socio de la Cooperativa");
                            }
                        } else {
                            jsonResultado.put("error", "No posee una cuenta cooperativa");
                        }
                    } else {
                        jsonResultado.put("error", "No posee una cuenta usuario");
                    }
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonResultado);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se encontró el número de cuenta\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(salida);
                }
            }
        }
        
        
        // REALIZO LA BUSQUEDA DE TODOS LOS RETIROS
        if (action.equalsIgnoreCase("retiros")) {
            // obtengo el id de la cuenta del usuario
            int idCuenta = Integer.parseInt((String) request.getParameter("id"));
            // declaro un objeto json para las respuestas
            JSONObject jsonResultado = new JSONObject(); 
            try ( PrintWriter out = response.getWriter()) {
                try {
                    // realizo la busqueda de la cuenta por el id
                    CuentaJpaController cuentaJpaController = new CuentaJpaController();
                    Cuenta cuenta = cuentaJpaController.findCuenta(idCuenta);
                    // obtengo el usuario
                    Usuario usuario = cuenta.getIdUsuario();
                    // verifico si el usuario está registrado
                    if (usuario != null) {
                        // obtengo la cuenta cooperativa del usuario
                        CuentaCooperativa cuentaCooperativa = usuario.getCuentaCooperativa();
                        // verifico si la cuenta cooperativa está creada
                        if (cuentaCooperativa != null) {
                            // obtengo el socio
                            Socios socio = cuentaCooperativa.getIdSocios();
                            // verifico si el socio esta registrado
                            if (socio != null) {
                                // obtengo la lista de retiros
                                List<Retiro> listaRetiro = (List<Retiro>) socio.getRetiroCollection();
                                // verifico si el hay algun retiro
                                if (listaRetiro.size() > 0) {
                                    // creo un json array para guardar los retiros
                                    JSONArray ja = new JSONArray();
                                    // itero la lista y guardo el retiro en el json array
                                    for(Retiro retiro : listaRetiro) {
                                        JSONObject jsonDeposito = new JSONObject();
                                        jsonDeposito.put("monto", Math.round(retiro.getMontoRetiro()*100.0)/100.0);
                                        jsonDeposito.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(retiro.getFechaRetiro()));
                                        ja.put(jsonDeposito);
                                    }
                                    // presento la respuesta
                                    jsonResultado.put("message", "OK");
                                    jsonResultado.put("operacion", "Retiro");
                                    jsonResultado.put("retiros", ja);
                                } else {
                                    jsonResultado.put("info", "No ha realizado ningún retiro");
                                }
                            } else {
                                jsonResultado.put("error", "No está registrado como Socio de la Cooperativa");
                            }
                        } else {
                            jsonResultado.put("error", "No posee una cuenta cooperativa");
                        }
                    } else {
                        jsonResultado.put("error", "No posee una cuenta usuario");
                    }
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonResultado);

                } catch (Exception e) {
                    String salida = "{\"error\":\"Lo sentimos, no se encontró el número de cuenta\"}";
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
