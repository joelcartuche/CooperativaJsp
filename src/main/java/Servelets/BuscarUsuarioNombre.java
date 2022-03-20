/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.UsuarioJpaController;
import Modelos.CuentaCooperativa_;
import Modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author joelc
 */
@WebServlet(name = "BuscarUsuarioNombre", urlPatterns = {"/BuscarUsuarioNombre"})
public class BuscarUsuarioNombre extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        System.out.println("Enteeeeeeeebuscar usuario");
        String nombreApellido= request.getParameter("nombreApellido");

        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        List<Usuario> listaUsuario = usuarioJpaController.findUsuarioNombre(nombreApellido);
        String data="";
        if (listaUsuario==null || listaUsuario.size()==0) {
            data="error";
        }else{
            for (Usuario usuario : listaUsuario) {
                if (!usuario.getEsEliminado()) {
                    data+=usuario.getIdUsuario().toString()+";";
                    data+=usuario.getNombreUsuario().toString()+";";
                    data+=usuario.getApellidoUsuario().toString()+";";
                    data+=usuario.getCedulaUsuario().toString()+";";
                    data+=usuario.getTelefonoUsuario().toString()+";";
                }
            }
        }
        
        out.print(data);
        
    }
    
    private String buscarUsuarioNombre(String nombreApellido){
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        List<Usuario> listaUsuario = usuarioJpaController.findUsuarioNombre(nombreApellido);
        String data="";
        if (listaUsuario==null || listaUsuario.size()==0) {
            data="error";
        }else{
            for (Usuario usuario : listaUsuario) {
                if (!usuario.getEsEliminado()) {
                    data+=usuario.getIdUsuario().toString()+";";
                    data+=usuario.getNombreUsuario().toString()+";";
                    data+=usuario.getApellidoUsuario().toString()+";";
                    data+=usuario.getCedulaUsuario().toString()+";";
                    data+=usuario.getTelefonoUsuario().toString()+";";
                }
            }
        }
        return data;
    };
    
    private String buscarUsuarioId(String id){
        String data="";
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        Usuario usuario = usuarioJpaController.findUsuario(Integer.parseInt(id));
        data = "{\"nombreUsuario\":"+usuario.getNombreUsuario()+" "+usuario.getApellidoUsuario()+"}";
        return data;
    };
    
    
    

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
