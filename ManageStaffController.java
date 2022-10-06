/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.CustomerDAO;
import dao.StaffDAO;
import dto.CustomerDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ownhi
 */
@WebServlet(name = "ManageStaffController", urlPatterns = {"/ManageStaffController"})
public class ManageStaffController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       try {
            StaffDAO staffDao = new StaffDAO();
            List<StaffDTO> listStaff = staffDao.getlistStaff();
            if (listStaff.size() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("LIST_STAFF", listStaff);
            }
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "        <link rel = \"icon\" href =\"https://cdn-icons-png.flaticon.com/512/1903/1903162.png\" type = \"image/x-icon\">\n"
                    + "        <title>Manage Page</title>         \n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "                <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Add a new staff/deliverer account</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                        <form>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>CustomerID</label>\n"
                    + "                                <input name=\"isbn\" type=\"text\" class=\"form-control\" required>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Name</label>\n"
                    + "                                <input name=\"name\" type=\"text\" class=\"form-control\" required>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Password</label>\n"
                    + "                                <select name=\"publisher\" class=\"form-select\" aria-label=\"Default select example\">\n"
                    + "\n"
                    + "                                    <option value=\"\"></option>\n"
                    + "\n"
                    + "                                </select>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Email</label>\n"
                    + "                                <select name=\"category\" class=\"form-select\" aria-label=\"Default select example\">\n"
                    + "                                    <option value=\"\"></option>\n"
                    + "                                </select>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Phone</label>\n"
                    + "                                <textarea name=\"author-name\" class=\"form-control\" required></textarea>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Address</label>\n"
                    + "                                <input name=\"price\" type=\"number\" class=\"form-control\" required>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Point</label>\n"
                    + "                                <input name=\"image\" type=\"text\" class=\"form-control\" required>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Status</label>\n"
                    + "                                <textarea name=\"quantity\" class=\"form-control\" required></textarea>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "\n"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <input type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" value=\"Cancel\">\n"
                    + "                                <input type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Add\">\n"
                    + "                            </div>\n"
                    + "                        </form>      "
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
                    + "                            <h4>Manage Customer Account</h4>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                                <button style=\"border: none; color: #edeef1; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Add new staff/deliverer</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n"
                    + "                             <table>\n"
                    + "                                <tr>\n"
                    + "                                    <th>staffID</th>\n"
                    + "                                    <th>Password</th>\n"
                    + "                                    <th>Name</th>\n"
                    + "                                    <th>Role</th>\n"
                    + "                                    <th>Phone</th>\n"
                    + "                                    <th>Date of birth</th>\n"
                    + "                                    <th>Status</th>\n"
                    + "                                    <th>Setting</th>\n"
                    + "                                </tr>");
            for (int i = 0; i < listStaff.size(); i++) {
                out.println("<tr>\n"
                        + "                                    <td>" + listStaff.get(i).getStaffID() + "</td>                                   \n"
                        + "                                    <td>" + listStaff.get(i).getPassword() + "</td>\n"
                        + "                                    <td>" + listStaff.get(i).getName() + "</td>\n"
                        + "                                    <td>" + listStaff.get(i).getRole()+ "</td>\n"
                        + "                                    <td>" + listStaff.get(i).getPhone()+ "</td>\n"
                        + "                                    <td>" + listStaff.get(i).getDateOfBirth()+ "</td>\n"
                        + "                                    <td>" + listStaff.get(i).getStatus()+ "</td>\n"
                        + "                                    <td>\n"
                        + "                                        <button data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                        <button data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                    </td>\n"
                        + "                                </tr>\n"
                        + "                                 </table>\n"
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
                        + "        </div>\n");
            }
        } catch (Exception e) {
            log("Error at ManageStaffController: " + e.toString());
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
