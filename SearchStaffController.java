/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vungo
 */
//Locate at controler.search
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchStaffController extends HttpServlet {

    private static final String ERROR = "searchStaff.jsp";
    private static final String SUCCESS = "searchStaff.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            request.setCharacterEncoding("UTF-8");
            String temp = Normalizer.normalize(request.getParameter("searchStaff"), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String sSearch = pattern.matcher(temp).replaceAll("");
            HttpSession session = request.getSession();
            StaffDAO dao= new StaffDAO();
            session.setAttribute("LIST_STAFF", dao.getListStaff());
            session.setAttribute("LIST_STAFF_SEARCH", dao.searchStaff(sSearch));
            List<StaffDTO> listStaff = dao.searchStaff(sSearch);
            if (listStaff.size() > 0) {
                session.setAttribute("LIST_STAFF", listStaff);
                //request.setAttribute("SEARCH_STAFF_CONTROLLER", "SearchStaffController?searchStaff=" + request.getParameter("searchStaff"));
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE_STAFF", "KHÔNG TÌM THẤY NHÂN VIÊN!");
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at SearchBookController: " + e.toString());
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
