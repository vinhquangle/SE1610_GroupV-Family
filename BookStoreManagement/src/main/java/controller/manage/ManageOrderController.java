/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import dao.BookDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.PromotionDTO;
import dto.StaffDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thịnh
 */
public class ManageOrderController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index = 0;
        int indexCount = 0;
        String getOrder = "";
        boolean check = false;
        String read = "readonly=\"\"";
        String who = "";
        String note = "Trống";
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String search = new String();
        String status = new String();
        String orderID = new String();
        String setStatus = "inline-block";
        String modal = new String();
        String sta = new String();
        String delete = new String();
        StaffDTO staff = new StaffDTO();
        OrderDTO order = new OrderDTO();
        BookDAO bookDao = new BookDAO();
        OrderDAO orderDao = new OrderDAO();
        OrderDetailDAO orderDetailDao = new OrderDetailDAO();
        List<OrderDTO> listOrder = new ArrayList<>();
        List<OrderDetailDTO> listOrderDetail = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            search = request.getParameter("searchOrder");
            orderID = request.getParameter("orderID");
            status = request.getParameter("status");
            String use = request.getParameter("use");
            String edit = request.getParameter("edit");
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            if (use == null) {

            } else if (use.equals("edit")) {
                if (edit != null) {
                    if (edit.equals("1")) {
                        if (orderDao.updateStatus("1", staff.getStaffID(), orderID)) {
                            modal = "Xác nhận đơn hàng thành công";
                        }
                    } else if (edit.equals("0")) {
                        if (orderDao.updateStatus("0", staff.getStaffID(), orderID)) {
                            order = orderDao.loadOrderByOrderID(orderID);
                            listOrderDetail = orderDetailDao.loadAllOrderDetailByOrderID(orderID);
                            for (OrderDetailDTO orderDetailDTO : listOrderDetail) {
                                bookDao.refundQuantity(orderDetailDTO.getIsbn().getIsbn(), orderDetailDTO.getQuantity());
                            }
                            modal = "Hủy đơn hàng thành công";
                        }
                    }
                }
            } else if (use.equals("get")) {
                if (orderDao.getOrder(staff.getStaffID(), orderID)) {
                    modal = "Nhận đơn hàng thành công";
                } else {
                    modal = "Nhận đơn hàng thất bại";
                }
            }
            if (search == null || search == "") {
                search = "";
                if (status.equals("1")) {
                    indexCount = orderDao.loadOrderByStatus("1").size();
                    listOrder = orderDao.load9OrderByStatus("1", index);
                } else if (status.equals("0")) {
                    indexCount = orderDao.loadOrderByStatusNull().size();
                    listOrder = orderDao.load9OrderByStatusNull(index);
                    read = "";
                    note = "Ghi chú cho đơn hàng";
                    getOrder = "get";
                } else if (status.equals("-1")) {
                    indexCount = orderDao.loadOrderByStatus("0").size();
                    listOrder = orderDao.load9OrderByStatus("0", index);
                } else if (status.equals("ship")) {
                    if (staff.getRole().equals("Deliverer")) {
                        indexCount = orderDao.loadOrderByDeliverer(staff.getStaffID()).size();
                        listOrder = orderDao.load9OrderByDeliverer(staff.getStaffID(), index);
                        read = "";
                        note = "Ghi chú cho đơn hàng";
                        getOrder = "get";
                    }
                }
            } else {
                //Chuyển chuỗi có dấu thành không dấu
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchOrder"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                //Chuyển chuỗi có dấu thành không dấu
                if (status.equals("1")) {
                    indexCount = orderDao.searchOrder(txtSearch, "1").size();
                    listOrder = orderDao.search9Order(txtSearch, index, "1");
                } else if (status.equals("0")) {
                    indexCount = orderDao.searchOrderNull(txtSearch).size();
                    listOrder = orderDao.search9OrderNull(txtSearch, index);
                    read = "";
                    note = "Ghi chú cho đơn hàng";
                    getOrder = "get";
                } else if (status.equals("-1")) {
                    indexCount = orderDao.searchOrder(txtSearch, "0").size();
                    listOrder = orderDao.search9Order(txtSearch, index, "0");
                } else if (status.equals("ship")) {
                    if (staff.getRole().equals("Deliverer")) {
                        indexCount = orderDao.searchOrderDeliverer(staff.getStaffID(), txtSearch).size();
                        listOrder = orderDao.search9OrderDeliverer(staff.getStaffID(), txtSearch, index);
                        read = "";
                        note = "Ghi chú cho đơn hàng";
                        getOrder = "get";
                    }
                }
            }
            order = orderDao.loadOrderByOrderID(orderID);
            listOrderDetail = orderDetailDao.loadAllOrderDetailByOrderID(orderID);
            if (order.getPromotion().getPromotionID() == null) {
                order.setPromotion(new PromotionDTO());
                order.getPromotion().setPromotionID("Không có khuyến mãi");
                order.getPromotion().setDescription("Không có khuyến mãi");
            }
            if (order.getDescription() == null) {
                order.setDescription("");
            }
            try {
                if (order.getStaff() == null || order.getStaff().getStaffID().equals("")) {
                    order.setStaff(new StaffDTO());
                    check = true;
                }
            } catch (Exception e) {
                order.setStaff(new StaffDTO());
                check = true;
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
                    + "                        <h5 style=\"width: 1100px;\" class=\"modal-title\" id=\"exampleModalLongTitle\">Thông tin đơn hàng (Mã đơn: " + order.getOrderID() + ")</h5>\n"
                    + "                        <button type=\"button\" class=\"close\" data-bs-dismiss=\"modal\" aria-label=\"Close\">\n"
                    + "                            <span aria-hidden=\"true\">&times;</span>\n"
                    + "                        </button>\n"
                    + "                    </div>\n"
                    + "                    <div style=\" max-height: calc(100vh - 200px);\" class=\"modal-body\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-4\">\n"
                    + "                                <div><b>Khuyến mãi</b></div>\n"
                    + "                                <textarea rows=\"2\" style=\"width: 100%; border: none; text-align: center\" readonly=\"\" placeholder=\"Nội dung khuyến mãi\">" + order.getPromotion().getDescription() + "</textarea>\n"
                    + "                                <div><b>Tổng phụ</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + currencyVN.format(order.getSubtotal()) + "\">\n"
                    + "                                <div><b>Phần trăm khuyến mãi</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + order.getDiscount() + "%\">\n"
                    + "                                <div><b>Phí vận chuyển</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + currencyVN.format(order.getDeliveryCost()) + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-8\">\n"
                    + "                                <div><h4><b>Trạng thái</b></h4></div>\n");
            sta = order.getStatus();
            if (sta == null) {
                setStatus = "inline-block";
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: red; border: none; color: #d68829; font-weight: bold; text-align: center;\" value=\"Đang tiến hành\"/></h4></div>\n");
            } else if (sta.equals("1")) {
                setStatus = "none";
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: green; border: none; color: green; font-weight: bold; text-align: center;\" value=\"Hoàn thành\"/></h4></div>\n");
            } else if (sta.equals("0")) {
                setStatus = "none";
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: red; border: none; color: red; font-weight: bold; text-align: center;\" value=\"Hủy bỏ\"/></h4></div>\n");
            }
            out.println("                           <div style=\"text-align: left; width: 160px; margin: auto\" class=\"setStatus\">\n"
                    + "                                    <div style=\"display: " + setStatus + "\">\n"
                    + "                                        <input name=\"setStatus\" type=\"radio\" id=\"status-2\" value=\"1\">\n"
                    + "                                        <label for=\"status-2\">\n"
                    + "                                            <span></span>\n"
                    + "                                            <b style=\"color: green; font-weight: bold;\">Hoàn thành</b>\n"
                    + "                                        </label>\n"
                    + "                                    </div> \n"
                    + "                                    <div style=\"display: " + setStatus + "\">\n"
                    + "                                        <input name=\"setStatus\" type=\"radio\" id=\"status-3\" value=\"0\">\n"
                    + "                                        <label for=\"status-3\">\n"
                    + "                                            <span></span>\n"
                    + "                                            <b style=\"color: red; font-weight: bold;\">Hủy bỏ</b>\n"
                    + "                                        </label>\n"
                    + "                                    </div> \n"
                    + "                                </div>\n"
                    + "                            <div style=\"width: 500px; text-align: center; margin-top: 30px; margin-left: auto; margin-right: auto\">\n"
                    + "                        <h3><b>Tổng thành tiền: " + currencyVN.format(order.getTotal()) + "</b></h3>\n"
                    + "                            </div>\n"
                    + "                         </div>\n");
            out.println("                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                                <div><b>Ghi chú</b></div>\n"
                    + "                                <textarea " + read + " style=\"width: 100%;\" placeholder=\"" + note + "\">" + order.getDescription() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                        <h3><p><b>Chi tiết đơn hàng</b></p></h3>\n"
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
                    + "                        </div>\n"
                    + "                 <div style=\"overflow-y: scroll; overflow-x: hidden; height: 250px\">\n");
            int count = 0;
            for (OrderDetailDTO orderDetailDTO : listOrderDetail) {
                count++;
                out.println("                     <div style=\"border-bottom: 1px solid #cccdcf; margin-bottom: 10px;\" class=\"row\">\n"
                        + "                            <div class=\"col-md-1\">\n"
                        + "                                <b>" + count + "</b>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\">\n"
                        + "                                <img style=\"width: 100%;\" src=\"" + orderDetailDTO.getIsbn().getImg() + "\">\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-5\">\n"
                        + "                                <b>Giá bán: <span style=\"color: #d10024\">" + currencyVN.format(orderDetailDTO.getPrice()) + "</span></b></br>\n"
                        + "                                <b>ISBN: <span style=\"color: #d10024\">" + orderDetailDTO.getIsbn().getIsbn() + "</span></b></br>\n"
                        + "                                <b>Tiêu đề: <span style=\"color: #d10024\">" + orderDetailDTO.getIsbn().getName() + "</span></b></br>\n"
                        + "                                <b>Tác giả: <span style=\"color: #d10024\">" + orderDetailDTO.getIsbn().getAuthorName() + "</span></b></br>\n"
                        + "                                <b>Nhà xuất bản: <span style=\"color: #d10024\">" + orderDetailDTO.getIsbn().getPublisher().getName() + "</span></span></b></br>\n"
                        + "                                <b>Thể loại: <span style=\"color: #d10024\">" + orderDetailDTO.getIsbn().getCategory().getName() + "</span></b>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-1\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + orderDetailDTO.getQuantity() + "</span>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-3\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + currencyVN.format(orderDetailDTO.getTotal()) + "</span>\n"
                        + "                            </div>\n"
                        + "         </div>\n"
            
            );
            }
            out.println("                   </div>\n"
                    + "                 </div>\n"
                    + "                    <div class=\"modal-footer\">\n"
                    + "                        <button style=\"display: none;\" type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n"
                    + "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n");
            if (read == "" && !staff.getRole().equals("Deliverer")) {
                out.println("                        <button data-bs-dismiss=\"modal\" onclick=\"loadOrder('ManageOrderController?index=" + index + "'," + index + ",'" + status + "','" + search + "','edit','" + order.getOrderID() + "')\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n");
            } else if (order.getStaff().getStaffID().equals(staff.getStaffID())) {
                out.println("                        <button data-bs-dismiss=\"modal\" onclick=\"loadOrder('ManageOrderController?index=" + index + "'," + index + ",'" + status + "','" + search + "','edit','" + order.getOrderID() + "')\" type=\"button\" class=\"btn btn-primary\">Lưu thay đổi</button>\n");
            }
            if (!getOrder.equals("") && order.getAddress() != null && staff.getRole().equals("Deliverer") && check) {
                out.println("                        <button data-bs-dismiss=\"modal\" onclick=\"loadOrder('ManageOrderController?index=" + index + "'," + index + ",'" + status + "','" + search + "','get','" + order.getOrderID() + "')\" type=\"button\" class=\"btn btn-primary\">Nhận đơn hàng</button>\n");
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
                    + "                            <h2 onclick=\"loadOrder('ManageOrderController','" + index + "','" + status + "')\" style=\"display: inline-block; cursor: pointer; background-color: #494f57; color: white; padding: 5px; float: left;\">Quản lí đơn hàng</h2>\n"
                    + "                            <div style=\"display: inline-block; float: right;\" class=\"add-product\">\n"
                    + "                             <h5 style=\"margin-right: 120px; display: inline-block;\">\n"
                    + "                             <form>"
                    + "                                    <input id=\"searchOrder\" class=\"input\" type=\"text\" value=\"" + search + "\" name=\"searchOrder\" placeholder=\"Tìm kiếm theo mã đơn hoặc tên khách hàng/nhân viên\" style=\"width: 520px\">\n"
                    + "                                    <input onclick=\"loadOrder('ManageOrderController','" + index + "','" + status + "',document.getElementById(\'searchOrder\').value)\" class=\"search-btn\" type=\"submit\" name=\"button\" value=\"Tìm kiếm\" name=\"action\" style=\"width: 100px\"/>\n"
                    + "                              </form>"
                    + "                            </h5>"
                    + "                            </div>\n");
            if (listOrder.size() <= 0) {
                out.println("<div class=\"\">\n"
                        + "                    <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>\n"
                        + "                </div>");
            } else {
                out.println("                            <table>\n"
                        + "                                <tr>\n"
                        + "                                    <th>Mã đơn</th>\n"
                        + "                                    <th>Tên khách hàng</th>\n"
                        + "                                    <th>Nhân viên phụ trách</th>\n"
                        + "                                    <th>Địa chỉ giao hàng</th>\n"
                        + "                                    <th>Ngày đặt</th>\n"
                        + "                                    <th>Phí vận chuyển</th>\n"
                        + "                                    <th>Tổng thành tiền</th>\n"
                        + "                                    <th>Trạng thái</th>\n"
                        + "                                    <th>Tình trạng</th>\n"
                        + "                                </tr>\n");
            }
            for (OrderDTO orderDTO : listOrder) {
                if (orderDTO.getCustomer().getName() == null) {
                    orderDTO.getCustomer().setName("N/A");
                }
                if (orderDTO.getAddress() == null) {
                    orderDTO.setAddress("Nhận hàng tại nhà sách Phương Nam");
                }
                if (orderDTO.getStaff().getName() == null) {
                    orderDTO.getStaff().setName("N/A");
                    who = "style=\"color: red\"";
                } else {
                    who = "";
                }
                out.println("                              <tr>\n"
                        + "                                    <td>" + orderDTO.getOrderID() + "</td>\n"
                        + "                                    <td>" + orderDTO.getCustomer().getName() + "</td>\n"
                        + "                                    <td " + who + ">" + orderDTO.getStaff().getName() + "</td>\n"
                        + "                                    <td><textarea readonly=\"\" rows=\"3\" style=\"width: 350px;\">" + orderDTO.getAddress() + "</textarea></td>\n"
                        + "                                    <td>" + orderDTO.getDate() + "</td>\n"
                        + "                                    <td>" + currencyVN.format(orderDTO.getDeliveryCost()) + "</td>\n"
                        + "                                    <td>" + currencyVN.format(orderDTO.getTotal()) + "</td>\n");
                sta = orderDTO.getStatus();
                if (sta == null) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; border: none; color: #d68829; font-weight: bold\" value=\"Đang tiến hành\"/></td>\n");
                } else if (sta.equals("1")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; color: green; border: none; color: green; font-weight: bold\" value=\"Hoàn thành\"/></td>\n");
                } else if (sta.equals("0")) {
                    out.println("                              <td><input readonly=\"\" style=\"width: 120px; color: red; border: none; color: red; font-weight: bold\" value=\"Hủy bỏ\"/></td>\n");
                }
                delete = orderDTO.getDelete();
                if (delete.equals("0")) {
                    out.println("                              <td><i style=\"color: green;\" class=\"fa fa-check\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: green; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                } else {
                    out.println("                              <td><i style=\"color: red;\" class=\"fa fa-times\" aria-hidden=\"true\"></i><input readonly=\"\" style=\"width: 30px; color: red; border: none;\" type=\"hidden\" value=\"" + delete + "\"/></td>\n");
                }
                out.println("                                    <td>\n"
                        + "                                        <button id=\"edit\" onclick=\"loadOrder('ManageOrderController?index=" + index + "','" + index + "','" + status + "','" + search + "','load','" + orderDTO.getOrderID() + "')\" data-toggle=\"tooltip\" title=\"Thông tin chi tiết\" class=\"pd-setting-ed\"><i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i></button>\n"
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
                out.println("                    <a id=\"" + j + "\" onclick=\"loadOrder('ManageOrderController?searchOrder=" + search + "&index=" + j + "'," + j + ",'" + status + "')\" href=\"#\">" + j + "</a>\n");
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
