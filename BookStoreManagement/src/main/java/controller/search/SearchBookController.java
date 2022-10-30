/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dao.BookDAO;
import dto.BookDTO;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh-21/09/2022 Controller dung de search book by
 * ISBN/Title/Authors
 */
//Quang Vinh >>>>>>>>>>
public class SearchBookController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            //Chuyển chuỗi có dấu thành không dấu
            request.setCharacterEncoding("UTF-8");
            String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String txtSearch = pattern.matcher(temp).replaceAll("");
            //Chuyển chuỗi có dấu thành không dấu
            HttpSession session = request.getSession();
            BookDAO dao = new BookDAO();
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            session.setAttribute("COUNT_BOOK", dao.searchBook(txtSearch,"1").size());//Lấy sản phẩm theo tìm kiếm
            session.setAttribute("LIST_BOOK_SORT", dao.searchBook(txtSearch,"1"));
            List<BookDTO> list = dao.searchBook9(txtSearch, index,"1");//Lấy sản phẩm theo tìm kiếm và phân trang
            if (list.size() > 0) {
                session.setAttribute("LIST_BOOK", list);
                request.setAttribute("CONTROLLER", "SearchBookController?searchBook=" + request.getParameter("searchBook") + "&");
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE", "KHÔNG TÌM THẤY!");
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
//<<<<<<<<<<