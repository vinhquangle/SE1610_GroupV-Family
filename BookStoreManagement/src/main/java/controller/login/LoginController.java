/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.login;

import dao.CustomerDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.StaffDTO;
import java.io.IOException;
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
public class LoginController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/LoginPage/login.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals("Login")) {
                throw new Exception();
            }
            String role = request.getParameter("role");
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            CustomerDAO cusDao = new CustomerDAO();
            StaffDAO staffDao = new StaffDAO();
            HttpSession session = request.getSession();
            boolean valid = false;
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);
            if (!valid) {
                request.setAttribute("ERROR", "Captcha Invalid!");
                throw new Exception();
            }
            if (role.equals("0")) {
                CustomerDTO loginUser = cusDao.checkLogin(userID, password);
                if (loginUser != null && cusDao.updateStatusOnline(userID)) {
                    session.setAttribute("LOGIN_CUS", loginUser);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Incorrect Account Or Password ");
                }
            } else if (role.equals("1")) {
                StaffDTO loginUser = staffDao.checkLogin(userID, password);
                if (loginUser != null && staffDao.updateStatusOnline(userID)) {
                    session.setAttribute("LOGIN_STAFF", loginUser);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Incorrect Account Or Password ");
                }
            }
        } catch (Exception e) {
            log("ERROR AT GETCONTROLLER : " + e.toString());
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
