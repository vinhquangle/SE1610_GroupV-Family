/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.login;

import dao.CustomerDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
import dto.StaffDTO;
import dto.StaffErrorDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ownhi
 */
public class ViewProfileController extends HttpServlet {
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/viewprofile.jsp";
    private static final String ERROR = "WEB-INF/JSP/HomePage/viewprofile.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        CustomerErrorDTO cusError= new CustomerErrorDTO();
        StaffErrorDTO staffError = new StaffErrorDTO();
        try{
                HttpSession session = request.getSession();
                CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
                StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
                if(cus != null){
                request.setCharacterEncoding("UTF-8");
                CustomerDAO daoCus = new CustomerDAO();
                String customerID = request.getParameter("cusID");
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                int point = Integer.parseInt(request.getParameter("point"));
                String status = request.getParameter("status");
                String delete = request.getParameter("delete");
                
                boolean checkValidation = true;
                if(fullName.length() > 100 || fullName.length() <2 ){
                    checkValidation = false;
                    cusError.setNameError("Họ và tên phải trong khoảng [2,100] ký tự");
                }
                if(fullName.isBlank() || fullName.isEmpty()){
                    checkValidation = false;
                    cusError.setNameError("Họ và tên không được bỏ trống");
                }
                if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
                    checkValidation = false;
                    cusError.setEmailError("Đây không phải là email");
                }
                if(email.isBlank() || email.isEmpty()){
                    checkValidation = false;
                    cusError.setEmailError("Email không được bỏ trống");
                }
                if(!phone.matches(new CustomerDTO().PHONE_FORMAT) ){
                    checkValidation = false;
                    cusError.setPhoneError("Số điện thoại không hợp lệ");
                }
                if(address.length() > 500 || fullName.length() <5 ){
                    checkValidation = false;
                    cusError.setNameError("Họ và tên phải trong khoảng [5,500] ký tự");
                }
                if(address.isBlank() || address.isEmpty()){
                    checkValidation = false;
                    cusError.setAddressError("Họ và tên không được bỏ trống");
                }
                
                if(checkValidation){
                   
                    boolean checkUpdate = daoCus.updateCustomer(new CustomerDTO(customerID, fullName, password, email, address, phone, point, status, delete));
                    if(checkUpdate){
                        if(cus.getCustomerID().equals(customerID)){
                            cus.setCustomerID(customerID);
                            cus.setName(fullName);
                            cus.setEmail(email);
                            cus.setPassword(password);
                            cus.setAddress(address);
                            cus.setPhone(phone);
                            cus.setDelete(delete);
                            cus.setPoint(point);
                            cus.setStatus(status);
                        }
                        url = SUCCESS;
                        session.setAttribute("MESSAGE", "Thay đổi thành công");
                        session.setAttribute("LOGIN_CUS", cus);
                    }
                }else{
                     request.setAttribute("CUSTOMER_ERROR", cusError);
                }
                }
                // STAFF
                
                if(staff != null){
                 request.setCharacterEncoding("UTF-8");
                StaffDAO staffDAO = new StaffDAO();
                String staffID = request.getParameter("staffID");
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String role = request.getParameter("role");
                String password = request.getParameter("password");
                String dateofbirth = request.getParameter("dateofbirth");
                String status = request.getParameter("status");
                String delete = request.getParameter("delete");
                
                boolean checkValidation = true;
                if(fullName.length() > 100 || fullName.length() <2 ){
                    checkValidation = false;
                    staffError.setNameEr("Họ và tên phải trong khoảng [2,100] ký tự");
                }
                if(fullName.isBlank() || fullName.isEmpty()){
                    checkValidation = false;
                    staffError.setNameEr("Họ và tên không được bỏ trống");
                }
                if(!phone.matches(new StaffDTO().PHONE_FORMAT) ){
                    checkValidation = false;
                    staffError.setPhoneEr("Số điện thoại không hợp lệ");
                }
                if(!dateofbirth.matches("^\\d{4}-\\d{2}-\\d{2}$")){
                     checkValidation = false;
                    staffError.setDateOfBirthEr("Ngày-tháng-năm không hợp lệ");
                }
                
                if(checkValidation){
                    boolean checkUpdate = staffDAO.updateStaff(new StaffDTO(staffID, fullName, password, role, phone, dateofbirth, status, delete));
                    if(checkUpdate){
                        if(staff.getStaffID().equals(staffID)){
                            staff.setName(fullName);
                            staff.setPassword(password);
                            staff.setRole(role);
                            staff.setPhone(phone);
                            staff.setDateOfBirth(dateofbirth);
                            staff.setStatus(status);
                            staff.setDelete(delete);
                            staff.setStaffID(staffID);
                        }
                        url = SUCCESS;
                        session.setAttribute("MESSAGE", "Thay đổi thành công");
                        session.setAttribute("LOGIN_STAFF", staff);
                    }
                }else{
                    request.setAttribute("STAFF_ERROR", staffError);
                }
                }
                
                
        }catch(Exception e){
            log("Error at ViewProfileController: " + e.toString());
        }finally{
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
