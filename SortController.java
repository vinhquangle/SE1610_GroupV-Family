/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

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
 * @author vqphi
 */
@WebServlet(name = "SortController", urlPatterns = {"/SortController"})
public class SortController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            BookDAO bookDao = new BookDAO();
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            url = SUCCESS;
            String data = request.getParameter("data");
            String cateID = request.getParameter("cateID");
            String pubID = request.getParameter("pubID");
            List<CategoryDTO> listCate = cateDao.getListCategory(); //Load tất cả thể loại
            List<PublisherDTO> listPub = pubDao.getListPublisher(); //Load tất cả NXB
            List<BookDTO> listBookPriceDESC = bookDao.sortPriceDesc(listCate, listPub, cateID); //Sort sách theo giá giảm dần
            List<BookDTO> listBookPriceASC = bookDao.sortPriceAsc(listCate, listPub, cateID); //Sort sách theo giá tăng dần
            List<BookDTO> listBookAlphabetASC = bookDao.sortAlphabetASC(listCate, listPub, cateID); //Sort sách theo chữ cái Z-A
            List<BookDTO> listBookAlphabetDESC = bookDao.sortAlphabetDESC(listCate, listPub, cateID);//Sort sách theo chữ cái A-Z

            if (data.equals("1")) {
                if (listBookPriceDESC.size() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LIST_BOOK", listBookPriceDESC); //Đưa infor sách lên session scope
                    session.setAttribute("LIST_PUB", listPub); //Đưa tất cả thể loại sách lên session scope
                    session.setAttribute("LIST_CATE", listCate); //Đưa tất cả NXB lên session scope
                    url = SUCCESS;
                }
            } else if (data.equals("2")) {
                if (listBookPriceASC.size() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LIST_BOOK", listBookPriceASC); //Đưa infor sách lên session scope
                    session.setAttribute("LIST_PUB", listPub); //Đưa tất cả thể loại sách lên session scope
                    session.setAttribute("LIST_CATE", listCate); //Đưa tất cả NXB lên session scope
                    url = SUCCESS;
                }
            } else if (data.equals("3")) {
                if (listBookAlphabetASC.size() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LIST_BOOK", listBookAlphabetASC); //Đưa infor sách lên session scope
                    session.setAttribute("LIST_PUB", listPub); //Đưa tất cả thể loại sách lên session scope
                    session.setAttribute("LIST_CATE", listCate); //Đưa tất cả NXB lên session scope
                    url = SUCCESS;
                }
            } else if (data.equals("4")) {
                if (listBookAlphabetDESC.size() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LIST_BOOK", listBookAlphabetDESC); //Đưa infor sách lên session scope
                    session.setAttribute("LIST_PUB", listPub); //Đưa tất cả thể loại sách lên session scope
                    session.setAttribute("LIST_CATE", listCate); //Đưa tất cả NXB lên session scope
                    url = SUCCESS;
                }
            }
            

        } catch (Exception e) {
            log("ERROR AT SORTCONTROLLER : " + e.toString());
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
