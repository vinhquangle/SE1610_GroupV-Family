/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.modify;

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

/**
 *
 * @author PCPV
 */
//Quốc Thịnh >>>>>>>>>>
public class ChangePasswordController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/ProfilePage/changePass.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ProfilePage/viewprofile.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean check = true;
        try {
            String action = request.getParameter("action");
            if (action.equals("Change")) {
                String curPass = request.getParameter("current");
                String newPass = request.getParameter("new");
                String confirm = request.getParameter("confirm");
                if (curPass == null || curPass == "") {
                    request.setAttribute("mess1", "Mật khẩu hiện tại không được để trống");
                    check = false;
                }
                if (newPass == null || newPass == "") {
                    request.setAttribute("mess2", "Mật khẩu mới không được để trống");
                    check = false;
                }
                if (confirm == null || confirm == "") {
                    System.out.println("4");
                    request.setAttribute("mess3", "Xác nhận mật khẩu mới không được để trống");
                    check = false;
                }
                if (check) {
                    HttpSession session = request.getSession();
                    CustomerDTO cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
                    if (cus != null) {
                        CustomerDAO cusDao = new CustomerDAO();
                        MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                        String AES_decryptedStr = myCipher.AES_decrypt(cus.getPassword());//Giải mã AES
                        if (!curPass.equals(AES_decryptedStr)) {
                            request.setAttribute("mess1", "Mật khẩu hiện tại không chính xác");
                            check = false;
                        }
                        if (newPass.equals(curPass)) {
                            request.setAttribute("mess2", "Mật khẩu mới bị trùng với mật khẩu hiện tại");
                            check = false;
                        }
                        if (!newPass.equals(confirm)) {
                            request.setAttribute("mess3", "Xác nhận mật khẩu không khớp");
                            check = false;
                        }
                        if (check) {
                            if (newPass.length() < 8) {
                                request.setAttribute("mess2", "Mật khẩu mới phải có độ dài từ 8 kí tự trở lên");
                                throw new Exception();
                            } else {
                                String AES_ecryptedStr = myCipher.AES_encrypt(newPass);//Mã hóa AES
                                newPass = AES_ecryptedStr;
                                if (cusDao.changePassword(newPass, cus.getCustomerID())) {
                                    url = SUCCESS;
                                    request.setAttribute("MESS", "Thay đổi mật khẩu thành công!");
                                } else {
                                    request.setAttribute("MESS", "Thay đổi mật khẩu thất bại!");
                                }
                            }
                        } else {
                            throw new Exception();
                        }
                    } else {
                        StaffDTO staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
                        StaffDAO staffDao = new StaffDAO();
                        MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                        String AES_decryptedStr = myCipher.AES_decrypt(staff.getPassword());//Giải mã AES
                        if (!curPass.equals(AES_decryptedStr)) {
                            request.setAttribute("mess1", "Mật khẩu hiện tại không chính xác");
                            check = false;
                        }
                        if (newPass.equals(curPass)) {
                            request.setAttribute("mess2", "Mật khẩu mới bị trùng với mật khẩu hiện tại");
                            check = false;
                        }
                        if (!newPass.equals(confirm)) {
                            request.setAttribute("mess3", "Xác nhận mật khẩu không khớp");
                            check = false;
                        }
                        if (check) {
                            if (newPass.length() < 8) {
                                request.setAttribute("mess2", "Mật khẩu mới phải có độ dài từ 8 kí tự trở lên");
                                throw new Exception();
                            } else {
                                String AES_ecryptedStr = myCipher.AES_encrypt(newPass);//Mã hóa AES
                                newPass = AES_ecryptedStr;
                                if (staffDao.changePassword(newPass, staff.getStaffID())) {
                                     url = SUCCESS;
                                    request.setAttribute("MESS", "Thay đổi mật khẩu thành công!");
                                } else {
                                    request.setAttribute("MESS", "Thay đổi mật khẩu thất bại!");
                                }
                            }
                        } else {
                            throw new Exception();
                        }
                    }
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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