/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import dao.PublisherDAO;
import dto.PublisherDTO;
import dto.PublisherErrorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh
 */
@WebServlet(name = "CreatePublisherController", urlPatterns = {"/CreatePublisherController"})
public class CreatePublisherController extends HttpServlet {

    
    private static final String ERROR = "publisher.jsp";
    private static final String SUCCESS = "publisher.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        PublisherErrorDTO pubError = new PublisherErrorDTO();
        boolean checkValidation = true;
        PublisherDAO pubDao = new PublisherDAO();
        try {
            request.setCharacterEncoding("UTF-8");
            HttpSession session = request.getSession();
            String pubID = request.getParameter("publisherID");
            String name = request.getParameter("name");
            String status = request.getParameter("status");
            
            //Check lenght publisherID
            if(pubID.length() > 10 || pubID.length() < 2){                
                pubError.setPublisherIDError("Độ dài ID nhà xuất bản phải từ 2-50 kí tự!"); 
                checkValidation = false;
                if(pubID == ""){
                    pubError.setPublisherIDError("ID không được để trống!");
                }
            }
            //Check duplicate ID
            boolean checkDuplicateID = pubDao.checkDuplicateID(pubID);
            if(checkDuplicateID){                
                pubError.setPublisherIDError("ID đã được sử dụng!"); 
                checkValidation = false;
            }
            
            //Check ID theo pattern
            boolean checkIDPattern = pubDao.validate(pubID);
            if(!checkIDPattern){                
                pubError.setPublisherIDError("ID phải bắt đầu bằng kí tự 'P' và theo sau từ 1 đến 10 chữ số!"); 
                checkValidation = false;
            }
            
            //Check name
            if(name.length()>100 || name.length()<5){                
                pubError.setNameError("Độ dài tên nhà xuất bản phải từ 5-100 kí tự!");  
                checkValidation = false;
                if(name == ""){
                    pubError.setNameError("Tên không được để trống!");
                }
            }
            
            //Check duplicate name
            boolean checkDuplicateN = pubDao.checkDuplicateName(name);
            if(checkDuplicateN){                
                pubError.setNameError("Tên đã được sử dụng!"); 
                checkValidation = false;
            }
            
            //Neu validate thanh cong
            if(checkValidation){               
                boolean checkInsert = pubDao.create(new PublisherDTO(pubID, name, status));
                if(checkInsert){
                    session.setAttribute("MESSAGE", "Thêm nhà xuất bản mới thành công");
                    url = SUCCESS;
                }
            }else{
                request.setAttribute("PUB_ERROR", pubError);
            }
        } catch (Exception e) {
            log("Error at CreatePublisherController: " + e.toString());
            
        }finally{
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
