/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import cart.Cart;
import dao.BRequestDetailDAO;
import dao.BookDAO;
import dao.BookRequestDAO;
import dto.BookDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh
 */
public class SendRequestController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String CREATE_CART = "WEB-INF/JSP/CartPage/createRequest.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/createRequest.jsp";
    private static final String FAIL = "WEB-INF/JSP/CartPage/viewCart.jsp";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String emptyCart = "";
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            if (staff == null) {
                url = FAIL;
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div style=\"text-align: center;\" class=\"col-md-12\">\n"
                        + "                             <p style=\"color: red; text-align: center; font-size: 15px;\"><b>Bạn cần đăng nhập trước khi tiến hành thanh toán</b></p>\n"
                        + "                             <form action=\"LoginController\" method=\"POST\">\n"
                        + "                               <button name=\"action\" value=\"Login\" type=\"submit\" style=\"border: none; width: 30%; color: white; background-color: green; font-weight: bold; \">Đăng nhập</button>\n"
                        + "                              </form>\n"
                        + "                                </div>\n"
                        + "                            </div>");

            } else if (staff != null) {
                url = CREATE_CART;
            }
            if (action.equals("Request") && staff != null) {
                try {
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart.getCart().size() > 0) {
                        int requestID = 0;
                        double total = 0;
                        BookRequestDAO bDAO = new BookRequestDAO();
                        BRequestDetailDAO bDetailsDAO = new BRequestDetailDAO();
                        BookDAO bookDao = new BookDAO();
                        for (BookDTO book : cart.getCart().values()) {
                            if (book.getQuantity() > bookDao.quantityCheck(book.getIsbn(), "1")) {
                                url = CREATE_CART;
                                request.setAttribute("MODAL", "<div class=\"row\">\n"
                                        + "                         <div class=\"col-md-12\">\n"
                                        + "                                <div class=\"product-preview\">\n"
                                        + "                                    <img src=\"https://bengo.vn/static/version1650994791/frontend/MageBig/martfury_layout01/vi_VN/images/empty-cart.svg\"/>\n"
                                        + "                                </div>\n"
                                        + "                            </div>\n"
                                        + "                         <div class=\"col-md-12\">\n"
                                        + "                             <p style=\"color: red; margin-top: 20px; text-align: center;\"><b>Thanh toán thất bại(Số lượng sách trong kho không đủ)</b></p>\n"
                                        + "                                </div>\n"
                                        + "                            </div>");
                                emptyCart = "Empty";
                                throw new Exception();
                            }
                            total += book.getQuantity() * book.getPrice();
                        }
                        requestID = bDAO.createRequest(staff.getStaffID(), "1");
                        if (requestID > 0) {
                            total = 0;
                            for (BookDTO book : cart.getCart().values()) {
                                if (bookDao.updateQuantity(book.getIsbn(), book.getQuantity())) {                                   
                                    bDetailsDAO.createRequestDetail(requestID, book, "1");
                                }
                            }
                            session.setAttribute("SIZE", 0);
                            cart.removeAll();
                            session.setAttribute("CART", cart);
                            url = CREATE_CART;
                            request.setAttribute("MODAL", "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-12\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"IMG/cart.png\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-12\">\n"
                                    + "                             <p style=\"color: green; margin-top: 20px; text-align: center;\"><b>Thanh toán thành công</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>");
                        }
                    } else {
                        url = CREATE_CART;
                        request.setAttribute("MODAL", "<div class=\"row\">\n"
                                + "                         <div class=\"col-md-12\">\n"
                                + "                                <div class=\"product-preview\">\n"
                                + "                                    <img src=\"https://bengo.vn/static/version1650994791/frontend/MageBig/martfury_layout01/vi_VN/images/empty-cart.svg\"/>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                         <div class=\"col-md-12\">\n"
                                + "                             <p style=\"color: red; margin-top: 20px; text-align: center;\"><b>Giỏ hàng đang trống, không thể tiến hành thanh toán</b></p>\n"
                                + "                                </div>\n"
                                + "                            </div>");
                    }
                } catch (Exception e) {
                    if (emptyCart.equals("Empty")) {
                        throw new Exception();
                    } else if (emptyCart == "") {
                        url = CREATE_CART;
                        request.setAttribute("MODAL", "<div class=\"row\">\n"
                                + "                         <div class=\"col-md-12\">\n"
                                + "                                <div class=\"product-preview\">\n"
                                + "                                    <img src=\"https://bengo.vn/static/version1650994791/frontend/MageBig/martfury_layout01/vi_VN/images/empty-cart.svg\"/>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                         <div class=\"col-md-12\">\n"
                                + "                             <p style=\"color: red; margin-top: 20px; text-align: center;\"><b>Giỏ hàng đang trống, không thể tiến hành thanh toán</b></p>\n"
                                + "                                </div>\n"
                                + "                            </div>");
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
