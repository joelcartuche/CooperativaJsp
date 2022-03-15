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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            System.out.println("entre1");

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
        System.out.println("Entre get");
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
        String salida ="{\"data\":"; //almacena el Json de salida
        
        try ( PrintWriter out = response.getWriter()) {
            
            Encriptar enc = new Encriptar();//encriptador de datos en MD5
            String user = request.getParameter("user"); //recogemos los datos enviados desde el template
            String password = request.getParameter("password");
            HttpSession sesion = request.getSession(); // almacenamos la sesion iniciada
            CuentaJpaController cuentaJpaController = new CuentaJpaController(); //llamamos al controlador jpa
            Cuenta cuentaUsurioBuscado = cuentaJpaController.findCuentaUsuario(user); //buscamos el usuario dado el nombre de usuario
            
            if (cuentaUsurioBuscado != null) { //en caso  de que no exista el usuario buscado
                if (cuentaUsurioBuscado.getPassword().equals(enc.getMD5(password))) { //comparamos las contraseñas
                    //enviamos parametros a la sesion
                    sesion.setAttribute("logueado", "1"); 
                    sesion.setAttribute("user", cuentaUsurioBuscado.getUsuario());
                    sesion.setAttribute("id", cuentaUsurioBuscado.getIdCuenta());
                    //enviamos el json para la vista login.jsp
                    salida= salida+"{\"user\":\""+cuentaUsurioBuscado.getUsuario()+"\",\"id\":"+cuentaUsurioBuscado.getIdCuenta()+",\"logueado\":1}}";
                    out.print(salida);
                } else {//retornamos en este caso no existe contraseña
                    salida= salida+"{\"esContraIncorrecta\":1}}";
                    out.print(salida);
                    
                }
            } else {//retornamos el valor noExisteUsuario
                salida = salida+"{\"noExisteUsuario\":1}}";
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
