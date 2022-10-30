/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.checkout;

import cart.Cart;
import dao.BookDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.BookDTO;
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
    private static final String CREATE_CART = "WEB-INF/JSP/CartPage/createCart.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/checkout.jsp";
    private static final String CHECKOUT = "GetController";
    private static final String FAIL = "WEB-INF/JSP/CartPage/viewCart.jsp";
    private static final String PAYPAL = "AuthorizePaymentController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        String emptyCart = "";
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            String payment = request.getParameter("payment");
            String method = request.getParameter("method");
            BookDAO bookDao = new BookDAO();
            OrderDAO orderDao = new OrderDAO();
            OrderDetailDAO detailDao = new OrderDetailDAO();
            double total = 0;
            Cart cart = (Cart) session.getAttribute("CART");
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
                url = CREATE_CART;
            }
            if (action.equals("Order") && cus != null) {
                if (payment.equals("paypal")) {
                    if (method.equals("ship")) {
                        session.setAttribute("SHIP", "YES");
                        session.setAttribute("SHIPING", request.getParameter("address"));
                        session.setAttribute("DES", request.getParameter("des"));
                        session.setAttribute("PROMOTION", "1");
                    } else if (method.equals("store")) {
                        session.setAttribute("SHIP", "NO");
                        session.setAttribute("DES", request.getParameter("des"));
                        session.setAttribute("PROMOTION", "1");
                    }
                    url = PAYPAL;
                } else if (payment.equals("cash")) {
                    for (BookDTO book : cart.getCart().values()) {
                        if (book.getQuantity() > bookDao.quantityCheck(book.getIsbn(), "1")) {
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
                            url = CHECKOUT;
                            throw new Exception();
                        }
                        total += book.getQuantity() * book.getPrice();
                    }
                    String address = request.getParameter("address");
                    String des = request.getParameter("des");
                    int orderID = 0;
                    double feeShip = 0;
                    if (method.equals("ship")) {
                        if (total < 350000) {
                            feeShip = 24000;
                        } else if (total >= 350000) {
                            feeShip = 0;
                        }
                        orderID = orderDao.insertOrderOnlineShip(cus.getCustomerID(), "1", address, total, 0.0, feeShip, total + feeShip, des);
                    } else if (method.equals("store")) {
                        orderID = orderDao.insertOrderOnlineStore(cus.getCustomerID(), "1", total, 0.0, 0, total, des);
                    }
                    total = 0;
                    for (BookDTO book : cart.getCart().values()) {
                        if (bookDao.updateQuantity(book.getIsbn(), book.getQuantity())) {
                            total = book.getQuantity() * book.getPrice();
                            detailDao.insertOrderDetail(orderID, book, total, "1");
                        }
                    }
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
                    session.setAttribute("SIZE", 0);
                    cart.removeAll();
                    session.setAttribute("CART", cart);
                    url = CHECKOUT;
                }
            } else if (action.equals("Store") && staff != null) {
                try {
                    cart = (Cart) session.getAttribute("CART");
                    if (cart.getCart().size() > 0) {
                        int orderID = 0;
                        total = 0;
                        bookDao = new BookDAO();
                        orderDao = new OrderDAO();
                        detailDao = new OrderDetailDAO();
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
                        orderID = orderDao.insertOrderOffline(staff.getStaffID(), "1", total, 0.0, total, "1");
                        if (orderID > 0) {
                            total = 0;
                            for (BookDTO book : cart.getCart().values()) {
                                if (bookDao.updateQuantity(book.getIsbn(), book.getQuantity())) {
                                    total = book.getQuantity() * book.getPrice();
                                    detailDao.insertOrderDetail(orderID, book, total, "1");
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
//<<<<<<<<<<
