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
 * @author Admin
 */
//Quốc Phi, Quốc Thịnh >>>>>>>>>>
public class ManageCustomerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html class=\"no-js\" lang=\"en\">\n"
                + "\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                + "        <title>Customer</title>\n"
                + "        <meta name=\"description\" content=\"\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <!-- The Modal -->\n"
                + "        <div class=\"modal\" id=\"myModal\">\n"
                + "            <div class=\"modal-dialog\">\n"
                + "                <div class=\"modal-content\">\n"
                + "                    <!-- Modal Header -->\n"
                + "                    <div class=\"modal-header\">\n"
                + "                        <h4 class=\"modal-title\">Modal Heading</h4>\n"
                + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                + "                    </div>\n"
                + "                    <!-- Modal body -->\n"
                + "                    <div class=\"modal-body\">\n"
                + "\n"
                + "                    </div>\n"
                + "                    <!-- Modal footer -->\n"
                + "                    <div class=\"modal-footer\">               \n"
                + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-danger\"></button>\n"
                + "                        <button type=\"button\" class=\"btn btn-danger\" data-bs-dismiss=\"modal\">Close</button>\n"
                + "                        <button type=\"button\" class=\"btn btn-primary\">Add</button>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <!-- The Modal -->\n"
                + "        <div class=\"product-status mg-tb-15\" style=\"margin-top: 50px; height: 405px;\">\n"
                + "            <div class=\"container-fluid\">\n"
                + "                <div class=\"row\">\n"
                + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                + "                        <div class=\"product-status-wrap\">\n"
                + "                            <h4 style=\"display: inline-block;\">Manage Customer Account</h4>\n"
                + "<div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                + "                                    <b>Add new customer account</b>\n"
                + "                                </button>\n"
                + "                            </div>"
                + "                            <table>\n"
                + "                                <tr>\n"
                + "                                    <th>customerID</th>\n"
                + "                                    <th>Name</th>\n"
                + "                                    <th>Password</th>\n"
                + "                                    <th>Email</th>\n"
                + "                                    <th>Phone</th>\n"
                + "                                    <th>Address</th>\n"
                + "                                    <th>Point</th>\n"
                + "                                    <th>Status</th>\n"
                + "                                    <th>Setting</th>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td>123</td>                                   \n"
                + "                                    <td>Jewelery Title 1</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>C1</td>\n"
                + "                                    <td>100</td>\n"
                + "                                    <td>$15</td>\n"
                + "                                    <td>\n"
                + "                                        <button class=\"pd-setting\">Online</button>\n"
                + "                                    </td>\n"
                + "                                    <td>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td>123</td>                                   \n"
                + "                                    <td>Jewelery Title 1</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>C1</td>\n"
                + "                                    <td>100</td>\n"
                + "                                    <td>$15</td>\n"
                + "                                    <td>\n"
                + "                                        <button class=\"pd-setting\">Offline</button>\n"
                + "                                    </td>\n"
                + "                                    <td>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td>123</td>                                   \n"
                + "                                    <td>Jewelery Title 1</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>Name</td>\n"
                + "                                    <td>C1</td>\n"
                + "                                    <td>100</td>\n"
                + "                                    <td>$15</td>\n"
                + "                                    <td>\n"
                + "                                        <button class=\"pd-setting\">Online</button>\n"
                + "                                    </td>\n"
                + "                                    <td>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                        <button data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "\n"
                + "                            </table>\n"
                + "                            <div class=\"custom-pagination\">\n"
                + "                                <nav aria-label=\"Page navigation example\">\n"
                + "                                    <ul class=\"pagination\">\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Previous</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">1</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">2</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">3</a></li>\n"
                + "                                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Next</a></li>\n"
                + "                                    </ul>\n"
                + "                                </nav>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "\n"
                + "</html>");
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