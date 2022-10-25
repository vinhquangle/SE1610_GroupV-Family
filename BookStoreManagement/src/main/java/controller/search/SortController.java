/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

import dao.CategoryDAO;
import dao.PublisherDAO;
import dto.BookDTO;
import java.io.IOException;
import java.util.ArrayList;
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
//Quốc Phi >>>>>>>>>>
public class SortController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String sort = request.getParameter("sort");
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            String txtsearch = request.getParameter("searchBook");
            String cateN = request.getParameter("cateID");
            String pubN = request.getParameter("pubID");
            String max = request.getParameter("max");
            String min = request.getParameter("min");
            String mes = request.getParameter("mess");
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            if (cateN != "") {
                request.setAttribute("CATEGORY", cateDao.getCategory(cateN));//Lấy thông tin thể loại
            } else if (pubN != "") {
                request.setAttribute("PUBLISHER", pubDao.getPublisher(pubN));//Lấy thông tin nhà xuất bản
            } else if (max != null && min != null) {
                request.setAttribute("MAX", max);
                request.setAttribute("MIN", min);
                request.setAttribute("MESS", mes);
            }
            String mess = new String();
            HttpSession session = request.getSession();
            List<BookDTO> listBook = (List<BookDTO>) session.getAttribute("LIST_BOOK_SORT");
            List<BookDTO> list = new ArrayList<BookDTO>();
            int size = listBook.size();//Lấy số lượng từ danh sách sản phẩm
            switch (sort) {
                case "0":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return (int) ((int) o1.getPrice() - o2.getPrice());//Sắp xếp tăng dần theo giá 
                        }
                    });
                    mess = "Giá bán (Tăng dần - Thấp tới cao)";
                    break;
                case "1":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return (int) ((int) o2.getPrice() - o1.getPrice());//Sắp xếp giảm dần theo giá 
                        }
                    });
                    mess = "Giá bán (Giảm dần - Cao tới thấp)";
                    break;
                case "2":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return o1.getName().compareTo(o2.getName());//Sắp xếp tăng dần theo chữ cái
                        }
                    });
                    mess = "Tiêu đề (Chữ cái - A -> Z)";
                    break;
                case "3":
                    Collections.sort(listBook, new Comparator<BookDTO>() {
                        public int compare(BookDTO o1, BookDTO o2) {
                            return o2.getName().compareTo(o1.getName());//Sắp xếp giảm dần theo chữ cái
                        }
                    });
                    mess = "Tiêu đề (Chữ cái - Z -> A)";
                    break;
            }
            for (int i = 0; i < 9; i++) {//Sắp xếp sản phẩm theo phân trang
                int number = (index - 1) * 9 + i;
                if (number <= (size - 1)) {
                    list.add(i, listBook.get((index - 1) * 9 + i));
                }
            }
            session.setAttribute("LIST_BOOK", list);
            request.setAttribute("SORT", mess);
            request.setAttribute("CONTROLLER", "SortController?sort=" + sort + "&searchBook=" + txtsearch + "&cateID=" + cateN + "&pubID=" + pubN + "&max=" + max + "&min=" + min + "&mess=" + mes + "&");
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
//<<<<<<<<<<