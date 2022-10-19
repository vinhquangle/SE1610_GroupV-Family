/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.BookDAO;
import dao.CategoryDAO;
import dao.PublisherDAO;
import dao.ReviewDAO;
import dto.BookDTO;
import dto.BookErrorDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import dto.ReviewDTO;
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
        int quanAdd = 0;
        double priceS = 0;
        String search = new String();
        String modal = new String();
        String price = "";
        String quantity = "";
        String success = "";
        BookDTO book = new BookDTO();
        BookDTO newBook = new BookDTO();
        BookDAO bookDao = new BookDAO();
        List<BookDTO> list = new ArrayList<BookDTO>();
        List<PublisherDTO> listPub = new ArrayList<>();
        List<CategoryDTO> listCate = new ArrayList<>();
        BookErrorDTO bookError = new BookErrorDTO();
        try {
            HttpSession session = request.getSession();
            String isbnN = request.getParameter("isbnN");
            String isbn = request.getParameter("isbn");
            book = bookDao.loadBook(isbn, "%%");
            PublisherDAO pubDao = new PublisherDAO();
            CategoryDAO cateDao = new CategoryDAO();
            ReviewDAO reviewDao = new ReviewDAO();
            indexCount = bookDao.countBook("%%");//Lấy số lượng sản phẩm trong kho
            search = request.getParameter("searchBook");
            String use = request.getParameter("use");
            listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
            listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                list = bookDao.getListBook(index, "%%");//Lấy sản phẩm theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = bookDao.searchBook(txtSearch, "%%").size();//Tìm kiếm tất cả sách thỏa yêu cầu
                list = bookDao.searchBook9(txtSearch, index, "%%");//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
            }
            if (isbnN != null && isbnN != "") {
                book = bookDao.loadBook(isbnN, "%%");
                if (use.equals("edit")) {
                    String title = request.getParameter("title");
                    String author = request.getParameter("author");
                    String pub = request.getParameter("pub");
                    String cate = request.getParameter("cate");
                    quantity = request.getParameter("quan");
                    String des = request.getParameter("des");
                    String img = request.getParameter("img");
                    price = request.getParameter("price");
                    if (author == null || author == "") {
                        modal = "Chỉnh sửa thông tin thất bại(Tác giả không được để trống)";
                        throw new Exception();
                    }
                    try {
                        int quan = Integer.parseInt(quantity);
                    } catch (Exception e) {
                        modal = "Chỉnh sửa thông tin thất bại(Số lượng không hợp lệ)";
                        throw new Exception();
                    }
                    if (Integer.parseInt(quantity) < 0) {
                        modal = "Chỉnh sửa thông tin thất bại(Số lượng phải từ 0 trở lên)";
                        throw new Exception();
                    }
                    try {
                        double pri = Double.parseDouble(price);
                    } catch (Exception e) {
                        modal = "Chỉnh sửa thông tin thất bại(Giá bán không hợp lệ)";
                        throw new Exception();
                    }
                    if (Double.parseDouble(price) < 1000) {
                        modal = "Chỉnh sửa thông tin thất bại(Giá bán phải từ 1000 trở lên)";
                        throw new Exception();
                    }
                    book.setIsbn(isbn);
                    book.setName(title);
                    book.setAuthorName(author);
                    book.setPublisher(pubDao.getPublisher(pub));
                    book.setCategory(cateDao.getCategory(cate));
                    book.setQuantity(Integer.parseInt(quantity));
                    book.setPrice(Double.parseDouble(price));
                    book.setDescription(des);
                    book.setImg(img);
                    if (bookDao.updateBook(book, isbnN)) {//Cập nhật thông tin sách
                        modal = "Chỉnh sửa thông tin thành công";
                    } else {
                        modal = "Chỉnh sửa thông tin thất bại";
                        book = bookDao.loadBook(isbnN, "%%");
                    }
                } else if (use.equals("remove")) {
                    book.setStatus("0");
                    if (bookDao.updateBook(book, isbnN)) {//Cập nhật thông tin sách
                        modal = "Xóa thành công";
                    }
                } else if (use.equals("recover")) {
                    book.setStatus("1");
                    if (bookDao.updateBook(book, isbnN)) {
                        modal = "Khôi phục thành công";
                    }
                }
            } else if (use.equals("add")) {
                String title = request.getParameter("title");
                String publisherID = request.getParameter("pub");
                String categoryID = request.getParameter("cate");
                String authorName = request.getParameter("author");
                String des = request.getParameter("des");
                price = request.getParameter("price");
                quantity = request.getParameter("quan");
                String image = request.getParameter("img");
                int quan = 0;
                boolean flag = true;
                try {
                    priceS = Double.parseDouble(price);
                } catch (Exception e) {
                    bookError.setPriceError("Giá bán không hợp lệ");
                    flag = false;
                }
                if (priceS < 1000) {
                    bookError.setPriceError("Giá bán phải từ 1000 trở lên");
                    flag = false;
                }
                if (bookDao.checkIsbn(isbn, "")) {
                    bookError.setIsbnError("Trùng lặp ISBN");
                    flag = false;
                } else if (isbn.isBlank()) {
                    bookError.setIsbnError("ISBN không được để trống");
                    flag = false;
                } else if (isbn.length() > 17 || isbn.length() < 9) {
                    bookError.setIsbnError("ISBN phải có độ dài từ 9 đến 17 kí tự");
                    flag = false;
                }
                try {
                    quanAdd = Integer.parseInt(quantity);
                } catch (Exception e) {
                    bookError.setQuantityError("Số lượng không hợp lệ");
                    flag = false;
                }
                if (quanAdd < 0) {
                    bookError.setQuantityError("Số lượng phải từ 0 trở lên");
                    flag = false;
                }
                if (title.isBlank()) {
                    bookError.setNameError("Tiêu đề sách không được để trống");
                    flag = false;
                } else if (title.length() > 150) {
                    bookError.setNameError("Tiêu đề sách không quá 150 kí tự");
                    flag = false;
                }
                if (publisherID.isBlank()) {
                    bookError.getPublisherError().setName("Vui lòng chọn nhà xuất bản");
                    flag = false;
                }
                if (categoryID.isBlank()) {
                    bookError.getCategoryError().setName("Vui lòng chọn thể loại sách");
                    flag = false;
                }
                if (authorName.isBlank()) {
                    bookError.setAuthorNameError("Tên tác giả không được để trống");
                    flag = false;
                } else if (authorName.length() > 50) {
                    bookError.setAuthorNameError("Tên tác giả không vượt quá 50 kí tự");
                    flag = false;
                }
                if (image.isBlank()) {
                    bookError.setImgError("Liên kết hình ảnh không được để trống");
                    flag = false;
                } else if (image.length() > 500) {
                    bookError.setImgError("Liên kết hình ảnh không vượt quá 500 kí tự");
                    flag = false;
                }
                newBook = new BookDTO(isbn, pubDao.getPublisher(publisherID), cateDao.getCategory(categoryID), new ReviewDTO("1", 1, 1, "1"), title, authorName, priceS, des, image, quan, "1");
                if (flag) {
                    newBook.setReview(reviewDao.createReview());
                    if (bookDao.createBook(newBook)) {
                        success = "Tạo sách mới thành công!";
                    }
                }
            }
            if (search == null || search == "") {
                search = "";
                list = bookDao.getListBook(index, "%%");//Lấy sản phẩm theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                indexCount = bookDao.searchBook(txtSearch, "%%").size();//Lấy số lượng tìm kiếm tất cả sách thỏa yêu cầu
                list = bookDao.searchBook9(txtSearch, index, "%%");//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
            }
        } catch (Exception e) {
            log("Error at ManageBookController: " + e.toString());
        } finally {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Quản lí sách</title>\n"
                    + "        <meta name=\"description\" content=\"\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">"
                    + "     <div style=\" width: 100%;\" class=\"modal\" id=\"exampleModalCenter\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-xl\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h3  style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalCenterTitle\">Thông tin sách</h3>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                 </div>\n"
                    + "                    <div style=\"width: 1140px;\" class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <img style=\"width: 60%;\" src=\"" + book.getImg() + "\"/></br>\n"
                    + "                                   <div>Hình ảnh </div><textarea id=\"img\" style=\"width: 300px;\" rows=\"3\">" + book.getImg() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <div><p>ISBN: <input readonly id=\"isbn\" style=\"font-weight: bold\" value=\"" + book.getIsbn() + "\"></p></div>\n"
                    + "                                <div style=\"display: none;\"><p>ISBN: <input id=\"isbnN\" style=\"font-weight: bold\" value=\"" + book.getIsbn() + "\"></p></div>\n"
                    + "                                <div><p>Tác giả: <input maxlength=\"50\" id=\"author\" style=\"font-weight: bold\" value=\"" + book.getAuthorName() + "\"></p></div>\n"
                    + "                                <div><p>Thể loại: <select id=\"category\" style=\"width: 200px; font-weight: bold\">\n"
                    + "                                            <option disabled selected hidden>" + book.getCategory().getName() + "</option>\n");
            for (CategoryDTO cate : listCate) {
                out.println("                                  <option style=\"font-weight: bold\">" + cate.getName() + "</option>\n");
            }
            out.println("                                        </select></p></div>\n"
                    + "                                <div><p>Nhà xuất bản: <select id=\"publisher\" style=\"width: 200px; font-weight: bold\">\n"
                    + "                                            <option disabled selected hidden>" + book.getPublisher().getName() + "</option>\n");
            for (PublisherDTO pub : listPub) {
                out.println("                                  <option style=\"font-weight: bold\">" + pub.getName() + "</option>\n");
            }
            out.println("                                        </select></p></div>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <div><p>Số lượng: <input id=\"quantity\" style=\"font-weight: bold\" value=\"" + book.getQuantity() + "\"></p></div>\n"
                    + "                                <div><p>Giá bán: <input id=\"price\" style=\"font-weight: bold\" value=\"" + (int) book.getPrice() + "\"></p></div>\n"
                    + "                                <div><p>Đánh giá: <b>" + book.getReview().getRate() + " / 5</b></p></div>\n"
                    + "                                 <input style=\"display: none;\" id=\"status\" value=\"" + book.getStatus() + "\">\n");
            if (book.getStatus().equals("1")) {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"book('ManageBookController?index=" + index + "','remove'," + index + ")\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"book('ManageBookController?index=" + index + "','recover'," + index + ")\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println(" </div>\n"
                    + "                        </div>\n"
                    + "                        <div style=\"text-align: center;\" class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">Tiêu đề<input id=\"name\" style=\"width: 100%; font-weight: bold; text-align: center;\" value=\"" + book.getName() + "\" maxlength=\"150\"></div>\n"
                    + "                        </div>\n"
                    + "                        <div style=\"text-align: center; margin-top: 10px;\" class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">Mô tả<textarea id=\"des\" style=\"width: 100%; font-weight: bold\" rows=\"10\">" + book.getDescription() + "</textarea></div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"book('ManageBookController?index=" + index + "','edit'," + index + ")\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>"
                    + " <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog  modal-lg\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Tạo sách</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div id=\"mcontent\" class=\"modal-body\">\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 30px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>ISBN</label>\n"
                    + "                                <input placeholder=\"Nhập ISBN của sách\" value=\"" + newBook.getIsbn() + "\" style=\"font-weight: bold\" id=\"inputisbn\" name=\"isbn\" type=\"number\" class=\"form-control c\" minlength=\"2\" maxlength=\"17\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getIsbnError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Tiêu đề sách</label>\n"
                    + "                                <input placeholder=\"Nhập tiêu đề sách\" value=\"" + newBook.getName() + "\" style=\"font-weight: bold\" name=\"name\" type=\"text\" class=\"form-control c\" minlength=\"1\" maxlength=\"150\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getNameError() + "</p></br>\n"
                    + "                         <div class=\"form-group\">\n"
                    + "                                <label>Nhà xuất bản</label>\n"
                    + "                                <select value=\"" + newBook.getPublisher().getPublisherID() + "\" style=\"font-weight: bold\" name=\"publisher\" class=\"form-select c\" aria-label=\"Default select example\">\n"
                    + "                                 <option disabled selected hidden value=\"\">Chọn nhà xuất bản</option>\n"
            );

            try {
                for (PublisherDTO publisherDTO : listPub) {
                    out.println(" <option value=\"" + publisherDTO.getPublisherID() + "\">" + publisherDTO.getName() + "</option>\n");
                }
                out.println("                              </select>\n"
                        + "                              </div>\n"
                        + "                        <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getPublisherError().getName() + "</p></br>\n"
                        + "                              <div class=\"form-group\">\n"
                        + "                                <label>Thể loại</label>\n"
                        + "                                <select value=\"" + newBook.getCategory().getName() + "\" style=\"font-weight: bold\" name=\"category\" class=\"form-select c\" aria-label=\"Default select example\">\n"
                        + "                                     <option disabled selected hidden value=\"\">Chọn thể loại</option>\n");
                for (CategoryDTO categoryDTO : listCate) {
                    out.println("                                    <option value=\"" + categoryDTO.getCategoryID() + "\">" + categoryDTO.getName() + "</option>\n");
                }
                out.println("                                </select>\n"
                        + "                            </div>\n"
                        + "                         <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getCategoryError().getName() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Tác giả</label>\n"
                        + "                                <input placeholder=\"Nhập tên tác giả\" value=\"" + newBook.getAuthorName() + "\" style=\"font-weight: bold\" name=\"author-name\" class=\"form-control c\" maxlength=\"50\">\n"
                        + "                            </div>\n"
                        + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getAuthorNameError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Giá bán</label>\n"
                        + "                                <input placeholder=\"Nhập giá bán sách\" value=\"" + priceS + "\" style=\"font-weight: bold\" name=\"price\" type=\"number\" class=\"form-control c\" min=\"1000\">\n"
                        + "                            </div>\n"
                        + "                         <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getPriceError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Hình ảnh</label>\n"
                        + "                                <input placeholder=\"Nhập liên kết hình ảnh\" value=\"" + newBook.getImg() + "\" style=\"font-weight: bold\" name=\"image\" type=\"text\" class=\"form-control c\">\n"
                        + "                            </div>\n"
                        + "                                  <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getImgError() + "</p></br>\n"
                        + "                            <div class=\"form-group\">\n"
                        + "                                <label>Số lượng</label>\n"
                        + "                                <input placeholder=\"Nhập số lượng sách\" value=\"" + quanAdd + "\" style=\"font-weight: bold\" name=\"quantity\" type=\"number\" class=\"form-control c\" min=\"0\">\n"
                        + "                            </div>\n"
                        + "                                  <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + bookError.getQuantityError() + "</p></br>\n"
                        + "                                  </div>\n"
                        + "                             <div style=\"text-align: center;\" class=\"row\">\n"
                        + "                            <div class=\"col-md-12\"> <b>Mô tả </b> <textarea placeholder=\"Nhập mô tả sách\" class=\"c\" style=\"width: 97%; font-weight: bold\" rows=\"10\">" + newBook.getDescription() + "</textarea></div>\n"
                        + "                        </div>"
                        + "                            <div class=\"modal-footer\">\n"
                        + "                                <input style=\"display: none;\" type=\"button\" class=\"btn\" value=\"z\">\n"
                        + "                                <button style=\"background-color: #494f57; color: white\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Hủy</button>\n"
                        + "                                <input data-bs-dismiss=\"modal\" onclick=\"add('ManageBookController','c')\" type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Tạo\">\n"
                        + "                            </div>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        <!-- The Modal -->"
                        + "        <div class=\"product-status mg-tb-15\">\n"
                        + "            <div class=\"container-fluid\">\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                        + "                        <div class=\"product-status-wrap\">\n"
                        + "                            <h2 onclick=\"load('ManageBookController','')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí sách</h2>\n"
                        + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                        + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                        + "                             <form>"
                        + "                                    <input id=\"searchBook\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchBook\" placeholder=\"Tìm sách theo tiêu đề, tác giả hoặc ISBN\" style=\"width: 380px\">\n"
                        + "                                    <input onclick=\"load('ManageBookController','',document.getElementById(\'searchBook\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                        + "                              </form>"
                        + "                            </h5>"
                        + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                        + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                        + "                                    <b>Thêm sách mới</b>\n"
                        + "                                </button>\n"
                        + "                            </div>\n");
                if (list.size() <= 0) {
                    out.println("<div class=\"\">\n"
                            + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                            + "                </div>");
                } else {
                    out.println("                            <table>\n"
                            + "                                <tr>\n"
                            + "                                    <th>ISBN</th>\n"
                            + "                                    <th style=\"width: 300px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;\">Tiêu đề</th>\n"
                            + "                                    <th>Tác giả</th>\n"
                            + "                                    <th>Nhà xuất bản</th>\n"
                            + "                                    <th>Thể loại</th>\n"
                            + "                                    <th>SỐ lượng</th>\n"
                            + "                                    <th>Giá bán</th>\n"
                            + "                                    <th>Hình ảnh</th>\n"
                            + "                                    <th>Tình trạng</th>\n"
                            + "                                    <th></th>\n"
                            + "                                </tr>");

                }
            } catch (Exception e) {
            } finally {
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    count++;
                    out.println("<tr>\n"
                            + "                                    <td>" + list.get(i).getIsbn() + "</td>  \n"
                            + "                                    <td><div style=\"width: 300px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;\">" + list.get(i).getName() + "</div></td>\n"
                            + "                                    <td>" + list.get(i).getAuthorName() + "</td>\n"
                            + "                                    <td>" + list.get(i).getPublisher().getName() + "</td>\n"
                            + "                                    <td>" + list.get(i).getCategory().getName() + "</td>\n"
                            + "                                    <td>" + list.get(i).getQuantity() + "</td>\n"
                            + "                                    <td>" + (int) list.get(i).getPrice() + "</td>\n"
                            + "                                    <td><img style=\"width: 100px\" src=\"" + list.get(i).getImg() + "\" alt=\"\"</td>\n");
                    String status = list.get(i).getStatus();
                    if (status.equals("1")) {
                        out.println("                                  <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    } else {
                        out.println("                                    <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    }
                    out.println("                                    <td>\n"
                            + "                                        <button id=\"edit\" onclick=\"loadBook('ManageBookController?index=" + index + "','" + search + "','" + list.get(i).getIsbn() + "'," + index + ", 'load')\"  title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                            + "                                   </td>\n"
                            + "                                </tr>"
                    );
                }
            }
            out.println(" </table>\n"
                    + "         <div class=\"pagination\">\n");

            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a id=\"" + j + "\" onclick=\"load('ManageBookController?searchBook=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
            }
            out.println("       </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>    \n"
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
//<<<<<<<<<<
