/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.PromotionDAO;
import dto.PromotionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vqphi
 */
@WebServlet(name = "ModifyPromotionController", urlPatterns = {"/ModifyPromotionController"})
public class ModifyPromotionController extends HttpServlet {
    public static final String ERROR = "";
    public static final String SUCCESS = "SearchPromotionController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL = ERROR;
        String startDate = request.getParameter("StartDate");
        String endDate = request.getParameter("EndDate");
        String description = request.getParameter("Description");
        double condition = Double.parseDouble(request.getParameter("Condition"));
        double discount = Double.parseDouble(request.getParameter("Discount"));
        try{
            HttpSession session = request.getSession();
            PromotionDAO promotionDAO = new PromotionDAO();
            String action = request.getParameter("action");
            if("Update".equals(action)){
                PromotionDTO proDTO = new PromotionDTO();                  
                proDTO.setDateStart(startDate);
                proDTO.setDateEnd(endDate);
                proDTO.setDescription(description);
                proDTO.setCondition(condition);
                proDTO.setDiscount(discount);
                URL = SUCCESS;
            }
        }catch(Exception e){
            log("Error at ModifyPromotionController" + e.toString());
        }finally{
            request.getRequestDispatcher(URL).forward(request, response);
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
