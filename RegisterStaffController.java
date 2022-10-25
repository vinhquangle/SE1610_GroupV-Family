/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import aes.MyAES;
import dao.StaffDAO;
import dto.StaffDTO;
import dto.StaffErrorDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vungo
 */
//Locate at controller.login
public class RegisterStaffController extends HttpServlet {

    private static final String ERROR = " locate ";//createStaff.jsp
    private static final String SUCCESS = "WEB-INF/JSP/LoginPage/login.jsp";//login.jsp

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        String msg = "Đăng kí thất bại";
        String action = request.getParameter("action");
        try {
            if ("Register".equals(action)) {
                String staffID = request.getParameter("staffID");
                String name = request.getParameter("name");
                String role = request.getParameter("role");// khong can thiet check vì là select
                String phone = request.getParameter("phone");
                String dateOfBirth = request.getParameter("dateOfBirth");
                String password = request.getParameter("password");
                String confirm = request.getParameter("confirm");
                boolean checkValidation = true;

                StaffErrorDTO staffError = new StaffErrorDTO();
                StaffDAO staffDao = new StaffDAO();
                //check validation staffID
                if (staffID.length() < 0 && staffID.length() > 10) {
                    staffError.setStaffIDError("Độ dài tài khoản phải từ 1 đến 10 kí tự.");
                    checkValidation = false;

                } else if (staffID == "") {
                    staffError.setStaffIDError("ID không được để trống.");
                    checkValidation = false;
                }
                //check duplicate cho staffID
                boolean checkDupStaffID = staffDao.checkStaffID(staffID);
                if (staffDao.checkStaffID(staffID)) {
                    staffError.setStaffIDError("ID đã tồn tại.");
                    checkValidation = false;
                }

                //check validation cho name 
                if (name.length() < 1 && name.length() > 50) {
                    staffError.setNameError("Độ dài tên phải từ 2 đến 50 kí tự.");
                    checkValidation = false;

                } else if (name == "") {
                    staffError.setNameError("Tên không được để trống.");
                    checkValidation = false;
                }
                if (role.equals("Nhân Viên")) {
                    role = "Staff";
                } else {
                    role = "Deliverer";
                }

                //check validation cho phone
                try {
                    Integer.parseInt(phone);
                } catch (Exception e) {
                    staffError.setPhoneError("Số điện thoại không hợp lệ!");
                }
                if (phone.length() > 15 || phone.length() < 8) {
                    staffError.setPhoneError("Số điện thoại không hợp lệ!");
                    checkValidation = false;

                } else if (phone == "") {
                    staffError.setPhoneError("Số điện thoại không được để trống!");
                    checkValidation = false;
                }
                //check date of birth
                LocalDate birthDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
                LocalDate localDate = LocalDate.now(); // lấy ngày hiện tại so với birthDate
                if (birthDate.isAfter(localDate)) {
                    staffError.setDateOfBirthError("Ngày sinh không được lớn hơn hôm nay.");
                    checkValidation = false;
                }
                // check số lượng kí tự trong password
                if (password.length() < 8) {
                    staffError.setPasswordError("Mật khẩu nên dài từ 8 kí tự trở lên");
                    checkValidation = false;
                } else if (password == "") {
                    staffError.setPasswordError("Mật khẩu không được để trống");
                    checkValidation = false;
                }
                //check pass-confirm
                if (!password.equals(confirm)) {
                    staffError.setConfirmError("Xác nhận mật khẩu không khớp!");
                    checkValidation = false;
                }

// nếu checkValidation=true nghĩa là tất cả thông tin hợp lệ thì sẽ insert vào database
                //Ma hoa AES
                MyAES myCipher = new MyAES(password, MyAES.AES_192);
                String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
                password = AES_ecryptedStr;
                //Ma hoa AES
                if (checkValidation) {
                    boolean checkInsert = staffDao.createStaff(new StaffDTO(staffID, name, password, role, phone, dateOfBirth, "0", "0"));
                    if (checkInsert) {

                        msg = "Đăng kí thành công";
                        request.setAttribute("REGISTER_STAFF", msg);

                    }
                    url = SUCCESS;
                } else {
                    request.setAttribute("STAFF_ERROR", staffError);
                    request.setAttribute("REGISTER_STAFF", msg);
                }

            }

        } catch (Exception e) {
            log("Error at RegisterStaffDelivery" + e.toString());
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
