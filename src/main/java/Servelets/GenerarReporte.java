/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servelets;

import Controladores.DepositoJpaController;
import Controladores.RetiroJpaController;
import Controladores.SociosJpaController;
import Modelos.Deposito;
import Modelos.Retiro;
import Utilidades.Dominio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;


@WebServlet(name = "GenerarReporte", urlPatterns = {"/GenerarReporte"})
public class GenerarReporte extends HttpServlet {

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
            out.println("<title>Servlet GenerarReporte</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GenerarReporte at " + request.getContextPath() + "</h1>");
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
        String fechaInicioDada = request.getParameter("fechaInicio");
        String fechaFinDada = request.getParameter("fechaFin");
        String depositoORetiro = request.getParameter("depostioOretiro");
        String accion = request.getParameter("accion");

        
        System.out.println("depostio  = " +depositoORetiro );
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");//instanciamos el formato de la fecha
        
        Date fechaInicio = null;
        Date fechaFin  =null;
        
        
        if(fechaInicioDada ==null || fechaInicioDada.equals("")){
            out.print("errorFechaInicio");
        }
        if(fechaFinDada ==null || fechaFinDada.equals("")){
            out.print("errorFechaFin");
        }else{
            
            try {//convertimos las fechas dadas  de string a Date
                fechaInicio = formato.parse(fechaInicioDada.replace("-", "/"));
                fechaFin = formato.parse(fechaFinDada.replace("-", "/"));
            } catch (ParseException ex) {
                Logger.getLogger(GenerarReporte.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (fechaInicio != null && fechaFin != null) { //en caso de que no surgio ningun error con el cambio
                String salida = "";
                if (accion == null || accion.equals("")) {
                    if (depositoORetiro.equals("retiro")) {
                        salida = generarRetiro(fechaInicio, fechaFin);
                    } else if (depositoORetiro.equals("deposito")) {

                        salida = generarDeposito(fechaInicio, fechaFin);
                    } else {
                        salida = "errorBusquedaNoEncontrada";
                    }
                } else {
                    if (accion.equals("generarCsv")) {
                        Boolean esDeposito = false;
                        if (depositoORetiro.equals("deposito")) {
                            esDeposito= true;
                        }
                        try {
                            generarExel(esDeposito, fechaInicio, fechaFin);
                        } catch (Exception ex) {
                            out.print("errorCsv");
                            Logger.getLogger(GenerarReporte.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(salida);

            } else {
                
                out.print("errorFecha");
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
    
    public String generarDeposito(Date fechaInicio,Date fechaFin){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");//instanciamos el formato de la fecha
        //instanciamos el controlador del deposito
        DepositoJpaController depositoJpaController = new DepositoJpaController();
        List<Deposito> depositoList = depositoJpaController.findDepositoFechaInicioFin(fechaInicio, fechaFin);
        String salida = "";
        if (!depositoList.isEmpty()) {
            salida+="[";
            int cont = 0;//almacena el conteo del numero de datos recorridos
            for (Deposito deposito : depositoList) {
                //creamos los datos que seran enviados a la vista
                salida += "[\"" + "Deposito" + "\",";
                salida += "\"" + deposito.getCodigoSocio().getCuentaCooperativa().getNumeroCuenta() + "\",";
                salida += "\"" + deposito.getCodigoSocio().getNombreSocio() + "\",";
                salida += "\"" + deposito.getCodigoSocio().getCedulaSocio() + "\",";
                salida += "\"" + formato.format(deposito.getFechaDeposito()) + "\",";
                salida += "\"" + deposito.getMontoDeposito() + "\"]";
                if (cont < depositoList.size() - 1) {// solo se añadiran comas asta el penultimo dato
                    salida += ",";
                }
                cont++;
            }
            salida += "]";

        } else {
            salida="errorBusquedaNoEncontrada";
        }

        return salida;
    };
    
    public String generarRetiro(Date fechaInicio,Date fechaFin){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");//instanciamos el formato de la fecha
        //instanciamos el controlador del deposito
        RetiroJpaController retiroJpaController = new RetiroJpaController();
        List<Retiro> retiroList = retiroJpaController.findRetiroFechaInicioFin(fechaInicio, fechaFin);
        String salida = "";
        if (!retiroList.isEmpty()) {
            salida+="[";
            int cont = 0;//almacena el conteo del numero de datos recorridos
            for (Retiro retiro : retiroList) {
                //creamos los datos que seran enviados a la vista
                salida += "[\"" + "Retiro" + "\",";
                salida += "\"" + retiro.getCodigoSocio().getCuentaCooperativa().getNumeroCuenta() + "\",";
                salida += "\"" + retiro.getCodigoSocio().getNombreSocio() + "\",";
                salida += "\"" + retiro.getCodigoSocio().getCedulaSocio() + "\",";
                salida += "\"" + formato.format(retiro.getFechaRetiro()) + "\",";
                salida += "\"" + retiro.getMontoRetiro() + "\"]";
                if (cont < retiroList.size() - 1) {// solo se añadiran comas asta el penultimo dato
                    salida += ",";
                }
                cont++;
            }
            salida += "]";

        } else {
            salida="errorBusquedaNoEncontrada";
        }

        return salida;
    };
    
    private static void generarExel(Boolean esDeposito, Date fechaInicio, Date fechaFin) throws Exception {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");//instanciamos el formato de la fecha
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Hoja excel");

        String[] headers = new String[]{
                "Tipo de operación",
                "Numero de cuenta",
                "Nombre del socio",
                "Número de cedula",
                "Fecha",
                "Monto"
                
        };

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        

        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
       
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
 

        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        RetiroJpaController retiroJpaController = new RetiroJpaController();
        DepositoJpaController depositoJpaController = new DepositoJpaController();
        //generamos los datos
        
        List<Retiro> retiroList = retiroJpaController.findRetiroFechaInicioFin(fechaInicio, fechaFin);
        List<Deposito> depositoList = depositoJpaController.findDepositoFechaInicioFin(fechaInicio, fechaFin);

        if (!esDeposito) {
            for (int i = 0; i < retiroList.size(); ++i) {
                HSSFRow dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue("Retiro");
                dataRow.createCell(1).setCellValue(retiroList.get(i).getCodigoSocio().getCuentaCooperativa().getNumeroCuenta());
                dataRow.createCell(2).setCellValue(retiroList.get(i).getCodigoSocio().getNombreSocio());
                dataRow.createCell(3).setCellValue(retiroList.get(i).getCodigoSocio().getCedulaSocio());
                dataRow.createCell(4).setCellValue(formato.format(retiroList.get(i).getFechaRetiro()));
                dataRow.createCell(5).setCellValue(retiroList.get(i).getMontoRetiro());
                
            }
        }else{
            for (int i = 0; i < depositoList.size(); ++i) {
                HSSFRow dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue("Deposito");
                dataRow.createCell(1).setCellValue(depositoList.get(i).getCodigoSocio().getCuentaCooperativa().getNumeroCuenta());
                dataRow.createCell(2).setCellValue(depositoList.get(i).getCodigoSocio().getNombreSocio());
                dataRow.createCell(3).setCellValue(depositoList.get(i).getCodigoSocio().getCedulaSocio());
                dataRow.createCell(4).setCellValue(formato.format(depositoList.get(i).getFechaDeposito()));
                dataRow.createCell(5).setCellValue(depositoList.get(i).getMontoDeposito());
            }
        
        }

/*
        HSSFRow dataRow = sheet.createRow(1 + DATA.size());
        HSSFCell total = dataRow.createCell(1);
        total.setCellType(CellType.FORMULA);
        total.setCellStyle(style);
        total.setCellFormula(String.format("SUM(B2:B%d)", 1 + DATA.size()));
        "C:\Users\joelc\Downloads\"
*/
        Dominio dom = new Dominio();
        Date date = Calendar.getInstance().getTime();
        String nombreArchivo = "date_"
                +date.getYear()+"_"
                +date.getMonth()+"_"
                +date.getDay()+"_"
                +date.getHours()+"_"
                +date.getMinutes()+"_"
                +date.getSeconds() +".xls";
        FileOutputStream file = new FileOutputStream("C:\\Users\\joelc\\Downloads\\"+nombreArchivo);
        workbook.write(file);
        file.close();
    }
}
