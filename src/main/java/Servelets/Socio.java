/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CuentaCooperativaJpaController;
import Controladores.SociosJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Modelos.Cuenta;
import Modelos.CuentaCooperativa;
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

                    // Creamos un nuevo socio
                    SociosJpaController socioJpaController = new SociosJpaController();
                    Socios socio = new Socios();
                    socio.setNombreSocio(nombre);
                    socio.setApellidoSocio(apellido);
                    socio.setCedulaSocio(cedula);
                    socio.setTelefonoSocio(telefono);
                    socio.setDireccionSocio(direccion);
                    socio.setEsEliminado(Boolean.FALSE);

                    // creamos un nuevo usuario
                    UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
                    Usuario usuario = new Usuario();
                    usuario.setNombreUsuario(nombre);
                    usuario.setApellidoUsuario(apellido);
                    usuario.setCedulaUsuario(cedula);
                    usuario.setTelefonoUsuario(telefono);

                    try {
                        // guardamos el socio en la bse de datos
                        socioJpaController.create(socio);
                        socio.setCodigoSocio(socio.getIdSocios());
                        socioJpaController.edit(socio);

                        //guardamos el usuario en la base de datos
                        usuarioJpaController.create(usuario);

                        // Creamos una nueva cuenta cooperativa
                        CuentaCooperativaJpaController cuentaCooperativaJpaController = new CuentaCooperativaJpaController();
                        CuentaCooperativa cuentaCooperativa = new CuentaCooperativa();
                        cuentaCooperativa.setNombreCuenta(socio.getNombreSocio());
                        cuentaCooperativa.setIdSocios(socio);
                        cuentaCooperativa.setIdUsuario(usuario);

                        // creamos una cuenta cooperativa para buscar si existe replicada en la base de datos
                        CuentaCooperativa findCuenta = new CuentaCooperativa();
                        // realizamos la generacion del numero de cuenta y el codigo de cuenta
                        // el codigo de cuenta es el mismo que el numero de cuenta
                        boolean esBuscarBumero = true;
                        int intentos = 0;
                        while (esBuscarBumero && intentos <= 20) {
                            // generamos un número de cuenta
                            int numeroRandom = (int) (Math.random() * 999999999 + 1);
                            // buscamos si existe una cuenta con el mismo número
                            findCuenta = cuentaCooperativaJpaController.findNumeroCuentaCooperativa("" + numeroRandom);
                            // si no existe, guardamos el numero aleatorio en la cuenta
                            if (findCuenta == null) {
                                cuentaCooperativa.setNumeroCuenta("" + numeroRandom);
                                cuentaCooperativa.setCodigoCuenta("" + numeroRandom);
                                esBuscarBumero = false;
                            }
                            intentos = intentos + 1;
                        }
                        // guardamos la cuenta cooperativa en la base de datos
                        cuentaCooperativaJpaController.create(cuentaCooperativa);
                        // presentamos la respuesta del servidor
                        salida = "{\"message\":\"El Socio " + socio.getNombreSocio() + " " + socio.getApellidoSocio() + " se creó exitosamente.\",";
                        salida += "\"numeroCuenta\":\"" + cuentaCooperativa.getNumeroCuenta() + "\"}";
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
