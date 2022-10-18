/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.PublisherDAO;
import dto.PublisherDTO;
import dto.PublisherErrorDTO;
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

/**
 *
 * @author Quang Vinh
 */
//Quang Vinh, Quốc Thịnh >>>>>>>>>>
public class ManagePublisherController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<PublisherDTO> listPub = new ArrayList<PublisherDTO>();
        int index = 0;
        int count = 0;
        int indexCount = 0;
        String search = new String();
        String modal = new String();
        String success = new String();
        PublisherDTO pub = new PublisherDTO();
        PublisherDTO newPub = new PublisherDTO();
        PublisherErrorDTO pubError = new PublisherErrorDTO();
        try {
            PublisherDAO pubDao = new PublisherDAO();
            indexCount = pubDao.countPub();// Lay so luong Publisher trong store
            search = request.getParameter("searchPublisher");
            String publisherID = request.getParameter("publisherID");
            String pubB = request.getParameter("pubB");
            String use = request.getParameter("use");
            pub = pubDao.getPublisher(publisherID);
            boolean checkValidation = true;
            //Chuyển chuỗi có dấu thành không dấu
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (search == null || search == "") {
                search = "";
                listPub = pubDao.getListPublisher9(index);//Lay publisher roi phan trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchPublisher"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                indexCount = pubDao.searchPub(txtSearch).size();//Tìm kiếm tất cả publisher thỏa yêu cầu
                listPub = pubDao.searchPub9(txtSearch, index);//Tìm kiếm tất cả publisher thỏa yêu cầu theo phân trang
            }
            if (pubB != null && pubB != "") {
                if (use.equals("edit")) {
                    String pubName = request.getParameter("name");
                    if (pubName != null && pubName != "" && pubName.length() <= 50) {
                        pub.setPublisherID(publisherID);
                        pub.setName(pubName);
                        if (pubDao.updatePub(pub, pubB)) {
                            modal = "Chỉnh sửa thông tin thành công";
                        } else {
                            modal = "Chỉnh sửa thông tin thất bại";
                            throw new Exception();
                        }
                    } else {
                        modal = "Chỉnh sửa thông tin thất bại";
                        throw new Exception();
                    }
                } else if (use.equals("remove")) {
                    pub.setStatus("0");
                    if (pubDao.updatePub(pub, pubB)) {//Cap nhat thong tin publisher
                        modal = "Xóa thành công";
                    }
                } else if (use.equals("recover")) {
                    pub.setStatus("1");
                    if (pubDao.updatePub(pub, pubB)) {//Cap nhat thong tin publisher
                        modal = "Khôi phục thành công";
                    }
                }
            } else if (use.equals("add")) {
                String pubID = request.getParameter("publisherID");
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                if (pubID == "") {
                    pubError.setPublisherIDError("ID không được để trống!");
                    checkValidation = false;
                }
                if (pubID.length() > 10 || pubID.length() < 2) {
                    pubError.setPublisherIDError("Độ dài ID nhà xuất bản phải từ 2-10 kí tự!");
                    checkValidation = false;
                }
                //Check duplicate ID
                boolean checkDuplicateID = pubDao.checkDuplicateID(pubID);
                if (checkDuplicateID) {
                    pubError.setPublisherIDError("ID đã được sử dụng!");
                    checkValidation = false;
                }

                //Check ID theo pattern
                //Check name
                if (name.length() > 50 || name.length() < 2) {
                    pubError.setNameError("Độ dài tên nhà xuất bản phải từ 2-50 kí tự!");
                    checkValidation = false;
                    if (name == "") {
                        pubError.setNameError("Tên không được để trống!");
                    }
                }

                //Check duplicate name
                boolean checkDuplicateN = pubDao.checkDuplicateName(name);
                if (checkDuplicateN) {
                    pubError.setNameError("Tên đã được sử dụng!");
                    checkValidation = false;
                }
                newPub = new PublisherDTO(pubID, name, status);
                //Neu validate thanh cong
                if (checkValidation) {
                    boolean checkInsert = pubDao.create(newPub);
                    if (checkInsert) {
                        success = "Thêm nhà xuất bản mới thành công!";
                    }
                }
            }
            //Cho nay dung de giu lai trang sau khi update (Thay thong tin da update khong can load lai trang)
            if (search == null || search == "") {
                search = "";
                listPub = pubDao.getListPublisher9(index);//Lay publisher roi phan trang
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchPublisher"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                indexCount = pubDao.searchPub(txtSearch).size();//Tìm kiếm tất cả publisher thỏa yêu cầu
                listPub = pubDao.searchPub9(txtSearch, index);//Tìm kiếm tất cả publisher thỏa yêu cầu theo phân trang
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
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">"
                    + "<!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-lg\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin nhà xuất bản</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <p>Mã nhà xuất bản</p>\n"
                    + "                                <p style=\"font-weight: bold;\" id=\"pubID\" placeholder=\"Nhập mã nhà xuất bản\">" + pub.getPublisherID() + "</p>\n"
                    + "                                <input id=\"pubB\" placeholder=\"Nhập mã nhà xuất bản\" value=\"" + pub.getPublisherID() + "\" style=\"display: none;\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <p>Nhà xuất bản</p>\n"
                    + "                                <input style=\"font-weight: bold;\" id=\"pubName\" placeholder=\"Nhập tên nhà xuất bản\" value=\"" + pub.getName() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n");
            if (pub.getStatus().equals("1")) {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"pub('ManagePublisherController?index=" + index + "','remove'," + index + ")\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"pub('ManagePublisherController?index=" + index + "','recover'," + index + ")\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("</div>\n"
                    + "                    </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"pub('ManagePublisherController?index=" + index + "','edit'," + index + ")\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Modal -->"
                    + " <!-- The Modal -->\n"
                    + "        <div class=\"modal\" id=\"myModal\">\n"
                    + "            <div class=\"modal-dialog  modal-lg\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <!-- Modal Header -->\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h4 class=\"modal-title\">Thêm nhà xuất bản</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div id=\"mcontent\" class=\"modal-body\">\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 20px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>ID nhà xuất bản</label>\n"
                    + "                                <input placeholder=\"Nhập ID của nhà xuất bản\" value=\"" + newPub.getPublisherID() + "\" style=\"font-weight: bold\" id=\"pubid\" name=\"isbn\" type=\"text\" class=\"form-control c\" minlength=\"2\" maxlength=\"10\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + pubError.getPublisherIDError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Nhà xuất bản</label>\n"
                    + "                                <input placeholder=\"Nhập tên nhà xuất bản\" value=\"" + newPub.getName() + "\" style=\"font-weight: bold\" name=\"name\" type=\"text\" class=\"form-control c\" minlength=\"1\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + pubError.getNameError() + "</p></br>\n"
                    + "                        </div>"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <input style=\"display: none;\" type=\"button\" class=\"btn\" value=\"z\">\n"
                    + "                                <button style=\"background-color: #494f57; color: white\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Hủy</button>\n"
                    + "                                <input data-bs-dismiss=\"modal\" onclick=\"addPub('ManagePublisherController','c')\" type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Tạo\">\n"
                    + "                            </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        <!-- The Modal -->"
                    + "        <div class=\"product-status mg-tb-15\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                    + "                        <div class=\"product-status-wrap\">\n"
                    + "                            <h2 onclick=\"loadPub('ManagePublisherController','')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí nhà xuất bản</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchPublisher\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchPublisher\" placeholder=\"Tìm kiếm nhà xuất bản theo tên hoặc ID\" style=\"width: 400px\">\n"
                    + "                                    <input onclick=\"loadPub('ManagePublisherController'," + index + ",document.getElementById(\'searchPublisher\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Thêm nhà xuất bản mới</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n"
            );
            if (listPub.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>ID nhà xuất bản</th>\n"
                        + "                                    <th>Nhà xuất bản</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            for (int i = 0; i < listPub.size(); i++) {
                count++;
                out.println("          <tr>\n"
                        + "                                    <td>" + listPub.get(i).getPublisherID() + "</td>\n"
                        + "                                    <td>" + listPub.get(i).getName() + "</td>\n");
                String status = listPub.get(i).getStatus();
                if (status.equals("1")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updatePub('ManagePublisherController?index=" + index + "','" + search + "','" + listPub.get(i).getPublisherID() + "'," + index + ",'load')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                   </td>\n"
                        + "                                </tr>\n"
                );
            }
            out.println(" </table>\n"
                    + "         <div class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a id=\"" + j + "\" onclick=\"loadPub('ManagePublisherController?searchPublisher=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
            }
            out.println("       </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>    \n"
                    + "  </body>\n"
                    + "</html>\n");
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