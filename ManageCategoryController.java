/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.CategoryDAO;
import dto.CategoryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ownhi
 */
//Huu Hieu
public class ManageCategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String search = new String();
        String success = new String();
        String error = new String();
       List<CategoryDTO> listCate = new ArrayList();
        try {
            HttpSession session = request.getSession();
            CategoryDAO cateDAO = new CategoryDAO();
            String categoryIDN = request.getParameter("categoryIDN");// lấy param của category đang được chỉnh sửa
            String use = request.getParameter("use"); //use này đại diện cho hành động remove hoặc edit
            indexCount = cateDAO.countCate();// lay so luong san pham trong kho
            search = request.getParameter("searchCategory");
            
           try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }            
            if (search == null || search == "") {
                search = "";
                listCate = cateDAO.getList9Category(index);//Lấy sản phẩm theo phân trang
            } else {
                //chuyen chuoi co dau thanh khong dau
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchCategory"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //chuyen chuoi co dau thanh khong dau
                indexCount = cateDAO.searchCate(txtSearch).size();//Tim kiem tat ca category theo yeu cau
                listCate = cateDAO.searchCate(txtSearch);// Tim kiem aat ca sach theo trang
            }
            
            //hàm if này để làm update và remove
            if (categoryIDN != null) {
                CategoryDTO newCate = cateDAO.loadCate(categoryIDN);
                if (use.equals("edit")) {
                    String categoryID = request.getParameter("categoryID");
                    String name = request.getParameter("name");
                    String status = request.getParameter("status");
                    newCate.setCategoryID(categoryID);
                    newCate.setName(name);
                    newCate.setStatus(status);
                    if (cateDAO.updateCate(newCate, categoryIDN)) {
                        success = "Successfully!";
                        error = null;
                    } else {
                        error = "Fail!";
                        success = null;
                    }
                } else if (use.equals("remove")) {
                    success = "Successfully removed!";
                    newCate.setStatus("0");
                    cateDAO.updateCate(newCate, categoryIDN);
                } else if (use.equals("recover")) {
                    success = "Successfully recovered!";
                    newCate.setStatus("1");
                    cateDAO.updateCate(newCate, categoryIDN);
                }
                if (search == null || search == "") {
                    search = "";
                    listCate = cateDAO.getList9Category(index);//Lấy sản phẩm theo phân trang
                } else {
                    //Chuyển chuỗi có dấu thành không dấu
                    request.setCharacterEncoding("UTF-8");
                    String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    String txtSearch = pattern.matcher(temp).replaceAll("");
                    //Chuyển chuỗi có dấu thành không dấu
                    request.setCharacterEncoding("UTF-8");
                    indexCount = cateDAO.searchCate(txtSearch).size();//Lấy số lượng tìm kiếm tất cả cate thỏa yêu cầu
                    listCate = cateDAO.searchCate_9(txtSearch, index);//Tìm kiếm tất cả cate thỏa yêu cầu theo phân trang
                }
            }
            
        } catch (Exception e) {
            log("Error at ManageCategoryController: " + e.toString());
        }finally{
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Customer</title>\n"
                    + "        <meta name=\"description\" content=\"\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                  + "        <!-- The Modal -->\n"
                + "        <div class=\"modal\" id=\"myModal\">\n"
                + "            <div class=\"modal-dialog\">\n"
                + "                <div class=\"modal-content\">\n"
                + "                    <!-- Modal Header -->\n"
                + "                    <div class=\"modal-header\">\n"
                + "                        <h4 class=\"modal-title\">Modal Heading</h4>\n"
                + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                + "                    </div>\n"
                + "                    <!-- Modal body -->\n"
                + "                    <div class=\"modal-body\">\n"
                + "\n"
                + "                    </div>\n"
                + "                    <!-- Modal footer -->\n"
                + "                    <div class=\"modal-footer\">               \n"
                + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-danger\"></button>\n"
                + "                        <button type=\"button\" class=\"btn btn-danger\" data-bs-dismiss=\"modal\">Close</button>\n"
                + "                        <button type=\"button\" class=\"btn btn-primary\">Add</button>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <!-- The Modal -->\n"
                        + "        <div class=\"product-status mg-tb-15\">\n"
                        + "            <div class=\"container-fluid\">\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                        + "                        <div class=\"product-status-wrap\">\n"
                        + "                            <h2 onclick=\"load('ManageCategoryController')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Manage Category</h2>\n"
                        + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                        + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                        + "                             <form>"
                        + "                                    <input id=\"searchCategory\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchCategory\" placeholder=\"Search by name, categoryID\" style=\"width: 350px\">\n"
                        + "                                    <input onclick=\"loadCate('ManageCategoryController',document.getElementById(\'searchCategory\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Search\" name=\"action\" style=\"width: 100px\"/>\n"
                        + "                              </form>"
                        + "                            </h5>"
                        + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                        + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                        + "                                    <b>Add new category</b>\n"
                        + "                                </button>\n"
                        + "                            </div>\n");
                if (success != null) {
                    out.println("<p style=\"font-size: 30px; display: inline-block; background-color: green; color: white;\"><b>" + success + "</b></p>");
                }
                if (error != null) {
                    out.println("<p style=\"font-size: 30px; display: inline-block; background-color: red; color: white;\"><b>" + error + "</b></p>");
                }
              if (listCate.size() <= 0) {
                    out.println("<div class=\"\">\n"
                            + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">NOT FOUND!</p>\n"
                            + "                </div>");
                } else {
                    out.println( "                            <table>\n"
                    + "                                <tr>\n"
                    + "                                    <th>CategoryID</th>\n"
                    + "                                    <th>Name</th>\n"
                    + "                                    <th>Status</th>\n"
                    + "                                    <th>Setting</th>\n"
                    + "                                </tr>\n");
              }
            int count = 0;
            for (int i = 0; i < listCate.size(); i++) {
                count++;
                out.println("          <tr>\n"
                        + "                                    <td><input style=\"width: 120px;\" class=\"" + count + "\" value=\"" + listCate.get(i).getCategoryID()+ "\"/></td>                                   \n"
                        + "                                    <input class=\"" + count + "\" value=\"" + listCate.get(i).getCategoryID() + "\" type=\"hidden\"/>"
                        + "                                    <td><textarea rows=\"3\" style=\" width: 170px;\" class=\"" + count + "\">" + listCate.get(i).getName()+ "</textarea></td>\n");
                    String status = listCate.get(i).getStatus();
                    if (status.equals("1")) {
                        out.println("                                  <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    } else {
                        out.println("                                    <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                    }
                    out.println("                                    <td>\n"
                            + "                                        <button id=\"edit\" onclick=\"cate('ManageCategoryController?index=" + index + "'," + count + ",'edit')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n");
                    if (listCate.get(i).getStatus().equals("1")) {
                        out.println("                                        <button id=\"remove\" onclick=\"cate('ManageCategoryController?index=" + index + "'," + count + ",'remove')\" data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n");
                    } else {
                        out.println("                                        <button id=\"recover\" onclick=\"cate('ManageCategoryController?index=" + index + "'," + count + ",'recover')\" data-toggle=\"tooltip\" title=\"Recover\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
                    }
                    out.println("                                   </td>\n"
                            + "                                </tr>"
                    );
            }
               out.println(" </table>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>    \n"
                    + "<div style=\"margin: auto; display: inline-block;\" class=\"custom-pagination\">\n"
                    + "            <nav aria-label=\"Page navigation example\">\n"
                    + "                <ul class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int i = 1; i < page + 1; i++) {
                out.println("                    <li class=\"page-item\"><a onclick=\"load('ManageCategoryController?searchCategory=" + search + "&index=" + i + "')\" class=\"page-link\" href=\"#\">" + i + "</a></li>\n");
            }
            out.println("</ul>\n"
                    + "            </nav>\n"
                    + "        </div>  "
                    + "  </body>\n"
                    + "\n"
                    + "</html>\n"
                    + "");
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
