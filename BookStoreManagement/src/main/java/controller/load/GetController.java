/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.load;

import dao.BookDAO;
<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/load/GetController.java
import dao.CategoryDAO;
import dao.PublisherDAO;
import dao.ReviewDetailDAO;
import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import dto.ReviewDetailDTO;
import java.io.IOException;
import java.util.List;
=======
import dto.BookDTO;
import java.io.IOException;
import java.util.Map;
>>>>>>> Quang-Vinh-Branch:AddBookCartController.java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/load/GetController.java
//Quốc Thịnh >>>>>>>>>>
public class GetController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";
=======
//Quang Vinh
@WebServlet(name = "AddBookCartController", urlPatterns = {"/AddBookCartController"})
public class AddBookCartController extends HttpServlet {

    private static final String HOME = "WEB-INF/JSP/HomePage/homePage.jsp";
    private static final String CART = "WEB-INF/JSP/CartPage/viewCart.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ProductPage/productDetails.jsp";
>>>>>>> Quang-Vinh-Branch:AddBookCartController.java

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART;
        String controller = new String();
        try {
<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/load/GetController.java
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            BookDAO bookDao = new BookDAO();
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            ReviewDetailDAO reviewDetailDao = new ReviewDetailDAO();
            List<CategoryDTO> listCate = cateDao.getListCategory("1"); //Lấy tất cả thể loại
            List<PublisherDTO> listPub = pubDao.getListPublisher("1"); //Lấy tất cả NXB
            List<BookDTO> listBook = bookDao.getListBook(index, "1"); //Lấy thông tin sách cho phân trang số 1
            List<ReviewDetailDTO> listReviewDetail = reviewDetailDao.loadReview("1");//Lấy tất cả đánh giá sách
            int count = bookDao.countBook("1");//Đếm tổng số lượng sản phẩm trong database
            url = SUCCESS;
            if (listBook.size() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("LIST_BOOK", listBook);
                session.setAttribute("LIST_PUB", listPub);
                session.setAttribute("LIST_CATE", listCate);
                session.setAttribute("LIST_REVIEW_DETAIL", listReviewDetail);
                session.setAttribute("COUNT_BOOK", count);
                session.setAttribute("LIST_BOOK_SORT", bookDao.getAllBook("1"));
                request.setAttribute("CONTROLLER", "GetController?");
=======
            String action = request.getParameter("action");
            controller = request.getParameter("controller");
            int quantityCheck = Integer.parseInt(request.getParameter("quantityCheck"));
            if (action.equals("View")) {
                url = CART;
                throw new Exception();
            }
            HttpSession session = request.getSession();
            String isbn = request.getParameter("isbn");
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
                        + "                             <p style=\"color: red;\"><b>Thêm \""+ book.getName() +"\" - số lượng "+ quantity +"  vào giỏ hàng thất bại</b></p>\n"
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
                        + "                             <p style=\"color: red;\"><b>Thêm \""+ book.getName() +"\" - số lượng "+ quantity +"  vào giỏ hàng thất bại</b></p>\n"
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
                        + "                             <p style=\"color: red;\"><b>Thêm \""+ book.getName() +"\" - số lượng "+ quantity +"  vào giỏ hàng thất bại</b></p>\n"
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
>>>>>>> Quang-Vinh-Branch:AddBookCartController.java
            }
        } catch (Exception e) {
            log("ERROR AT GETCONTROLLER : " + e.toString());
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
<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/load/GetController.java
//<<<<<<<<<<
=======
//
>>>>>>> Quang-Vinh-Branch:AddBookCartController.java
