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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ownhi
 */
public class AddCustomerController extends HttpServlet {
    private static final String ERROR = "customer.jsp";
    private static final String SUCCESS = "customer.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
         CustomerErrorDTO cusError= new CustomerErrorDTO();
        try{
                HttpSession session = request.getSession();
                request.setCharacterEncoding("UTF-8");
                CustomerDAO daoCus = new CustomerDAO();
                String customerID = request.getParameter("cusID");
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String confirm = request.getParameter("confirm");

                boolean checkValidation = true;
                if(customerID.length() > 50 || customerID.length() < 2){
                    checkValidation = false;
                    cusError.setCustomerIDError("Độ dài của tài khoản phải trong khoảng [2,50] ký tự");
                }
                if(customerID.isBlank() || customerID.isEmpty()){
                    checkValidation = false;
                    cusError.setCustomerIDError("Tài khoản không được bỏ trống");
                }
                boolean checkDuplicateID = daoCus.checkCustomerID(customerID);//Kiểm tra trùng lặp CustomerID
                if (checkDuplicateID) {
                    cusError.setCustomerIDError("Tài khoản đã được sử dụng");
                    checkValidation = false;
                }
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
                
                if(!password.equals(confirm)){
                    cusError.setConfirmError("Mật khẩu không khớp!!!");
                    checkValidation= false;
                }
                if(password.isBlank() || password.isEmpty()){
                    cusError.setPasswordError("Mật khẩu không được bỏ trống!");
                }
                MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
                password = AES_ecryptedStr;
                if(checkValidation){
                    boolean checkCreate = daoCus.createAccount(new CustomerDTO(customerID, fullName, password, email, address, phone));
                    if(checkCreate){
                        session.setAttribute("MESSAGE", "Tạo thành công");
                        url = SUCCESS;
                    }
                }else{
                     request.setAttribute("CUS_ERROR", cusError);
                }
        }catch(Exception e){
log("Error at AddCustomerController: " + e.toString());
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
