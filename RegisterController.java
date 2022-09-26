/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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

/**
 *
 * @author Admin
 */
public class RegisterController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/LoginPage/register.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        
        try {
            String action = request.getParameter("action");
            if (action.equals("Register")) {
                throw new Exception();
                }
                CustomerDAO dao = new CustomerDAO();
                CustomerErrorDTO cusError = new CustomerErrorDTO();

                String email = request.getParameter("email");
                String customerID = request.getParameter("customerID");
                String fullName = request.getParameter("fullName");
                String password = request.getParameter("password");
                String confirm = request.getParameter("confirm");
                boolean checkValidation = true;
                if (customerID.length() > 10 || customerID.length() < 1) {
                    cusError.setCustomerIDError("Length customerID must be [2;50]");
                    checkValidation = false;
                }

                //check duplicate Email
                boolean checkDuplicateEmail = dao.checkCustomerEmail(email);
                if (checkDuplicateEmail) {
                    cusError.setEmailError("Duplicated Email");
                    checkValidation=false;
                }

                //check duplicate ID
                boolean checkDuplicateID = dao.checkCustomerID(customerID);
                if (checkDuplicateID) {
                    cusError.setCustomerIDError("Duplicated ID");
                    checkValidation=false;
                }

                //check fullName length
                if (fullName.length() > 50 || fullName.length() < 1) {

                    cusError.setNameError("FullName must be [2;50]");
                    checkValidation = false;
                }
                //check pass-confirm
                if (!password.equals(confirm)) {
                    cusError.setConfirmError("Confirm password are NOT the same as Password");
                    checkValidation = false;
                }
                // nếu checkValidation=true nghĩa là tất cả thông tin hợp lệ thì sẽ insert vào database
                if (checkValidation) {
                    boolean checkInsert = dao.register(new CustomerDTO(customerID, fullName, password, email, "", "", 0, true, false));
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
