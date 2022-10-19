/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import aes.MyAES;
import dao.CustomerDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
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

/**
 *
 * @author Admin
 */
//Quốc Phi, Quốc Thịnh >>>>>>>>>>
public class ManageCustomerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String search = new String();
        String modal = new String();
        String AES_decryptedStr = new String();
        String success = new String();
        String confirm = new String();
        CustomerDTO cus = new CustomerDTO();
        CustomerDTO newCus = new CustomerDTO();
        CustomerDAO cusDao = new CustomerDAO();
        CustomerErrorDTO cusError = new CustomerErrorDTO();
        List<CustomerDTO> listCust = new ArrayList<CustomerDTO>();
        try {
            indexCount = cusDao.getlistCustomer().size();
            search = request.getParameter("searchCus");
            String cusID = request.getParameter("cusID");
            String use = request.getParameter("use");
            cus = cusDao.loadCustomer(cusID);
            MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
            AES_decryptedStr = myCipher.AES_decrypt(cus.getPassword());//Giải mã AES
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                listCust = cusDao.getlist9Customer(index);//Lấy tài khoản khách theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchCus"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = cusDao.searchCustomerAccount(txtSearch).size();
                listCust = cusDao.search9CustomerAccount(txtSearch, index);
            }
            if (use.equals("edit")) {
                String email = request.getParameter("email");
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String point = request.getParameter("point");
                String addr = request.getParameter("addr");
                int po = 0;
                if (name.length() > 100 || name.length() < 2) {
                    modal = "Chỉnh sửa thông tin thất bại(Họ và tên phải trong khoảng [2,100] ký tự)";
                    throw new Exception();
                }
                if (name.isBlank() || name.isEmpty()) {
                    modal = "Chỉnh sửa thông tin thất bại(Họ và tên không được bỏ trống)";
                    throw new Exception();
                }
                if (email.isBlank() || email.isEmpty()) {
                    modal = "Chỉnh sửa thông tin thất bại(Email không được bỏ trống)";
                    throw new Exception();
                }
                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                    modal = "Chỉnh sửa thông tin thất bại(Email không hợp lệ)";
                    throw new Exception();
                }
                if (phone.isBlank() || phone.isEmpty()) {
                    modal = "Chỉnh sửa thông tin thất bại(Số điện thoại không được để trống)";
                    throw new Exception();
                } else if (!phone.matches(new CustomerDTO().PHONE_FORMAT)) {
                    modal = "Chỉnh sửa thông tin thất bại(Số điện thoại không hợp lệ)";
                    throw new Exception();
                }
                if (addr.length() > 500 || addr.length() < 5) {
                    modal = "Chỉnh sửa thông tin thất bại(Địa chỉ phải trong khoảng [5,500] ký tự)";
                    throw new Exception();
                }
                if (addr.isBlank() || addr.isEmpty()) {
                    modal = "Chỉnh sửa thông tin thất bại(Địa chỉ không được bỏ trống)";
                    throw new Exception();
                }
                try {
                    po = Integer.parseInt(point);
                } catch (Exception e) {
                    modal = "Chỉnh sửa thông tin thất bại(Điểm thưởng không hợp lệ)";
                    throw new Exception();
                }
                if (po < 0) {
                    modal = "Chỉnh sửa thông tin thất bại(Điểm thưởng phải lớn hơn hoặc bằng 0)";
                    throw new Exception();
                }
                cus.setEmail(email);
                cus.setName(name);
                cus.setPhone(phone);
                cus.setAddress(addr);
                cus.setPoint(po);
                if (cusDao.updateCustomer(cus)) {
                    modal = "Chỉnh sửa thông tin thành công";
                } else {
                    modal = "Chỉnh sửa thông tin thất bại";
                    cus = cusDao.loadCustomer(cusID);
                }
            } else if (use.equals("remove")) {
                cus.setDelete("1");
                if (cusDao.updateCustomer(cus)) {
                    modal = "Xóa thành công";
                }
            } else if (use.equals("recover")) {
                cus.setDelete("0");
                if (cusDao.updateCustomer(cus)) {
                    modal = "Khôi phục thành công";
                }
            } else if (use.equals("add")) {
                String email = request.getParameter("email");
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String point = request.getParameter("point");
                String addr = request.getParameter("addr");
                String password = request.getParameter("password");
                confirm = request.getParameter("confirm");
                int po = 0;
                boolean checkValidation = true;
                if (cusID.length() > 50 || cusID.length() < 2) {
                    checkValidation = false;
                    cusError.setCustomerIDError("Độ dài của tài khoản phải trong khoảng [2,50] ký tự");
                }
                if (cusID.isBlank() || cusID.isEmpty()) {
                    checkValidation = false;
                    cusError.setCustomerIDError("Tài khoản không được bỏ trống");
                }
                boolean checkDuplicateID = cusDao.checkCustomerID(cusID);//Kiểm tra trùng lặp CustomerID
                if (checkDuplicateID) {
                    cusError.setCustomerIDError("Tài khoản đã được sử dụng");
                    checkValidation = false;
                }
                if (name.length() > 100 || name.length() < 2) {
                    checkValidation = false;
                    cusError.setNameError("Họ và tên phải trong khoảng [2,100] ký tự");
                }
                if (name.isBlank() || name.isEmpty()) {
                    checkValidation = false;
                    cusError.setNameError("Họ và tên không được bỏ trống");
                }
                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                    checkValidation = false;
                    cusError.setEmailError("Đây không phải là email");
                }
                if (email.isBlank() || email.isEmpty()) {
                    checkValidation = false;
                    cusError.setEmailError("Email không được bỏ trống");
                }
                if (!phone.matches(new CustomerDTO().PHONE_FORMAT)) {
                    checkValidation = false;
                    cusError.setPhoneError("Số điện thoại không hợp lệ");
                }
                if (addr.length() > 500 || addr.length() < 5) {
                    checkValidation = false;
                    cusError.setAddressError("Địa chỉ phải trong khoảng [5,500] ký tự");
                }
                if (addr.isBlank() || addr.isEmpty()) {
                    checkValidation = false;
                    cusError.setAddressError("Địa chỉ không được bỏ trống");
                }
                if (!password.equals(confirm)) {
                    cusError.setConfirmError("Mật khẩu không khớp!!!");
                    checkValidation = false;
                }
                if (password.isBlank() || password.isEmpty()) {
                    cusError.setPasswordError("Mật khẩu không được bỏ trống!");
                } else if (password.length() < 8) {
                    cusError.setPasswordError("Mật khẩu nên dài từ 8 kí tự trở lên");
                    checkValidation = false;
                } else if (password.length() > 50) {
                    cusError.setPasswordError("Mật khẩu không nên dài quá 50 kí tự");
                    checkValidation = false;
                }
                if (confirm == "") {
                    cusError.setConfirmError("Xác nhận mật khẩu không được để trống!");
                    checkValidation = false;
                }
                try {
                    po = Integer.parseInt(point);
                } catch (Exception e) {
                    cusError.setPointError("Điểm thưởng không hợp lệ");
                    checkValidation = false;
                }
                if (po < 0) {
                    cusError.setPointError("Điểm thưởng phải lớn hơn hoặc bằng 0");
                    checkValidation = false;
                }
                newCus = new CustomerDTO(cusID, name, password, email, addr, phone, po, "0", "0");
                if (checkValidation) {
                    myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                    String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
                    newCus.setPassword(AES_ecryptedStr);
                    if (cusDao.createAccount(newCus)) {
                        newCus.setPassword(password);
                        success = "Tạo tài khoản mới thành công";
                    }
                }
            }
            if (search == null || search == "") {
                search = "";
                listCust = cusDao.getlist9Customer(index);//Lấy nhân viên theo phân trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchStaff"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                indexCount = cusDao.searchCustomerAccount(txtSearch).size();
                listCust = cusDao.search9CustomerAccount(txtSearch, index);
            }
        } catch (Exception e) {

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
                    + "                           <p style=\"width: 85%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 30px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Họ và tên</label>\n"
                    + "                                <input placeholder=\"Nhập họ và tên\" value=\"" + newCus.getName() + "\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getNameError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Tài khoản</label>\n"
                    + "                                <input placeholder=\"Nhập tài khoản\" value=\"" + newCus.getCustomerID() + "\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getCustomerIDError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Mật khẩu</label>\n"
                    + "                                <input placeholder=\"Nhập mật khẩu\" value=\"" + newCus.getPassword() + "\" style=\"font-weight: bold\" type=\"password\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getPasswordError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Xác nhận mật khẩu</label>\n"
                    + "                                <input placeholder=\"Nhập lại mật khẩu\" value=\"" + confirm + "\" style=\"font-weight: bold\" type=\"password\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getConfirmError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Email</label>\n"
                    + "                                <input placeholder=\"Nhập địa chỉ email\" value=\"" + newCus.getEmail() + "\" style=\"font-weight: bold\" type=\"text\" class=\"form-control c\" maxlength=\"50\">\n"
                    + "                         </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getEmailError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Số điện thoại</label>\n"
                    + "                                <input placeholder=\"Nhập số điện thoại\" value=\"" + newCus.getPhone() + "\" style=\"font-weight: bold\" type=\"number\" class=\"form-control c\" minlength=\"2\" maxlength=\"20\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getPhoneError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Điểm thưởng</label>\n"
                    + "                                <input placeholder=\"Nhập số điểm thưởng\" value=\"" + newCus.getPoint() + "\" style=\"font-weight: bold\" type=\"number\" class=\"form-control c\" minlength=\"2\" maxlength=\"15\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getPointError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Địa chỉ</label>\n"
                    + "                                 <textarea rows=\"4\" placeholder=\"Nhập địa chỉ\" style=\"font-weight: bold\" class=\"form-control c\" maxlength=\"500\">" + newCus.getAddress() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 85%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cusError.getAddressError() + "</p></br>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal footer -->\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-danger\"></button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"addCus('ManageCustomerController','c')\" type=\"button\" class=\"btn btn-primary\">Tạo</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- The Modal -->\n"
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
                    + "                                <input id=\"cusID\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập tài khoản\" value=\"" + cus.getCustomerID() + "\" readonly>\n"
                    + "                                <div>Mật khẩu</div>\n"
                    + "                                <input id=\"password\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập mật khẩu\" value=\"" + AES_decryptedStr + "\" readonly>\n"
                    + "                                <div>Điểm</div>\n"
                    + "                                <input id=\"point\" type=\"number\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập điểm thưởng\" value=\"" + cus.getPoint() + "\">\n"
                    + "                     <div>Trạng thái</div>\n");

            String status = cus.getStatus();
            if (status.equals("1")) {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; width: 300px color: green; border: none; color: green; margin-bottom: 25px;\" value=\"Online\"/>\n");
            } else {
                out.println("                              <input readonly=\"\" style=\"text-align: center; font-weight: bold; width: 300px color: red; border: none; color: red; margin-bottom: 25px;\" value=\"Offline\"/>\n");
            }
            out.println("               </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div>Họ và tên</div>\n"
                    + "                                <input id=\"name\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập họ và tên\" value=\"" + cus.getName() + "\">\n"
                    + "                                <div>Số điện thoại</div>\n"
                    + "                                <input id=\"phone\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập số điện thoại\" value=\"" + cus.getPhone() + "\">\n"
                    + "                                <div>Email</div>\n"
                    + "                                <input id=\"email\" type=\"text\" style=\"margin-bottom: 25px; font-weight: bold; width: 300px\" placeholder=\"Nhập địa chỉ email\" value=\"" + cus.getEmail() + "\">\n"
                    + "                                <div>Tình trạng</div>\n");
            String delete = cus.getDelete();
            if (delete.equals("0")) {
                out.println("                              <td><i style=\"color: green; text-align: center; width: 50px;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\" color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"cus('ManageCustomerController?index=" + index + "','remove'," + index + ")\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                              <td><i style=\"color: red; text-align: center; width: 50px;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"cus('ManageCustomerController?index=" + index + "','recover'," + index + ")\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("                       </div>\n"
                    + "                                <div>Địa chỉ</div>\n"
                    + "                                <input id=\"addr\" style=\"margin-bottom: 25px; font-weight: bold; width: 100%;\" placeholder=\"Nhập địa chỉ\" value=\"" + cus.getAddress() + "\">\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"cus('ManageCustomerController?index=" + index + "','edit'," + index + ")\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n"
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
                    + "                        <h4 class=\"modal-title\">Modal Heading</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal footer -->\n"
                    + "                    <div class=\"modal-footer\">               \n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-danger\"></button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-danger\" data-bs-dismiss=\"modal\">Close</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-primary\">Add</button>\n"
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
                    + "                            <h2 onclick=\"loadCus('ManageCustomerController','')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí khách hàng</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchCus\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchCus\" placeholder=\"Tìm kiếm khách hàng theo tài khoản hoặc tên\" style=\"width: 450px\">\n"
                    + "                                    <input onclick=\"loadCus('ManageCustomerController'," + index + ",document.getElementById(\'searchCus\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Thêm tài khoản nhân viên</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n");
            if (listCust.size() <= 0) {
                out.println("           <div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>Tài khoản</th>\n"
                        + "                                    <th>Họ và tên</th>\n"
                        + "                                    <th>Mật khẩu</th>\n"
                        + "                                    <th>Email</th>\n"
                        + "                                    <th>Số điện thoại</th>\n"
                        + "                                    <th>Địa chỉ</th>\n"
                        + "                                    <th>Điểm</th>\n"
                        + "                                    <th>Trạng thái</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            for (CustomerDTO customerDTO : listCust) {
                out.println("                <tr>\n"
                        + "                    <td>" + customerDTO.getCustomerID() + "</td>\n"
                        + "                    <td>" + customerDTO.getName() + "</td>\n"
                        + "                    <td><input type=\"password\" name=\"password\" style=\"border: none\" readonly=\"\" value=\"" + customerDTO.getPassword() + "\"/></td>\n"
                        + "                    <td>" + customerDTO.getEmail() + "</td>\n"
                        + "                    <td>" + customerDTO.getPhone() + "</td>\n"
                        + "                    <td>" + customerDTO.getAddress() + "</td>\n"
                        + "                    <td>" + customerDTO.getPoint() + "</td>\n");
                status = customerDTO.getStatus();
                if (status.equals("1")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 50px; color: green; border: none; color: green;\" value=\"Online\"/></td>\n");
                } else {
                    out.println("                              <td><input readonly=\"\" style=\"width: 50px; color: red; border: none; color: red;\" value=\"Offline\"/></td>\n");
                }
                delete = customerDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                }
                out.println("                        <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updateCus('ManageCustomerController?index=" + index + "','" + search + "','" + customerDTO.getCustomerID() + "'," + index + ",'load')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                        </td>\n"
                        + "                        \n"
                        + "                </tr>\n");
            }
            out.println("                            </table>\n"
                    + "                          <div class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a id=\"" + j + "\" onclick=\"loadCus('ManageCustomerController?searchCus=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
            }
            out.println("               </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "\n"
                    + "</html>");

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
