/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaJpaController;
import Modelos.Cuenta;
import Utilidades.Dominio;
import Utilidades.Encriptar;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author joelc Contraseña= #VFmwfozF30e usuario = administrador
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    Dominio dom = new Dominio();

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

        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
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
        RequestDispatcher vista = request.getRequestDispatcher("login.jsp");
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

        String salida = ""; //almacena el Json de salida

        try ( PrintWriter out = response.getWriter()) {
            //recogemos los datos enviados desde el template
            String user = request.getParameter("user");
            String password = request.getParameter("password");
            //llamamos al controlador jpa
            CuentaJpaController cuentaJpaController = new CuentaJpaController();
            //buscamos el usuario dado el nombre de usuario
            Cuenta cuentaUsurioBuscado = cuentaJpaController.findCuentaUsuario(user);
            if (cuentaUsurioBuscado != null) { //en caso  de que no exista el usuario buscado
                Encriptar enc = new Encriptar();//encriptador de datos en MD5
                if (cuentaUsurioBuscado.getPassword().equals(enc.getMD5(password))) { //comparamos las contraseñas
                    // almacenamos la sesion iniciada
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("logueado", "1");
                    sesion.setAttribute("user", cuentaUsurioBuscado.getUsuario());
                    sesion.setAttribute("id", cuentaUsurioBuscado.getIdCuenta());
                    sesion.setAttribute("rol", cuentaUsurioBuscado.getIdRol().getTipoRol());
                    sesion.setAttribute("nombre", cuentaUsurioBuscado.getIdUsuario().getNombreUsuario());
                    sesion.setAttribute("apellido", cuentaUsurioBuscado.getIdUsuario().getApellidoUsuario());
                    //enviamos el json para la vista login.jsp
                    JSONObject jsonRes = new JSONObject();
                    jsonRes.put("user", cuentaUsurioBuscado.getUsuario());
                    jsonRes.put("logueado", 1);
                    jsonRes.put("rol", cuentaUsurioBuscado.getIdRol().getTipoRol());
                    out.print(jsonRes);
                } else {//retornamos en este caso no existe contraseña
                    salida = "{\"esContraIncorrecta\":1}";
                    out.print(salida);

                }
            } else {//retornamos el valor noExisteUsuario
                salida = "{\"noExisteUsuario\":1}";
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
