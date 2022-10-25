/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dao.BookDAO;
import dto.BookDTO;
import dto.PublisherDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
//Hữu Hiếu >>>>>>>>>>
public class FilterPublisherController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String pubID = request.getParameter("pubID");
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            HttpSession session = request.getSession();
            BookDAO bookDAO = new BookDAO();
            List<BookDTO> listBookbyPub = bookDAO.filterbyPub(pubID, "1");//Lấy sản phẩm theo nhà xuất bản
            session.setAttribute("COUNT_BOOK", listBookbyPub.size());
            session.setAttribute("LIST_BOOK_SORT", listBookbyPub);
            listBookbyPub = bookDAO.filterbyPub9(pubID, index, "1");//Lấy sản phẩm theo nhà xuất bản và phân trang
            if (listBookbyPub.size() > 0) {
                session.setAttribute("LIST_BOOK", listBookbyPub);
                request.setAttribute("PUBLISHER", new PublisherDTO(pubID, listBookbyPub.get(0).getPublisher().getName(), listBookbyPub.get(0).getPublisher().getStatus()));
                request.setAttribute("CONTROLLER", "FilterPublisherController?pubID=" + pubID + "&");
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE", "NOT FOUND!");
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at PublisherController: " + e.toString());
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
