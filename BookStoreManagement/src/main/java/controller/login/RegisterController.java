/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.login;

import dao.CustomerDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.CustomerErrorDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import recaptcha.VerifyUtils;

/**
 *
 * @author Admin
 */
public class RegisterController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/LoginPage/register.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/LoginPage/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals("Register")) {
                throw new Exception();
            }
            CustomerDAO daoCus = new CustomerDAO();
            StaffDAO daoStaff = new StaffDAO();
            CustomerErrorDTO cusError = new CustomerErrorDTO();
            String email = request.getParameter("email");
            String customerID = request.getParameter("customerID");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            boolean checkValidation = true;
            boolean valid = false;
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);
            if (!valid) {
                request.setAttribute("ERROR", "Captcha Invalid!");
                throw new Exception();
            }
            if (customerID.length() > 50 || customerID.length() < 2) {
                cusError.setCustomerIDError("Length account must be 2 to 50 characters");
                checkValidation = false;
            }
            //check duplicate Email
            boolean checkDuplicateEmail = daoCus.checkCustomerEmail(email);
            if (checkDuplicateEmail) {
                cusError.setEmailError("Email already in use");
                checkValidation = false;
            }
            //check duplicateID
            boolean checkDuplicateID = daoCus.checkCustomerID(customerID);//check duplicate ID in tblCustomer
            boolean ScheckDuplicateID = daoStaff.checkCusIDinStaff(customerID);//check duplicate ID in tblStaff
            if (checkDuplicateID == true || ScheckDuplicateID == true) {
                cusError.setCustomerIDError("Account already in use");
                checkValidation = false;
            }
            //check fullName length
            if (fullName.length() > 50 || fullName.length() < 2) {

                cusError.setNameError("FullName must be 2 to 50 characters");
                checkValidation = false;
            }
            //check pass-confirm
            if (!password.equals(confirm)) {
                cusError.setConfirmError("Passwords do not match!");
                checkValidation = false;
            }
            // nếu checkValidation=true nghĩa là tất cả thông tin hợp lệ thì sẽ insert vào database
            if (checkValidation) {
                boolean checkInsert = daoCus.createAccount(new CustomerDTO(customerID, fullName, password, email, address, phone, 0, "0", "0"));
                if (checkInsert) {
                    url = SUCCESS;
                }
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
