/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.login;

import aes.MyAES;
import dao.CustomerDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
import email.JavaMailUtil;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import recaptcha.VerifyUtils;

/**
 *
 * @author Admin
 */
//Ngọc Thy >>>>>>>>>>
public class RegisterController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/LoginPage/register.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/LoginPage/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            CustomerErrorDTO cusError = new CustomerErrorDTO();
            CustomerDAO daoCus = new CustomerDAO();
            CustomerDTO cus = new CustomerDTO();
            String codeVerify = request.getParameter("verify");
            if (action.equals("Register")) {
                throw new Exception();
            }
            System.out.println(codeVerify);
            if (codeVerify != null) {
                System.out.println(codeVerify);
                String code = (String) session.getAttribute("CODE");
                if (code.equals(codeVerify)) {
                    cus = (CustomerDTO) session.getAttribute("CUS");
                    boolean checkInsert = daoCus.createAccount(cus);//Tạo Account và insert dữ liệu vào tblCustomer
                    if (checkInsert) {
                        url = SUCCESS;
                        request.setAttribute("MODAL", "<div class=\"row\">"
                                + "                         <div style=\"text-align: center\" class=\"col-md-12\">\n"
                                + "                             <p style=\"color: green;\"><b>Đăng kí tài khoản thành công</b></p>\n"
                                + "                         </div>\n"
                                + "                     </div>\n"
                                + "                     <div class=\"row\">\n"
                                + "                         <div class=\"col-md-12\">\n"
                                + "                                <div style=\"text-align: center;\" class=\"product-preview\">\n"
                                + "                                    <img style=\"height: 100px;\" src=\"IMG/success.png\"/>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                            </div>");
                    }
                    throw new Exception();
                } else {
                    request.setAttribute("MODAL", "Mã xác minh địa chỉ Email không chính xác. Vui lòng nhập lại!");
                    throw new Exception();
                }
            }
            String email = request.getParameter("email");
            String regex = "^(.+)@(.+)$";//Mẫu xác thực email
            Pattern pattern = Pattern.compile(regex);//Mẫu xác thực email
            Matcher matcher = pattern.matcher(email);//Mẫu xác thực email
            Random rand = new Random();
            if (!matcher.matches()) {//Kiểm tra email hợp lệ
                cusError.setEmailError("Email không hợp lệ!");
            }
            StaffDAO daoStaff = new StaffDAO();
            String customerID = request.getParameter("customerID");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("passwordr");
            String confirm = request.getParameter("confirm");

            boolean checkValidation = true;
            boolean valid = false;
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);//Xác thực reCaptcha
            if (!valid) {
                request.setAttribute("ERROR", "reCaptcha không hợp lệ!");
                throw new Exception();
            }
            if (email == "") {
                cusError.setEmailError("Email không được để trống");
                checkValidation = false;
            }

            if (customerID.length() > 50 || customerID.length() < 2) {
                cusError.setCustomerIDError("Độ dài tài khoản phải từ 2-50 kí tự!");
                checkValidation = false;
                if (customerID == "") {
                    cusError.setCustomerIDError("Tài khoản không được để trống!");
                }
            }
            boolean checkDuplicateEmail = daoCus.checkCustomerEmail(email);//Kiểm tra trùng lặp Email
            if (checkDuplicateEmail) {
                cusError.setEmailError("Email này đã được sử dụng!");
                checkValidation = false;
            }
            try {
                Integer.parseInt(phone);
            } catch (Exception e) {
                cusError.setPhoneError("Số điện thoại không hợp lệ!");
            }
            if (phone.length() > 15 || phone.length() < 8) {
                cusError.setPhoneError("Số điện thoại không hợp lệ!");
                checkValidation = false;
                if (phone == "") {
                    cusError.setPhoneError("Số điện thoại không được để trống!");
                }
            }
            if (address.length() > 200) {
                cusError.setAddressError("Địa chỉ quá dài (bé hơn 200 kí tự)!");
                checkValidation = false;
                if (address == "") {
                    cusError.setAddressError("Địa chỉ không được để trống!");
                }
            }
            boolean checkDuplicateID = daoCus.checkCustomerID(customerID);//Kiểm tra trùng lặp CustomerID
            boolean ScheckDuplicateID = daoStaff.checkCusIDinStaff(customerID);//Kiểm tra trùng lặp StaffID
            if (checkDuplicateID == true || ScheckDuplicateID == true) {
                cusError.setCustomerIDError("Tài khoản đã được sử dụng");
                checkValidation = false;
            }
            if (fullName.length() > 50 || fullName.length() < 2) {
                cusError.setNameError("Độ dài họ và tên phải từ 2-50 kí tự!");
                checkValidation = false;
                if (fullName == "") {
                    cusError.setNameError("Họ và tên không được để trống!");
                }
            }
            if (password.length() < 8) {
                cusError.setPasswordError("Mật khẩu nên dài từ 8 kí tự trở lên");
                checkValidation = false;
                if (password == "") {
                    cusError.setPasswordError("Mật khẩu không được để trống");
                }
            }
            if (!password.equals(confirm)) {
                cusError.setConfirmError("Xác nhận mật khẩu không khớp!");
                checkValidation = false;
            }
            //Ma hoa AES
            MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
            String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
            password = AES_ecryptedStr;
            //Ma hoa AES
            if (checkValidation) {
                cus = new CustomerDTO(customerID, fullName, password, email, address, phone, 0, "0", "0");
                session.setAttribute("CUS", cus);
                request.setAttribute("MODAL", "Mã xác minh đã được gửi qua địa chỉ Email của bạn");
                int codeVerify1 = rand.ints(1000, 9999).findFirst().getAsInt();//Tạo mã xác minh cho email
                JavaMailUtil.sendMail(email, codeVerify1, "Verify");//Gữi mã xác minh email
                session.setAttribute("CODE", String.valueOf(codeVerify1));
            } else {
                request.setAttribute("CUSTOMER_ERROR", cusError);
            }
        } catch (Exception e) {
            log("ERROR AT REGISTERCONTROLLER : " + e.toString());
        } finally {
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
//<<<<<<<<<<
