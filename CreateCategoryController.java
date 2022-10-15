/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.CategoryDAO;
import dto.CategoryDTO;
import dto.CategoryErrorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ownhi
 */
public class CreateCategoryController extends HttpServlet {
    private static final String ERROR= "category.jsp";
    private static final String SUCCESS= "category.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        CategoryErrorDTO cateError = new CategoryErrorDTO();
        try {
            request.setCharacterEncoding("UTF-8");
            HttpSession session = request.getSession();
            String cateID = request.getParameter("cateID");
            String name = request.getParameter("name");
            String status = request.getParameter("status");
            boolean checkValidation = true;
            CategoryDAO cateDAO = new CategoryDAO();

            
            if(cateID.isBlank() || cateID.isEmpty()){
                    cateError.setCategoryIDError("ID không được để trống!");
                    checkValidation = false;
                }
            //Check duplicate ID
            boolean checkDuplicateID = cateDAO.checkDuplicateID(cateID);
            if(checkDuplicateID){                
                cateError.setCategoryIDError("ID đã được sử dụng!"); 
                checkValidation = false;
            }

            //Check ID theo pattern
            boolean checkIDPattern = cateDAO.validate(cateID);
            if(!checkIDPattern){                
                cateError.setCategoryIDError("ID phải bắt đầu bằng kí tự 'P' và theo sau từ 1 đến 10 chữ số!"); 
                checkValidation = false;
            }

            //Check name
            if(name.length()>100 || name.length()<5){                
                cateError.setNameError("Độ dài tên nhà xuất bản phải từ 5-100 kí tự!");  
                checkValidation = false;
            }
            if(name.isEmpty() || name.isBlank()){
                    cateError.setNameError("Tên không được để trống!");
                    checkValidation = false;
                }

            //Check duplicate name
            boolean checkDuplicateN = cateDAO.checkDuplicateName(name);
            if(checkDuplicateN){                
                cateError.setNameError("Tên đã được sử dụng!"); 
                checkValidation = false;
            }

            //Neu validate thanh cong
            if(checkValidation){               
                boolean checkCreate = cateDAO.createCate(new CategoryDTO(cateID, name, status));
                if(checkCreate){
                    session.setAttribute("MESSAGE", "Thêm thể loại mới thành công");
                    url = SUCCESS;
                }
            }else{
                request.setAttribute("CATE_ERROR", cateError);
            }
        } catch (Exception e) {
            log("Error at CreateCategoryController: " + e.toString());

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
