/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.load;

import dao.ReviewDetailDAO;
import dto.CustomerDTO;
import dto.ReviewDetailDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thịnh
 */
public class LoadReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String reviewId = new String();
        CustomerDTO cus = new CustomerDTO();
        StaffDTO staff = new StaffDTO();
        ReviewDetailDAO detailDao = new ReviewDetailDAO();
        List<ReviewDetailDTO> listReviewBook = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            reviewId = request.getParameter("reviewId");
            cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            String use = request.getParameter("use");
            if(use!=null){
                if(use.equals("hide")){
                    String reviewDetailID = request.getParameter("reviewDetailID");
                    detailDao.setStatus("0", reviewDetailID);
                }
            }
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            indexCount = (detailDao.loadReview("1", reviewId).size());
            listReviewBook = detailDao.loadReview5("1", reviewId, index);
        } catch (Exception e) {

        } finally {
            PrintWriter out = response.getWriter();
            int count = 0;
            for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                int star = (int) reviewDetailDTO.getRate();
                int starEmpty = 5 - star;
                out.println("                        <ul class=\"reviews\">\n"
                        + "                           <li>\n"
                        + "                              <div class=\"review-heading\">\n"
                        + "                             <h5 style=\"word-wrap: break-word; white-space: pre-wrap;\" class=\"name\">" + reviewDetailDTO.getCustomer().getName() + "</h5>\n"
                        + "                                   <p class=\"date\">" + reviewDetailDTO.getDate() + "</p>\n"
                        + "                                    <div class=\"review-rating\">\n");

                for (int i = 0; i < star; i++) {
                    out.println("                             <i class=\"fa fa-star\"></i>\n");
                }
                for (int i = 0; i < starEmpty; i++) {
                    out.println("                             <i class=\"fa fa-star-o\"></i>\n");
                }
                out.println("                                     </div>\n"
                        + "                                </div>\n"
                        + "                             <div class=\"review-body\">\n"
                        + "                              <p style=\"display: inline-block; width: 92%; word-break: break-all; white-space: normal;\">" + reviewDetailDTO.getDescription() + "</p>\n");
                count--;
                if (staff != null) {
                    out.println("                 <i style=\"float: right; cursor: pointer; padding: 5px;\" onclick=\"show(" + count + ")\" class=\"fa fa-ellipsis-v\"></i>\n"
                            + "             <a class=\"tagA\" onclick=\"loadReview("+ index +","+ reviewId +",'hide',"+ reviewDetailDTO.getReviewDetailID() +")\" style=\"left: 400px; position: absolute; background-color: white; border: 1px solid black; cursor: pointer; padding: 5px; font-size: 12px; width: 90px\" id=\"" + count + "\">Ẩn đánh giá</a>");
                }
                out.println("                          </div>\n"
                        + "                             </li>\n"
                        + "                             </ul>\n");
            }
            out.println("                <div style=\"text-align: center;\" class=\"row\">\n"
                    + "                    <div class=\"col-md-12\">\n"
                    + "                              <div class=\"pagination\">\n");

            int page = 0;
            if ((indexCount % 5) == 0) {
                page = indexCount / 5;
            } else {
                page = (indexCount / 5) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                           <a id=\"" + j + "\" onclick=\"loadReview(" + j + "," + reviewId + ")\">" + j + "</a>\n");
            }
            out.println("                               </div>\n"
                    + "                        </div>\n"
                    + "                      </div>");
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
