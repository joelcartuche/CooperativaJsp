/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaJpaController;
import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.IllegalOrphanException;
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

/**
 *
 * @author joelc
 */
@WebServlet(name = "CrearCuenta", urlPatterns = {"/CrearCuenta"})
public class CrearCuenta extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String idCuenta = request.getParameter("idCuenta");
        String usuarioNombre = request.getParameter("usuario");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");
        String idUsuario = request.getParameter("idUsuario");
        String idRol = request.getParameter("idRol");
        System.out.println("--------------------------");
        System.out.println("1111111");
        System.out.println("--------------------------");
        
        if (usuarioNombre == null || usuarioNombre.isEmpty()) {
            out.print("errorNombreUsuario");
        } else if (clave1 == null || clave1.equals("")) {
            out.print("errorClaveNoIngresada");
        } else if (!clave1.equals(clave2)) {
            out.print("errorClaveNoCoincide");
        } else if (idUsuario == null || idUsuario.equals("")) {
            out.print("errorUsuario");
        } else if (idRol == null || idRol.equals("")) {
            out.print("errorIdRol");
        } else {
            System.out.println("--------------------------");
        System.out.println("2222222");
        System.out.println("--------------------------");
            Encriptar encriptar = new Encriptar();
            System.out.println("--------------------------");
        System.out.println("3333333");
        System.out.println("--------------------------");
            CuentaJpaController cuentaJpaController = new CuentaJpaController();
            System.out.println("--------------------------");
        System.out.println("444444");
        System.out.println("--------------------------");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
            System.out.println("--------------------------");
        System.out.println("555555555");
        System.out.println("--------------------------");
            RolJpaController rolJpaController = new RolJpaController();
            System.out.println("--------------------------");
        System.out.println("666666666");
        System.out.println(idCuenta);
        System.out.println("--------------------------");
            Cuenta cuenta = new Cuenta();
            System.out.println("--------------------------");
        System.out.println("7777777777");
        System.out.println("--------------------------");
            cuenta.setUsuario(usuarioNombre);
            System.out.println("--------------------------");
        System.out.println("88888888888");
        System.out.println("--------------------------");
            
            cuenta.setPassword(encriptar.getMD5(clave1));
            System.out.println("--------------------------");
        System.out.println("999999999");
        System.out.println("--------------------------");
            
            cuenta.setIdUsuario(usuarioJpaController.findUsuario(Integer.parseInt(idUsuario)));
            System.out.println("--------------------------");
        System.out.println("1010101010");
        System.out.println("--------------------------");
            cuenta.setIdRol(rolJpaController.findRol(Integer.parseInt(idRol)));
            System.out.println("--------------------------");
            System.out.println("111111111oce");
            System.out.println("--------------------------");
            try {
                cuentaJpaController.create(cuenta);
                out.print("success");
            } catch (Exception e) {
                out.print("errorCuentaNoCreada");
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
