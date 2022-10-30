/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.search;

<<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/search/FilterCategoryController.java
import dao.BookDAO;
import dto.BookDTO;
import dto.CategoryDTO;
import java.io.IOException;
import java.util.List;
========
import dao.CustomerDAO;
import dao.StaffDAO;
import java.io.IOException;
>>>>>>>> Quốc-Thịnh-Branch:BookStoreManagement/src/main/java/controller/login/LogoutController.java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
<<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/search/FilterCategoryController.java
//Hữu Hiếu >>>>>>>>>>
public class FilterCategoryController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/HomePage/homePage.jsp";
========
//Quốc Thịnh >>>>>>>>>>
public class LogoutController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "LoginController?action=Login";
>>>>>>>> Quốc-Thịnh-Branch:BookStoreManagement/src/main/java/controller/login/LogoutController.java

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
<<<<<<<< HEAD:BookStoreManagement/src/main/java/controller/search/FilterCategoryController.java
            String cateID = request.getParameter("cateID");
            int index = 1;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            HttpSession session = request.getSession();
            BookDAO bookDAO = new BookDAO();
            List<BookDTO> listBookbyCate = bookDAO.filterbyCate(cateID,"1");//Lấy sản phẩm theo thể loại
            session.setAttribute("COUNT_BOOK", listBookbyCate.size());
            session.setAttribute("LIST_BOOK_SORT", listBookbyCate);
            listBookbyCate = bookDAO.filterbyCate9(index, cateID,"1");//Lấy sản phẩm theo thể loại và phân trang
            if (listBookbyCate.size() > 0) {
                request.setAttribute("CATEGORY", new CategoryDTO(cateID, listBookbyCate.get(0).getCategory().getName(), listBookbyCate.get(0).getCategory().getStatus()));
                session.setAttribute("LIST_BOOK", listBookbyCate);
                request.setAttribute("CONTROLLER", "FilterCategoryController?cateID=" + cateID + "&");
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE", "NOT FOUND!");
                url = SUCCESS;
========
            HttpSession session = request.getSession();
            CustomerDAO cusDao = new CustomerDAO();
            StaffDAO staffDao = new StaffDAO();
            String cus = request.getParameter("cusID");
            String staff = request.getParameter("staffID");
            if (cus != null) {
                cusDao.updateStatusOffline(cus);//Cập nhật trạng thái tài khoản khách hành
            } else if (staff != null) {
                staffDao.updateStatusOffline(staff);//Cập nhật trạng thái tài khoản nhân viên
            }
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();//Xóa session scope
>>>>>>>> Quốc-Thịnh-Branch:BookStoreManagement/src/main/java/controller/login/LogoutController.java
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