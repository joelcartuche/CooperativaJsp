/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.CreditoJpaController;
import Controladores.SociosJpaController;
import Controladores.TasaAmortizacionJpaController;
import Modelos.Credito;
import Modelos.Socios;
import Modelos.TasaAmortizacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
@WebServlet(name = "CrearCredito", urlPatterns = {"/CrearCredito"})
public class CrearCredito extends HttpServlet {

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
            out.println("<title>Servlet CrearCredito</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearCredito at " + request.getContextPath() + "</h1>");
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
        String monto = request.getParameter("monto");
        String idSocio = request.getParameter("idSocio");
        String totalPagar = request.getParameter("totalPagar");
        String dataTasaAmortizacionDada =  request.getParameter("dataTasaAmortizacion");
        
        
        if (monto==null||monto.equals("")) {
            out.print("errorMonto");
        }else if(dataTasaAmortizacionDada ==null || dataTasaAmortizacionDada.equals("")){
            out.print("errorTasa");
        }else if(idSocio==null ||idSocio.equals("")){
            out.print("errorSocio");
        }else{
            
            String [] dataTasa = dataTasaAmortizacionDada.split(",");
            
            //instanciamos los controladores
            CreditoJpaController creditoJpaController = new CreditoJpaController();
            SociosJpaController sociosJpaController = new SociosJpaController();
            TasaAmortizacionJpaController tasaAmortizacionJpaController = new TasaAmortizacionJpaController();
            
            //intanciamos los modelos
            Credito credito = new Credito();         
            Socios socio = sociosJpaController.findSocios(Integer.parseInt(idSocio));
            TasaAmortizacion tasaAmortizacion = new TasaAmortizacion();
            
            credito.setMontoCredito(Float.parseFloat(monto));
            credito.setInteresCredito(0.8);
            credito.setEsEliminado(Boolean.FALSE);
            
            credito.setIdCodigoSocio(socio); 
            
            //el codigo sera configurado luego ahora para la insercion se lo hace con 
            //un valor cualquiera para evitar el erro de campo nulo generado por la base de datos
            credito.setCodigoCredito(0);
            
            creditoJpaController.create(credito);
            
            //configuramos el codigo del credito igualandolo al id_credito
            credito.setCodigoCredito(credito.getIdCredito());
            try {
                creditoJpaController.edit(credito);
                for (int i = 0; i < dataTasa.length; i += 4) {
                    tasaAmortizacion = new TasaAmortizacion();
                    tasaAmortizacion.setNumeroPagos(dataTasa.length/4);
                    tasaAmortizacion.setEsEliminado(Boolean.FALSE);
                    tasaAmortizacion.setEsPagado(Boolean.FALSE);
                    tasaAmortizacion.setFechaCreacion(Calendar.getInstance().getTime());
                    tasaAmortizacion.setSaldoDeuda(Double.parseDouble(dataTasa[i]));
                    tasaAmortizacion.setCuota(Double.parseDouble(dataTasa[i+1]));
                    tasaAmortizacion.setInteres(Double.parseDouble(dataTasa[i+2]));
                    tasaAmortizacion.setAmortizacion(Double.parseDouble(dataTasa[i+3]));
                    tasaAmortizacion.setCodigoCredito(credito);
                    tasaAmortizacionJpaController.create(tasaAmortizacion);
                }
                
                out.print("success");

                
            } catch (Exception ex) {
                Logger.getLogger(CrearCredito.class.getName()).log(Level.SEVERE, null, ex);
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
