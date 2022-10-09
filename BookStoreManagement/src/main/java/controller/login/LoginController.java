/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.login;

import aes.MyAES;
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
//Quốc Thịnh >>>>>>>>>>
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
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            CustomerDAO cusDao = new CustomerDAO();
            StaffDAO staffDao = new StaffDAO();
            HttpSession session = request.getSession();
            if (userID == "" || password == "") {
                request.setAttribute("ERROR", "Tài khoản và mật khẩu không được để trống");
                throw new Exception();
            }
            boolean valid = false;
            //Ma hoa AES
            MyAES myCipher = new MyAES(password, MyAES.AES_192);
            String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
            String AES_decryptedStr = myCipher.AES_decrypt(AES_ecryptedStr);//Giải mã AES
            password = AES_ecryptedStr;
            //Ma hoa AES
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);//Kiểm tra respone từ reCaptcha
            if (!valid) {
                request.setAttribute("ERROR", "reCaptcha không hợp lệ!");
                throw new Exception();
            }
            CustomerDTO loginCus = cusDao.checkLogin(userID, password);//Kiểm tra, xác thực tài khoản
            if (loginCus != null && cusDao.updateStatusOnline(userID)) {
                session.setAttribute("LOGIN_CUS", loginCus);
                url = SUCCESS;
            } else {
                StaffDTO loginStaff = staffDao.checkLogin(userID, password);
                if (loginStaff != null && staffDao.updateStatusOnline(userID)) {//Cập nhật trang thái tài khoản
                    session.setAttribute("LOGIN_STAFF", loginStaff);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Tài khoản hoặc mật khẩu không chính xác");
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
//<<<<<<<<<<