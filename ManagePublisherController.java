/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.PublisherDAO;
import dto.PublisherDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quang Vinh
 */
public class ManagePublisherController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<PublisherDTO> listPub = new ArrayList<PublisherDTO>();
        int index = 0;
        int count = 0;
        int indexCount = 0;
        String search = new String();
        String success = new String();
        String fail = new String();
        try {
            HttpSession session = request.getSession();
            PublisherDAO pubDao = new PublisherDAO();
            indexCount = pubDao.countPub();// Lay so luong Publisher trong store
            listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
            search = request.getParameter("searchPublisher");
            String pubB = request.getParameter("pubB");
            String use = request.getParameter("use");
            //Chuyển chuỗi có dấu thành không dấu
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                listPub = pubDao.getListPublisher_6(index);//Lay publisher roi phan trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchPublisher"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                indexCount = pubDao.searchPub(txtSearch).size();//Tìm kiếm tất cả publisher thỏa yêu cầu
                listPub = pubDao.searchPub(txtSearch);//Tìm kiếm tất cả publisher thỏa yêu cầu theo phân trang
            }
            if (pubB != null) {
                PublisherDTO newPub = pubDao.getPublisherByID(pubB);
                if (use.equals("edit")) {
                    String publisherID = request.getParameter("publisherID");
                    String pubName = request.getParameter("name");
                    String status = request.getParameter("status");
                    newPub.setPublisherID(publisherID);
                    newPub.setName(pubName);
                    if (pubDao.updatePub(newPub, pubB)) {
                        success = "Updated successfully";
                        fail = null;
                    } else {
                        fail = "Updated fail!!!";
                        success = null;
                    }
                }else if(use.equals("remove")){
                    success = "Removed successfully";
                    newPub.setStatus("0");
                    pubDao.updatePub(newPub, pubB);//Cap nhat thong tin publisher
                }else if(use.equals("recover")){
                    success = "Recovered successfully";
                    newPub.setStatus("1");
                    pubDao.updatePub(newPub, pubB);//Cap nhat thong tin publisher
                }
                //Cho nay dung de giu lai trang sau khi update (Thay thong tin da update khong can load lai trang)
                if (search == null || search == "") {
                    search = "";
                    listPub = pubDao.getListPublisher_6(index);//Lay publisher roi phan trang
                } else {
                    //Chuyển chuỗi có dấu thành không dấu
                    request.setCharacterEncoding("UTF-8");
                    String temp = Normalizer.normalize(request.getParameter("searchPublisher"), Normalizer.Form.NFD);
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    String txtSearch = pattern.matcher(temp).replaceAll("");
                    indexCount = pubDao.searchPub(txtSearch).size();//Tìm kiếm tất cả publisher thỏa yêu cầu
                    listPub = pubDao.searchPub_6(txtSearch, index);//Tìm kiếm tất cả publisher thỏa yêu cầu theo phân trang
                }
            }  
            
        } catch (Exception e) {
            log("Error at ManagePublisherController: " + e.toString());
        } finally {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html class=\"no-js\" lang=\"en\">\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "        <title>Publisher</title>\n"
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
                    + "                    <div class=\"modal-footer\">\n"
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
                    + "                            <h2 onclick=\"load('ManagePublisherController')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Manage Publisher</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchPublisher\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchPublisher\" placeholder=\"Search by publisherID, Name\" style=\"width: 350px\">\n"
                    + "                                    <input onclick=\"publisher('ManagePublisherController',document.getElementById(\'searchPublisher\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Search\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Add new publisher</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n");
            if (success != null) {
                out.println("<p style=\"font-size: 30px; display: inline-block; background-color: green; color: white;\"><b>" + success + "</b></p>");
            }
            if (fail != null) {
                out.println("<p style=\"font-size: 30px; display: inline-block; background-color: red; color: white;\"><b>" + fail + "</b></p>");
            }
            if (listPub.size() < 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">NOT FOUND!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>Publisher ID</th>\n"
                        + "                                    <th>Publisher Name</th>\n"
                        + "                                    <th>Status</th>\n"
                        + "                                    <th>Setting</th>\n"
                        + "                                </tr>\n");
            }
            for (int i = 0; i < listPub.size(); i++) {
                count++;
                out.println("          <tr>\n"
                        + "                                    <td><input style=\"width: 120px;\" class=\"" + count + "\" value=\"" + listPub.get(i).getPublisherID() + "\"/></td>\n"
                        + "                                    <input class=\"" + count + "\" value=\"" + listPub.get(i).getPublisherID() + "\" type=\"hidden\"/>"
                        + "                                    <td><input style=\"width: 200px;\" class=\"" + count + "\" value=\"" + listPub.get(i).getName() + "\"/></td>\n");
                String status = listPub.get(i).getStatus();
                if (status.equals("1")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updatePub('ManagePublisherController?index=" + index + "'," + count + ",'edit')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n");
                if (listPub.get(i).getStatus().equals("1")) {
                    out.println("                                        <button id=\"remove\" onclick=\"updatePub('ManagePublisherController?index=" + index + "'," + count + ",'remove')\" data-toggle=\"tooltip\" title=\"Trash\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>\n");
                } else {
                    out.println("                                        <button id=\"recover\" onclick=\"updatePub('ManagePublisherController?index=" + index + "'," + count + ",'recover')\" data-toggle=\"tooltip\" title=\"Recover\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
                }
                out.println("                                   </td>\n"
                        + "                                </tr>\n"
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
            if ((indexCount % 10) == 0) {
                page = indexCount / 10;
            } else {
                page = (indexCount / 10) + 1;
            }
            for (int i = 1; i <= page + 1; i++) {
                out.println("                    <li class=\"page-item\"><a onclick=\"publisher('ManagePublisherController?searchPublisher=" + search + "&index=" + i + "')\" class=\"page-link\" href=\"#\">" + i + "</a></li>\n");
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
