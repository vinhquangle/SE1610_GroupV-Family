/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import cart.Cart;
import dao.BRequestDetailDAO;
import dao.BookDAO;
import dao.BookRequestDAO;
import dto.BRequestDetailDTO;
import dto.BookDTO;
import dto.BookRequestDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thịnh
 */
public class ManageRequestController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int quantity = 0;
        int indexCount = 0;
        Cart cart = new Cart();
        String use = new String();
        String isbn = new String();
        String modal = new String();
        String success = new String();
        String search = new String();
        BookDTO book = new BookDTO();
        BookDAO bookDao = new BookDAO();
        StaffDTO staff = new StaffDTO();
        List<BookDTO> listBook = new ArrayList<>();
        Locale localeVN = new Locale("vi", "VN");
        BookRequestDAO requestDao = new BookRequestDAO();
        BRequestDetailDAO detailDao = new BRequestDetailDAO();
        BookRequestDTO requestBook = new BookRequestDTO();
        BRequestDetailDTO requestDetail = new BRequestDetailDTO();
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {
            HttpSession session = request.getSession();
            use = request.getParameter("use");
            isbn = request.getParameter("isbn");
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (session != null) {
                cart = (Cart) session.getAttribute("CART1");
            }
            indexCount = bookDao.countBook("%%");
            listBook = bookDao.getListBook(index, "%%");
            if (use != null) {
                if (use.equals("addBook")) {
                    try {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    } catch (Exception e) {
                        modal = "Thêm vào thất bại(số lượng không hợp lệ)";
                        throw new Exception();
                    }
                    if (quantity < 1) {
                        modal = "Thêm vào thất bại(số lượng phải lớn hơn 0)";
                        throw new Exception();
                    }
                    if (cart == null) {
                        cart = new Cart();//Khởi tạo giỏ hàng
                    }
                    book = bookDao.loadBook(isbn, "%%");//Lấy chi tiết sản phẩm theo ISBN
                    book.setQuantity(quantity);//Thay đổi số lượng sản phẩm
                    cart.add(book);//Thêm vào giỏ hàng
                    modal = "Thêm vào danh sách yêu cầu nhập thành công";
                } else if (use.equals("editBook")) {
                    try {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(số lượng không hợp lệ)";
                        throw new Exception();
                    }
                    if (quantity < 1) {
                        modal = "Thay đổi thất bại(số lượng phải lớn hơn 0)";
                        throw new Exception();
                    }
                    if (cart != null) {
                        if (cart.getCart().containsKey(isbn)) {
                            book = cart.getCart().get(isbn);
                            book.setQuantity(quantity);
                            cart.edit(isbn, book);//Thay đổi số lượng sản phẩm có trong giỏ hàng
                            modal = "Thay đổi số lượng thành công";
                        }
                    }
                } else if (use.equals("searchAdd")) {
                    search = request.getParameter("searchRequest");
                    if (search == null || search == "") {
                        search = "";
                        listBook = bookDao.getListBook(index, "%%");//Lấy sản phẩm theo phân trang
                    } else {
                        //Chuyển chuỗi có dấu thành không dấu
                        request.setCharacterEncoding("UTF-8");
                        String temp = Normalizer.normalize(request.getParameter("searchRequest"), Normalizer.Form.NFD);
                        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                        String txtSearch = pattern.matcher(temp).replaceAll("");
                        //Chuyển chuỗi có dấu thành không dấu
                        indexCount = bookDao.searchBook(txtSearch, "%%").size();//Lấy số lượng tìm kiếm tất cả sách thỏa yêu cầu
                        listBook = bookDao.searchBook9(txtSearch, index, "%%");//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
                    }
                } else if (use.equals("add")) {
                    if (cart == null || cart.getCart().size() <= 0) {
                        modal = "Tạo thất bại(Danh sách đang trống)";
                        throw new Exception();
                    } else if (cart.getCart().size() > 0) {
                        int requestID = requestDao.insertRequest(staff.getStaffID(), "1", "0");
                        for (BookDTO bookK : cart.getCart().values()) {
                            detailDao.insertRequestDetail(requestID, bookK, "1", "0");
                        }
                        modal = "Tạo yêu cầu nhập thành công";
                        success = "Done";
                        session.setAttribute("CART1", new Cart());
                    }
                }
            }
            session.setAttribute("CART1", cart);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Customer</title>\n"
                    + "        <meta name=\"description\" content=\"\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">"
                    + "        <div class=\"container\">\n"
                    + "            <div class=\"row justify-content-center\">\n"
                    + "                <!-- Modal-->\n"
                    + "                <div id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\" class=\"modal text-left\">\n"
                    + "                    <div role=\"document\" class=\"modal-dialog modal-xl\">\n"
                    + "                        <div class=\"modal-content\">\n"
                    + "                            <div style=\"display: flex; justify-content: space-around;\" class=\"modal-header row border-0\">\n"
                    + "                                <div style=\"width: 200px;\" class=\"tabs col-md-6\" id=\"tab01\">\n"
                    + "                                    <h6>Danh sách sản phẩm</h6>\n"
                    + "                                </div>\n"
                    + "                                <div  style=\"width: 200px;\" class=\"tabs col-md-5\" id=\"tab02\">\n"
                    + "                                    <h6 >Danh sách nhập hàng</h6>\n"
                    + "                                </div>\n"
                    + "                                <div style=\"width: 450px\" class=\"col-md-1\">\n"
                    + "                                    <button style=\"float: right; background-color: white; border: none;\" type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                                        <span aria-hidden=\"true\">&times;</span>\n"
                    + "                                    </button>\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchBook\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchBook\" placeholder=\"Tìm kiếm sách\" style=\"width: 200px\">\n"
                    + "                                    <input id=\"searchButton\"  data-bs-dismiss=\"modal\" onclick=\"loadRequest('ManageRequestController','" + index + "','tab01',document.getElementById(\'searchBook\').value,'','searchAdd')\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"line\"></div>\n"
                    + "                            <div style=\"height: 500px; overflow-y: scroll; overflow-x: hidden; \" class=\"modal-body p-0\">\n"
                    + "                                <fieldset id=\"tab011\">\n"
                    + "                                    <div style=\"width: 1138px; margin: auto\" class=\"row\">\n"
                    + "                                        <div class=\"col-md-12\">\n"
                    + "                                            <div style=\" background-color: #1e1e27; color: white; font-weight: bold; font-size: 12px; text-align: center;\" class=\"row\">\n"
                    + "                                                <div class=\"col-md-1\"></div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\">ISBN</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Tiêu đề</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Thể loại</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Tác giả</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Nhà xuất bản</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\">Giá bán</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\"></div>\n"
                    + "                                            </div>\n"
                    + "                                            <div id=\"createContent\"></div>\n"
                    + "                                        </div>\n"
                    + "                                    </div>\n");
            int count = 0;
            for (BookDTO bookDTO : listBook) {
                count--;
                out.println("                                    <div style=\"font-size: 12px; border: 1px solid #dedede;\" class=\"row\">\n"
                        + "                                        <div class=\"col-md-1\"><img style=\"height: 100px;\" src=\"" + bookDTO.getImg() + "\"></div>\n"
                        + "                                        <div style=\"font-weight: bold; font-size: 10px;\" class=\"col-md-1\">" + bookDTO.getIsbn() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getName() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getCategory().getName() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getAuthorName() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getPublisher().getName() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-1\">" + currencyVN.format(bookDTO.getPrice()) + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-1\">\n"
                        + "                                            <div style=\"width 50px\" class=\"input-number\">\n"
                        + "                                                <input id=\"" + count + "\" style=\"width: 46px\" type=\"number\" name=\"quantity\" value=\"1\">\n"
                        + "                                            </div>\n"
                        + "                                            <button data-bs-dismiss=\"modal\" onclick=\"loadRequest('ManageRequestController','" + index + "','','" + search + "','','addBook','" + bookDTO.getIsbn() + "',document.getElementById(\'" + count + "\').value)\" style=\"color: white; border: 1px solid white; background-color: green\" type=\"button\">Thêm</button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n");
            }
            out.println("       <div class=\"row\">\n"
                    + "       <div style=\"text-align: center\" class=\"col-md-12\">\n"
                    + "<div class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a href=\"#\" class=\"index\" data-bs-dismiss=\"modal\" onclick=\"loadRequest('ManageRequestController?searchRequest=" + search + "&index=" + j + "'," + j + ",'tab01','" + search + "','','searchAdd')\">" + j + "</a>\n");
            }
            out.println("       </div>\n"
                    + "       </div>\n"
                    + "       </div>\n");
            out.println("                                </fieldset>\n"
                    + "                                <fieldset id=\"tab021\">\n");
            if (success.equals("Done")) {
                out.println("  <p style=\"margin: 5px auto 5px auto; text-align: center; width: 100%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 25px;\">Tạo yêu cầu nhập thành công</p></br>");
                success = "";
            }
            if (cart != null && cart.getCart().size() > 0) {
                out.println("                                    <div style=\"width: 1138px; margin: auto\" class=\"row\">\n"
                        + "                                        <div class=\"col-md-12\">\n"
                        + "                                            <div style=\" background-color: #1e1e27; color: white; font-weight: bold; font-size: 12px; text-align: center;\" class=\"row\">\n"
                        + "                                                <div class=\"col-md-1\"></div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\">ISBN</div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-3\">Tiêu đề</div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Thể loại</div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Tác giả</div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Nhà xuất bản</div>\n"
                        + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\"></div>\n"
                        + "                                            </div>\n"
                        + "                                            <div id=\"createContent\"></div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n");
                char alphabet = 'A';
                for (BookDTO bookDTO : cart.getCart().values()) {
                    out.println("                                    <div style=\"font-size: 12px; border: 1px solid #dedede;\" class=\"row\">\n"
                            + "                                        <div class=\"col-md-1\"><img style=\"height: 100px;\" src=\"" + bookDTO.getImg() + "\"></div>\n"
                            + "                                        <div style=\"font-weight: bold; font-size: 10px;\" class=\"col-md-1\">" + bookDTO.getIsbn() + "</div>\n"
                            + "                                        <div style=\"font-weight: bold;\" class=\"col-md-3\">" + bookDTO.getName() + "</div>\n"
                            + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getCategory().getName() + "</div>\n"
                            + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getAuthorName() + "</div>\n"
                            + "                                        <div style=\"font-weight: bold;\" class=\"col-md-2\">" + bookDTO.getPublisher().getName() + "</div>\n"
                            + "                                        <div style=\"font-weight: bold;\" class=\"col-md-1\">\n"
                            + "                                            <div style=\"width 50px\" class=\"input-number\">\n"
                            + "                                                <input id=\"" + alphabet + "\" style=\"width: 46px\" type=\"number\" name=\"quantity\" value=\"" + bookDTO.getQuantity() + "\">\n"
                            + "                                            </div>\n"
                            + "                                            <button data-bs-dismiss=\"modal\" onclick=\"loadRequest('ManageRequestController','" + index + "','','" + search + "','','editBook','" + bookDTO.getIsbn() + "',document.getElementById(\'" + alphabet + "\').value)\" style=\"color: white; border: 1px solid white; background-color: green; width: 46px;\" type=\"button\">Lưu</button>\n"
                            + "                                        </div>\n"
                            + "                                    </div>\n");
                    alphabet++;
                }
            } else {
                out.println("<div class=\"row\">\n"
                        + "<div style=\"\" class=\"col-md-12\">\n"
                        + "     <p style=\"text-align: center; color: #929292; font-size: 50px; margin-top: 200px\">Danh sách trống</p>\n"
                        + "  </div>\n"
                        + "  </div>\n");
            }
            out.println("                                </fieldset>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"line\"></div>\n"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <button hidden=\"\" type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Đóng</button>\n"
                    + "                                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                                <button data-bs-dismiss=\"modal\" type=\"button\" onclick=\"loadRequest('ManageRequestController','','','','','add')\" class=\"btn btn-primary\">Tạo yêu cầu nhập</button>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <div class=\"product-status mg-tb-15\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                    + "                        <div class=\"product-status-wrap\">\n"
                    + "                            <h2 onclick=\"loadRequest('ManageRequestController','" + index + "','')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí yêu cầu nhập</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchRequest\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchRequest\" placeholder=\"Tìm kiếm yêu cầu nhập\" style=\"width: 400px\">\n"
                    + "                                    <input onclick=\"loadRequest('ManageRequestController','" + index + "','',document.getElementById(\'searchRequest\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Tạo yêu cầu nhập sách</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n"
                    + "                          </div>\n"
                    + "                       </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "  </body>\n"
                    + "</html>\n");
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
