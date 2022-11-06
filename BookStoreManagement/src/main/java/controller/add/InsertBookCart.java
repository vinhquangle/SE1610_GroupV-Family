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
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thịnh
 */
public class InsertBookCart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int quantity = 0;
        double totalS = 0;
        boolean flag = false;
        Cart cart = new Cart();
        BookDAO dao = new BookDAO();
        String mess = new String();
        String isbn = new String();
        BookDTO bookCheck = new BookDTO();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {
            HttpSession session = request.getSession();
            int quantityCheck = Integer.parseInt(request.getParameter("quantityCheck"));
            String use = request.getParameter("use");
            isbn = request.getParameter("isbn");
            BookDTO book = dao.loadBook(isbn, "1");
            if (session != null) {
                cart = (Cart) session.getAttribute("CART");
                if (isbn.equals("F5")) {
                    throw new Exception();
                }
                if (use != null) {
                    if (use.equals("Add")) {
                        try {
                            quantity = Integer.parseInt(request.getParameter("quantity"));
                        } catch (Exception e) {
                            mess = "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            throw new Exception();
                        }
                        if (quantity < 1) {
                            mess = "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            throw new Exception();
                        }
                        if (quantity > quantityCheck) {
                            mess = "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            throw new Exception();
                        }
                        if (cart == null) {
                            cart = new Cart();//Khởi tạo giỏ hàng
                        } else if (cart.getCart().containsKey(isbn)) {
                            book = cart.getCart().get(isbn);//Lấy sản phẩm trong giỏ hàng theo ISBN
                            int quan = book.getQuantity() + quantity;
                            if (quan > quantityCheck) {
                                mess = "<div class=\"row\">\n"
                                        + "                         <div class=\"col-md-3\">\n"
                                        + "                                <div class=\"product-preview\">\n"
                                        + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                        + "                                </div>\n"
                                        + "                            </div>\n"
                                        + "                         <div class=\"col-md-9\">\n"
                                        + "                             <p style=\"color: red;\"><b>Thêm \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                        + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                                        + "                                </div>\n"
                                        + "                            </div>";
                                throw new Exception();
                            }
                        }
                        book = dao.loadBook(isbn, "1");//Lấy chi tiết sản phẩm theo ISBN
                        book.setQuantity(quantity);//Thay đổi số lượng sản phẩm
                        cart.add(book);//Thêm vào giỏ hàng
                        session.setAttribute("CART", cart);
                        Map<String, BookDTO> listSize = cart.getCart();
                        session.setAttribute("SIZE", listSize.size());
                        mess = "<div class=\"row\">"
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
                                + "                            </div>";
                    } else if (use.equals("Remove")) {
                        if (cart != null) {
                            mess = "<div class=\"row\">"
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
                                    + "                            </div>";
                            cart.remove(isbn);//Xóa sản phẩm khỏi giỏ hàng theo ISBN
                            session.setAttribute("CART", cart);
                            Map<String, BookDTO> listSize = cart.getCart();
                            session.setAttribute("SIZE", listSize.size());
                        }
                    } else if (use.equals("Edit")) {
                        try {
                            quantity = Integer.parseInt(request.getParameter("quantity"));
                        } catch (Exception e) {
                            mess = "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            throw new Exception();
                        }
                        if (quantity < 1) {
                            mess = "<div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  vào giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu phải là số dương lớn hơn 0)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            flag = true;
                            throw new Exception();
                        }
                        if (quantity > quantityCheck) {
                            mess = " <div class=\"row\">\n"
                                    + "                         <div class=\"col-md-3\">\n"
                                    + "                                <div class=\"product-preview\">\n"
                                    + "                                    <img src=\"" + book.getImg() + "\"/>\n"
                                    + "                                </div>\n"
                                    + "                            </div>\n"
                                    + "                         <div class=\"col-md-9\">\n"
                                    + "                             <p style=\"color: red;\"><b>Thay đổi \"" + book.getName() + "\" - số lượng " + quantity + "  trong giỏ hàng thất bại</b></p>\n"
                                    + "                             <p style=\"color: red;\"><b>(Số lượng yêu cầu không có sẳn - Hiện tại còn " + quantityCheck + " sản phẩm)</b></p>\n"
                                    + "                                </div>\n"
                                    + "                            </div>";
                            flag = true;
                            throw new Exception();
                        }
                        if (cart != null) {
                            if (cart.getCart().containsKey(isbn)) {//Kiểm tra sản phẩm trong giỏ hàng
                                book = cart.getCart().get(isbn);
                                book.setQuantity(quantity);
                                cart.edit(isbn, book);//Thay đổi số lượng sản phẩm có trong giỏ hàng
                                session.setAttribute("CART", cart);
                                mess = "<div class=\"row\">"
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
                                        + "                            </div>";
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Error at InsertBookCart: " + e.toString());
        } finally {
            PrintWriter out = response.getWriter();
            if (!isbn.equals("F5")) {
                out.println("                  <!-- Modal -->\n"
                        + "        <div  class=\"modal fade\" id=\"eModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                        + "            <div class=\"modal-dialog\" role=\"document\">\n"
                        + "                <div class=\"modal-content\">\n"
                        + "                    <div class=\"modal-header\">\n"
                        + "                        <h5 class=\"modal-title\" id=\"exampleModalLabel\">Thông báo</h5>\n"
                        + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                        + "                            <span aria-hidden=\"true\">&times;</span>\n"
                        + "                        </button>\n"
                        + "                    </div>\n"
                        + "                    <div class=\"modal-body\">\n"
                        + "                        " + mess + "\n"
                        + "                    </div>\n"
                        + "                    <div class=\"modal-footer\">\n"
                        + "                        <button type=\"button\" class=\"btn btn-danger\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <!-- Modal -->         \n");
            }
            if (cart != null) {
                char alphabet = 'A';
                if (cart.getCart().size() > 0) {
                    for (BookDTO book : cart.getCart().values()) {
                        quantity = book.getQuantity();
                        if (book.getIsbn().equals(isbn) && flag) {
                            System.out.println("1");
                            quantity = Integer.parseInt(request.getParameter("quantity"));
                        }
                        try {
                            bookCheck = dao.loadBook(book.getIsbn(), "1");
                            totalS += book.getPrice() * book.getQuantity();
                            out.println("                     <div class=\"order-col\">\n"
                                    + "                        <button type=\"button\" style=\"margin-right: 20px; border: none;\" onclick=\"loadAdd(document.getElementById('" + alphabet + "').value," + book.getIsbn() + "," + bookCheck.getQuantity() + ",'Edit')\" title=\"Chỉnh sửa\" class=\"fa-solid fa-pen-to-square\"></button>\n"
                                    + "                       <div style=\"width: 600px;\"><input id=\"" + alphabet + "\" type=\"number\" style=\"width:50px\" value=\"" + quantity + "\">x " + book.getName() + "</div>\n"
                                    + "                         <div style=\"width: 100px;\"></div>\n"
                                    + "                        <div style=\"width: 150px;\">" + currencyVN.format(book.getPrice() * book.getQuantity()) + "</div>\n"
                                    + "                         <div>\n"
                                    + "                             <button type=\"button\" onclick=\"loadAdd('1'," + book.getIsbn() + ",'1','Remove')\" title=\"Xóa\" style=\"border: none;\" class=\"fa-solid fa-trash-can\"></button></div>\n"
                                    + "                          </div>\n");
                            alphabet++;
                        } catch (Exception e) {
                        }
                    }
                }
                out.println("                     <input id=\"totalS\" hidden=\"\" value=\"" + currencyVN.format(totalS) + "\">");
            }
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
