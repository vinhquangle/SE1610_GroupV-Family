/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import cart.Cart;
import dao.BookDAO;
import dto.BookDTO;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh - 21/09/2022
 */
@WebServlet(name = "AddBookCartController", urlPatterns = {"/AddBookCartController"})
public class AddBookCartController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/CartPage/viewCart.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ProductPage/productDetails.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            int quantityCheck = Integer.parseInt(request.getParameter("quantityCheck"));          
            if (action.equals("View")) {
                throw new Exception();
            }
            HttpSession session = request.getSession();
            String isbn = request.getParameter("isbn");
            int quantity = 0;
            try {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            } catch (Exception e) {
                request.setAttribute("MESSAG_FAIL", "Failed - Quantity must be greater than 0 or be a number!");
                url = SUCCESS;
                throw new Exception();
            }
            if (quantity < 1) {
                request.setAttribute("MESSAG_FAIL", "Failed - Quantity must be greater than 0 or be a number!");
                url = SUCCESS;
                throw new Exception();
            }
            BookDAO dao = new BookDAO();
            BookDTO book = new BookDTO();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();
                    int select = 0;
                    session.setAttribute("SELECT", select);
                }else if (cart.getCart().containsKey(isbn)) {
                    book = cart.getCart().get(isbn);
                    int quan = book.getQuantity() + quantity;
                    if (quan > quantityCheck) {
                        request.setAttribute("MESSAG_FAIL", "Failed - Quantity greater than the quantity in stock !(Only " + quantityCheck + " in stock)");
                        url = SUCCESS;
                        throw new Exception();
                    }
                }
                book = dao.loadBook(isbn);
                book.setQuantity(quantity);
                cart.add(book);
                //Cap nhat lai so luong
                Map<String, BookDTO> listSize = cart.getCart();
                int select = (int) session.getAttribute("SELECT");
                select+=quantity;
                session.setAttribute("SELECT", select);
                session.setAttribute("SIZE", listSize.size());
                session.setAttribute("CART", cart);
                request.setAttribute("MESSAG_ADD", "Added to your cart! - " + book.getName() + " - " + "Quantity: " + quantity);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at AddBookCartController: " + e.toString());
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
