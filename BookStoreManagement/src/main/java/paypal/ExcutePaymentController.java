/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paypal;

import cart.Cart;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import dao.BookDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.BookDTO;
import dto.CustomerDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 *
 */
//Quốc Thịnh >>>>>>>>>>
public class ExcutePaymentController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/CartPage/checkoutFail.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/checkoutSuccess.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            BookDAO bookDao = new BookDAO();
            OrderDAO orderDao = new OrderDAO();
            OrderDetailDAO detailDao = new OrderDetailDAO();
            double total = 0;
            String paymentID = request.getParameter("paymentId");
            String payerID = request.getParameter("PayerID");
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.executePayment(paymentID, payerID);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            String ship = (String) session.getAttribute("SHIP");
            String promotion = (String) session.getAttribute("PROMOTION");
            String des = (String) session.getAttribute("DES");
            CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            Cart cart = (Cart) session.getAttribute("CART");
            for (BookDTO book : cart.getCart().values()) {
                if (book.getQuantity() > bookDao.quantityCheck(book.getIsbn(), "1")) {
                    url = ERROR;
                    throw new Exception();
                }
                total += book.getQuantity() * book.getPrice();
            }
            int orderID = 0;
            if (ship.equals("YES")) {
                String address = (String) session.getAttribute("SHIPING");
                double feeShip = Double.parseDouble((String) session.getAttribute("FEE_SHIP"));
                orderID = orderDao.insertOrderOnlineShip(cus.getCustomerID(), promotion, address, total, 0.0, feeShip, total + feeShip, des);
            } else if (ship.equals("NO")) {
                orderID = orderDao.insertOrderOnlineStore(cus.getCustomerID(), promotion, total, 0.0, 0, total, des);
            }
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
            url = SUCCESS;
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
