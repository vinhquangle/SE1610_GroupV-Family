/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import cart.Cart;
import dao.BookDAO;
import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import java.io.IOException;
import java.util.List;
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
    private static final String SUCCESS = "GetController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals("View")) {
                throw new Exception();
            }
            HttpSession session = request.getSession();
            String isbn = request.getParameter("isbn");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            BookDAO dao = new BookDAO();
            List<CategoryDTO> listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");//Load tất cả thể loại
            List<PublisherDTO> listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB"); //Load tất cả NXB
            BookDTO book = dao.loadBook(listCate, listPub, isbn);
            book.setQuantity(quantity);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();
                }
                cart.add(book);
                //Cap nhat lai so luong
                Map<String, BookDTO> listSize = cart.getCart();
                session.setAttribute("SIZE", listSize.size());
                session.setAttribute("CART", cart);
                request.setAttribute("MESSAGE1", "Add " + book.getName() + " - " + "Quantity: " + book.getCategory().getName() + " success");
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
