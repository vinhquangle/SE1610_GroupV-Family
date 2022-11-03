/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
import dto.OrderDTO;
import dto.StaffDTO;
import dto.StaffErrorDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PCPV
 */
//Hữu Hiếu, Quốc Thịnh >>>>>>>>>>
public class ViewProfileController extends HttpServlet {

    private static final String SUCCESS = "WEB-INF/JSP/ProfilePage/viewprofile.jsp";
    private static final String ERROR = "WEB-INF/JSP/ProfilePage/viewprofile.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        CustomerErrorDTO cusError = new CustomerErrorDTO();
        StaffErrorDTO staffError = new StaffErrorDTO();
        try {
            HttpSession session = request.getSession();
            CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
            StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            if (cus != null) {
                request.setCharacterEncoding("UTF-8");
                OrderDAO orderDao = new OrderDAO();
                List<OrderDTO> listOrder = orderDao.loadOrderByCusID(cus.getCustomerID());
                request.setAttribute("ORDER", listOrder);
                CustomerDAO daoCus = new CustomerDAO();
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                boolean checkValidation = true;
                if (fullName.length() > 100 || fullName.length() < 2) {
                    checkValidation = false;
                    cusError.setNameError("Họ và tên phải trong khoảng [2,100] ký tự");
                }
                if (fullName.isBlank() || fullName.isEmpty()) {
                    checkValidation = false;
                    cusError.setNameError("Họ và tên không được bỏ trống");
                }
                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                    checkValidation = false;
                    cusError.setEmailError("Email không hợp lệ");
                }
                if (email.isBlank() || email.isEmpty()) {
                    checkValidation = false;
                    cusError.setEmailError("Email không được bỏ trống");
                }
                if (phone.isBlank() || phone.isEmpty()) {
                    checkValidation = false;
                    cusError.setPhoneError("Số điện thoại không được để trống");
                } else if (!phone.matches(new CustomerDTO().PHONE_FORMAT)) {
                    checkValidation = false;
                    cusError.setPhoneError("Số điện thoại không hợp lệ");
                }else if(daoCus.checkCustomerPhone(phone)){
                    checkValidation = false;
                    cusError.setPhoneError("Số điện thoại đã được sử dụng");
                }
                if (address.length() > 500 || address.length() < 5) {
                    checkValidation = false;
                    cusError.setAddressError("Địa chỉ phải trong khoảng [5,500] ký tự");
                }
                if (address.isBlank() || address.isEmpty()) {
                    checkValidation = false;
                    cusError.setAddressError("Địa chỉ không được bỏ trống");
                }
                if (checkValidation) {
                    cus.setName(fullName);
                    cus.setEmail(email);
                    cus.setAddress(address);
                    cus.setPhone(phone);
                    if (daoCus.updateCustomer(cus)) {
                        url = SUCCESS;
                        request.setAttribute("MESSAGE", "Thay đổi thành công");
                        session.setAttribute("LOGIN_CUS", cus);
                    }
                } else {
                    cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
                    request.setAttribute("CUSTOMER_ERROR", cusError);
                }
            }
            // STAFF

            if (staff != null) {
                request.setCharacterEncoding("UTF-8");
                StaffDAO staffDAO = new StaffDAO();
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String dateofbirth = request.getParameter("dob");
                LocalDate localDate = LocalDate.now();
                LocalDate birthDate = LocalDate.now();
                boolean checkValidation = true;
                if (fullName.length() > 100 || fullName.length() < 2) {
                    checkValidation = false;
                    staffError.setNameError("Họ và tên phải trong khoảng [2,100] ký tự");
                }
                if (fullName.isBlank() || fullName.isEmpty()) {
                    checkValidation = false;
                    staffError.setNameError("Họ và tên không được bỏ trống");
                }
                if (phone.isBlank() || phone.isEmpty()) {
                    checkValidation = false;
                    staffError.setPhoneError("Số điện thoại không được để trống");
                } else if (!phone.matches(new StaffDTO().PHONE_FORMAT)) {
                    checkValidation = false;
                    staffError.setPhoneError("Số điện thoại không hợp lệ");
                }
                try {
                    birthDate = LocalDate.parse(dateofbirth, DateTimeFormatter.ISO_DATE);
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
                if (checkValidation) {
                    staff.setName(fullName);
                    staff.setPhone(phone);
                    staff.setDateOfBirth(dateofbirth);
                    boolean checkUpdate = staffDAO.updateStaff(staff);
                    if (checkUpdate) {
                        url = SUCCESS;
                        request.setAttribute("MESSAGE", "Thay đổi thành công");
                        session.setAttribute("LOGIN_STAFF", staff);
                    }
                } else {
                    request.setAttribute("STAFF_ERROR", staffError);
                }
            }

        } catch (Exception e) {
            log("Error at ViewProfileController: " + e.toString());
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
