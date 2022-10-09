/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.BookDAO;
import dao.CategoryDAO;
import dao.PublisherDAO;
import dto.BookDTO;
import dto.BookErrorDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
//Quang Vinh, Quốc Thịnh >>>>>>>>>>
public class ManageBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String search = new String();
        String succes = new String();
        String error = new String();
        List<BookDTO> list = new ArrayList<BookDTO>();
        try {
            HttpSession session = request.getSession();
            BookDAO bookDao = new BookDAO();
            PublisherDAO pubDao = new PublisherDAO();
            CategoryDAO cateDao = new CategoryDAO();
            indexCount = bookDao.countBook();//Lấy số lượng sản phẩm trong kho
            search = request.getParameter("searchBook");
            String isbnN = request.getParameter("isbnN");
            String use = request.getParameter("use");
            //Chuyển chuỗi có dấu thành không dấu
            request.setCharacterEncoding("UTF-8");
            String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String txtSearch = pattern.matcher(temp).replaceAll("");
            //Chuyển chuỗi có dấu thành không dấu
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                list = bookDao.getListBook(index);//Lấy sản phẩm theo phân trang
            } else {
                indexCount = bookDao.searchBook(txtSearch).size();//Tìm kiếm tất cả sách thỏa yêu cầu
                list = bookDao.searchBook_9(txtSearch, index);//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
            }
            if (isbnN != null) {
                BookDTO newBook = bookDao.loadBook(isbnN);
                if (use.equals("edit")) {
                    String isbn = request.getParameter("isbn");
                    String title = request.getParameter("title");
                    String author = request.getParameter("author");
                    String pub = request.getParameter("pub");
                    String cate = request.getParameter("cate");
                    String quantity = request.getParameter("quan");
                    if (Integer.parseInt(quantity) < 0) {
                        error = "Fail! Quantity must be positive number!";
                        throw new Exception();
                    }
                    String price = request.getParameter("price");
                    String img = request.getParameter("img");
                    String status = request.getParameter("status");
                    newBook.setIsbn(isbn);
                    newBook.setName(title);
                    newBook.setAuthorName(author);
                    newBook.setPublisher(pubDao.getPublisher(pub));
                    newBook.setCategory(cateDao.getCategory(cate));
                    newBook.setQuantity(Integer.parseInt(quantity));
                    newBook.setPrice(Double.parseDouble(price));
                    if (bookDao.updateBook(newBook, isbnN)) {//Cập nhật số lượng sách
                        succes = "Successfully!";
                        error = null;
                    } else {
                        error = "Fail!";
                        succes = null;
                    }
                } else if (use.equals("remove")) {
                    succes = "Successfully removed!";
                    newBook.setStatus("0");
                    bookDao.updateBook(newBook, isbnN);//Cập nhật số lượng sách
                } else if (use.equals("recover")) {
                    succes = "Successfully recovered!";
                    newBook.setStatus("1");
                    bookDao.updateBook(newBook, isbnN);//Cập nhật số lượng sách
                }
                if (search == null || search == "") {
                    search = "";
                    list = bookDao.getListBook(index);//Lấy sản phẩm theo phân trang
                } else {
                    request.setCharacterEncoding("UTF-8");
                    indexCount = bookDao.searchBook(txtSearch).size();//Lấy số lượng tìm kiếm tất cả sách thỏa yêu cầu
                    list = bookDao.searchBook_9(txtSearch, index);//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
                }
            }
        } catch (Exception e) {
            log("Error at ManageBookController: " + e.toString());
        } finally {
            BookErrorDTO bookError = (BookErrorDTO) request.getAttribute("BOOK_ERROR");
            if (bookError == null) {
                bookError = new BookErrorDTO();
            }
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Manage Book</title>\n"
                    + "        <meta name=\"description\" content=\"\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + " <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Add New Book</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                        <form id=\"form-add\">\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div id=\"mcontent\" class=\"modal-body\">\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>ISBN</label>\n"
                    + "                                <input id=\"inputisbn\" name=\"isbn\" type=\"number\" class=\"form-control c\" required minlength=\"2\" maxlength=\"13\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"color: red;\">" + bookError.getIsbnError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Book Title</label>\n"
                    + "                                <input name=\"name\" type=\"text\" class=\"form-control c\" required minlength=\"1\" maxlength=\"150\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"color: red;\">" + bookError.getNameError() + "</p></br>\n"
                    + "                         <div class=\"form-group\">\n"
                    + "                                <label>Publisher</label>\n"
                    + "                                <select name=\"publisher\" class=\"form-select c\" aria-label=\"Default select example\" required>\n"
                    + "                                 <option disabled selected hidden value=\"\">Choose A Publisher</option>\n");
            List<PublisherDTO> listPub = new ArrayList<>();
            List<CategoryDTO> listCate = new ArrayList<>();
            try {
                HttpSession session = request.getSession();
                listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
                listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
                for (PublisherDTO publisherDTO : listPub) {
                    out.println(" <option value=\"" + publisherDTO.getPublisherID() + "\">" + publisherDTO.getName() + "</option>\n");
                }
                out.println("                              </select>\n"
                        + "                              </div>\n"
                        + "                        <p style=\"color: red;\">" + bookError.getPublisherError().getName() + "</p></br>\n"
                        + "                              <div class=\"form-group\">\n"
                        + "                                <label>Category</label>\n"
                        + "                                <select name=\"category\" class=\"form-select c\" aria-label=\"Default select example\" required>\n"
                        + "                                     <option disabled selected hidden value=\"\">Choose A Category</option>\n");
                for (CategoryDTO categoryDTO : listCate) {
                    out.println("                                    <option value=\"" + categoryDTO.getCategoryID() + "\">" + categoryDTO.getName() + "</option>\n");
                }
                out.println("                                </select>\n"
                        + "                            </div>\n"
                        + "                         <p style=\"color: red;\">" + bookError.getCategoryError().getName() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Author</label>\n"
                        + "                                <input name=\"author-name\" class=\"form-control c\" required maxlength=\"50\">\n"
                        + "                            </div>\n"
                        + "                            <p style=\"color: red;\">" + bookError.getAuthorNameError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Price</label>\n"
                        + "                                <input name=\"price\" type=\"number\" class=\"form-control c\" required min=\"1000\">\n"
                        + "                            </div>\n"
                        + "                         <p style=\"color: red;\">" + bookError.getPriceError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Image</label>\n"
                        + "                                <input name=\"image\" type=\"text\" class=\"form-control c\" required>\n"
                        + "                            </div>\n"
                        + "                                  <p style=\"color: red;\">" + bookError.getImgError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Quantity</label>\n"
                        + "                                <input name=\"quantity\" type=\"number\" class=\"form-control c\" min=\"0\" required>\n"
                        + "                            </div>\n"
                        + "                                  <p style=\"color: red;\">" + bookError.getQuantityError() + "</p></br>\n"
                        + "                                  </div>\n"
                        + "                            <div class=\"modal-footer\">\n"
                        + "                                <input style=\"display: none;\" type=\"button\" class=\"btn\" value=\"z\">\n"
                        + "                                <button style=\"background-color: #494f57; color: white\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                        + "                                <input onclick=\"add('ManageBookController')\" type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Add\">\n"
                        + "                            </div>\n"
                        + "                         </form>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        <!-- The Modal -->"
                        + "        <div class=\"product-status mg-tb-15\">\n"
                        + "            <div class=\"container-fluid\">\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                        + "                        <div class=\"product-status-wrap\">\n"
                        + "                            <h2 onclick=\"load('ManageBookController')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Manage Book</h2>\n"
                        + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                        + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                        + "                             <form>"
                        + "                                    <input id=\"searchBook\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchBook\" placeholder=\"Search by title, author or ISBN\" style=\"width: 350px\">\n"
                        + "                                    <input onclick=\"load('ManageBookController',document.getElementById(\'searchBook\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Search\" name=\"action\" style=\"width: 100px\"/>\n"
                        + "                              </form>"
                        + "                            </h5>"
                        + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                        + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                        + "                                    <b>Add new book</b>\n"
                        + "                                </button>\n"
                        + "                            </div>\n"
                );
                if (succes != null) {
                    out.println("<p style=\"font-size: 30px; display: inline-block; background-color: green; color: white;\"><b>" + succes + "</b></p>");
                }
                if (error != null) {
                    out.println("<p style=\"font-size: 30px; display: inline-block; background-color: red; color: white;\"><b>" + error + "</b></p>");
                }
                if (list.size() <= 0) {
                    out.println("<div class=\"\">\n"
                            + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">NOT FOUND!</p>\n"
                            + "                </div>");
                } else {
                    out.println("                            <table>\n"
                            + "                                <tr>\n"
                            + "                                    <th>ISBN</th>\n"
                            + "                                    <th>Book Title</th>\n"
                            + "                                    <th>Author</th>\n"
                            + "                                    <th>Publisher</th>\n"
                            + "                                    <th style=\"width: 40px;\">Category</th>\n"
                            + "                                    <th>Quantity</th>\n"
                            + "                                    <th>Price</th>\n"
                            + "                                    <th>IMG</th>\n"
                            + "                                    <th>Status</th>\n"
                            + "                                    <th></th>\n"
                            + "                                </tr>");

                }
            } catch (Exception e) {
            } finally {
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    count++;
                    out.println("<tr>\n"
                            + "                                    <td><input style=\"width: 120px;\" class=\"" + count + "\" value=\"" + list.get(i).getIsbn() + "\"/></td>                                   \n"
                            + "                                    <input class=\"" + count + "\" value=\"" + list.get(i).getIsbn() + "\" type=\"hidden\"/>"
                            + "                                    <td><textarea rows=\"3\" style=\" width: 170px;\" class=\"" + count + "\">" + list.get(i).getName() + "</textarea></td>\n"
                            + "                                    <td><textarea rows=\"2\" style=\" width: 100px;\" class=\"" + count + "\">" + list.get(i).getAuthorName() + "</textarea></td>\n"
                            + "                                    <td>"
                            + "                                     <select style=\"width: 130px;\" name=\"sort\" class=\"" + count + "\">\n"
                            + "                                            <option disabled selected hidden>" + list.get(i).getPublisher().getName() + "</option>\n");
                    for (PublisherDTO pub : listPub) {
                        if (!pub.getPublisherID().equals(list.get(i).getPublisher().getPublisherID())) {
                            out.println("                                            <option>" + pub.getName() + "</option>\n");
                        }
                    }
                    out.println("                                        </select>"
                            + "                                      </td>\n"
                            + "                                    <td>"
                            + "                                     <select style=\"width: 150px;\" name=\"sort\" class=\"" + count + "\">\n"
                            + "                                            <option disabled selected hidden>" + list.get(i).getCategory().getName() + "</option>\n");
                    for (CategoryDTO cate : listCate) {
                        if (!cate.getCategoryID().equals(list.get(i).getCategory().getCategoryID())) {
                            out.println("                                            <option>" + cate.getName() + "</option>\n");
                        }
                    }
                    out.println("                                        </select>"
                            + "                                      </td>\n"
                            + "                                    <td><input style=\"width: 55px;\" class=\"" + count + "\" min=\"0\" type=\"number\" value=\"" + list.get(i).getQuantity() + "\"/></td>\n"
                            + "                                    <td><input style=\"width: 80px;\" class=\"" + count + "\" type=\"number\" value=\"" + (int) list.get(i).getPrice() + "\"/></td>\n"
                            + "                                    <td class=\"" + count + "\"><img src=\"" + list.get(i).getImg() + "\" alt=\"\"</td>\n");
                    String status = list.get(i).getStatus();
                    if (status.equals("1")) {
                        out.println("                                  <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    } else {
                        out.println("                                    <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    }
                    out.println("                                    <td>\n"
                            + "                                        <button id=\"edit\" onclick=\"book('ManageBookController?index=" + index + "'," + count + ",'edit')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n");
                    if (list.get(i).getStatus().equals("1")) {
                        out.println("                                        <button id=\"remove\" onclick=\"book('ManageBookController?index=" + index + "'," + count + ",'remove')\" data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n");
                    } else {
                        out.println("                                        <button id=\"recover\" onclick=\"book('ManageBookController?index=" + index + "'," + count + ",'recover')\" data-toggle=\"tooltip\" title=\"Recover\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
                    }
                    out.println("                                   </td>\n"
                            + "                                </tr>"
                    );
                }
            }
            out.println(" </table>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>    \n"
                    + "<div style=\"margin: auto; display: inline-block;\" class=\"custom-pagination\">\n"
                    + "            <nav aria-label=\"Page navigation example\">\n"
                    + "                <ul class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 10) == 0) {
                page = indexCount / 10;
            } else {
                page = (indexCount / 10) + 1;
            }
            for (int i = 1; i < page + 1; i++) {
                out.println("                    <li class=\"page-item\"><a onclick=\"load('ManageBookController?searchBook=" + search + "&index=" + i + "')\" class=\"page-link\" href=\"#\">" + i + "</a></li>\n");
            }
            out.println("</ul>\n"
                    + "            </nav>\n"
                    + "        </div>  "
                    + "  </body>\n"
                    + "\n"
                    + "</html>\n"
                    + "");
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
