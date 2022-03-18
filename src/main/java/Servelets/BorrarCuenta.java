/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaJpaController;
import Controladores.RolJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuenta;
import Modelos.Rol;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author joelc
 */
@WebServlet(name = "BorrarCuenta", urlPatterns = {"/BorrarCuenta"})
public class BorrarCuenta extends HttpServlet {

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
        String salida = "";
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String esEliminado = request.getParameter("esEliminado");
        CuentaJpaController cuentaJpaController = new CuentaJpaController();
        Cuenta cuenta = cuentaJpaController.findCuenta(Integer.parseInt(id));
        cuenta.setEsEliminado(!Boolean.parseBoolean(esEliminado));
        try {
            cuentaJpaController.edit(cuenta);
            salida = "{\"success\":1}";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BorrarCuenta.class.getName()).log(Level.SEVERE, null, ex);
            salida = "{\"success\":0}";
        } catch (Exception ex) {
            Logger.getLogger(BorrarCuenta.class.getName()).log(Level.SEVERE, null, ex);
            salida = "{\"success\":0}";
        }
        out.print(salida);

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
