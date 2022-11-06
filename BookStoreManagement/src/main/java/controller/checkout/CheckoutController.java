/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.checkout;

import aes.MyAES;
import cart.Cart;
import dao.BookDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PromotionDAO;
import dto.BookDTO;
import dto.CustomerDTO;
import dto.PromotionDTO;
import dto.StaffDTO;
import email.JavaMailUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            double total = 0;
            double discount = 0;
            String promotion = new String();
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            String payment = request.getParameter("payment");
            String method = request.getParameter("method");
            BookDAO bookDao = new BookDAO();
            OrderDAO orderDao = new OrderDAO();
            CustomerDAO cusDao = new CustomerDAO();
            OrderDetailDAO detailDao = new OrderDetailDAO();
            Cart cart = (Cart) session.getAttribute("CART");
            CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            LocalDate localDate = LocalDate.now();
            LocalDate dateEnd = LocalDate.now();
            PromotionDAO proDao = new PromotionDAO();
            List<PromotionDTO> listPro = proDao.loadAvailablePromotion("1");
            for (PromotionDTO promotionDTO : listPro) {
                dateEnd = LocalDate.parse(promotionDTO.getDateEnd());
                if (dateEnd.isBefore(localDate)) {
                    promotionDTO.setStatus("0");
                    proDao.updatePromotion(promotionDTO);
                }
            }
            listPro = proDao.loadAvailablePromotion("1");
            session.setAttribute("PROMOTION", proDao.loadAvailablePromotion("1"));
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
                        session.setAttribute("PROMOTION", listPro);
                        session.setAttribute("SHIP", "YES");
                        session.setAttribute("SHIPING", request.getParameter("address"));
                        session.setAttribute("DES", request.getParameter("des"));
                    } else if (method.equals("store")) {
                        session.setAttribute("PROMOTION", listPro);
                        session.setAttribute("SHIP", "NO");
                        session.setAttribute("DES", request.getParameter("des"));
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
                    for (PromotionDTO promotionDTO : listPro) {
                        if (promotionDTO.getCondition() <= total && promotionDTO.getDiscount() >= discount) {
                            discount = promotionDTO.getDiscount();
                            promotion = promotionDTO.getPromotionID();
                        }
                    }
                    discount = Double.parseDouble(String.format("%.2f", discount));
                    if (method.equals("ship")) {
                        if (total < 360000) {
                            feeShip = 24000;
                        } else if (total >= 360000) {
                            feeShip = 0;
                        }
                        orderID = orderDao.insertOrderOnlineShip(cus.getCustomerID(), promotion, address, total, discount, feeShip, (total * (1 - discount) + feeShip), des);
                    } else if (method.equals("store")) {
                        orderID = orderDao.insertOrderOnlineStore(cus.getCustomerID(), promotion, total, discount, 0, (total * (1 - discount)), des);
                    }
                    total = 0;
                    for (BookDTO book : cart.getCart().values()) {
                        if (bookDao.updateQuantity(book.getIsbn(), book.getQuantity())) {
                            total = book.getQuantity() * book.getPrice();
                            detailDao.insertOrderDetail(orderID, book, total, "1");
                        }
                    }
                    JavaMailUtil.sendMail(cus.getEmail(), 0, "Checkout", cart, orderID, feeShip, discount);
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
                        String phone = request.getParameter("phone");
                        String cusID = request.getParameter("cusID");
                        if (cusID != null) {
                            cus = cusDao.loadCustomer(cusID);
                            orderID = orderDao.insertOrderOffline(cusID, staff.getStaffID(), "", total, 0.0, total, "1");
                            if (orderID > 0) {
                                total = 0;
                                for (BookDTO book : cart.getCart().values()) {
                                    if (bookDao.updateQuantity(book.getIsbn(), book.getQuantity())) {
                                        total = book.getQuantity() * book.getPrice();
                                        detailDao.insertOrderDetail(orderID, book, total, "1");
                                    }
                                }
                                JavaMailUtil.sendMail(cus.getEmail(), 0, "Checkout", cart, orderID, 0, 0);
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
                        } else if (cusDao.checkCustomerPhone(phone)) {
                            url = CREATE_CART;
                            request.setAttribute("MODAL", "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-12\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"https://bengo.vn/static/version1650994791/frontend/MageBig/martfury_layout01/vi_VN/images/empty-cart.svg\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-12\">\n"
                                    + "                             <p style=\"color: red; margin-top: 20px; text-align: center;\"><b>Thanh toán thất bại(Số điện thoại đã được sử dụng)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>");
                        } else {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                            LocalDateTime now = LocalDateTime.now();
                            MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                            String AES_ecryptedStr = myCipher.AES_encrypt(phone);//Mã hóa AES
                            CustomerDTO customer = new CustomerDTO(dtf.format(now), phone, AES_ecryptedStr, "", "", phone, 0, "0", "0");
                            if (cusDao.createAccount(customer)) {
                                orderID = orderDao.insertOrderOffline(customer.getCustomerID(), staff.getStaffID(), "", total, 0.0, total, "1");
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
                            }
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
            log("Error at CheckoutController: " + e.toString());
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
