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
 * @author ownhi
 */
//Hữu Hiếu, Quốc Thịnh >>>>>>>>>>
public class ManageCategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String search = new String();
        String modal = new String();
        String success = new String();
        String error = new String();
        CategoryDTO cate = new CategoryDTO();
        CategoryDTO newCate = new CategoryDTO();
        CategoryErrorDTO cateError = new CategoryErrorDTO();
        List<CategoryDTO> listCate = new ArrayList();
        try {
            CategoryDAO cateDAO = new CategoryDAO();
            String categoryIDN = request.getParameter("cateE");// lấy param của category đang được chỉnh sửa
            String use = request.getParameter("use"); //use này đại diện cho hành động remove hoặc edit
            indexCount = cateDAO.countCate();// lay so luong san pham trong kho
            search = request.getParameter("searchCategory");
            String categoryID = request.getParameter("categoryID");
            cate = cateDAO.getCategory(categoryID);
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
                listCate = cateDAO.searchCate9(txtSearch, index);// Tim kiem aat ca sach theo trang
            }
            //hàm if này để làm update và remove
            if (categoryIDN != null && categoryIDN != "") {
                if (use.equals("edit")) {
                    String name = request.getParameter("name");
                    if (name != null && name != "" && name.length() <= 50) {
                        String status = request.getParameter("status");
                        cate.setCategoryID(categoryID);
                        cate.setName(name);
                        cate.setStatus(status);
                        if (cateDAO.updateCate(cate, categoryIDN) && cate.getName() != "") {
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
                    cate.setStatus("0");
                    if (cateDAO.updateCate(cate, categoryIDN)) {
                        modal = "Xóa thành công";
                    }
                } else if (use.equals("recover")) {
                    cate.setStatus("1");
                    if (cateDAO.updateCate(cate, categoryIDN)) {
                        modal = "Khôi phục thành công";
                    }
                }
            } else if (use.equals("add")) {
                String cateID = request.getParameter("categoryID");
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                boolean checkValidation = true;
                if (cateID.isBlank() || cateID.isEmpty() || cateID == "") {
                    cateError.setCategoryIDError("ID không được để trống!");
                    checkValidation = false;
                }

                if (cateID.length() > 10 || cateID.length() < 2) {
                    cateError.setCategoryIDError("Độ dài ID nhà xuất bản phải từ 2-10 kí tự!");
                    checkValidation = false;
                }
                //Check duplicate ID
                boolean checkDuplicateID = cateDAO.checkDuplicateID(cateID);
                if (checkDuplicateID) {
                    cateError.setCategoryIDError("ID đã được sử dụng!");
                    checkValidation = false;
                }
                //Check name
                if (name.length() > 50 || name.length() < 2) {
                    cateError.setNameError("Độ dài tên nhà xuất bản phải từ 2-50 kí tự!");
                    checkValidation = false;
                }
                if (name.isEmpty() || name.isBlank()) {
                    cateError.setNameError("Tên không được để trống!");
                    checkValidation = false;
                }

                //Check duplicate name
                boolean checkDuplicateN = cateDAO.checkDuplicateName(name);
                if (checkDuplicateN) {
                    cateError.setNameError("Tên đã được sử dụng!");
                    checkValidation = false;
                }

                //Neu validate thanh cong
                newCate = new CategoryDTO(cateID, name, status);
                if (checkValidation) {
                    boolean checkCreate = cateDAO.createCate(newCate);
                    if (checkCreate) {
                        success = "Thêm nhà thể loại mới thành công!";
                    }
                }
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
                listCate = cateDAO.searchCate9(txtSearch, index);//Tìm kiếm tất cả cate thỏa yêu cầu theo phân trang
            }
        } catch (Exception e) {
            log("Error at ManageCategoryController: " + e.toString());
        } finally {
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
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">"
                    + "<!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-lg\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin thể loại</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <p>Mã thể loại</p>\n"
                    + "                                <p style=\"font-weight: bold;\" id=\"cateID\" placeholder=\"Nhập mã thể loại sách\">" + cate.getCategoryID() + "</p>\n"
                    + "                                <input id=\"cateE\" placeholder=\"Nhập mã nhà xuất bản\" value=\"" + cate.getCategoryID() + "\" style=\"display: none;\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <p>Thể loại</p>\n"
                    + "                                <input style=\"font-weight: bold;\" id=\"cateName\" placeholder=\"Nhập thể loại sách\" value=\"" + cate.getName() + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-4\">\n");
            if (cate.getStatus().equals("1")) {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"remove\" onclick=\"cate('ManageCategoryController?index=" + index + "','remove'," + index + ")\" data-toggle=\"tooltip\" title=\"Xóa\" class=\"pd-setting-ed\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>");
            } else {
                out.println("                                <div style=\"display: inline-block; margin-right: 10px;\"><p>Tình trạng: <i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i></p></div>\n"
                        + "                             <button data-bs-dismiss=\"modal\" id=\"recover\" onclick=\"cate('ManageCategoryController?index=" + index + "','recover'," + index + ")\" data-toggle=\"tooltip\" title=\"Khôi phục\" class=\"pd-setting-ed\"><i class=\"fas fa-trash-restore-alt\" aria-hidden=\"true\"></i></button>\n");
            }
            out.println("</div>\n"
                    + "                    </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button data-bs-dismiss=\"modal\" onclick=\"cate('ManageCategoryController?index=" + index + "','edit'," + index + ")\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n"
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
                    + "                        <h4 class=\"modal-title\">Thêm thể loại sách</h4>\n"
                    + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n"
                    + "                    </div>\n"
                    + "                    <!-- Modal body -->\n"
                    + "                    <div id=\"mcontent\" class=\"modal-body\">\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: green; font-weight: bold; display: inline-block; border-radius: 10px; font-size: 20px;\">" + success + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>ID thể loại</label>\n"
                    + "                                <input placeholder=\"Nhập ID của thể loại sách\" value=\"" + newCate.getCategoryID() + "\" style=\"font-weight: bold\" id=\"pubid\" name=\"isbn\" type=\"text\" class=\"form-control c\" minlength=\"2\" maxlength=\"10\">\n"
                    + "                            </div>\n"
                    + "                           <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cateError.getCategoryIDError() + "</p></br>\n"
                    + "                            <div class=\"form-group\">\n"
                    + "                                <label>Thể loại</label>\n"
                    + "                                <input placeholder=\"Nhập tên thể loại sách\" value=\"" + newCate.getName() + "\" style=\"font-weight: bold\" name=\"name\" type=\"text\" class=\"form-control c\" minlength=\"1\" maxlength=\"50\">\n"
                    + "                            </div>\n"
                    + "                            <p style=\"width: 60%; color: white; background-color: red; font-weight: bold; display: inline-block; border-radius: 10px;\">" + cateError.getNameError() + "</p></br>\n"
                    + "                        </div>"
                    + "                            <div class=\"modal-footer\">\n"
                    + "                                <input style=\"display: none;\" type=\"button\" class=\"btn\" value=\"z\">\n"
                    + "                                <button style=\"background-color: #494f57; color: white\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Hủy</button>\n"
                    + "                                <input data-bs-dismiss=\"modal\" onclick=\"addCate('ManageCategoryController','c')\" type=\"submit\" name=\"action\" class=\"btn btn-success\" value=\"Tạo\">\n"
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
                    + "                            <h2 onclick=\"load('ManageCategoryController','" + index + "')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí thể loại sách</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchCategory\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchCategory\" placeholder=\"Tìm kiếm theo ID/tên thể loại sách\" style=\"width: 400px\">\n"
                    + "                                    <input onclick=\"loadCate('ManageCategoryController','" + index + "',document.getElementById(\'searchCategory\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                                <button style=\"border: none; color: white; background-color: #009933;\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#myModal\">\n"
                    + "                                    <img style=\" height: 20px; width: 20px;\" src=\"IMG/plus.png\"/>\n"
                    + "                                    <b>Thêm thể loại mới</b>\n"
                    + "                                </button>\n"
                    + "                            </div>\n");
            if (listCate.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">NOT FOUND!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>ID thể loại</th>\n"
                        + "                                    <th>Thể loại</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            int count = 0;
            for (int i = 0; i < listCate.size(); i++) {
                count++;
                out.println("          <tr>\n"
                        + "                                    <td>" + listCate.get(i).getCategoryID() + "</td>\n"
                        + "                                    <input class=\"" + count + "\" value=\"" + listCate.get(i).getCategoryID() + "\" type=\"hidden\"/>"
                        + "                                    <td>" + listCate.get(i).getName() + "</td>\n");
                String status = listCate.get(i).getStatus();
                if (status.equals("1")) {
                    out.println("                                  <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                } else {
                    out.println("                                    <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" class=\"" + count + "\" type=\"hidden\" value=\"" + status + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updateCate('ManageCategoryController?index=" + index + "','" + search + "','" + listCate.get(i).getCategoryID() + "'," + index + ",'load')\" data-toggle=\"tooltip\" title=\"Edit\" class=\"pd-setting-ed\"><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></button>\n"
                        + "                                   </td>\n"
                        + "                                </tr>"
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
                out.println("                    <a id=\"" + j + "\" onclick=\"loadCate('ManageCategoryController?searchCategory=" + search + "&index=" + j + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
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