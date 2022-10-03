/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.modify;

import cart.Cart;
import dao.BookDAO;
import dto.BookDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class EditBookCartController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String isbn = request.getParameter("isbn");
            int quantity = 0;
            BookDAO dao = new BookDAO();
            int quantityCheck = dao.quantityCheck(isbn);
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                request.setAttribute("MESSAG_FAIL", "Failed - Quantity must be greater than 0 or be a number!");
                throw new Exception();
            } else if (quantity > quantityCheck) {
                request.setAttribute("MESSAG_FAIL", "Failed - The requested quantity for " + quantity + " is not available!(Only " + quantityCheck + " in stock)");
                throw new Exception();
            }
            HttpSession session = request.getSession();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    BookDTO book = new BookDTO();
                    if (cart.getCart().containsKey(isbn)) {
                        book = cart.getCart().get(isbn);
                        book.setQuantity(quantity);
                        cart.edit(isbn, book);
                        session.setAttribute("CART", cart);
                        url = SUCCESS;
                        request.setAttribute("MESSAG_ADD", "Modified quantity! - " + book.getName() + " - " + "Quantity: " + quantity);
                    }
                }
            }
        } catch (Exception e) {
            log("Error at EditBookCartController: " + e.toString());
            url = SUCCESS;
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
