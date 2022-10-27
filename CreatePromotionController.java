/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.PromotionDAO;
import dto.PromotionDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vqphi
 */
@WebServlet(name = "CreatePromotionController", urlPatterns = {"/CreatePromotionController"})
public class CreatePromotionController extends HttpServlet {

    public static final String ERROR = "createPromotion.jsp";
    public static final String SUCCESS = "createPromotion.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
String url = ERROR;
String msg = "Thêm thất bại";
String action = request.getParameter("action");
try{
    if("Create".equals(action)){
        String idstaff = request.getParameter("StaffID");
        String dateStart = request.getParameter("DateStart");
        String dateEnd = request.getParameter("DateEnd");
        String description = request.getParameter("Description");
        String condition = request.getParameter("Condition");
        String discount = request.getParameter("Discount");
        String status = request.getParameter("Status");
        boolean checkValidation = true;
        
        PromotionDTO proDTO = new PromotionDTO();
        PromotionDAO proDAO = new PromotionDAO();
        StaffDTO staffDTO = new StaffDTO();
        //check validation description
        if(description.isEmpty()){
            proDTO.setDescription("Mô tả không được để trống!");
            checkValidation = false;
        }
        
        if(checkValidation){
            boolean checkInsert = proDAO.createPromotion(new PromotionDTO(staffDTO.staffID, dateStart, dateEnd, condition, discount, status));
            if(checkInsert){
                msg = "Thêm Thành Công!";
                request.setAttribute("CREATE_PROMO", msg);
            }
            url = SUCCESS;
        } else{
            
        }
        
    }
    
}catch(Exception e){
    
}finally{
    
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
