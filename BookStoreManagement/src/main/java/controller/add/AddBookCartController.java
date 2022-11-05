/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import cart.Cart;
import dao.BookDAO;
import dao.CategoryDAO;
import dao.PublisherDAO;
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
//Quang Vinh >>>>>>>>>>
@WebServlet(name = "AddBookCartController", urlPatterns = {"/AddBookCartController"})
public class AddBookCartController extends HttpServlet {

    private static final String HOME = "WEB-INF/JSP/HomePage/homePage.jsp";
    private static final String CART = "WEB-INF/JSP/CartPage/viewCart.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ProductPage/productDetails.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART;
        String controller = new String();
        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            controller = request.getParameter("controller");
            int quantityCheck = Integer.parseInt(request.getParameter("quantityCheck"));
            if (action.equals("View")) {
                url = CART;
                throw new Exception();
            }
            HttpSession session = request.getSession();
            String isbn = request.getParameter("isbn");
            String cateN = request.getParameter("cateID");
            String pubN = request.getParameter("pubID");
            String max = request.getParameter("max");
            String min = request.getParameter("min");
            String mes = request.getParameter("mess");
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            if (cateN != "") {
                request.setAttribute("CATEGORY", cateDao.getCategory(cateN));//Lấy thông tin thể loại
            } else if (pubN != "") {
                request.setAttribute("PUBLISHER", pubDao.getPublisher(pubN));//Lấy thông tin nhà xuất bản
            } else if (max != null && min != null) {
                request.setAttribute("MAX", max);
                request.setAttribute("MIN", min);
                request.setAttribute("MESS", mes);
            }
            int quantity = 0;
            BookDAO dao = new BookDAO();
            BookDTO book = dao.loadBook(isbn, "1");//Lấy chi tiết sản phẩm theo ISBN
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
                        + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                if (action.equals("Home")) {
                    url = HOME;
                } else {
                    url = SUCCESS;
                }
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
                        + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                if (action.equals("Home")) {
                    url = HOME;
                } else {
                    url = SUCCESS;
                }
                throw new Exception();
            }
            if (quantity > quantityCheck) {
                request.setAttribute("MODAL", "<div class=\"row\">\n"
                        + "                         <div class=\"col-md-3\">\n"
                        + "                                <div class=\"product-preview\">\n"
                        + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                         <div class=\"col-md-9\">\n"
                        + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  trong giỏ hàng thất bại</b></p>\n"
                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                        + "                                </div>\n"
                        + "                            </div>");
                if (action.equals("Home")) {
                    url = HOME;
                } else {
                    url = SUCCESS;
                }
                throw new Exception();
            }
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();//Khởi tạo giỏ hàng
                    int select = 0;
                    session.setAttribute("SELECT", select);
                } else if (cart.getCart().containsKey(isbn)) {
                    book = cart.getCart().get(isbn);//Lấy sản phẩm trong giỏ hàng theo ISBN
                    int quan = book.getQuantity() + quantity;
                    if (quan > quantityCheck) {
                        request.setAttribute("MODAL", "<div class=\"row\">\n"
                                + "                         <div class=\"col-md-3\">\n"
                                + "                                <div class=\"product-preview\">\n"
                                + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                         <div class=\"col-md-9\">\n"
                                + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                                + "                                </div>\n"
                                + "                            </div>");
                        if (action.equals("Home")) {
                            url = HOME;
                        } else {
                            url = SUCCESS;
                        }
                        throw new Exception();
                    }
                }
                book = dao.loadBook(isbn, "1");//Lấy chi tiết sản phẩm theo ISBN
                book.setQuantity(quantity);//Thay đổi số lượng sản phẩm
                cart.add(book);//Thêm vào giỏ hàng
                Map<String, BookDTO> listSize = cart.getCart();
                int select = (int) session.getAttribute("SELECT");
                select += quantity;
                session.setAttribute("SELECT", select);
                session.setAttribute("SIZE", listSize.size());
                session.setAttribute("CART", cart);
                request.setAttribute("MODAL", "<div class=\"row\">"
                        + "                         <div style=\"text-align: center\" class=\"col-md-12\">\n"
                        + "                             <p style=\"color: green;\"><b>Thêm vào giỏ hàng thành công</b></p>\n"
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
                if (action.equals("Home")) {
                    url = HOME;
                } else {
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            log("Error at AddBookCartController: " + e.toString());
        } finally {
            request.setAttribute("CONTROLLER", controller);
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
