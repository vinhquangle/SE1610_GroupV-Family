/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.PromotionDAO;
import dto.PromotionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vungo
 */
/*
- SearchPromotionController có 3 chức năng load tất cả promotion lên jsp, search promotion, tự động
update status thành 1 nếu promotion sau ngày hiện tại của hệ thống bằng cách nhấn nút UpdateStatus
- Đặt link dẫn đến trang show promotion là SearchControllerPromotionController để load toàn bộ promotion
trong db lên jsp
Locate this file at :controller.search
*/
@WebServlet(name = "SearchPromotionController", urlPatterns = {"/SearchPromotionController"})
public class SearchPromotionController extends HttpServlet {

    public static final String ERROR = "crudPromotion.jsp";
    public static final String SUCCESS = "crudPromotion.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            PromotionDAO proDAO = new PromotionDAO();
            // Load all promotion 
            List<PromotionDTO> proList = proDAO.loadPromotion();
            if (proList.size() > 0) {
                session.setAttribute("PROMOTION_LIST", proList);
                url = SUCCESS;
            } else {
                request.setAttribute("PROMOTION_ERROR", "Danh sách trống!!");
                url = ERROR;
            }
            //Lấy action của nút bấm
            String action = request.getParameter("action");
            // Nếu bấm nút Search thì thực hiện search
            if ("SearchP".equals(action)) {
                String search = request.getParameter("searchP");
                proList = proDAO.searchPromotion(search);
                if (proList.size() > 0) {
                    session.setAttribute("PROMOTION_LIST", proList);
                    url = SUCCESS;
                } else {
                    request.setAttribute("PRMOTION_ERROR", "Không tìm thấy mã giảm giá.");
                    url = ERROR;
                }
            }
            //Nếu bấm nút UpdateStatus thì nó update tất cả status có endDate nhỏ hơn hôm nay
            if ("UpdateStatus".equals(action)) {
                PromotionDTO proDTO = new PromotionDTO();
                for (PromotionDTO pro : proList) {
                    String promoID = pro.getPromotionID();
                    String endDate = pro.getDateEnd();
                    LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
                    LocalDate localDate = LocalDate.now(); // lấy ngày hiện tại so với endDate
                    if (localDate.isAfter(end)) {
                        proDAO.updateStatusPromotion(promoID);
                    }
                }
                url = SUCCESS;

            }
        } catch (Exception e) {
            log("Error at SearchPromotionController" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
