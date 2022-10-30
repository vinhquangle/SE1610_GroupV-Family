/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.delete;

import cart.Cart;
import dto.BookDTO;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
//Quang Vinh >>>>>>>>>>
public class RemoveBookCartController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String isbn = request.getParameter("isbn");
            HttpSession session = request.getSession();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    int select = (int) session.getAttribute("SELECT");
                    select -= cart.getCart().get(isbn).getQuantity();
                    session.setAttribute("SELECT", select);
                    request.setAttribute("MODAL", "<div class=\"row\">"
                            + "                         <div style=\"text-align: center\" class=\"col-md-12\">\n"
                            + "                             <p style=\"color: #f58005;\"><b>Đã xóa sản phẩm khỏi giỏ hàng</b></p>\n"
                            + "                         </div>\n"
                            + "                     </div>\n"
                            + "                     <div class=\"row\">\n"
                            + "                         <div class=\"col-md-3\">\n"
                            + "                                <div class=\"product-preview\">\n"
                            + "                                    <img src=\"" + cart.getCart().get(isbn).getImg() + "\"/>\n"
                            + "                                </div>\n"
                            + "                            </div>\n"
                            + "                         <div class=\"col-md-9\">\n"
                            + "                                <div class=\"product-details\">\n"
                            + "                                    <h4 class=\"product-name\">" + cart.getCart().get(isbn).getName() + "</h4>\n"
                            + "                                 </div>"
                            + "                                   <div>\n"
                            + "                                        <p class=\"product-price\">Số lượng: " + cart.getCart().get(isbn).getQuantity() + "</p>\n"
                            + "                                   </div>\n"
                            + "                                </div>\n"
                            + "                            </div>");
                    cart.remove(isbn);//Xóa sản phẩm khỏi giỏ hàng theo ISBN
                    session.setAttribute("CART", cart);
                    //Cap nhat lai so luong san pham
                    Map<String, BookDTO> listSize = cart.getCart();
                    session.setAttribute("SIZE", listSize.size());
                    if (listSize.size() == 0) {
                        session.setAttribute("SELECT", 0);
                    }
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            log("Error at RemoveBookCartController: " + e.toString());
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