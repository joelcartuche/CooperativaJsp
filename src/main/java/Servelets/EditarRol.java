package Servelets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Controladores.RolJpaController;
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


@WebServlet(urlPatterns = {"/EditarRol"})
public class EditarRol extends HttpServlet {

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
        String salida ="{"; //almacena el Json de salida
        try ( PrintWriter out = response.getWriter()) {
            String tipoRol = request.getParameter("tipoRol");
            String esEliminado = request.getParameter("esEliminado");
            String id = request.getParameter("id");
            RolJpaController rolJpaController = new RolJpaController();
            Rol rol = rolJpaController.findRol(Integer.parseInt(id));
            System.out.println(tipoRol+"");
            if (!tipoRol.equals("Seleccionado")) {
                rol.setTipoRol(tipoRol);
                rol.setEsEliminado(Boolean.parseBoolean(esEliminado));
                try {
                    rolJpaController.edit(rol);
                    System.out.println("succes");
                    salida += "\"success\":1}";
                } catch (Exception ex) {
                    salida += "\"success\":0}";
                    Logger.getLogger(EditarRol.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("2222");
                }
            }else{
                salida += "\"success\":0}";
                System.out.println("errrr2");
            }
            out.print(salida);
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
