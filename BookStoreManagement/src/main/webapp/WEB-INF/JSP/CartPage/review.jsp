<%-- 
    Document   : receipt
    Created on : Oct 15, 2022, 3:38:48 PM
    Author     : PC
--%>

<%@page import="dto.PromotionDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.BookDTO"%>
<%@page import="cart.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Receipt</title>
    </head>
    <body>
        <%
            String paymentId = (String) request.getAttribute("paymentId");
            String PayerID = (String) request.getAttribute("PayerID");
            double feeShip = Double.parseDouble((String) session.getAttribute("FEE_SHIP"));
            int day = java.time.LocalDate.now().getDayOfMonth();
            int month = java.time.LocalDate.now().getMonthValue();
            int year = java.time.LocalDate.now().getYear();
            Cart cart = (Cart) session.getAttribute("CART");
        %>
        <div class="container">
            <div class="row">
                <div style="height: 100%; width: 100%; background-color: white;" class="well col-xs-10 col-sm-10 col-md-6">
                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <address>
                                <strong>Nhà sách Phương Nam</strong>
                                <br>
                                Đ. Nguyễn Ảnh Thủ, Ấp Đông, Hóc Môn,
                                <br>
                                Thành phố Hồ Chí Minh
                                <br>
                                <abbr title="Phone">Hotline: </abbr> 1900-6656
                            </address>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6 text-right">
                            <p>
                                <em>Thời gian: Ngày <%= day%>, tháng <%= month%>, năm <%= year%></em>
                            </p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="text-center">
                            <h1>Hóa đơn</h1>
                        </div>
                        </span>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Tiêu đề sách</th>
                                    <th>Số lượng</th>
                                    <th class="text-center">Giá bán</th>
                                    <th class="text-center">Tổng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    double totalS = 0;
                                    double discount = 0;
                                    double total = 0;
                                    for (BookDTO book : cart.getCart().values()) {
                                        total += book.getPrice() * book.getQuantity();
                                    }
                                    List<PromotionDTO> listPro = (List<PromotionDTO>) session.getAttribute("PROMOTION");
                                    for (PromotionDTO promotionDTO : listPro) {
                                        if (promotionDTO.getCondition() <= total && promotionDTO.getDiscount() >= discount) {
                                            discount = promotionDTO.getDiscount();
                                        }
                                    }
                                    discount = Double.parseDouble(String.format("%.2f", discount));
                                    for (BookDTO book : cart.getCart().values()) {
                                        double price = (double) Math.round((book.getPrice() * (1 - discount) / 24000) * 100) / 100.0;
                                        total = price * book.getQuantity();
                                        totalS += total;
                                %>
                                <tr>
                                    <td class="col-md-3"><img style="width: 150px;" src="<%= book.getImg()%>"></td>
                                    <td class="col-md-6"><em><%= book.getName()%></em></h4></td>
                                    <td class="col-md-1" style="text-align: center"> <%= book.getQuantity()%> </td>
                                    <td class="col-md-1 text-center">$<%= price%></td>
                                    <td class="col-md-1 text-center">$<%= total%></td>
                                </tr>
                                <%
                                    }
                                    totalS = Double.parseDouble(String.format("%.2f", totalS));
                                %>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td style="width: 100%;" class="text-right">
                                        <p>
                                            <strong>Tổng phụ: </strong>
                                        </p>
                                        <p>
                                            <strong></strong>
                                            <strong>Phí vận chuyển: </strong>
                                        </p></td>
                                    <td class="text-center">
                                        <p>
                                            <strong>$<%= totalS%></strong>
                                        </p>
                                        <p>
                                            <strong>$<%= feeShip%></strong>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td class="text-right"><h4><strong>Tổng thành tiền: </strong></h4></td>
                                    <td class="text-center text-danger"><h4><strong>$<%= totalS + feeShip%></strong></h4></td>
                                </tr>
                            </tbody>
                        </table>
                        <form action="ExcutePaymentController" method="POST">
                            <input type="hidden" name="paymentId" value="<%= paymentId%>">
                            <input type="hidden" name="PayerID" value="<%= PayerID%>">
                            <button  type="submit" class="btn btn-success btn-lg btn-block">
                                Thanh toán <span class="glyphicon glyphicon-chevron-right"></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
