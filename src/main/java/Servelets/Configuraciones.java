/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.ConfiguracionJpaController;
import Modelos.Configuracion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "Configuraciones", urlPatterns = {"/Configuraciones"})
public class Configuraciones extends HttpServlet {

    String page = "Configuraciones/configuraciones.jsp";
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
        // llamamos al controlador
        ConfiguracionJpaController configuracionJpaController = new ConfiguracionJpaController();
        // buscamos y obtenemos en una lista el objeto Configuracion
        List<Configuracion> listaConfiguracion = configuracionJpaController.findConfiguracionEntities();
        // ? el sistema solo maneja una tabla Configurar, por ende, se verifica si existe
        // si existe Configurar creadas, se extrae el primer elemento encontrado o caso contrario se envia valores por defecto a la vista
        if (!listaConfiguracion.isEmpty()) {
            // obtengo el primer elemento de la busqueda y envio los datos a la vista
            Configuracion configuracion = listaConfiguracion.get(0);
            request.setAttribute("interes_credito", configuracion.getInteresCredito());
            request.setAttribute("monto_max_credito", configuracion.getMontoMaxCredito());
            request.setAttribute("plazo_max_meses", configuracion.getPlazoMaxMeses());
        } else {
            // si no existe ningun elemento creado, se envian datos por defecto
            request.setAttribute("interes_credito", 0);
            request.setAttribute("monto_max_credito", 0);
            request.setAttribute("plazo_max_meses", 0);
        }
        // se redirecciona a la pagina de configuracion
        String acceso = page;
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
        try ( PrintWriter out = response.getWriter()) {
            // Obtengo los parametros del formulario
            double interes_credito = Double.parseDouble((String) request.getParameter("interes_credito"));
            double monto_max_credito = Double.parseDouble((String) request.getParameter("monto_max_credito"));
            int plazo_max_meses = Integer.parseInt((String) request.getParameter("plazo_max_meses"));
            try {
                // llamamos el controlador
                ConfiguracionJpaController configuracionJpaController = new ConfiguracionJpaController();
                // buscamos y obtenemos en una lista el objeto Configuracion
                List<Configuracion> listaConfiguracion = configuracionJpaController.findConfiguracionEntities();
                // ? el sistema solo maneja una tabla Configurar, por ende, se verifica si existe
                // si existe Configurar creadas, se extrae el primer elemento encontrado o caso contrario se crea una nueva tabla
                if (!listaConfiguracion.isEmpty()) {
                    // obtengo el primer elemento de la busqueda
                    Configuracion configuracion = listaConfiguracion.get(0);
                    // actualizo la informacion
                    configuracion.setInteresCredito(interes_credito);
                    configuracion.setMontoMaxCredito(monto_max_credito);
                    configuracion.setPlazoMaxMeses(plazo_max_meses);
                    // guardo en la base de datos
                    configuracionJpaController.edit(configuracion);
                } else {
                    // creo un nuevo objeto Configuracion
                    Configuracion newConfiguracion = new Configuracion();
                    // ingreso los datos
                    newConfiguracion.setInteresCredito(interes_credito);
                    newConfiguracion.setMontoMaxCredito(monto_max_credito);
                    newConfiguracion.setPlazoMaxMeses(plazo_max_meses);
                    // creo y guardo en la DB un nuevo objeto de Configuracion
                    configuracionJpaController.create(newConfiguracion);
                }
                // ceamos un objeto JSON para hacer la respuesta en la vista
                JSONObject jsonRes = new JSONObject();
                jsonRes.put("message", "Datos guardados con éxito");
                // enviamos los datos a la vista
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                out.print(jsonRes);

            } catch (Exception e) {
                String salida = "{\"error\":\"Lo sentimos, no se puede guardar los datos\"}";
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(salida);
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
