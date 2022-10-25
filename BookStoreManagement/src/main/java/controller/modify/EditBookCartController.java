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
//Quang Vinh >>>>>>>>>>
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
            int quantityCheck = dao.quantityCheck(isbn, "1");//Lấy số lượng sản phẩm còn trong kho
            BookDTO book = dao.loadBook(isbn, "1");//Lấy thông tin sản phẩm theo ISBN
            try {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            } catch (Exception e) {
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div class=\"col-md-3\">\n"
                        + "                                <div class=\"product-preview\">\n"
                        + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                         <div class=\"col-md-9\">\n"
                        + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  trong giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                throw new Exception();
            }
            if (quantity < 1) {
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div class=\"col-md-3\">\n"
                        + "                                <div class=\"product-preview\">\n"
                        + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                         <div class=\"col-md-9\">\n"
                        + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  trong giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                throw new Exception();
            } else if (quantity > quantityCheck) {
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div class=\"col-md-3\">\n"
                        + "                                <div class=\"product-preview\">\n"
                        + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                         <div class=\"col-md-9\">\n"
                        + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  trong giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                throw new Exception();
            }
            HttpSession session = request.getSession();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    if (cart.getCart().containsKey(isbn)) {//Kiểm tra sản phẩm trong giỏ hàng
                        book = cart.getCart().get(isbn);
                        int select = (int) session.getAttribute("SELECT");
                        select -= book.getQuantity();
                        select += quantity;
                        session.setAttribute("SELECT", select);
                        book.setQuantity(quantity);
                        cart.edit(isbn, book);//Thay đổi số lượng sản phẩm có trong giỏ hàng
                        session.setAttribute("CART", cart);
                        url = SUCCESS;
                        request.setAttribute("MODAL", "<div class=\"row\">"
                                + "                         <div style=\"text-align: center\" class=\"col-md-12\">\n"
                                + "                             <p style=\"color: green;\"><b>Thay đổi số lượng sản phẩm thành công</b></p>\n"
                                + "                         </div>\n"
                                + "                     </div>\n"
                                + "                     <div class=\"row\">\n"
                                + "                         <div class=\"col-md-3\">\n"
                                + "                                <div class=\"product-preview\">\n"
                                + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                         <div class=\"col-md-9\">\n"
                                + "                                <div class=\"product-details\">\n"
                                + "                                    <h4 class=\"product-name\">" + book.getName() + "</h4>\n"
                                + "                                 </div>"
                                + "                                   <div>\n"
                                + "                                        <p class=\"product-price\">Số lượng: " + quantity + "</p>\n"
                                + "                                   </div>\n"
                                + "                                </div>\n"
                                + "                            </div>");
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
//<<<<<<<<<<
