/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import aes.MyAES;
import dao.StaffDAO;
import dto.StaffDTO;
import dto.StaffErrorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 
 */
//Ngọc Thy, Quốc Thịnh >>>>>>>>>>
public class ManageStaffController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        LocalDate localDate = LocalDate.now();
        String search = new String();
        String modal = new String();
        String AES_decryptedStr = new String();
        String success = new String();
        String confirm = new String();
        List<String> listRole = new ArrayList<String>();
        StaffDTO staff = new StaffDTO();
        StaffDTO newStaff = new StaffDTO();
        StaffDAO staffDao = new StaffDAO();
        StaffErrorDTO staffError = new StaffErrorDTO();
        List<StaffDTO> listStaff = new ArrayList<StaffDTO>();
        try {
            indexCount = staffDao.getListStaff().size();
            search = request.getParameter("searchStaff");
            String staffID = request.getParameter("staffID");
            String use = request.getParameter("use");
            staff = staffDao.loadStaff(staffID);
            MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
            AES_decryptedStr = myCipher.AES_decrypt(staff.getPassword());//Giải mã AES
            listRole.add("Admin");
            listRole.add("Staff");
            listRole.add("Deliverer");
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                listStaff = staffDao.getListStaff9(index);//Lấy nhân viên theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchStaff"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = staffDao.searchStaff(txtSearch).size();
                listStaff = staffDao.search9Staff(txtSearch, index);
            }
            if (use.equals("edit")) {
                String role = request.getParameter("role");
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                LocalDate birthDate = LocalDate.now();
                //check validation cho name 
                if (name == "") {
                    modal = "Chỉnh sửa thông tin thất bại(Tên không được để trống)";
                    throw new Exception();
                } else if (name.length() < 2 || name.length() > 50) {
                    modal = "Chỉnh sửa thông tin thất bại(Độ dài tên phải từ 2 đến 50 kí tự)";
                    throw new Exception();
                }
                //check validation cho phone
                try {
                    Integer.parseInt(phone);
                } catch (Exception e) {
                    modal = "Số điện thoại không hợp lệ!";
                    throw new Exception();
                }
                if (phone.length() > 15 || phone.length() < 7) {
                    modal = "Số điện thoại không hợp lệ!";
                    throw new Exception();
                }
                try {
                    birthDate = LocalDate.parse(dob, DateTimeFormatter.ISO_DATE);
                } catch (Exception e) {
                    modal = "Ngày sinh không hợp lệ";
                    throw new Exception();
                }
                if (birthDate.isAfter(localDate.minusYears(18))) {
                    modal = "Thông tin ngày sinh chưa đủ 18 tuổi";
                    throw new Exception();
                }
                if (birthDate.isBefore(localDate.minusYears(80))) {
                    modal = "Thông tin ngày sinh vượt quá số tuối quy định";
                    throw new Exception();
                }
                staff.setRole(role);
                staff.setDateOfBirth(dob);
                staff.setName(name);
                staff.setPhone(phone);
                if (staffDao.updateStaff(staff)) {
                    modal = "Chỉnh sửa thông tin thành công";
                } else {
                    modal = "Chỉnh sửa thông tin thất bại";
                    staff = staffDao.loadStaff(staffID);
                }
            } else if (use.equals("remove")) {
                staff.setDelete("1");
                if (staffDao.updateStaff(staff)) {
                    modal = "Xóa thành công";
                }
            } else if (use.equals("recover")) {
                staff.setDelete("0");
                if (staffDao.updateStaff(staff)) {
                    modal = "Khôi phục thành công";
                }
            } else if (use.equals("add")) {
                String role = request.getParameter("role");
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                String password = request.getParameter("password");
                confirm = request.getParameter("confirm");
                LocalDate birthDate = LocalDate.now();
                boolean checkValidation = true;
                if (staffID == "") {
                    staffError.setStaffIDError("Tài khoản không được để trống.");
                    checkValidation = false;
                } else if (staffID.length() < 1 || staffID.length() > 10) {
                    staffError.setStaffIDError("Độ dài tài khoản phải từ 1 đến 10 kí tự.");
                    checkValidation = false;
                }
                if (staffDao.checkStaffID(staffID)) {
                    staffError.setStaffIDError("Tài khoản đã được sử dụng");
                    checkValidation = false;
                }
                if (name == "") {
                    staffError.setNameError("Tên không được để trống.");
                    checkValidation = false;
                } else if (name.length() < 2 || name.length() > 50) {
                    staffError.setNameError("Độ dài tên phải từ 2 đến 50 kí tự.");
                    checkValidation = false;
                }
                if (role == null || role == "") {
                    staffError.setRoleError("Vui lòng chọn vai trò");
                    checkValidation = false;
                }
                try {
                    Integer.parseInt(phone);
                } catch (Exception e) {
                    staffError.setPhoneError("Số điện thoại không hợp lệ!");
                    checkValidation = false;
                }
                if (phone == "") {
                    staffError.setPhoneError("Số điện thoại không được để trống!");
                    checkValidation = false;
                } else if (phone.length() > 15 || phone.length() < 8) {
                    staffError.setPhoneError("Số điện thoại không hợp lệ!");
                    checkValidation = false;
                }
                try {
                    birthDate = LocalDate.parse(dob, DateTimeFormatter.ISO_DATE);
                } catch (Exception e) {
                    staffError.setDateOfBirthError("Ngày sinh không hợp lệ");
                    checkValidation = false;
                }
                if (birthDate.isAfter(localDate.minusYears(18))) {
                    staffError.setDateOfBirthError("Thông tin ngày sinh chưa đủ 18 tuổi");
                    checkValidation = false;
                }
                if (birthDate.isBefore(localDate.minusYears(80))) {
                    staffError.setDateOfBirthError("Thông tin ngày sinh vượt quá số tuối quy định");
                    checkValidation = false;
                }
                if (password == "") {
                    staffError.setPasswordError("Mật khẩu không được để trống");
                    checkValidation = false;
                } else if (password.length() < 8) {
                    staffError.setPasswordError("Mật khẩu nên dài từ 8 kí tự trở lên");
                    checkValidation = false;
                } else if (password.length() > 50) {
                    staffError.setPasswordError("Mật khẩu không nên dài quá 50 kí tự");
                    checkValidation = false;
                }
                //check pass-confirm
                if (confirm == "") {
                    staffError.setConfirmError("Xác nhận mật khẩu không được để trống!");
                    checkValidation = false;
                } else if (!password.equals(confirm)) {
                    staffError.setConfirmError("Xác nhận mật khẩu không khớp!");
                    checkValidation = false;
                }
                newStaff = new StaffDTO(staffID, name, password, role, phone, dob, "0", "0");
                if (checkValidation) {
                    myCipher = new MyAES("1", MyAES.AES_192);
                    String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
                    newStaff.setPassword(AES_ecryptedStr);
                    if (staffDao.createStaff(newStaff)) {
                        newStaff.setPassword(password);
                        success = "Tạo tài khoản mới thành công";
                    }
                }
            }
            if (search == null || search == "") {
                search = "";
                listStaff = staffDao.getListStaff9(index);//Lấy nhân viên theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchStaff"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = staffDao.searchStaff(txtSearch).size();
                listStaff = staffDao.search9Staff(txtSearch, index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter out = response.getWriter();
            out.println("<html class=\"no-js\" lang=\"en\">\n"
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
                    + " <!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-lg\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 800px\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin tài khoản nhân viên</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div>Tài khoản</div>\n"
                    + "                                <input id=\"staffID\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập tài khoản\" value=\"" + staff.getStaffID() + "\" readonly>\n"
                    + "                                <div>Mật khẩu</div>\n"
                    + "                                <input id=\"password\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập mật khẩu\" value=\"" + AES_decryptedStr + "\" readonly>\n"
                    + "                                <div>Vai trò</div>\n"
                    + "                 <select id=\"role\" style=\"width: 200px; font-weight: bold; margin-bottom: 25px; width: 300px\">\n");
            String roleE = staff.getRole();
            if (roleE.equals("Admin")) {
                out.println("                           <option disabled selected hidden value=\"Admin\">Quản trị viên</option>\n");
            } else if (roleE.equals("Staff")) {
                out.println("                           <option disabled selected hidden value=\"Staff\">Nhân viên</option>\n");
            } else if (roleE.equals("Deliverer")) {
                out.println("                           <option disabled selected hidden value=\"Deliverer\">Người giao hàng</option>\n");
            }
            for (String role : listRole) {
                String vrole = role;
                if (vrole.equals("Admin")) {
                    vrole = "Quản trị viên";
                } else if (vrole.equals("Staff")) {
                    vrole = "Nhân viên";
                } else if (vrole.equals("Deliverer")) {
                    vrole = "Người giao hàng";
                }
                out.println("                                  <option style=\"font-weight: bold\" value=\"" + role + "\">" + vrole + "</option>\n");
            }
            out.println("                                        </select>\n"
                    + "                                <div>Trạng thái</div>\n");
            String status = staff.getStatus();
            if (status.equals("1")) {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; width: 300px color: green; border: none; color: green; margin-bottom: 25px;\" value=\"Online\"/>\n");
            } else {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; width: 300px color: red; border: none; color: red; margin-bottom: 25px;\" value=\"Offline\"/>\n");
            }
            out.println("               </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div>Họ và tên</div>\n"
                    + "                                <input id=\"name\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập họ và tên\" value=\"" + staff.getName() + "\">\n"
                    + "                                <div>Số điện thoại</div>\n"
                    + "                                <input id=\"phone\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập số điện thoại\" value=\"" + staff.getPhone() + "\">\n"
                    + "                                <div>Ngày sinh</div>\n"
                    + "                                <input id=\"dob\" type=\"date\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập ngày sinh\" value=\"" + staff.getDateOfBirth() + "\" min=\"" + localDate.minusYears(80) + "\" max=\"" + localDate.minusYears(18) + "\">\n"
                    + "                                <div>Tình trạng</div>\n");
            String delete = staff.getDelete();
            if (delete.equals("0")) {
                out.println("                              <td><i style=\"color: green; text-align: center; width: 50px;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\" color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"staff('ManageStaffController?index=" + index + "','remove'," + index + ")\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                              <td><i style=\"color: red; text-align: center; width: 50px;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"staff('ManageStaffController?index=" + index + "','recover'," + index + ")\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("                       </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"staff('ManageStaffController?index=" + index + "','edit'," + index + ")\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Modal -->"
                    + "        <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Tạo tài khoản</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 30px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Họ và tên</label>\n"
                    + "                                <input placeholder=\"Nhập họ và tên\" value=\"" + newStaff.getName() + "\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getNameError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Tài khoản</label>\n"
                    + "                                <input placeholder=\"Nhập tài khoản\" value=\"" + newStaff.getStaffID() + "\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\" maxlength=\"10\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getStaffIDError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Mật khẩu</label>\n"
                    + "                                <input placeholder=\"Nhập mật khẩu\" value=\"" + newStaff.getPassword() + "\" style=\"font-weight: bold\" type=\"password\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getPasswordError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Xác nhận mật khẩu</label>\n"
                    + "                                <input placeholder=\"Nhập lại mật khẩu\" value=\"" + confirm + "\" style=\"font-weight: bold\" type=\"password\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getConfirmError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Vai trò</label>\n"
                    + "                                <select value=\"" + newStaff.getRole() + "\" style=\"font-weight: bold\" name=\"category\" class=\"form-select c\" aria-label=\"Default select example\">\n"
                    + "                                     <option disabled selected hidden value=\"\">Chọn vai trò</option>\n");
            for (String role : listRole) {
                String vrole = role;
                if (vrole.equals("Admin")) {
                    vrole = "Quản trị viên";
                } else if (vrole.equals("Staff")) {
                    vrole = "Nhân viên";
                } else if (vrole.equals("Deliverer")) {
                    vrole = "Người giao hàng";
                }
                out.println("                                  <option style=\"font-weight: bold\" value=\"" + role + "\">" + vrole + "</option>\n");
            }
            out.println("                                </select>\n"
                    + "                         </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getRoleError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Số điện thoại</label>\n"
                    + "                                <input placeholder=\"Nhập số điện thoại\" value=\"" + newStaff.getPhone() + "\" style=\"font-weight: bold\" type=\"number\" class=\"form-control c\" minlength=\"2\" maxlength=\"15\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getPhoneError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Ngày sinh (mm/dd/yyyy)</label>\n"
                    + "                                <input placeholder=\"Nhập ngày sinh\" value=\"" + newStaff.getDateOfBirth() + "\" style=\"font-weight: bold\" type=\"date\" class=\"form-control c\" min=\"" + localDate.minusYears(80) + "\" max=\"" + localDate.minusYears(18) + "\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 80%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + staffError.getDateOfBirthError() + "</p></br>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal footer -->\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-danger\"></button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"addStaff('ManageStaffController','c')\" type=\"button\" class=\"btn btn-primary\">Tạo</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- The Modal -->\n"
                    + "        <div class=\"product-status mg-tb-15\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                    + "                        <div class=\"product-status-wrap\">\n"
                    + "                            <h2 onclick=\"loadStaff('ManageStaffController','')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí nhân viên</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchStaff\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchStaff\" placeholder=\"Tìm kiếm nhân viên theo mã nhân viên hoặc tên\" style=\"width: 450px\">\n"
                    + "                                    <input onclick=\"loadStaff('ManageStaffController'," + index + ",document.getElementById(\'searchStaff\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Thêm tài khoản nhân viên</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n"
            );
            if (listStaff.size() <= 0) {
                out.println("           <div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                    <table>\n"
                        + "                                <tr>\n"
                        + "                                   <th>Tài khoản</th>\n"
                        + "                                   <th>Họ và tên</th>\n"
                        + "                                   <th>Mật khẩu</th>\n"
                        + "                                   <th>Vai trò</th>\n"
                        + "                                   <th>Số điện thoại</th>\n"
                        + "                                   <th>Ngày sinh</th>\n"
                        + "                                   <th>Trạng thái</th>\n"
                        + "                                   <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            for (StaffDTO staffDTO : listStaff) {
                out.println("                <tr>\n"
                        + "                    <td>" + staffDTO.getStaffID() + "</td>\n"
                        + "                    <td>" + staffDTO.getName() + "</td>\n"
                        + "                    <td><input type=\"password\" name=\"password\" style=\"border: none\" readonly=\"\" value=\"" + staffDTO.getPassword() + "\"/></td>\n");
                String role = staffDTO.getRole();
                if (role.equals("Admin")) {
                    out.println("                    <td>Quản trị viên</td>\n");
                } else if (role.equals("Staff")) {
                    out.println("                    <td>Nhân viên</td>\n");
                } else if (role.equals("Deliverer")) {
                    out.println("                    <td>Người giao hàng</td>\n");
                }
                out.println("                    <td>" + staffDTO.getPhone() + "</td>\n"
                        + "                    <td>" + staffDTO.getDateOfBirth() + "</td>\n");
                status = staffDTO.getStatus();
                if (status.equals("1")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 50px; color: green; border: none; color: green;\" value=\"Online\"/></td>\n");
                } else {
                    out.println("                              <td><input readonly=\"\" style=\"width: 50px; color: red; border: none; color: red;\" value=\"Offline\"/></td>\n");
                }
                delete = staffDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                }
                out.println("                        <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updateStaff('ManageStaffController?index=" + index + "','" + search + "','" + staffDTO.getStaffID() + "'," + index + ",'load')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                        </td>\n"
                        + "                        \n"
                        + "                </tr>\n");
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
                out.println("                    <a id=\"" + j + "\" onclick=\"loadStaff('ManageStaffController?searchStaff=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
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