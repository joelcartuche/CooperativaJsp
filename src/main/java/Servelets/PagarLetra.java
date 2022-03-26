/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CreditoJpaController;
import Controladores.TasaAmortizacionJpaController;
import Modelos.Credito;
import Modelos.TasaAmortizacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
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
@WebServlet(name = "PagarLetra", urlPatterns = {"/PagarLetra"})
public class PagarLetra extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BorrarCredito</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BorrarCredito at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String formaPago = request.getParameter("formaPago");
        TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
        TasaAmortizacion tasaAmortizacion = tasaAmortizacionJpaController.findTasaAmortizacion(Integer.parseInt(id));
        tasaAmortizacion.setEsPagado(true);
        tasaAmortizacion.setFechaPago(Calendar.getInstance().getTime());
        
        
        if (formaPago == null || formaPago.equals("")) {
            out.print("errorFormaPago");
        } else {
            tasaAmortizacion.setFormaPago(formaPago);
            try {
                tasaAmortizacionJpaController.edit(tasaAmortizacion);
                out.print("success");
            } catch (Exception ex) {
                out.print("error");
                Logger.getLogger(PagarLetra.class.getName()).log(Level.SEVERE, null, ex);
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
