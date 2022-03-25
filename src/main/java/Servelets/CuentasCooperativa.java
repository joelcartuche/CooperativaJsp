/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaCooperativaJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.CuentaCooperativa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CuentasCooperativa", urlPatterns = {"/CuentasCooperativa"})
public class CuentasCooperativa extends HttpServlet {

    String listar = "GestionCuentaCooperativa/listarCuentasCooperativa.jsp";
    String ver = "GestionCuentaCooperativa/verCuentasCooperativa.jsp";
    String eliminar = "GestionCuentaCooperativa/borrarCuentaCooperativa.jsp";
    String editar = "GestionCuentaCooperativa/editarCuentaCooperativa.jsp";
    String buscar = "GestionCuentaCooperativa/buscarCuentaCooperativa.jsp";

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

        String acceso = "";
        String action = request.getParameter("accion");

        if (action.equalsIgnoreCase("listar")) {
            acceso = listar;

        } else if (action.equalsIgnoreCase("ver")) {
            // obtenemos el id de la url
            int id = Integer.parseInt((String) request.getParameter("id"));
            // buscamos la cuenta por el id
            CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
            CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);
            // enviamos la informacion a la vista
            request.setAttribute("cuentaCooperativa", cuentaCooperativa);
            request.setAttribute("esEliminadoCuenta", cuentaCooperativa.getEsEliminado());
            request.setAttribute("socio", cuentaCooperativa.getIdSocios());
            request.setAttribute("esEliminadoSocio", cuentaCooperativa.getIdSocios().getEsEliminado());
            request.setAttribute("usuario", cuentaCooperativa.getIdUsuario());
            request.setAttribute("esEliminadoUsuario", cuentaCooperativa.getIdUsuario().getEsEliminado());
            acceso = ver;

        } else if (action.equalsIgnoreCase("eliminar")) {
            // obtenemos los parametros y enviamos a la vista de eliminar
            request.setAttribute("id", request.getParameter("id"));
            request.setAttribute("esEliminado", request.getParameter("esEliminado"));
            acceso = eliminar;

        } else if (action.equalsIgnoreCase("editar")) {
            // obtenemos el id de la url
            int id = Integer.parseInt((String) request.getParameter("id"));
            // buscamos el socio por el id
            CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
            CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);
            // enviamos la informacion a la vista            
            request.setAttribute("cuenta", cuentaCooperativa);
            request.setAttribute("esEliminado", cuentaCooperativa.getEsEliminado());
            request.setAttribute("socioNombre", cuentaCooperativa.getIdSocios().getNombreSocio());
            request.setAttribute("socioApellido", cuentaCooperativa.getIdSocios().getApellidoSocio());
            acceso = editar;

        } else if (action.equalsIgnoreCase("buscarCuenta")) {
            acceso = buscar;

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

        String salida = ""; //almacena el Json de salida

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // FUNCIONALIDAD PARA AGREGAR UNA NUEVA CUENTA COOPERATIVA
        if (action.equalsIgnoreCase("agregar")) {

            // FUNCIONALIDAD PARA EDITAR CUENTA COOPERATIVA
        } else if (action.equalsIgnoreCase("editar")) {
            // obtenemos los parametros adicionales del formulario
            int id = Integer.parseInt(request.getParameter("id_cuenta_cooperativa"));
            String estado = request.getParameter("estadoCuenta");
            String nombreCuenta = request.getParameter("nombreCuenta");

            try ( PrintWriter out = response.getWriter()) {
                // buscamos la cuenta por el id en la base de datos y modificamos la informacion
                CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);

                cuentaCooperativa.setNombreCuenta(nombreCuenta);

                if (estado.equalsIgnoreCase("true")) {
                    cuentaCooperativa.setEsEliminado(Boolean.TRUE);
                } else if (estado.equalsIgnoreCase("false")) {
                    cuentaCooperativa.setEsEliminado(Boolean.FALSE);
                }

                try {
                    // actualizamos los datos en la DB
                    cuentaCooperativaJpaController.edit(cuentaCooperativa);
                    // realizamos el mensaje de salida
                    salida = "{\"message\":\"La información se actualizó exitosamente\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(salida);
                } catch (Exception e) {
                    salida = "{\"error\":\"Hubo un error al actualizar los datos.\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(salida);
                }
            }

            // FUNCIONALIDAD DE ELIMINAR CUENTA COOPERATIVA
        } else if (action.equalsIgnoreCase("eliminar")) {
            // obtenemos los parmetros de la URL
            int id = Integer.parseInt(request.getParameter("id"));
            boolean esEliminado = Boolean.parseBoolean(request.getParameter("esEliminado"));
            try ( PrintWriter out = response.getWriter()) {
                // obtenenmos el socio por el id y modificamos el atributo esEliminado
                CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findCuentaCooperativa(id);
                cuentaCooperativa.setEsEliminado(!esEliminado);
                try {
                    // actualizamos la informacion en la base de datos
                    cuentaCooperativaJpaController.edit(cuentaCooperativa);
                    // enviamos la informacion a la vista
                    salida = "{\"message\":\"Los cambios se realizaron exitosamente\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(salida);

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            // ACCION DE BUSCAR CUENTA COOPERATIVA POR EL NUMERO DE CUENTA
        } else if (action.equalsIgnoreCase("buscarCuenta")) {
            try ( PrintWriter out = response.getWriter()) {
                // obtengo el numero de cuenta del formulario de busqueda
                String numCuenta = request.getParameter("numCuenta");
                try {
                    // buscamos la cuenta por su numero
                    CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                    CuentaCooperativa cuentaCooperativa = cuentaCooperativaJpaController.findNumeroCuentaCooperativa(numCuenta);
                    // preparo los datos en un JSON
                    JSONObject jsonRes = new JSONObject(); // convierto el objeto a json
                    jsonRes.put("idCuenta", cuentaCooperativa.getIdCuentaCooperativa());
                    jsonRes.put("numeroCuenta", cuentaCooperativa.getNumeroCuenta());
                    jsonRes.put("nombreCuenta", cuentaCooperativa.getNombreCuenta());
                    jsonRes.put("codigoCuenta", cuentaCooperativa.getCodigoCuenta());
                    jsonRes.put("nombreSocio", cuentaCooperativa.getIdSocios().getNombreSocio());
                    jsonRes.put("esEliminadoCuenta", cuentaCooperativa.getEsEliminado());
                    // enviar resultado
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(jsonRes);
                } catch (Exception e) {
                    salida = "{\"error\":\"Lo sentimos, no se encontró el número de cuenta " + numCuenta + "\"}";
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
