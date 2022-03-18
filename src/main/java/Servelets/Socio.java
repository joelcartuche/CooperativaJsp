/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "Socio", urlPatterns = {"/Socio"})
public class Socio extends HttpServlet {

    String listar = "views/listParnets.jsp";
    String agregar = "views/addParnet.jsp";
    String editar = "";
    String buscar = "";

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
            out.println("<title>Servlet Socio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Socio at " + request.getContextPath() + "</h1>");
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
        String acceso = "";
        String action = request.getParameter("accion");
        if (action.equalsIgnoreCase("listar")) {
            acceso = listar;
        } else if (action.equalsIgnoreCase("agregar")) {
            acceso = agregar;
        }
        //RequestDispatcher vista = request.getRequestDispatcher(acceso);
        //vista.forward(request, response);
        response.sendRedirect(acceso);
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
        //processRequest(request, response);
        String nombre = request.getParameter("nombre_socio");
        String apellido = request.getParameter("apellido_socio");
        String cedula = request.getParameter("cedula_socio");
        String telefono = request.getParameter("telefono_socio");
        String direccion = request.getParameter("direccion_socio");
        
        String salida ="{\"message\":\"El Socio se creó exitosamente\"}"; //almacena el Json de salida
        
        try ( PrintWriter out = response.getWriter()) {
            
            // aqui va la logica para registrar el nuevo socio

            // si el nuevo socio se creo exitosamente, se responde con:
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);
            out.print(salida);
            
            // si hubo un error con los datos, se responde con:
            //salida ="{\"error\":\"Hubo un error al registrar al socio.\"}";
            //response.setContentType("application/json");
            //response.setCharacterEncoding("UTF-8");
            //response.setStatus(400);
            //out.print(salida);
            
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
