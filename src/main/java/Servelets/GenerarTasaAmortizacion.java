/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;


@WebServlet(name = "GenerarTasaAmortizacion", urlPatterns = {"/GenerarTasaAmortizacion"})
public class GenerarTasaAmortizacion extends HttpServlet {

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
        String montoData = request.getParameter("monto");
        String plazoData = request.getParameter("plazo");
        Double interesAnual = 8.0; //interes representado en porcentaje 8% anual
        Double interesMensual = interesAnual/12; //interes mensual
        
        if (montoData ==null || montoData.equals("")) {
            out.print("errorMontoVacio");
        }else if(plazoData == null || plazoData.equals("")){
            out.print("errorPlazoVacio");
        }else{
            double monto = Double.parseDouble(montoData);
            double plazo = Double.parseDouble(plazoData);
            double interes = interesMensual/100;
            double pagoMensual= Math.pow((1+interes), plazo)*interes;
            pagoMensual = pagoMensual/(Math.pow((1+interes), plazo)-1);
            pagoMensual = monto*pagoMensual;
            BigDecimal bd = new BigDecimal(pagoMensual).setScale(2, RoundingMode.HALF_UP);//redondeamos a dos decimales
            pagoMensual = bd.doubleValue();//almacenamos la variable

            String salida="[";
            double totalPagar = 0.0;

            for (int i = 0; i < plazo; i++) {
                
                double interesBanco = monto * interes; //
                bd =new BigDecimal(interesBanco).setScale(2, RoundingMode.HALF_UP);//redondeamos a dos decimales
                interesBanco = bd.doubleValue();

                double pagoProducto = pagoMensual - interesBanco;
                bd =new BigDecimal(pagoProducto).setScale(2, RoundingMode.HALF_UP);//redondeamos a dos decimales
                pagoProducto = bd.doubleValue();
                
                totalPagar += pagoProducto;//calculamos el total pagado
                
                if(i<plazo-1){
                    salida+="["+monto+","+pagoMensual+","+interesBanco+","+pagoProducto+"]";
                    salida+=",";
                }else{
                    if (totalPagar < Double.parseDouble(montoData)) {//en caso de que no coicida el total pagado con el monto dado
                        pagoMensual = pagoMensual+(Double.parseDouble(montoData)-totalPagar);//sumamos al pago mensual el centavo que falta
                        //recalculamos el pago del producto
                        pagoProducto = pagoMensual - interesBanco;
                        bd = new BigDecimal(pagoProducto).setScale(2, RoundingMode.HALF_UP);//redondeamos a dos decimales
                        pagoProducto = bd.doubleValue();
                    }
                    salida += "[" + monto + "," + pagoMensual + "," + interesBanco + "," + pagoProducto + "]";
                }

                System.out.printf("%f                  | %f                 | %f            | %f                     \n",monto,interesBanco,pagoMensual,pagoProducto );
                monto = monto -pagoProducto;
                bd =new BigDecimal(monto).setScale(2, RoundingMode.HALF_UP);//redondeamos el monto a dos decimales
                monto = bd.doubleValue();
                
                
            }
            salida+="]";
            //pasamos a json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            out.print(salida);
        }
        
        
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
