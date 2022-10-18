/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.modify;


import dao.StaffDAO;
import dto.StaffDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * 
 */
public class UpdateAccountController extends HttpServlet {
    private static final String SUCCESS = "searchStaff.jsp";
    private static final String ERROR = "searchStaff.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String staffID = request.getParameter("staffID");
            String staffName = request.getParameter("name");
            String staffPassword = request.getParameter("password");
            String staffRole = request.getParameter("role");
            String staffPhone = request.getParameter("phone");
            String staffDate = request.getParameter("dateOfBirth");
            String staffStatus = request.getParameter("status");
            String staffDelete = request.getParameter("delete");
            StaffDAO dao = new StaffDAO();
            HttpSession session = request.getSession();
            StaffDTO loginUser = (StaffDTO) session.getAttribute("LOGIN_USER");

            boolean checkUpdate = dao.updateStaff(new StaffDTO(staffID, name, password, role, phone, dateOfBirth, status, delete));
            if (checkUpdate) {
                if (loginUser.getStaffID().equals(staffID)) {
                    loginUser.setName(staffName);
                    loginUser.setPassword(staffPassword);
                    loginUser.setRole(staffRole);
                    loginUser.setPhone(staffPhone);
                    loginUser.setDateOfBirth(staffDate);
                    loginUser.setStatus(staffStatus);
                    loginUser.setDelete(staffDelete);
                    session.setAttribute("LOGIN_USER", loginUser);
                }
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at UpdateController : " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
}