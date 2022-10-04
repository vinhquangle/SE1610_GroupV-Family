/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import dao.BookDAO;
import dto.BookDTO;
import dto.BookErrorDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh - 3/10/2022
 */
@WebServlet(name = "AddBookManageController", urlPatterns = {"/AddBookManageController"})
public class AddBookManageController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/ManagePage/manage.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/ManagePage/book.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            BookDAO daoBook = new BookDAO();
            BookErrorDTO bookError = new BookErrorDTO();
            List<CategoryDTO> listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
            List<PublisherDTO> listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");            
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("name");
            String publisherID = request.getParameter("publisher");
            String categoryID = request.getParameter("category");   
            String pub = "";
            String cate = "";
            for (PublisherDTO publisherDTO : listPub) {
                if(publisherID.equals(publisherDTO.getPublisherID())){
                    pub = publisherDTO.getName();
                }
            }
            
            for (CategoryDTO categoryDTO : listCate) {
                if(categoryID.equals(categoryDTO.getCategoryID())){
                    cate = categoryDTO.getName();
                }
            }
            String authorName = request.getParameter("author-name");
            double price = Double.parseDouble(request.getParameter("price"));
            String image = request.getParameter("img");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean checkValidation = true;
            
            //check duplicate ISBN
            boolean checkDuplicateIsbn = daoBook.checkDuplicateIsbn(isbn);
            if (checkDuplicateIsbn == true) {
                bookError.setIsbnError("ISBN already in use");
                checkValidation = false;
            }
            
            //Check length ISBN
            if (isbn.length() > 14 || isbn.length() < 2) {
                bookError.setIsbnError("Length isbn must be 2 to 13 numbers");
                checkValidation = false;
            }
            
            //Check length book-title
            if (title.length() > 100 || title.length() < 2) {
                bookError.setNameError("Length title must be 2 to 100 characters");
                checkValidation = false;
            }
                       
            
            //Check length author-name
            if (authorName.length() > 50 || authorName.length() < 2) {
                bookError.setAuthorNameError("Length Author-Name must be 2 to 50 characters");
                checkValidation = false;
            }
 
            if (checkValidation) {
                boolean check = daoBook.createBook(new BookDTO(isbn, new PublisherDTO(publisherID, pub), new CategoryDTO(categoryID, cate), title, authorName, price, image, quantity));
                if (check) {
                    url = SUCCESS;
                    session.setAttribute("MESSAGE", "Add success");
                }
            } else {
                request.setAttribute("BOOK_ERROR", bookError);
            }
        } catch (Exception e) {
            log("Error at AddBookManageController : " + e.toString());
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
