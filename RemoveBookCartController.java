/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.delete;

import cart.Cart;
import dto.BookDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh - 22/09/2022
 */
@WebServlet(name = "RemoveBookCartController", urlPatterns = {"/RemoveBookCartController"})
public class RemoveBookCartController extends HttpServlet {
    private static  final String ERROR = "JSP/CartPage/viewCart.jsp";
    private static  final String SUCCESS = "JSP/CartPage/viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try{
            String isbn = request.getParameter("isbn");
            HttpSession session = request.getSession();
            if(session!=null){
                Cart cart = (Cart) session.getAttribute("CART");
                if(cart!=null){
                    cart.remove(isbn);
                    session.setAttribute("CART", cart);
                    //Cap nhat lai so luong san pham
                    Map<String, BookDTO> listSize =  cart.getCart();
                    session.setAttribute("SIZE", listSize.size());
                    url = SUCCESS;
                }
            }
        }catch(Exception e){
            log("Error at RemoveBookCartController: " + e.toString());
        }finally{
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
