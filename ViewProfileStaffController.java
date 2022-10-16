/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ownhi
 */
public class ViewProfileStaffController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>\n" +
"<head>\n" +
"    <title>Profile Customer Page</title>\n" +
"    <meta charset=\"utf-8\">\n" +
"\n" +
"</head>\n" +
"\n" +
"<body>\n" +
"    \n" +
"                                        <div class=\"container bootstrap snippet\" style=\"background-color: white;\" >\n" +
"                                            <div>            \n" +
"                                                <div class=\"tab-content\">\n" +
"                                                    <div class=\"tab-pane active\" id=\"home\">\n" +
"\n" +
"                                                        <form class=\"form\" action=\"##\" method=\"post\" id=\"registrationForm\">\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"first_name\"><h4>Tên tài khoản</h4></label>\n" +
"                                                                    <input type=\"text\" class=\"form-control\" name=\"first_name\" id=\"first_name\" value=\"<%= cus.getCustomerID()%>\" title=\"enter your first name if any.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"last_name\"><h4>Họ và Tên</h4></label>\n" +
"                                                                    <input type=\"text\" class=\"form-control\" name=\"last_name\" id=\"last_name\" value=\"<%= cus.getName()%>\" title=\"enter your last name if any.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"phone\"><h4>Số điện thoại</h4></label>\n" +
"                                                                    <input type=\"text\" class=\"form-control\" name=\"phone\" id=\"phone\" value=\"<%= cus.getPhone()%>\" title=\"enter your phone number if any.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"\n" +
"                                                            <div class=\"form-group\">\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"mobile\"><h4>Địa Chỉ</h4></label>\n" +
"                                                                    <input type=\"text\" class=\"form-control\" name=\"mobile\" id=\"mobile\"value=\"<%= cus.getAddress()%>\" title=\"enter your mobile number if any.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"email\"><h4>Email</h4></label>\n" +
"                                                                    <input type=\"email\" class=\"form-control\" name=\"email\" id=\"email\" value=\"<%= cus.getEmail()%>\" title=\"enter your email.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"email\"><h4>Point</h4></label>\n" +
"                                                                    <input type=\"email\" class=\"form-control\" id=\"location\" value=\"<%= cus.getPoint()%>\" title=\"enter a location\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                            <div class=\"form-group\">\n" +
"\n" +
"                                                                <div class=\"col-xs-6\">\n" +
"                                                                    <label for=\"password\"><h4>Mật khẩu</h4></label>\n" +
"                                                                    <input type=\"password\" class=\"form-control\" name=\"password\" id=\"password\" value=\"<%= cus.getPassword()%>\" title=\"enter your password.\">\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                            <div class=\"form-group\">\n" +
"                                                                <div class=\"col-xs-12\">\n" +
"                                                                    <br>\n" +
"                                                                    <button class=\"btn btn-lg btn-success\" type=\"submit\"><i class=\"fa fa-check-circle\" aria-hidden=\"true\"></i>Lưu thay đổi</button>\n" +
"                                                                    <button class=\"btn btn-lg\" type=\"reset\"><i class=\"fa fa-refresh\" aria-hidden=\"true\"></i> Làm mới</button>\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"                                                        </form>\n" +
"\n" +
"                                                        <hr>\n" +
"\n" +
"                                                    </div><!--/tab-pane-->\n" +
"                                                </div><!--/tab-pane-->\n" +
"                                            </div><!--/tab-content-->\n" +
"                                        </div> \n" +
"                                        </div>                        \n" +
"                                    <!-- /content -->\n" +
"                                    </body>\n" +
"                                    </html>");
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
