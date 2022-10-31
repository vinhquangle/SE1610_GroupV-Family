/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import cart.Cart;
import dao.BRequestDetailDAO;
import dao.BResponseDetailDAO;
import dao.BookRequestDAO;
import dao.BookResponseDAO;
import dto.BRequestDetailDTO;
import dto.BResponseDetailDTO;
import dto.BookDTO;
import dto.BookRequestDTO;
import dto.BookResponseDTO;
import dto.StaffDTO;
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
 * @author Thịnh
 */
public class ManageResponseController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexR = 0;
        int quantity = 0;
        double price = 0;
        int indexCount = 0;
        int indexCountR = 0;
        Cart cart = new Cart();
        String success = new String();
        String search = new String();
        String modal = new String();
        String isbn = new String();
        String searchR = new String();
        String requestId = new String();
        BookDTO book = new BookDTO();
        StaffDTO staff = new StaffDTO();
        BookRequestDAO requestDao = new BookRequestDAO();
        BRequestDetailDAO detailDao = new BRequestDetailDAO();
        BResponseDetailDAO detailRSDao = new BResponseDetailDAO();
        BookResponseDTO responseDTO = new BookResponseDTO();
        BookRequestDTO requestBook = new BookRequestDTO();
        BookResponseDAO responseDAO = new BookResponseDAO();
        BResponseDetailDAO responseDetailDao = new BResponseDetailDAO();
        List<BRequestDetailDTO> listDetail = new ArrayList<>();
        List<BookRequestDTO> listRequest = new ArrayList<>();
        List<BookResponseDTO> listResponse = new ArrayList<>();
        List<BookResponseDTO> listResponse1 = new ArrayList<>();
        List<BResponseDetailDTO> listResponseDetail = new ArrayList<>();
        List<BResponseDetailDTO> listResponseDetail1 = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            search = request.getParameter("searchResponse");
            searchR = request.getParameter("searchRequest");
            indexCount = responseDAO.loadResponse().size();
            indexCountR = requestDao.loadRequest("%%").size();
            cart = (Cart) session.getAttribute("CART2");
            String responseID = request.getParameter("responseID");
            String use = request.getParameter("use");
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            try {
                indexR = Integer.parseInt(request.getParameter("indexR"));
            } catch (Exception e) {
                index = 1;
            }
            if (searchR == null || searchR == "") {
                searchR = "";
                listRequest = requestDao.load9Request("%%", indexR);
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchRequest"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCountR = requestDao.searchRequest(txtSearch, "%%").size();//Lấy số lượng tìm kiếm tất cả sách thỏa yêu cầu
                listRequest = requestDao.search9Request(txtSearch, indexR, "%%");//Tìm kiếm tất cả sách thỏa yêu cầu theo phân trang
            }
            if (search == null || search == "") {
                search = "";
                listResponse = responseDAO.load9Response(index);
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchResponse"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = responseDAO.searchResponse(txtSearch).size();
                listResponse = responseDAO.search9Response(txtSearch, index);
            }
            if (use != null) {
                if (use.equals("load")) {
                    responseDTO = responseDAO.getResponseByID(responseID);
                    listResponseDetail = responseDetailDao.loadDetailByResponseID(responseID);
                } else if (use.equals("remove")) {
                    responseDTO = responseDAO.getResponseByID(responseID);
                    listResponseDetail = responseDetailDao.loadDetailByResponseID(responseID);
                    responseDTO.setDelete("1");
                    if (responseDAO.updateResponse(responseDTO)) {
                        modal = "Xóa thành công";
                    }
                } else if (use.equals("recover")) {
                    responseDTO = responseDAO.getResponseByID(responseID);
                    listResponseDetail = responseDetailDao.loadDetailByResponseID(responseID);
                    responseDTO.setDelete("0");
                    if (responseDAO.updateResponse(responseDTO)) {
                        modal = "Khôi phục thành công";
                    }
                } else if (use.equals("choosen")) {
                    if (cart == null) {
                        cart = new Cart();//Khởi tạo giỏ hàng
                    }
                    requestId = request.getParameter("requestID");
                    requestBook = requestDao.getRequestById(requestId);
                    listResponse1 = responseDAO.loadResponseByRequest(requestId, "1");
                    listDetail = detailDao.loadDetailByRequest(requestId);
                    for (BRequestDetailDTO bRequestDetailDTO : listDetail) {
                        bRequestDetailDTO.getIsbn().setQuantity(0);
                        cart.add(bRequestDetailDTO.getIsbn());
                    }
                } else if (use.equals("add")) {
                    cart.removeAll();
                } else if (use.equals("save")) {
                    double quantityCheck = 0;
                    isbn = request.getParameter("isbn");
                    requestId = request.getParameter("requestID");
                    requestBook = requestDao.getRequestById(requestId);
                    listResponse1 = responseDAO.loadResponseByRequest(requestId, "1");
                    listDetail = detailDao.loadDetailByRequest(requestId);
                    try {
                        quantityCheck = Integer.parseInt(request.getParameter("quantityCheck"));
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(số lượng không hợp lệ)";
                        throw new Exception();
                    }
                    if (quantity < 1) {
                        modal = "Thay đổi thất bại(số lượng phải lớn hơn 0)";
                        throw new Exception();
                    }
                    if (quantity > quantityCheck) {
                        modal = "Thay đổi thất bại(số lượng vượt quá yêu cầu nhập)";
                        throw new Exception();
                    }
                    try {
                        price = Integer.parseInt(request.getParameter("price"));
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(Giá cả không hợp lệ)";
                        throw new Exception();
                    }
                    if (price < 1000) {
                        modal = "Thay đổi thất bại(Giá cả phải lớn hơn 1000)";
                        throw new Exception();
                    }
                    if (cart != null) {
                        if (cart.getCart().containsKey(isbn)) {
                            book = cart.getCart().get(isbn);
                            book.setQuantity(quantity);
                            book.setPrice(price);
                            cart.edit(isbn, book);//Thay đổi số lượng sản phẩm có trong giỏ hàng
                            modal = "Thay đổi thành công";
                        }
                    }
                } else if (use.equals("create")) {
                    requestId = request.getParameter("requestID");
                    if (cart == null || cart.getCart().size() <= 0) {
                        modal = "Tạo thất bại(Danh sách đang trống)";
                        throw new Exception();
                    } else if (cart.getCart().size() > 0) {
                        int responseId = responseDAO.insertResponse(requestId, staff.getStaffID(), "1", "0");
                        for (BookDTO bookK : cart.getCart().values()) {
                            detailRSDao.insertResponseDetail(responseId, bookK, "1", "0");
                        }
                    }
                    success = "Done";
                    requestId = request.getParameter("requestID");
                    requestBook = requestDao.getRequestById(requestId);
                    listResponse1 = responseDAO.loadResponseByRequest(requestId, "1");
                    listDetail = detailDao.loadDetailByRequest(requestId);
                    cart.removeAll();
                }else if (use.equals("search")){
                    cart.removeAll();
                }
            }
            if (search == null || search == "") {
                search = "";
                listResponse = responseDAO.load9Response(index);
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchResponse"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = responseDAO.searchResponse(txtSearch).size();
                listResponse = responseDAO.search9Response(txtSearch, index);
            }
            session.setAttribute("CART2", cart);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Customer</title>\n"
                    + "        <meta name=\"description\" content=\"\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">\n"
                    + "  <!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"modal2\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-xl\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin yêu cầu nhập (Mã đơn: " + requestBook.getRequestID() + ")</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div style=\" max-height: calc(100vh - 200px);\" class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div><b>Người tạo yêu cầu nhập</b></div>\n"
                    + "                                <input rows=\"2\" style=\"margin-bottom: 25px;\" readonly=\"\" style=\"margin-bottom: 25px; \" value=\"" + requestBook.getStaff().getName() + "\">\n"
                    + "                                <div><b>Ngày tạo</b></div>\n"
                    + "                                <input type=\"date\" readonly=\"\" style=\"margin-bottom: 25px;\" placeholder=\"Trống\" value=\"" + requestBook.getDate() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div><b>Tình trạng</b></div>\n"
            );
            String status = requestBook.getStatus();
            if (status.equals("1")) {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; color: green; border: none; color: green; margin-bottom: 25px;\" value=\"Hoàn thành\"/>\n");
            } else {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; color: red; border: none; color: red; margin-bottom: 25px;\" value=\"Đang đợi nhập\"/>\n");
            }
            out.println("                                <div><b>Trạng thái</b></div>\n");
            String delete = requestBook.getDelete();
            if (delete.equals("0")) {
                out.println("                              <i style=\"color: green; text-align: center; width: 50px;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\" color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/>\n");
            } else {
                out.println("                              <i style=\"color: red; text-align: center; width: 50px;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/>\n");
            }
            out.println("                         </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                        <h3><p><b>Chi tiết yêu cầu</b></p></h3>\n"
                    + "                            </div>\n"
                    + "                            </div>\n"
                    + "                        <div style=\"border-bottom: 2px solid #cccdcf;\" class=\"row\">\n"
                    + "                            <div class=\"col-md-2\"></div>\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <b><p>Thông tin sách</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                    + "                                <b><p>Số lượng yêu cầu</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                    + "                                <b><p>Số lượng đã nhập</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                    + "                                <b><p>Số lượng nhập</p></b>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                 <div style=\"overflow-y: scroll; overflow-x: hidden; height: 500px\">\n");
            try {
                char alphabet = 'A';
                for (BRequestDetailDTO detailDTO : listDetail) {
                    int quantityPass = 0;
                    int quantityNo = 0;
                    int quantityCart = 0;
                    double priceCart = 0;
                    for (BookResponseDTO bookResponseDTO : listResponse1) {
                        listResponseDetail1 = responseDetailDao.loadDetailByResponseID(bookResponseDTO.getResponseID());
                        for (BResponseDetailDTO bResponseDetailDTO : listResponseDetail1) {
                            if (bResponseDetailDTO.getIsbn().getIsbn().equals(detailDTO.getIsbn().getIsbn())) {
                                quantityPass += bResponseDetailDTO.getQuantity();
                            }
                        }
                    }
                    if (cart != null) {
                        for (BookDTO bookCheck : cart.getCart().values()) {
                            if (bookCheck.getIsbn().equals(detailDTO.getIsbn().getIsbn())) {
                                quantityCart = bookCheck.getQuantity();
                                priceCart = bookCheck.getPrice();
                            }
                        }
                    }
                    quantityNo = detailDTO.getQuantity() - quantityPass;
                    if (quantityNo <= 0) {
                        quantityCart = 0;
                    }
                    out.println("                        <div style=\"border-bottom: 1px solid #cccdcf; margin-bottom: 10px;\" class=\"row\">\n"
                            + "                            <div class=\"col-md-2\">\n"
                            + "                                <img style=\"width: 100%;\" src=\"" + detailDTO.getIsbn().getImg() + "\">\n"
                            + "                            </div>\n"
                            + "                            <div class=\"col-md-4\">\n"
                            + "                                <b>ISBN: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getIsbn() + "</span></b></br>\n"
                            + "                                <b>Tiêu đề: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getName() + "</span></b></br>\n"
                            + "                                <b>Tác giả: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getAuthorName() + "</span></b></br>\n"
                            + "                                <b>Nhà xuất bản: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getPublisher().getName() + "</span></span></b></br>\n"
                            + "                                <b>Thể loại: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getCategory().getName() + "</span></b></br>\n"
                            + "                                <b>Giá nhập: <input class=\"" + alphabet + "\" typle=\"number\" style=\"color: #d10024; font-weight: bold; width: 100px;\" value=\"" + (int) priceCart + "\"></b>\n"
                            + "                            </div>\n"
                            + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                            + "                                <span style=\"color: #d10024; font-weight: bold\">" + detailDTO.getQuantity() + "</span>\n"
                            + "                            </div>\n"
                            + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                            + "                                <span style=\"color: #d10024; font-weight: bold\">" + quantityPass + "</span>\n"
                            + "                            </div>\n");
                    if (quantityNo > 0) {
                        out.println("                          <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                                + "                                <input class=\"" + alphabet + "\" type=\"number\" style=\"color: #d10024; font-weight: bold; width: 50px;\" value=\"" + quantityCart + "\"> / <span style=\"color: #d10024; font-weight: bold\">" + quantityNo + "</span></br>\n"
                                + "                                <button data-bs-dismiss=\"modal\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','save','','" + indexR + "','','" + requestBook.getRequestID() + "','" + detailDTO.getIsbn().getIsbn() + "',document.getElementsByClassName(\'" + alphabet + "\')[1].value,document.getElementsByClassName(\'" + alphabet + "\')[0].value,'" + quantityNo + "')\" style=\"margin-top: 5px; color: white; border: 1px solid white; background-color: green; width: 60px;\" type=\"button\">Lưu</button>\n"
                                + "                            </div>\n");
                    } else {
                        out.println("                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                                + "                                <span style=\"color: green; font-weight: bold\">Hoàn thành</span>\n"
                                + "                            </div>\n");
                    }
                    out.println("         </div>\n");
                    alphabet++;
                }
            } catch (Exception e) {
            }
            out.println("               </div>\n"
                    + "                     </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','search','','',document.getElementById(\'searchRequest\').value)\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Trở về</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" type=\"button\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','create','','" + indexR + "','','" + requestBook.getRequestID() + "')\" class=\"btn btn-primary\">Tạo nhập hàng</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Modal -->"
                    + "        <div class=\"container\">\n"
                    + "            <div class=\"row justify-content-center\">\n"
                    + "                <!-- Modal-->\n"
                    + "                <div id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\" class=\"modal text-left\">\n"
                    + "                    <div role=\"document\" class=\"modal-dialog modal-xl\">\n"
                    + "                        <div style=\"height: 850px;\" class=\"modal-content\">\n"
                    + "                            <div style=\"display: flex; justify-content: space-around;\" class=\"modal-header row border-0\">\n"
                    + "                                <div style=\"text-align: center;\" class=\"col-md-11\">\n"
                    + "                                    <h3>Danh sách yêu cầu nhập</h3>\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchRequest\" class=\"input\" type=\"text\" value=\"" + searchR + "\" name=\"searchRequest\" placeholder=\"Tìm kiếm yêu cầu nhập\" style=\"width: 200px\">\n"
                    + "                                    <input id=\"searchButton\"  data-bs-dismiss=\"modal\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','search','','',document.getElementById(\'searchRequest\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                                </div>\n"
                    + "                                <div class=\"col-md-1\">\n"
                    + "                                    <button style=\"float: right; background-color: white; border: none;\" type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                                        <span aria-hidden=\"true\">&times;</span>\n"
                    + "                                    </button>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"line\"></div>\n"
                    + "                            <div style=\"height: 650px; overflow-y: scroll; overflow-x: hidden;\" class=\"modal-body p-0\">\n");
            if (success.equals("Done")) {
                out.println("  <p style=\"margin: 5px auto 5px auto; text-align: center; width: 100%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 25px;\">Tạo nhập sách thành công</p></br>");
                success = "";
            }
            out.println("                                    <div style=\"width: 1138px; margin: auto\" class=\"row\">\n"
                    + "                                        <div class=\"col-md-12\">\n"
                    + "                                            <div style=\" background-color: #1e1e27; color: white; font-weight: bold; font-size: 12px; text-align: center;\" class=\"row\">\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\">Mã yêu cầu</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-3\">Người tạo</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-3\">Ngày tạo</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Tình trạng</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-2\">Trạng thái</div>\n"
                    + "                                                <div style=\"border-left: 2px solid white;\" class=\"col-md-1\"></div>\n"
                    + "                                            </div>\n"
                    + "                                            <div id=\"createContent\"></div>\n"
                    + "                                        </div>\n"
                    + "                                    </div>\n");
            int count = 0;
            for (BookRequestDTO requestDTO : listRequest) {
                count--;
                out.println("                                    <div style=\"margin-left: auto; width: 1138px; height: 60px; font-size: 18px; border: 1px solid #dedede; text-align: center;\" class=\"row\">\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-1\">" + requestDTO.getRequestID() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-3\">" + requestDTO.getStaff().getName() + "</div>\n"
                        + "                                        <div style=\"font-weight: bold;\" class=\"col-md-3\">" + requestDTO.getDate() + "</div>\n");
                status = requestDTO.getStatus();
                if (status.equals("1")) {
                    out.println("                              <div style=\"font-weight: bold; color: green;\" class=\"col-md-2\">Hoàn thành</div>\n");
                } else {
                    out.println("                              <div style=\"font-weight: bold; color: red;\" class=\"col-md-2\">Đang đợi nhập</div>\n");
                }
                delete = requestDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <div style=\"font-weight: bold;\" class=\"col-md-2\"><i style=\"color: green; text-align: center; width: 50px;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\" color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></div>\n");
                } else {
                    out.println("                              <div style=\"font-weight: bold;\" class=\"col-md-2\"><i style=\"color: red; text-align: center; width: 50px;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></div>\n");
                }
                out.println("                                        <div style=\"font-weight: bold;\" class=\"col-md-1\">\n"
                        + "                                            <button data-bs-dismiss=\"modal\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','choosen','','','" + searchR + "','" + requestDTO.getRequestID() + "')\" style=\"color: white; border: 1px solid white; background-color: green\" type=\"button\">Chọn</button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                );
            }
            out.println("       <div class=\"row\">\n"
                    + "       <div style=\"text-align: center\" class=\"col-md-12\">\n"
                    + "<div class=\"pagination\">\n");
            int page = 0;
            if ((indexCountR % 9) == 0) {
                page = indexCountR / 9;
            } else {
                page = (indexCountR / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a href=\"#\" class=\"indexR\" data-bs-dismiss=\"modal\" onclick=\"loadResponse('ManageResponseController'," + index + ",'" + search + "','search',''," + j + ",'" + searchR + "')\">" + j + "</a>\n");
            }
            out.println("       </div>\n"
                    + "       </div>\n"
                    + "       </div>\n");
            out.println("                       </div>\n"
                    + "                            <div class=\"line\"></div>\n"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <button hidden=\"\" type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Đóng</button>\n"
                    + "                                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "  <!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-xl\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin nhập sách(Mã đơn: " + responseDTO.getResponseID() + ")</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div style=\" max-height: calc(100vh - 200px);\" class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div><b>Người tạo yêu cầu nhập</b></div>\n"
                    + "                                <input rows=\"2\" style=\"margin-bottom: 25px;\" readonly=\"\" style=\"margin-bottom: 25px; \" value=\"" + responseDTO.getStaff().getName() + "\">\n"
                    + "                                <div><b>Ngày tạo</b></div>\n"
                    + "                                <input type=\"date\" readonly=\"\" style=\"margin-bottom: 25px;\" placeholder=\"Trống\" value=\"" + responseDTO.getDate() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div><b>Tình trạng</b></div>\n"
            );
            status = responseDTO.getStatus();
            if (status.equals("1")) {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; color: green; border: none; color: green; margin-bottom: 25px;\" value=\"Hoàn thành\"/>\n");
            } else {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; color: red; border: none; color: red; margin-bottom: 25px;\" value=\"Đang đợi nhập\"/>\n");
            }
            out.println("                                <div><b>Trạng thái</b></div>\n");
            delete = responseDTO.getDelete();
            if (delete.equals("0")) {
                out.println("                              <i style=\"color: green; text-align: center; width: 50px;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\" color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','remove','" + responseDTO.getResponseID() + "')\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n");
            } else {
                out.println("                              <i style=\"color: red; text-align: center; width: 50px;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','recover','" + responseDTO.getResponseID() + "')\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("                         </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                        <h3><p><b>Chi tiết đơn nhập sách</b></p></h3>\n"
                    + "                            </div>\n"
                    + "                            </div>\n"
                    + "                        <div style=\"border-bottom: 2px solid #cccdcf;\" class=\"row\">\n"
                    + "                            <div class=\"col-md-1\">\n"
                    + "                                <b><p>STT</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-2\"></div>\n"
                    + "                            <div class=\"col-md-8\">\n"
                    + "                                <b><p>Thông tin sách</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-1\">\n"
                    + "                                <b><p>Số lượng</p></b>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                 <div style=\"overflow-y: scroll; overflow-x: hidden; height: 500px\">\n");
            count = 0;
            for (BResponseDetailDTO detailDTO : listResponseDetail) {
                count++;
                out.println("                        <div style=\"border-bottom: 1px solid #cccdcf; margin-bottom: 10px;\" class=\"row\">\n"
                        + "                            <div class=\"col-md-1\">\n"
                        + "                                <b>" + count + "</b>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\">\n"
                        + "                                <img style=\"width: 100%;\" src=\"" + detailDTO.getIsbn().getImg() + "\">\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-8\">\n"
                        + "                                <b>ISBN: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getIsbn() + "</span></b></br>\n"
                        + "                                <b>Tiêu đề: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getName() + "</span></b></br>\n"
                        + "                                <b>Tác giả: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getAuthorName() + "</span></b></br>\n"
                        + "                                <b>Nhà xuất bản: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getPublisher().getName() + "</span></span></b></br>\n"
                        + "                                <b>Thể loại: <span style=\"color: #d10024\">" + detailDTO.getIsbn().getCategory().getName() + "</span></b>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-1\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + detailDTO.getQuantity() + "</span>\n"
                        + "                            </div>\n"
                        + "         </div>\n");
            }
            out.println("               </div>\n"
                    + "                     </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Modal -->"
                    + "        <div class=\"product-status mg-tb-15\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                    + "                        <div class=\"product-status-wrap\">\n"
                    + "                            <h2 onclick=\"loadResponse('ManageResponseController','" + index + "')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí nhập hàng</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchResponse\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchResponse\" placeholder=\"Tìm kiếm đơn nhập hàng\" style=\"width: 520px\">\n"
                    + "                                    <input onclick=\"loadResponse('ManageResponseController','" + index + "',document.getElementById(\'searchResponse\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Tạo đơn nhập sách</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n");
            if (listResponse.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>Mã nhập hàng</th>\n"
                        + "                                    <th>Người nhập hàng</th>\n"
                        + "                                    <th>Ngày nhập</th>\n"
                        + "                                    <th>Trạng thái</th>\n"
                        + "                                </tr>\n");
            }
            for (BookResponseDTO rpDTO : listResponse) {
                if (rpDTO.getStaff().getName() == null) {
                    rpDTO.getStaff().setName("N/A");
                }
                out.println("                              <tr>\n"
                        + "                                    <td>" + rpDTO.getResponseID() + "</td>\n"
                        + "                                    <td>" + rpDTO.getStaff().getName() + "</td>\n"
                        + "                                    <td>" + rpDTO.getDate() + "</td>\n");
                delete = rpDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"loadResponse('ManageResponseController','" + index + "','" + search + "','load','" + rpDTO.getResponseID() + "')\" data-toggle=\"tooltip\" title=\"Thông tin chi tiết\" class=\"pd-setting-ed\"><i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i></button>\n"
                        + "                                   </td>\n"
                        + "                                </tr>"
                );
            }
            out.println(" </table>\n"
                    + "         <div class=\"pagination\">\n");
            page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a id=\"" + j + "\" onclick=\"loadResponse('ManageResponseController'," + j + ",'" + search + "')\" href=\"#\">" + j + "</a>\n");
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
