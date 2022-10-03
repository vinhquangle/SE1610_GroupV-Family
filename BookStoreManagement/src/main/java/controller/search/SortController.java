/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dto.BookDTO;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
public class SortController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String sort = request.getParameter("sort");
            String mess = new String();
            HttpSession session = request.getSession();
            List<BookDTO> listBook = (List<BookDTO>) session.getAttribute("LIST_BOOK");
            switch (sort) {
                case "0":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return (int) ((int) o1.getPrice()- o2.getPrice());
                        }
                    });
                    mess ="Price (Ascending - Low To High)";
                    break;
                case "1":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                             return (int) ((int) o2.getPrice()- o1.getPrice());
                        }
                    });
                    mess ="Price (Decreasing - High To Low)";
                    break;
                case "2":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                    mess ="Title (Alphabet - A to Z)";
                    break;
                case "3":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return o2.getName().compareTo(o1.getName());
                        }
                    });
                    mess ="Title (Alphabet - Z to A)";
                    break;
            }
            session.setAttribute("LIST_BOOK", listBook);
            request.setAttribute("SORT", mess);
            url = SUCCESS;
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
