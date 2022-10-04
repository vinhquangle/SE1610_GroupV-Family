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
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class ManageBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            BookDAO bookDao = new BookDAO();

            List<BookDTO> listBook = bookDao.getListBook();

            if (listBook.size() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("LIST_BOOK", listBook);

            }

        } catch (Exception e) {
            log("Error at ManageBookController: " + e.toString());
        }
        HttpSession session = request.getSession();
        BookErrorDTO bookError = new BookErrorDTO();
        CategoryDAO cateDao = new CategoryDAO();
        PublisherDAO pubDao = new PublisherDAO();

        List<CategoryDTO> listCate = cateDao.getListCategory(); //Load tất cả thể loại
        List<PublisherDTO> listPub = pubDao.getListPublisher(); //Load tất cả NXB
        session.setAttribute("LIST_PUB", listPub);
        session.setAttribute("LIST_CATE", listCate);
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n"
                + "<html class=\"no-js\" lang=\"en\">\n"
                + "\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                + "        <title>Book List</title>\n"
                + "        <meta name=\"description\" content=\"\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "<!-- The Modal -->\n"
                + "        <div class=\"modal\" id=\"myModal\">\n"
                + "            <div class=\"modal-dialog\">\n"
                + "                <div class=\"modal-content\">\n"
                + "                    <!-- Modal Header -->\n"
                + "                    <div class=\"modal-header\">\n"
                + "                        <h4 class=\"modal-title\">Add Book</h4>\n"
                + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                + "                    </div>\n"
                + "                    <!-- Modal body -->\n"
                + "                    <div class=\"modal-body\">\n"
                + "                        <form action=\"AddBookManageController\">\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>ISBN</label>\n"
                + "                                <input name=\"isbn\" type=\"text\" class=\"form-control\" required>\n"
                + "                            </div>\n");
        bookError.getIsbnError();
        out.println("\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Book Title</label>\n"
                + "                                <input name=\"name\" type=\"text\" class=\"form-control\" required>\n"
                + "                            </div>\n");
        bookError.getNameError();
        out.println("\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Publisher</label>\n"
                + "                                <select name=\"publisher\" class=\"form-select\" aria-label=\"Default select example\">\n");
        for (PublisherDTO publisherDTO : listPub) {
            out.println("                           <option value=" + publisherDTO.getPublisherID() + ">" + publisherDTO.getName() + "</option>\n");
        }
        out.println("                              </select>\n"
                + "                           </div>\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Category</label>\n"
                + "                                <select name=\"category\" class=\"form-select\" aria-label=\"Default select example\">\n");
        for (CategoryDTO categoryDTO : listCate) {
            out.println("                           <option value=" + categoryDTO.getCategoryID() + ">" + categoryDTO.getName() + "</option>\n");
        }
        out.println("                              </select>\n"
                + "                            </div>\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Author</label>\n"
                + "                                <textarea name=\"author-name\" class=\"form-control\" required></textarea>\n"
                + "                            </div>\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Price</label>\n"
                + "                                <input name=\"price\" type=\"number\" class=\"form-control\" required>\n"
                + "                            </div>\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Image</label>\n"
                + "                                <input name=\"image\" type=\"text\" class=\"form-control\" required>\n"
                + "                            </div>\n"
                + "\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <label>Quantity</label>\n"
                + "                                <input name=\"quantity\" type=\"number\" class=\"form-control\" required>\n"
                + "                            </div>\n"
                + "\n"
                + "\n"
                + "                            <div class=\"modal-footer\">\n"
                + "                                <input type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" value=\"Cancel\">\n"
                + "                                <input type=\"submit\" name = \"action\" class=\"btn btn-success\" value=\"Add\">\n"
                + "                            </div>\n"
                + "                        </form>\n"
                + "                    </div>\n"
                + "\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <!-- The Modal -->"
                + "        <div class=\"product-status mg-tb-15\">\n"
                + "            <div class=\"container-fluid\">\n"
                + "                <div class=\"row\">\n"
                + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                + "                        <div class=\"product-status-wrap\">\n"
                + "                            <h4 style=\"display: inline-block;\">Book List</h4>\n"
                + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                + "                                <button style=\"border: none; color: #edeef1; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                + "                                    <b>Add new book</b>\n"
                + "                                </button>\n"
                + "                            </div>\n"
                + "                            <table>\n"
                + "                                <tr>\n"
                + "                                    <th>ISBN</th>\n"
                + "                                    <th>Book Title</th>\n"
                + "                                    <th>Author</th>\n"
                + "                                    <th>Publisher</th>\n"
                + "                                    <th>Category</th>\n"
                + "                                    <th>Quantity</th>\n"
                + "                                    <th>Price</th>\n"
                + "                                    <th>IMG</th>\n"
                + "                                    <th>Setting</th>\n"
                + "                                </tr>");
        List<BookDTO> list = new ArrayList<>();
        try {
            list = (List<BookDTO>) session.getAttribute("LIST_BOOK");
        } catch (Exception e) {
        } finally {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            for (int i = 0; i < list.size(); i++) {
                String price = currencyVN.format(list.get(i).getPrice());
                out.println("<tr>\n"
                        + "                                    <td>" + list.get(i).getIsbn() + "</td>                                   \n"
                        + "                                    <td>" + list.get(i).getName() + "</td>\n"
                        + "                                    <td>" + list.get(i).getAuthorName() + "</td>\n"
                        + "                                    <td>" + list.get(i).getPublisher().getName() + "</td>\n"
                        + "                                    <td>" + list.get(i).getCategory().getName() + "</td>\n"
                        + "                                    <td>" + list.get(i).getQuantity() + "</td>\n"
                        + "                                    <td>" + price + "</td>\n"
                        + "                                    <td><img src=\"" + list.get(i).getImg() + "\" alt=\"\" /></td>\n"
                        + "                                    <td>\n"
                        + "                                        <button data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                        <button data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                    </td>\n"
                        + "                                </tr>");
            }
        }
        out.println(" </table>\n"
                + "                            <div class=\"custom-pagination\">\n"
                + "                                <nav aria-label=\"Page navigation example\">\n"
                + "                                    <ul class=\"pagination\">\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Previous</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">1</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">2</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">3</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Next</a></li>\n"
                + "                                    </ul>\n"
                + "                                </nav>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>    \n"
                + "    </body>\n"
                + "\n"
                + "</html>\n"
                + "");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
