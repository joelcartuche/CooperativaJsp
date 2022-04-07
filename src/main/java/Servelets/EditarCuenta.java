/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaJpaController;
import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuenta;
import Utilidades.Encriptar;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "EditarCuenta", urlPatterns = {"/EditarCuenta"})
public class EditarCuenta extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String idCuenta = request.getParameter("idCuenta");
        String usuarioNombre = request.getParameter("usuario");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");
        String idUsuario = request.getParameter("idUsuario");
        String idRol = request.getParameter("idRol");

        if (usuarioNombre == null || usuarioNombre.isEmpty()) {
            out.print("errorNombreUsuario");
        } else if (!clave1.equals(clave2)) {
            out.print("errorClaveNoCoincide");
        } else if (idUsuario == null || idUsuario.equals("")) {
            out.print("errorUsuario");
        } else if (idRol == null || idRol.equals("")) {
            out.print("errorIdRol");
        } else {

            Encriptar encriptar = new Encriptar();
            CuentaJpaController cuentaJpaController = new CuentaJpaController();
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
            RolJpaController rolJpaController = new RolJpaController();
            Cuenta cuenta = cuentaJpaController.findCuenta(Integer.parseInt(idCuenta));
            cuenta.setUsuario(usuarioNombre);

            if (!clave1.equals("")) {
                cuenta.setPassword(encriptar.getMD5(clave1));
            }

            if (cuenta.getIdUsuario().getIdUsuario()!= Integer.parseInt(idUsuario)) {
                cuenta.setIdUsuario(usuarioJpaController.findUsuario(Integer.parseInt(idUsuario)));
            }
            if (cuenta.getIdRol().getIdRol() != Integer.parseInt(idRol)) {
                cuenta.setIdRol(rolJpaController.findRol(Integer.parseInt(idRol)));
            }
 
 
            out.print("success");
            try {
                cuentaJpaController.edit(cuenta);
            } catch (NonexistentEntityException ex) {
                out.print("errorNoHayCuenta");
                Logger.getLogger(EditarCuenta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                out.print("errorInesperado");
                Logger.getLogger(EditarCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
