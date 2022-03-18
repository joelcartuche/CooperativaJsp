/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Rol;
import Modelos.Usuario;
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
@WebServlet(name = "EditarUsuario", urlPatterns = {"/EditarUsuario"})
public class EditarUsuario extends HttpServlet {

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
        String salida ="{"; //almacena el Json de salida
        PrintWriter out = response.getWriter();
        System.out.println("Entree");
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String cedula = request.getParameter("cedula");
        String telefono = request.getParameter("telefono");
        String esEliminado = request.getParameter("esEliminado");

        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        Usuario usuario = usuarioJpaController.findUsuario(Integer.parseInt(id));
        System.out.println(telefono.length()+"");
        if (!esSoloLetras(nombre)) {
            salida += "\"error\":1}";
        }else if(!esSoloLetras(apellido)){
            salida += "\"error\":2}";
        }else if (cedula.length()!=10) {
            salida += "\"error\":3}";
        }else if(telefono.length()!=9){
            salida += "\"error\":4}";
        }else{
            if (usuario != null) {
                usuario.setNombreUsuario(nombre);
                usuario.setApellidoUsuario(apellido);
                usuario.setCedulaUsuario(cedula);
                usuario.setTelefonoUsuario(telefono);
                usuario.setEsEliminado(Boolean.parseBoolean(esEliminado));

                try {
                    usuarioJpaController.edit(usuario);
                    salida += "\"success\":1}";
                } catch (NonexistentEntityException ex) {
                    salida += "\"success\":0}";
                    Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    salida += "\"success\":0}";
                    Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                salida += "\"success\":0}";
            }
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

    static boolean esSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }

        //Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
        return true;
    }
}
