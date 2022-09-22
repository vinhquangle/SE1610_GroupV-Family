/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String GET = "Get";
    private static final String GET_CONTROLLER = "GetController";
    private static final String SEARCH_BOOK = "SearchBook";
    private static final String SEARCH_BOOK_CONTROLLER = "SearchBookController";
    private static final String ADD_BOOKCART = "AddBook";
    private static final String ADD_BOOKCART_CONTROLLER = "AddBookCartController";
    private static final String REMOVE_BOOKCART = "RemoveBook";
    private static final String REMOVE_BOOKCART_CONTROLLER = "RemoveBookCartController";
    private static final String Edit_BOOKCART = "EditQuantity";
    private static final String EDIT_BOOKCART_CONTROLLER = "EditBookCartController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals(GET)) {
                url = GET_CONTROLLER;
            }
            else if(action.equals(SEARCH_BOOK)){
                url = SEARCH_BOOK_CONTROLLER;
            }
            else if(action.equals(ADD_BOOKCART)){
                url = ADD_BOOKCART_CONTROLLER;
            }
            else if(action.equals(REMOVE_BOOKCART)){
                url = REMOVE_BOOKCART_CONTROLLER;
            }
            else if(action.equals(Edit_BOOKCART)){
                url = EDIT_BOOKCART_CONTROLLER;
            }
        } catch (Exception e) {
            log("ERROR at MainController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        };
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
