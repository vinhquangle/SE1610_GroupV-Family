/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dao.BookDAO;
import dao.CategoryDAO;
import dao.PublisherDAO;
import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
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
 * @author Quang Vinh-21/09/2022
 * Controller dung de search book by ISBN/Title/Authors
 */
@WebServlet(name = "SearchBookController", urlPatterns = {"/SearchBookController"})
public class SearchBookController extends HttpServlet {

    private static final String ERROR = "JSP/HomePage/homePage.jsp";
    private static final String SUCCESS = "JSP/HomePage/homePage.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            request.setCharacterEncoding("UTF-8");
            String txtSearch = request.getParameter("searchBook");
            HttpSession session = request.getSession();
            BookDAO dao = new BookDAO();
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            List<CategoryDTO> listCate = cateDao.getListCategory();
            List<PublisherDTO> listPub = pubDao.getListPublisher();
            List<BookDTO> list = dao.SearchBook(txtSearch, listCate, listPub);          
            if (list.size() > 0) {
                session.setAttribute("LIST_BOOK", list);//ATTRIBUTE CHU HOA 
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
