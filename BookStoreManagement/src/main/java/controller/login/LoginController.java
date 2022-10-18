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
import email.JavaMailUtil;
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
    private static final String SUCCESS = "GetController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            String email = request.getParameter("email");
            if (email == null) {
                email = "";
            }
            if (action.equals("Login")) {
                throw new Exception();
            } else if (action.equals("Forgot")) {//Tạo form cho "Quên mật khẩu?"
                request.setAttribute("MODAL", " <form style=\"text-align: center;\" action=\"LoginController\" method=\"POST\">\n"
                        + "                         <p><b>Nhập Email đăng kí tài khoản để nhận được thông tin tài khoản/mật khẩu qua email!</b></p>\n"
                        + "                         <input style=\"width: 300px;\" placeholder=\"Nhập Email đăng kí\" type=\"text\" name=\"email\" value=\"" + email + "\">\n"
                        + "                         <input style=\"text-align: center;\" type=\"submit\" value=\"Gửi\">\n"
                        + "                         <input style=\"text-align: center;\" type=\"hidden\" name=\"action\" value=\"Send\">\n"
                        + "                     </form>");
                throw new Exception();
            } else if (action.equals("Send")) {
                CustomerDAO cusDao = new CustomerDAO();
                String modal = new String();
                if (cusDao.checkCustomerEmail(email)) {//Thực thi gửi email và thông báo email
                    modal = "<form style=\"text-align: center;\" action=\"LoginController\" method=\"POST\">\n"
                            + "                         <p><b>Nhập Email đăng kí tài khoản để nhận được thông tin tài khoản/mật khẩu qua email!</b></p>\n"
                            + "                         <input style=\"width: 300px;\" placeholder=\"Nhập Email đăng kí\" type=\"text\" name=\"email\" value=\"" + email + "\">\n"
                            + "                         <input style=\"text-align: center;\" type=\"submit\" value=\"Gửi\">\n"
                            + "                         <input style=\"text-align: center;\" type=\"hidden\" name=\"action\" value=\"Send\">\n"
                            + "                         <p style=\"text-align: center; color: green; margin-top: 10px;\"><b>Gửi qua Email thành công!</b></p>\n"
                            + "                     </form>";
                    JavaMailUtil.sendMail(email, 0, action);//Gửi thông tin qua email
                } else {
                    modal = "<form style=\"text-align: center;\" action=\"LoginController\" method=\"POST\">\n"
                            + "                         <p><b>Nhập Email đăng kí tài khoản để nhận được thông tin tài khoản/mật khẩu qua email!</b></p>\n"
                            + "                         <input style=\"width: 300px;\" placeholder=\"Nhập Email đăng kí\" type=\"text\" name=\"email\" value=\"" + email + "\">\n"
                            + "                         <input style=\"text-align: center;\" type=\"submit\" value=\"Gửi lại\">\n"
                            + "                         <input style=\"text-align: center;\" type=\"hidden\" name=\"action\" value=\"Send\">\n"
                            + "                         <p style=\"text-align: center; color: red; margin-top: 10px;\"><b>Email chưa được đăng kí tài khoản</b></p>\n"
                            + "                     </form>";
                }
                request.setAttribute("MODAL", modal);
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
            MyAES myCipher = new MyAES("1", MyAES.AES_192);
            String AES_ecryptedStr = myCipher.AES_encrypt(password);//Mã hóa AES
            password = AES_ecryptedStr;
            //Ma hoa AES
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);//Kiểm tra respone từ reCaptcha
            if (!valid) {
                request.setAttribute("ERROR", "reCaptcha không hợp lệ!");
                throw new Exception();
            }
            CustomerDTO loginCus = cusDao.checkLogin(userID, password);//Kiểm tra, xác thực tài khoản
            if (loginCus != null) {
                if (loginCus.getDelete().equals("0")) {
                    if (cusDao.updateStatusOnline(userID)) {
                        loginCus.setStatus("1");
                        session.setAttribute("LOGIN_CUS", loginCus);
                        url = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", "Tài khoản của bạn đã bị khóa");
                    throw new Exception();
                }
            } else {
                request.setAttribute("ERROR", "Tài khoản hoặc mật khẩu không chính xác");
                StaffDTO loginStaff = staffDao.checkLogin(userID, password);
                if (loginStaff != null) {
                    if (loginStaff.getDelete().equals("0")) {
                        if (staffDao.updateStatusOnline(userID)) {
                            loginStaff.setStatus("1");
                            session.setAttribute("LOGIN_STAFF", loginStaff);
                            url = SUCCESS;
                        }
                    } else {
                        request.setAttribute("ERROR", "Tài khoản của bạn đã bị khóa");
                        throw new Exception();
                    }
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
