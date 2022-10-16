/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.login;

import dao.CustomerDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
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
public class ViewProfileCusController extends HttpServlet {
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/viewprofile.jsp";
    private static final String ERROR = "WEB-INF/JSP/HomePage/viewprofile.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        CustomerErrorDTO cusError= new CustomerErrorDTO();
        try{
                HttpSession session = request.getSession();
                CustomerDAO daoCus = new CustomerDAO();
                String customerID = request.getParameter("cusID");
                String fullName = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String status = request.getParameter("status");
                int point = Integer.parseInt(request.getParameter("point"));
                String delete = request.getParameter("delete");
                
                CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_USER");
                    
                boolean checkValidation = true;
                if (email == "") {
                    cusError.setEmailError("Email không được để trống");
                    checkValidation = false;
                if (customerID.length() > 50 || customerID.length() < 2) {
                    cusError.setCustomerIDError("Độ dài tài khoản phải từ 2-50 kí tự!");
                    checkValidation = false;
                    if (customerID == "") {
                        cusError.setCustomerIDError("Tài khoản không được để trống!");
                    }
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
                if (checkDuplicateID == true) {
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
                
                if(checkValidation){
                    boolean checkUpdate = daoCus.updateCustomer(new CustomerDTO(customerID, email, password, email, address, phone, point, status, delete));
                    if(checkUpdate){
                        if(cus.getCustomerID().equals(customerID)){
                            cus.setCustomerID(customerID);
                            cus.setName(fullName);
                            cus.setAddress(address);
                            cus.setEmail(email);
                            cus.setPhone(phone);
                            cus.setPassword(password);
                            cus.setPoint(point);
                            cus.setDelete(delete);
                            cus.setStatus(status);
                        }
                    }
                    session.setAttribute("MESSAGE", "Chỉnh sửa thành công");
                    url= SUCCESS;
                }
                }
        }catch(Exception e){
            
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
