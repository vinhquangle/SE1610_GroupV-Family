/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dao.BookDAO;
import dto.BookDTO;
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
public class FilterPriceController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String min = request.getParameter("min");
            String max = request.getParameter("max");
            request.setAttribute("MAX", max);
            request.setAttribute("MIN", min);
            request.setAttribute("MESS", request.getParameter("mess"));
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            BookDAO bookDao = new BookDAO();
            HttpSession session = request.getSession();
            session.setAttribute("COUNT_BOOK", bookDao.filterByPrice(min, max, "1").size());//Lấy sản phẩm theo giá cả
            session.setAttribute("LIST_BOOK_SORT", bookDao.filterByPrice(min, max, "1"));
            List<BookDTO> listBook = bookDao.filterByPrice9(min, max, index, "1");//Lấy sản phẩm theo giá cả và phân trang
            if (listBook.size() > 0) {
                session.setAttribute("LIST_BOOK", listBook);
                request.setAttribute("CONTROLLER", "FilterPriceController?min=" + min + "&max=" + max + "&mess=" + request.getParameter("mess") + "&");
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE", "NOT FOUND!");
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at CategoryController: " + e.toString());
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
