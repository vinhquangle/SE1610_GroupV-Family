/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import dao.ReviewDAO;
import dao.ReviewDetailDAO;
import dto.CustomerDTO;
import dto.ReviewDTO;
import dto.ReviewDetailDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.NumberUtils;

/**
 *
 * @author Thịnh
 */
public class AddReviewController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String FAIL = "WEB-INF/JSP/ProductPage/productDetails.jsp";
    private static final String SUCCESS = "LoadController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            if (cus == null) {
                url = FAIL;
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                        + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>Bạn cần đăng nhập trước khi tạo đánh giá</b></p>\n"
                        + "                             <form action=\"LoginController\" method=\"POST\">\n"
                        + "                               <button name=\"action\" value=\"Login\" type=\"submit\" style=\"border: none; width: 30%; color: white; background-color: green; font-weight: bold; \">Đăng nhập</button>\n"
                        + "                              </form>\n"
                        + "                                </div>\n"
                        + "                            </div>");
            } else if (cus != null && action != null) {
                if (action.equals("Add")) {
                    String review = request.getParameter("review");
                    String reviewID = request.getParameter("reviewID");
                    LocalDate localDate = LocalDate.now();
                    ReviewDetailDAO detailDao = new ReviewDetailDAO();
                    List<ReviewDetailDTO> listReviewBook = new ArrayList<>();
                    if (request.getParameter("rating") == null || !NumberUtils.isNumber(request.getParameter("rating"))) {
                        url = FAIL;
                        request.setAttribute("MODAL", "<div class=\"row\">\n"
                                + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                                + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>Tạo đánh giá thất bại</b></p>\n"
                                + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>(Phần đánh giá sao không được để trống)</b></p>\n"
                                + "                                </div>\n"
                                + "                            </div>");
                    } else {
                        double rate = Double.parseDouble(request.getParameter("rating"));
                        if (detailDao.createReview(reviewID, cus.getCustomerID(), review, rate, String.valueOf(localDate))) {
                            ReviewDAO reviewDao = new ReviewDAO();
                            ReviewDTO reviewDto = reviewDao.getRateTime(reviewID);
                            reviewDto.setRate(((reviewDto.getRate()*reviewDto.getTimes())+rate)/(reviewDto.getTimes()+1));
                            reviewDto.setTimes(reviewDto.getTimes()+1);
                            if (reviewDao.updateReview(reviewDto)) {
                                url = SUCCESS;
                                request.setAttribute("MODAL", "<div class=\"row\">\n"
                                        + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                                        + "                             <p style=\"color: green; text-align: center; font-size: 15px;\"><b>Tạo đánh giá thành công</b></p>\n"
                                        + "                                </div>\n"
                                        + "                            </div>");
                                listReviewBook = detailDao.loadReview("1", reviewID);
                                session.setAttribute("LIST_REVIEW_DETAIL", listReviewBook);
                            }
                        }
                    }
                }else if(action.equals("Edit")){
                    String review = request.getParameter("review");
                    String reviewID = request.getParameter("reviewID");
                    String reviewDetailID = request.getParameter("reviewDetailID");
                    LocalDate localDate = LocalDate.now();
                    ReviewDetailDAO detailDao = new ReviewDetailDAO();
                    ReviewDetailDTO detailDto = new ReviewDetailDTO();
                    List<ReviewDetailDTO> listReviewBook = new ArrayList<>();
                    if (request.getParameter("rating") == null || !NumberUtils.isNumber(request.getParameter("rating"))) {
                        url = FAIL;
                        request.setAttribute("MODAL", "<div class=\"row\">\n"
                                + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                                + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>Thay đổi đánh giá thất bại</b></p>\n"
                                + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>(Phần đánh giá sao không được để trống)</b></p>\n"
                                + "                                </div>\n"
                                + "                            </div>");
                    } else {
                        double rate = Double.parseDouble(request.getParameter("rating"));
                        double tempRate = 0;
                        detailDto = detailDao.getReviewById(reviewDetailID);
                        if (detailDao.updateReview(reviewDetailID, cus.getCustomerID(), review, rate, String.valueOf(localDate))) {
                            ReviewDAO reviewDao = new ReviewDAO();
                            ReviewDTO reviewDto = reviewDao.getRateTime(reviewID);
                            tempRate = reviewDto.getRate()*reviewDto.getTimes()-detailDto.getRate();
                            reviewDto.setRate((tempRate+rate)/reviewDto.getTimes());
                            if (reviewDao.updateReview(reviewDto)) {
                                url = SUCCESS;
                                request.setAttribute("MODAL", "<div class=\"row\">\n"
                                        + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                                        + "                             <p style=\"color: green; text-align: center; font-size: 15px;\"><b>Thay đổi đánh giá thành công</b></p>\n"
                                        + "                                </div>\n"
                                        + "                            </div>");
                                listReviewBook = detailDao.loadReview("1", reviewID);
                                session.setAttribute("LIST_REVIEW_DETAIL", listReviewBook);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
