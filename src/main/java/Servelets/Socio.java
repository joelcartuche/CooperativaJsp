/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.SociosJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuenta;
import Modelos.Socios;
import Modelos.Usuario;
import Utilidades.Validar;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    String editar = "views/editParnet.jsp";
    String eliminar = "views/deleteParnet.jsp";
    String ver = "views/viewParnet.jsp";

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
            
        } else if (action.equalsIgnoreCase("editar")) {
            int id = Integer.parseInt((String) request.getParameter("id"));
            SociosJpaController sociosJpaController = new SociosJpaController();
            Socios socio = sociosJpaController.findSocios(id);
            request.setAttribute("socio", socio);
            request.setAttribute("estado", socio.getEsEliminado());
            //request.getSession().setAttribute("idSocio", request.getParameter("id"));
            acceso = editar;

        } else if (action.equalsIgnoreCase("eliminar")) {
            request.setAttribute("id", request.getParameter("id"));
            request.setAttribute("esEliminado", request.getParameter("esEliminado"));
            acceso = eliminar;
            
        } else if (action.equalsIgnoreCase("ver")) {
            int id = Integer.parseInt((String) request.getParameter("id"));
            SociosJpaController sociosJpaController = new SociosJpaController();
            Socios socio = sociosJpaController.findSocios(id);
            request.setAttribute("socio", socio);
            request.setAttribute("estado", socio.getEsEliminado());
            acceso = ver;
        }

        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
        //response.sendRedirect(acceso);
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

        String action = request.getParameter("accion");

        String nombre = request.getParameter("nombre_socio");
        String apellido = request.getParameter("apellido_socio");
        String cedula = request.getParameter("cedula_socio");
        String telefono = request.getParameter("telefono_socio");
        String direccion = request.getParameter("direccion_socio");

        String salida = ""; //almacena el Json de salida

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (action.equalsIgnoreCase("agregar")) {

            try ( PrintWriter out = response.getWriter()) {

                if (!Validar.esValidoCedulaEc(cedula)) {
                    salida = "{\"error\":\"Hubo un error al registrar la cédula.\"}";
                    out.print(salida);
                } else if (!Validar.esSoloLetras(nombre)) {
                    salida = "{\"error\":\"Hubo un error al registrar el nombre. Verifique que no contenga números.\"}";
                    out.print(salida);
                } else if (!Validar.esSoloLetras(apellido)) {
                    salida = "{\"error\":\"Hubo un error al registrar el apellido. Verifique que no contenga números.\"}";
                    out.print(salida);
                } else if (!Validar.esValidoTelefono(telefono)) {
                    salida = "{\"error\":\"Hubo un error al registrar el teléfono.\"}";
                    out.print(salida);
                } else {
                    SociosJpaController socioJpaController = new SociosJpaController();
                    UsuarioJpaController usuarioJpaController = new UsuarioJpaController();

                    Socios socio = new Socios();
                    Usuario usuario = new Usuario();
                    Cuenta cuenta = new Cuenta(); // numero de cedula

                    socio.setNombreSocio(nombre);
                    usuario.setNombreUsuario(nombre);
                    
                    socio.setApellidoSocio(apellido);
                    usuario.setApellidoUsuario(apellido);
                    
                    socio.setCedulaSocio(cedula);
                    usuario.setCedulaUsuario(cedula);
                    
                    socio.setTelefonoSocio(telefono);
                    usuario.setTelefonoUsuario(telefono);
                    
                    socio.setDireccionSocio(direccion);
                    socio.setEsEliminado(Boolean.FALSE);

                    try {
                        socioJpaController.create(socio); // guardamos el socio en la bse de datos
                        usuarioJpaController.create(usuario); //guardamos el usuario en la base de datos
                        salida = "{\"message\":\"El Socio se creó exitosamente\"}";
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.setStatus(200);
                        out.print(salida);
                    } catch (Exception e) {
                        salida = "{\"error\":\"Hubo un error al registrar al socio.\"}";
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.print(salida);
                    }
                }

            }

        } else if (action.equalsIgnoreCase("editar")) {

            int id = Integer.parseInt(request.getParameter("id_socio"));
            String estado = request.getParameter("estadoSocio");

            try ( PrintWriter out = response.getWriter()) {

                if (!Validar.esValidoCedulaEc(cedula)) {
                    salida = "{\"error\":\"Hubo un error al registrar la cédula.\"}";
                    out.print(salida);
                } else if (!Validar.esSoloLetras(nombre)) {
                    salida = "{\"error\":\"Hubo un error al registrar el nombre. Verifique que no contenga números.\"}";
                    out.print(salida);
                } else if (!Validar.esSoloLetras(apellido)) {
                    salida = "{\"error\":\"Hubo un error al registrar el apellido. Verifique que no contenga números.\"}";
                    out.print(salida);
                } else if (!Validar.esValidoTelefono(telefono)) {
                    salida = "{\"error\":\"Hubo un error al registrar el teléfono.\"}";
                    out.print(salida);
                } else {
                    SociosJpaController socioJpaController = new SociosJpaController();

                    Socios socio = new Socios();

                    socio.setIdSocios(id);
                    socio.setNombreSocio(nombre);
                    socio.setApellidoSocio(apellido);
                    socio.setCedulaSocio(cedula);
                    socio.setTelefonoSocio(telefono);
                    socio.setDireccionSocio(direccion);

                    if (estado.equalsIgnoreCase("true")) {
                        socio.setEsEliminado(Boolean.TRUE);
                    } else {
                        socio.setEsEliminado(Boolean.FALSE);
                    }

                    try {
                        socioJpaController.edit(socio); // guardamos el socio en la bse de datos

                        salida = "{\"message\":\"La información se actualizó exitosamente\"}";
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.setStatus(200);
                        out.print(salida);
                    } catch (Exception e) {
                        salida = "{\"error\":\"Hubo un error al actualizar los datos.\"}";
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.print(salida);
                    }
                }

            }
        } else if (action.equalsIgnoreCase("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean esEliminado = Boolean.parseBoolean(request.getParameter("esEliminado"));
            try ( PrintWriter out = response.getWriter()) {
                SociosJpaController sociosJpaController = new SociosJpaController();

                Socios socio = sociosJpaController.findSocios(id);
                socio.setEsEliminado(!esEliminado);

                try {
                    sociosJpaController.edit(socio);

                    salida = "{\"message\":\"Los cambios se realizaron exitosamente\"}";
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(200);
                    out.print(salida);

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
                }
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
