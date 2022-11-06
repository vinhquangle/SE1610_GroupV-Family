/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thịnh
 */
public class ViewOrderDetailController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        OrderDetailDAO detailDao = new OrderDetailDAO();
        OrderDAO orderDao = new OrderDAO();
        OrderDTO order = new OrderDTO();
        List<OrderDetailDTO> listDetail = new ArrayList<>();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {
            String orderID = request.getParameter("orderID");
            order = orderDao.loadOrderByOrderID(orderID);
            listDetail = detailDao.loadAllOrderDetailByOrderID(orderID);
            if (order.getPromotion().getDescription() == null) {
                order.getPromotion().setDescription("Không có khuyến mãi");
            }
            if (order.getAddress() == null) {
                order.setAddress("Nhận hàng tại nhà sách Phương Nam");
            }
            if(order.getDescription() == null){
                order.setDescription("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter out = response.getWriter();
            out.println(" <div class=\"row\">\n"
                    + "                            <div style=\"text-align: center\" class=\"col-md-6\">\n"
                    + "                                <div><b>Khuyến mãi</b></div>\n"
                    + "                                <textarea rows=\"2\" style=\"width: 100%; border: none; text-align: center\" readonly=\"\" placeholder=\"Nội dung khuyến mãi\">" + order.getPromotion().getDescription() + "</textarea>\n"
                    + "                                <div><b>Tổng phụ</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + currencyVN.format(order.getSubtotal()) + "\">\n"
                    + "                                <div><b>Phần trăm khuyến mãi</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + order.getDiscount() * 100 + "%\">\n"
                    + "                                <div><b>Phí vận chuyển</b></div>\n"
                    + "                                <input readonly=\"\" style=\"border: none; text-align: center\" placeholder=\"Trống\" value=\"" + currencyVN.format(order.getDeliveryCost()) + "\">\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                                <div style=\"text-align: center;\">Tình trạng</div>\n");
            String sta = order.getStatus();
            if (sta == null) {
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: red; border: none; color: #d68829; font-weight: bold; text-align: center;\" value=\"Đang tiến hành\"/></h4></div>\n");
            } else if (sta.equals("1")) {
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: green; border: none; color: green; font-weight: bold; text-align: center;\" value=\"Hoàn thành\"/></h4></div>\n");
            } else if (sta.equals("0")) {
                out.println("                              <div><h4><input readonly=\"\" style=\"width: 100%; color: red; border: none; color: red; font-weight: bold; text-align: center;\" value=\"Hủy bỏ\"/></h4></div>\n");
            }
            out.println("                           </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-12\">\n"
                    + "                                <div>Ghi chú</div>\n"
                    + "                                <textarea readonly=\"\" style=\"width: 100%; font-weight: bold;\" placeholder=\"Ghi chú cho đơn hàng\">" + order.getDescription() + "</textarea>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                        <h3 style=\"text-align: center;\"><b>Chi tiết đơn hàng</b></h3>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-6\">\n"
                    + "                        <h3 style=\"text-align: center;\"><b>Tổng thành tiền: " + currencyVN.format(order.getTotal()) + "</b></h3>\n"
                    + "                            </div>\n"
                    + "                            </div>\n"
                    + "                        <div style=\"border-bottom: 2px solid #cccdcf\" class=\"row\">\n"
                    + "                            <div class=\"col-md-1\">\n"
                    + "                                <b><p>STT</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"col-md-2\"></div>\n"
                    + "                            <div class=\"col-md-5\">\n"
                    + "                                <b><p>Thông tin sách</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                    + "                                <b><p>Số lượng</p></b>\n"
                    + "                            </div>\n"
                    + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                    + "                                <b><p>Tổng</p></b>\n"
                    + "                            </div>\n"
                    + "                        </div>\n");
            for (OrderDetailDTO orderDetailDTO : listDetail) {
                out.println("                        <div style=\"border-bottom: 1px solid #cccdcf\" class=\"row\">\n"
                        + "                            <div class=\"col-md-1\">\n"
                        + "                                <b>" + orderDetailDTO.getDetailID() + "</b>\n"
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
                        + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + orderDetailDTO.getQuantity() + "</span>\n"
                        + "                            </div>\n"
                        + "                            <div style=\"text-align: center;\" class=\"col-md-2\">\n"
                        + "                                <span style=\"color: #d10024; font-weight: bold\">" + currencyVN.format(orderDetailDTO.getTotal()) + "</span>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
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
