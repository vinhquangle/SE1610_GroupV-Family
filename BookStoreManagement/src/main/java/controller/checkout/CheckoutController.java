/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.checkout;

import dto.CustomerDTO;
import dto.StaffDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PCPV
 */
//Quốc Thịnh >>>>>>>>>>
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/checkout.jsp";
    private static final String FAIL = "WEB-INF/JSP/CartPage/viewCart.jsp";
    private static final String PAYPAL = "AuthorizePaymentController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            String payment = request.getParameter("payment");
            CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            if (cus == null && staff == null) {
                url = FAIL;
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                        + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>Bạn cần đăng nhập trước khi tiến hành thanh toán</b></p>\n"
                        + "                             <form action=\"LoginController\" method=\"POST\">\n"
                        + "                               <button name=\"action\" value=\"Login\" type=\"submit\" style=\"border: none; width: 30%; color: white; background-color: green; font-weight: bold; \">Đăng nhập</button>\n"
                        + "                              </form>\n"
                        + "                                </div>\n"
                        + "                            </div>");
            } else if (cus != null && action.equals("Checkout")) {
                url = SUCCESS;
            } else if (staff != null) {

            }
            if (action.equals("Order")) {
                if (payment.equals("paypal")) {
                    url = PAYPAL;
                } else if (payment.equals("momo")) {
                    
                } else if(payment.equals("cash")){

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
//<<<<<<<<<<