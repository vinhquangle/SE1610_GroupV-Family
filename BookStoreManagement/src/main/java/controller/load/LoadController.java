/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.load;

import dao.BookDAO;
import dto.BookDTO;
import java.io.IOException;
import java.util.ArrayList;
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
//Quốc Thịnh >>>>>>>>>>
public class LoadController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ProductPage/productDetails.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            BookDAO bookDao = new BookDAO();
            HttpSession session = request.getSession();
            String isbn = request.getParameter("isbn");
            List<BookDTO> listBook = bookDao.getAllBook("1");//Lấy tất cả sách trong database
            BookDTO book = bookDao.loadBook(isbn,"1"); //Lấy thông tin sách theo ISBN
            List<BookDTO> sameCate = new ArrayList<>();
            int count = 0;
            for (BookDTO bookDTO : listBook) {
                if (bookDTO.getCategory().getName().equals(book.getCategory().getName()) && count < 4 && !bookDTO.getIsbn().equals(book.getIsbn())) {
                    sameCate.add(bookDTO);//Thêm vào 4 cuốn sách cùng thể loại
                    count++;
                }
            }
            session.setAttribute("SAME_CATE", sameCate);
            session.setAttribute("BOOK_DETAIL", book);
            url = SUCCESS;
        } catch (Exception e) {
            log("ERROR AT GETCONTROLLER : " + e.toString());
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