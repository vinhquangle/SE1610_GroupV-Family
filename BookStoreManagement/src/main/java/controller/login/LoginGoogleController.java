/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.login;

import aes.MyAES;
import dao.CustomerDAO;
import dto.CustomerDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logingoogle.GooglePojo;
import logingoogle.GoogleUtils;

/**
 *
 * @author Admin
 */
//Quốc Thịnh >>>>>>>>>>
public class LoginGoogleController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginGoogleController() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = request.getRequestDispatcher("WEB-INF/JSP/LoginPage/login.jsp");
            dis.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            String userId = googlePojo.getId();
            String fullname = googlePojo.getEmail();
            String email = googlePojo.getEmail();
            String phone = new String();
            String address = new String();
            String password = googlePojo.getId();
            CustomerDAO dao = new CustomerDAO();
            MyAES myCipher = new MyAES("1", MyAES.AES_192);
            String AES_ecryptedStr = myCipher.AES_encrypt(password);
            CustomerDTO cus = new CustomerDTO(userId, fullname, AES_ecryptedStr, email, address, phone, 0, "1", "0");
            try {
                if (dao.checkCustomerEmail(email)) {//Kiểm tra id và email trùng lặp 
                    if (!dao.checkCustomerID(userId)) {
                        request.setAttribute("ERROR", "Email already in use");
                        RequestDispatcher dis = request.getRequestDispatcher("WEB-INF/JSP/LoginPage/login.jsp");
                        dis.forward(request, response);
                    } else if (dao.checkCustomerID(userId)) {
                        if (dao.updateStatusOnline(userId)) {
                            cus.setStatus("1");
                        }
                        cus = dao.checkLogin(userId, AES_ecryptedStr);//Kiểm tra, xác thực tài khoản
                    }
                } else if (!dao.createAccount(cus)) {
                    if (dao.updateStatusOnline(userId)) {
                        cus.setStatus("1");
                    }
                    cus = dao.checkLogin(userId, AES_ecryptedStr);//Kiểm tra, xác thực tài khoản
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginGoogleController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoginGoogleController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(LoginGoogleController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_CUS", cus);
                RequestDispatcher dis = request.getRequestDispatcher("GetController");
                dis.forward(request, response);
            }
        }
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
