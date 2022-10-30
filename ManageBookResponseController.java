/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.BResponseDetailDAO;
import dao.BookResponseDAO;
import dto.BResponseDetailDTO;
import dto.BookResponseDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ownhi
 */
public class ManageBookResponseController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String read = "readonly=\"\"";
        String note = "Trống";
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String search = new String();
        String status = new String();
        String setStatus = "inline-block";
        String modal = new String();
        String sta = new String();
        String delete = new String();
        BookResponseDTO responseDTO = new BookResponseDTO();
        BResponseDetailDTO responseDetail = new BResponseDetailDTO();
        BookResponseDAO responseDAO = new BookResponseDAO();
        BResponseDetailDAO responseDetailDao = new BResponseDetailDAO();
        List<BookResponseDTO> listResponse = new ArrayList<>();
        List<BResponseDetailDTO> listResponseDetail = new ArrayList<>();
        try {
            search = request.getParameter("searchResponse");
            String responseID = request.getParameter("responseID");
            status = "1";
            String use = request.getParameter("use");
           if (search == null || search == "") {
                search = "";
                if (status.equals("1")) {
                      listResponse = responseDAO.loadResponseByStatus("1");
                    setStatus = "none";
                } else if (status.equals("0")) {     
                    listResponse = responseDAO.loadResponseByStatusNull();
                    setStatus = "inline-block";
                } else if (status.equals("-1")) {
                    listResponse = responseDAO.loadResponseByStatus("0");
                    setStatus = "none";
               }
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchOrder"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
               //Chuyển chuỗi có dấu thành không dấu
                if (status.equals("1")) {
                    listResponse = responseDAO.searchBookResponse(txtSearch, "1");
                    setStatus = "none";
                
               } else if (status.equals("0")) {
                   listResponse = responseDAO.searchBookResponseNull(txtSearch);
                   read = "";
                } else if (status.equals("-1")) {
                    listResponse = responseDAO.searchBookResponse(txtSearch, "0");
                   setStatus = "none";
               }

            }
        } catch (Exception e) {

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
                    + "     <input style=\"display: none;\" id=\"makeModal\" value=\"" + modal + "\">\n"
                    + "  <!-- Modal -->\n"
                    + "        <div class=\"modal\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\n"
                    + "            <div class=\"modal-dialog modal-dialog-centered modal-xl\" role=\"document\">\n"
                    + "                <div class=\"modal-content\">\n"
                    + "                    <div class=\"modal-header\">\n"
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\"></h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div style=\" max-height: calc(100vh - 200px); overflow-y: auto;\" class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-4\">\n"                              
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-8\">\n"
                    + "                                <div><h4><b>Trạng thái</b></h4></div>\n");
          
            out.println("                           <div style=\"text-align: left; width: 160px; margin: auto\" class=\"setStatus\">\n"
                    + "                                    <div style=\"display: " + setStatus + "\">\n"
                    + "                                        <input name=\"setStatus\" type=\"radio\" id=\"status-2\" value=\"store\">\n"
                    + "                                        <label for=\"status-2\">\n"
                    + "                                            <span></span>\n"
                    + "                                            <b style=\"color: green; font-weight: bold;\">Hoàn thành</b>\n"
                    + "                                        </label>\n"
                    + "                                    </div> \n"
                    + "                                    <div style=\"display: " + setStatus + "\">\n"
                    + "                                        <input name=\"setStatus\" type=\"radio\" id=\"status-3\" value=\"store\">\n"
                    + "                                        <label for=\"status-3\">\n"
                    + "                                            <span></span>\n"
                    + "                                            <b style=\"color: red; font-weight: bold;\">Hủy bỏ</b>\n"
                    + "                                        </label>\n"
                    + "                                    </div> \n"
                    + "                                </div>\n"
                    + "                         </div>\n");
            out.println("                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-9\">\n"
                    + "                        <h3><p><b>Chi tiết đơn hàng</b></p></h3>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-3\">\n"
                    + "                            </div>\n"
                    + "                            </div>\n"
                    + "                        <div style=\"border-bottom: 2px solid #cccdcf;\" class=\"row\">\n"
                    + "                            <div class=\"col-md-1\">\n"
                    + "                                <b><p>STT</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-2\"></div>\n"
                    + "                            <div class=\"col-md-5\">\n"
                    + "                                <b><p>Thông tin sách</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-1\">\n"
                    + "                                <b><p>Số lượng</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-3\">\n"
                    + "                                <b><p>Tổng</p></b>\n"
                    + "                            </div>\n"
                    + "                        </div>\n");
            int count = 0;
            for (BResponseDetailDTO rpDetail : listResponseDetail) {
                count++;
                out.println("                        <div style=\"border-bottom: 1px solid #cccdcf; margin-bottom: 10px;\" class=\"row\">\n"
                        + "                            <div class=\"col-md-1\">\n"
                        + "                                <b>" + count + "</b>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\">\n"
                        + "                                <img style=\"width: 100%;\" src=\"" + rpDetail.getIsbn().getImg() + "\">\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-5\">\n"
                        + "                                <b>Giá Nhập: <span style=\"color: #d10024\">" + currencyVN.format(rpDetail.getPrice()) + "</span></b></br>\n"
                        + "                                <b>ISBN: <span style=\"color: #d10024\">" + rpDetail.getIsbn().getIsbn() + "</span></b></br>\n"
                        + "                                <b>Tiêu đề: <span style=\"color: #d10024\">" + rpDetail.getIsbn().getName() + "</span></b></br>\n"
                        + "                                <b>Tác giả: <span style=\"color: #d10024\">" + rpDetail.getIsbn().getAuthorName() + "</span></b></br>\n"
                        + "                                <b>Nhà xuất bản: <span style=\"color: #d10024\">" + rpDetail.getIsbn().getPublisher().getName() + "</span></span></b></br>\n"
                        + "                                <b>Thể loại: <span style=\"color: #d10024\">" + rpDetail.getIsbn().getCategory().getName() + "</span></b>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-1\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + rpDetail.getQuantity() + "</span>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-3\">\n"
                     
                        + "                            </div>\n"
                        + "         </div>\n");
            }
            out.println("</div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n");
            if (read == "") {
                out.println("                        <button type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n");
            }
            out.println("                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Modal -->"
                    + "        <div class=\"product-status mg-tb-15\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n"
                    + "                        <div class=\"product-status-wrap\">\n"
                    + "                            <h2 onclick=\"loadResponse('ManageBookResponseController','" + index + "','" + status + "')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí đơn hàng</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchResponse\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchResponse\" placeholder=\"Tìm kiếm theo mã phản hồi hoặc tên nhân viên\" style=\"width: 520px\">\n"
                    + "                                    <input onclick=\"loadResponse('ManageBookResponseController','" + index + "','" + status + "',document.getElementById(\'searchResponse\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                            </div>\n");
            if (listResponse.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">NOT FOUND!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>Mã Phản Hồi</th>\n"
                        + "                                    <th>Nhân viên phụ trách gửi yêu cầu</th>\n"
                        + "                                    <th>Ngày yêu cầu</th>\n"
                        + "                                    <th>Trạng thái</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            for (BookResponseDTO rpDTO : listResponse) {
                if(rpDTO.getStaff().getName()==null){
                    rpDTO.getStaff().setName("N/A");
                }
                out.println("                              <tr>\n"
                        + "                                    <td>" + rpDTO.getResponseID() + "</td>\n"
                        + "                                    <td>" + rpDTO.getStaff().getName() + "</td>\n"
                        + "                                    <td>" + rpDTO.getDate() + "</td>\n");

                sta = rpDTO.getStatus();
                if (sta == null) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; border: none; color: #d68829; font-weight: bold\" value=\"Đang tiến hành\"/></td>\n");
                } else if (sta.equals("1")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; color: green; border: none; color: green; font-weight: bold\" value=\"Hoàn thành\"/></td>\n");
                } else if (sta.equals("0")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; color: red; border: none; color: red; font-weight: bold\" value=\"Hủy bỏ\"/></td>\n");
                }
                delete = rpDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"updateReponse('ManageBookReponseController?index=" + index + "','" + index + "','" + status + "','" + search + "','load','" + rpDTO.getResponseID() + "')\" data-toggle=\"tooltip\" title=\"Thông tin chi tiết\" class=\"pd-setting-ed\"><i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i></button>\n"
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
                out.println("                    <a id=\"" + j + "\" onclick=\"loadResponse('ManageBookResponseController?searchResponse=" + search + "&index=" + j + "'," + j + ",'"+ status +"')\" href=\"#\">" + j + "</a>\n");
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
