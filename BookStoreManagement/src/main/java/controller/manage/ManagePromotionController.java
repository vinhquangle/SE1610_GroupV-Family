/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.PromotionDAO;
import dto.PromotionDTO;
import dto.PromotionErrorDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ManagePromotionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String search = new String();
        String modal = new String();
        String success = new String();
        StaffDTO staff = new StaffDTO();
        PromotionDAO proDao = new PromotionDAO();
        PromotionDTO promotion = new PromotionDTO();
        PromotionDTO newPromotion = new PromotionDTO();
        PromotionErrorDTO promotionError = new PromotionErrorDTO();
        List<PromotionDTO> listPromotion = new ArrayList<>();
        Locale localeVN = new Locale("vi", "VN");
        LocalDate localDate = LocalDate.now();
        LocalDate dateStart = LocalDate.now();
        LocalDate dateEnd = LocalDate.now();
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {
            HttpSession session = request.getSession();
            String use = request.getParameter("use");
            indexCount = proDao.loadPromotion().size();
            search = request.getParameter("searchPromotion");
            String promotionID = request.getParameter("promotionID");
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            promotion = proDao.getPromotionById(promotionID);
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                listPromotion = proDao.load9Promotion(index);//Lấy sản phẩm theo phân trang
            } else {
                System.out.println("1");
                //chuyen chuoi co dau thanh khong dau
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchPromotion"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //chuyen chuoi co dau thanh khong dau
                indexCount = proDao.searchPromotion(txtSearch).size();//Tim kiem tat ca category theo yeu cau
                listPromotion = proDao.search9Promotion(txtSearch, index);// Tim kiem aat ca sach theo trang
            }
            if (use != null) {
                if (use.equals("edit")) {
                    String dateS = request.getParameter("dateS");
                    String dateE = request.getParameter("dateE");
                    String des = request.getParameter("des");
                    double condition = 0;
                    double percent = 0;
                    try {
                        condition = Double.parseDouble(request.getParameter("condition"));
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(điều kiện không hợp lệ)";
                        throw new Exception();
                    }
                    if (condition < 0) {
                        modal = "Thay đổi thất bại(điều kiện phải lớn hơn 0)";
                        throw new Exception();
                    }
                    try {
                        percent = Double.parseDouble(request.getParameter("percent"));
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(phần trăm khuyến mãi không hợp lệ)";
                        throw new Exception();
                    }
                    if (percent < 0) {
                        modal = "Thay đổi thất bại(phần trăm khuyến mãi lớn hơn 0)";
                        throw new Exception();
                    }
                    try {
                        dateStart = LocalDate.parse(dateS, DateTimeFormatter.ISO_DATE);
                        dateEnd = LocalDate.parse(dateE, DateTimeFormatter.ISO_DATE);
                    } catch (Exception e) {
                        modal = "Thay đổi thất bại(ngày thiết đặt không hợp lệ)";
                        throw new Exception();
                    }
                    if (dateEnd.isBefore(dateStart)) {
                        modal = "Thay đổi thất bại(ngày kết thúc không hợp lệ)";
                        throw new Exception();
                    }
                    if (des == "") {
                        modal = "Thay đổi thất bại(mô tả khuyến mãi không được để trống)";
                        throw new Exception();
                    }
                    promotion.setDateStart(dateS);
                    promotion.setDateEnd(dateE);
                    promotion.setCondition(condition);
                    promotion.setDiscount(percent);
                    promotion.setDescription(des);
                    if (proDao.updatePromotion(promotion)) {
                        modal = "Thay đổi thành công";
                    }
                } else if (use.equals("remove")) {
                    promotion.setStatus("0");
                    if (proDao.updatePromotion(promotion)) {
                        modal = "Xóa thành công";
                    }
                } else if (use.equals("recover")) {
                    promotion.setStatus("1");
                    if (proDao.updatePromotion(promotion)) {
                        modal = "Khôi phục thành công";
                    }
                } else if (use.equals("add")) {
                    String dateS = request.getParameter("dateS");
                    String dateE = request.getParameter("dateE");
                    String des = request.getParameter("des");
                    double condition = 0;
                    double percent = 0;
                    boolean check = true;
                    try {
                        condition = Double.parseDouble(request.getParameter("condition"));
                    } catch (Exception e) {
                        promotionError.setCondition("Thay đổi thất bại(điều kiện không hợp lệ)");
                        check = false;
                    }
                    if (condition < 0) {
                        promotionError.setCondition("Thay đổi thất bại(điều kiện phải lớn hơn 0)");
                        check = false;
                    }
                    try {
                        percent = Double.parseDouble(request.getParameter("percent"));
                    } catch (Exception e) {
                        promotionError.setDiscount("Thay đổi thất bại(phần trăm khuyến mãi không hợp lệ)");
                        check = false;
                    }
                    if (percent < 0) {
                        promotionError.setDiscount("Thay đổi thất bại(phần trăm khuyến mãi lớn hơn 0)");
                        check = false;
                    }
                    try {
                        dateStart = LocalDate.parse(dateS, DateTimeFormatter.ISO_DATE);
                        dateEnd = LocalDate.parse(dateE, DateTimeFormatter.ISO_DATE);
                    } catch (Exception e) {
                        promotionError.setDateStart("Thay đổi thất bại(ngày thiết đặt không hợp lệ)");
                        promotionError.setDateEnd("Thay đổi thất bại(ngày thiết đặt không hợp lệ)");
                        check = false;
                    }
                    if (dateEnd.isBefore(dateStart)) {
                        promotionError.setDateEnd("Thay đổi thất bại(ngày kết thúc không hợp lệ)");
                        check = false;
                    }
                    if (des == "") {
                        promotionError.setDescription("Thay đổi thất bại(mô tả khuyến mãi không được để trống)");
                        check = false;
                    }
                    newPromotion = new PromotionDTO("", staff, dateS, dateE, des, condition, percent, "1");
                    if (check) {
                        if (proDao.createPromotion(newPromotion)) {
                            success = "Tạo chương trình khuyến mãi thành công";
                        }
                    }
                }
            }
            if (search == null || search == "") {
                search = "";
                listPromotion = proDao.load9Promotion(index);//Lấy sản phẩm theo phân trang
            } else {
                System.out.println("1");
                //chuyen chuoi co dau thanh khong dau
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchPromotion"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //chuyen chuoi co dau thanh khong dau
                indexCount = proDao.searchPromotion(txtSearch).size();//Tim kiem tat ca category theo yeu cau
                listPromotion = proDao.search9Promotion(txtSearch, index);// Tim kiem aat ca sach theo trang
            }
        } catch (Exception e) {

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
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">"
                    + " <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog  modal-lg\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Tạo khuyến mãi</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div id=\"mcontent\" class=\"modal-body\">\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 20px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Ngày bắt đầu</label>\n"
                    + "                                <input id=\"newDateS\" placeholder=\"Nhập ngày bắt đầu khuyến mãi\" value=\"" + newPromotion.getDateStart() + "\" style=\"font-weight: bold\" min=\"" + localDate + "\" id=\"pubid\" type=\"date\" class=\"form-control c\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + promotionError.getDateStart() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Ngày kết thúc</label>\n"
                    + "                                <input id=\"newDateE\" placeholder=\"Nhập ngày kết thúc khuyến mãi\" value=\"" + newPromotion.getDateEnd() + "\" style=\"font-weight: bold\" min=\"" + localDate + "\" type=\"date\" class=\"form-control c\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + promotionError.getDateEnd() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Khuyến mãi (%)</label>\n"
                    + "                                <input  id=\"newDis\" placeholder=\"Nhập phần trăm khuyến mãi\" value=\"" + newPromotion.getDiscount() + "\" style=\"font-weight: bold\" type=\"number\" class=\"form-control c\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + promotionError.getDiscount() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Điều kiện</label>\n"
                    + "                                <input id=\"newCon\" placeholder=\"Nhập điều kiện nhận khuyến mãi\" value=\"" + newPromotion.getCondition() + "\" style=\"font-weight: bold\" type=\"number\" class=\"form-control c\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + promotionError.getCondition() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Mô tả khuyến mãi</label>\n"
                    + "                                <textarea id=\"newDes\" placeholder=\"Nhập mô tả khuyến mãi\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\">" + newPromotion.getDescription() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + promotionError.getDescription() + "</p></br>\n"
                    + "                        </div>"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <input style=\"display: none;\" type=\"button\" class=\"btn\" value=\"z\">\n"
                    + "                                <button style=\"background-color: #494f57; color: white\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Hủy</button>\n"
                    + "                                <input data-bs-dismiss=\"modal\" onclick=\"loadPromotion('ManagePromotionController','" + index + "','" + search + "','" + promotion.getPromotionID() + "','add',document.getElementById(\'newDateS\').value,document.getElementById(\'newDateE\').value,document.getElementById(\'newCon\').value,document.getElementById(\'newDis\').value,document.getElementById(\'newDes\').value)\" type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Tạo\">\n"
                    + "                            </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        <!-- The Modal -->"
                    + " <!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-xl\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin chi tiết khuyến mãi</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Mã khuyến mãi</div>\n"
                    + "                                <input readonly=\"\" style=\"margin-bottom: 25px; \" placeholder=\"Mã khuyến mãi\" value=\"" + promotion.getPromotionID() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Ngày bắt đầu</div>\n"
                    + "                                <input class=\"date\" type=\"date\" style=\"margin-bottom: 25px;\" placeholder=\"Ngày bắt đầu khuyến mãi\" value=\"" + promotion.getDateStart() + "\"  min=\"" + localDate + "\">\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Mã nhân viên</div>\n"
                    + "                                <input readonly=\"\" style=\"margin-bottom: 25px; \" placeholder=\"Người tạo khuyến mãi\" value=\"" + promotion.getStaff().getStaffID() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Ngày kết thúc</div>\n"
                    + "                                <input class=\"date\" type=\"date\" style=\"margin-bottom: 25px;\" placeholder=\"Ngày kết thúc khuyến mãi\" value=\"" + promotion.getDateEnd() + "\" min=\"" + localDate + "\">\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Điều kiện</div>\n"
                    + "                                <input id=\"condition\" type=\"number\" style=\"margin-bottom: 25px; \" placeholder=\"Điều kiện kích hoạt khuyến mãi\" value=\"" + (int) promotion.getCondition() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"font-weight: bold;\">Phần trăm khuyến mãi (%)</div>\n"
                    + "                                <input id=\"discount\" type=\"number\" style=\"margin-bottom: 25px;\" placeholder=\"Phần trăm giảm giá\" value=\"" + promotion.getDiscount() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-12\">\n");
            if (promotion.getStatus().equals("1")) {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p style=\"font-weight: bold;\">Tình trạng: <i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"loadPromotion('ManagePromotionController'," + index + ",'" + search + "','" + promotion.getPromotionID() + "','remove')\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p style=\"font-weight: bold;\">Tình trạng: <i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"loadPromotion('ManagePromotionController'," + index + ",'" + search + "','" + promotion.getPromotionID() + "','recover')\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("                            </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                                <div style=\"font-weight: bold;\">Mô tả chi tiết khuyến mãi</div>\n"
                    + "                                <textarea id=\"des\" style=\"width: 100%;\" placeholder=\"Mô tả chi tiết cho khuyến mãi\">" + promotion.getDescription() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button onclick=\"loadPromotion('ManagePromotionController','" + index + "','" + search + "','" + promotion.getPromotionID() + "','edit',document.getElementsByClassName(\'date\')[0].value,document.getElementsByClassName(\'date\')[1].value,document.getElementById(\'condition\').value,document.getElementById(\'discount\').value,document.getElementById(\'des\').value)\" type=\"button\" class=\"btn btn-primary\" data-bs-dismiss=\"modal\">Lưu thay đổi</button>\n"
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
                    + "                            <h2 onclick=\"loadPromotion('ManagePromotionController','" + index + "')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí khuyến mãi</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchPromotion\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchPromotion\" placeholder=\"Tìm kiếm theo ID hoặc mô tả khuyến mãi\" style=\"width: 400px\">\n"
                    + "                                    <input onclick=\"loadPromotion('ManagePromotionController','" + index + "',document.getElementById(\'searchPromotion\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Thêm khuyến mãi mới</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n"
            );
            if (listPromotion.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>ID khuyến mãi</th>\n"
                        + "                                    <th>Ngày bắt đầu</th>\n"
                        + "                                    <th>Ngày kết thúc</th>\n"
                        + "                                    <th>Mô tả</th>\n"
                        + "                                    <th>Điều kiện</th>\n"
                        + "                                    <th>Khuyến mãi(%)</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            int count = 0;
            for (int i = 0; i < listPromotion.size(); i++) {
                count++;
                out.println("          <tr>\n"
                        + "                                    <td>" + listPromotion.get(i).getPromotionID() + "</td>\n"
                        + "                                    <input class=\"" + count + "\" value=\"" + listPromotion.get(i).getDateStart() + "\" type=\"hidden\"/>"
                        + "                                    <td>" + listPromotion.get(i).getDateStart() + "</td>\n"
                        + "                                    <td>" + listPromotion.get(i).getDateEnd() + "</td>\n"
                        + "                                    <td><textarea readonly=\"\" rows=\"3\" style=\"width: 420px;\">" + listPromotion.get(i).getDescription() + "</textarea></td>\n"
                        + "                                    <td>" + currencyVN.format(listPromotion.get(i).getCondition()) + "</td>\n"
                        + "                                    <td>" + listPromotion.get(i).getDiscount() + "</td>\n");
                String status = listPromotion.get(i).getStatus();
                if (status.equals("1")) {
                    out.println("                                  <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                } else {
                    out.println("                                    <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"loadPromotion('ManagePromotionController?index=" + index + "','" + index + "','" + search + "','" + listPromotion.get(i).getPromotionID() + "','load')\" data-toggle=\"tooltip\" title=\"Chỉnh sửa\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                   </td>\n"
                        + "                                </tr>");
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
                out.println("                    <a id=\"" + j + "\" onclick=\"loadPromotion('ManagePromotionController?searchPromotion=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
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
