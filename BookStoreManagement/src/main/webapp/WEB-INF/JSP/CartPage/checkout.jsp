<%-- 
    Document   : checkout
    Created on : Sep 20, 2022, 10:21:44 PM
    Author     : PC
--%>

<%@page import="dto.PromotionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Checkout</title>
    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row" style="margin-top: 200px">
                    <div class="col-md-12">
                        <h3 class="breadcrumb-header">Thủ tục thanh toán</h3>
                    </div>
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /BREADCRUMB -->
        <!-- SECTION -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <form id="myForm" action="CheckoutController" method="GET">
                        <div class="col-md-7">
                            <!-- Shipping Details -->
                            <div id="info" class="billing-details">
                                <div class="section-title">
                                    <h3 class="title">Địa chỉ giao hàng</h3>
                                </div>
                                <div class="form-group">
                                    <input class="input" type="text" name="first-name" placeholder="Họ và tên" value="<%= cus.getName()%>">
                                </div>
                                <div class="form-group">
                                    <input class="input" type="email" name="email" placeholder="Email" value="<%= cus.getEmail()%>">
                                </div>
                                <div class="form-group">
                                    <input class="input" type="text" name="address" placeholder="Địa chỉ" value="<%= cus.getAddress()%>">
                                </div>
                            </div>
                            <!-- /Shipping Details -->

                            <!-- Order notes -->
                            <p id="addr" style="display: none; color: #1e1e27; font-size: 20px;">Địa chỉ nhà sách: <span style="color: #d10024">146C Đ. Nguyễn Ảnh Thủ, Ấp Đông, Hóc Môn, Thành phố Hồ Chí Minh 700000</span>
                                (Lưu ý khi đến nhận hàng, quý khách vui lòng đọc số điện thoại đăng kí tài khoản để được hỗ trợ lấy hàng. Xin chân thành cảm ơn quý khách)</p>
                            <div class="order-notes">
                                <textarea name="des" class="input" placeholder="Ghi chú"></textarea>
                            </div>
                            <!-- /Order notes -->
                        </div>

                        <!-- Order Details -->
                        <div class="col-md-5 order-details">
                            <div class="section-title text-center">
                                <h3 class="title">Đơn hàng của bạn</h3>
                            </div>

                            <div class="order-summary">
                                <div class="order-col">
                                    <div><strong>Sản phẩm</strong></div>
                                    <div><strong>Tổng</strong></div>
                                </div>

                                <div class="order-products">
                                    <%                                        Cart cart = (Cart) session.getAttribute("CART");
                                        if (cart != null && cart.getCart().size() > 0) {
                                            double discount = 0;
                                            double ship = 24000;
                                            String des = new String();
                                            String totalS = new String();
                                            Locale localeVN = new Locale("vi", "VN");
                                            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                            List<PromotionDTO> listPro = (List<PromotionDTO>) session.getAttribute("PROMOTION");
                                            for (BookDTO book : cart.getCart().values()) {
                                                totalS = currencyVN.format(book.getPrice() * book.getQuantity());
                                    %>
                                    <div class="order-col">
                                        <div><%= book.getQuantity()%>x <%= book.getName()%></div>
                                        <div><%= totalS%></div>
                                    </div>
                                    <%
                                        }
                                    %>
                                    <script>
                                        function submitForm() {
                                            document.getElementById("myForm").submit();
                                        }
                                        function hide() {
                                            document.getElementById("payment-5").checked = true;
                                            document.getElementById("info").style.display = "none";
                                            document.getElementById("addr").style.display = "inline-block";
                                            document.getElementById("shipp").innerHTML = "Miễn phí";
                                            document.getElementById("total").innerHTML = document.getElementById("inputTotal").value;
                                        }
                                        function show() {
                                            document.getElementById("info").style.display = "inline-block";
                                            document.getElementById("addr").style.display = "none";
                                            document.getElementById("shipp").innerHTML = document.getElementById("inputShip").value;
                                            document.getElementById("total").innerHTML = document.getElementById("outputTotal").value;
                                        }
                                    </script>
                                </div>
                                <div class="payment-method">
                                    <div class="input-radio">
                                        <input checked="" name="method" type="radio" id="payment-4" value="ship">
                                        <label onclick="show()" for="payment-4">
                                            <span></span>
                                            <b>Giao hàng tận nơi (có thể mất phí)</b>
                                        </label>
                                        <div class="caption">
                                        </div>
                                        <div class="caption">
                                            <p>Đơn hàng của quý khách sẽ được giao theo địa chỉ chỉ định (đối với đơn hàng trên 360k sẽ được miễn phí giao hàng)</p>
                                        </div>
                                    </div> 
                                    <div class="input-radio">
                                        <input name="method" type="radio" id="payment-5" value="store">
                                        <label onclick="hide()" for="payment-5">
                                            <span></span>
                                            <b>Nhận tại cửa hàng</b>
                                        </label>
                                        <div class="caption">
                                            <p>Quý khách sẽ nhận hàng tại cửa hàng(miễn phí giao hàng) </p>
                                        </div>
                                    </div> 
                                </div>
                                <%
                                    for (PromotionDTO promotionDTO : listPro) {
                                        if (promotionDTO.getCondition() <= total && promotionDTO.getDiscount() >= discount) {
                                            discount = promotionDTO.getDiscount();
                                            des = promotionDTO.getDescription();
                                        }
                                    }
                                    if (discount > 0) {
                                %> 
                                <div class="input-radio">
                                    <input checked="" name="promotion" type="radio" id="promotion">
                                    <label for="promotion">
                                        <span style="background-color: green; color: green; border: none;" ></span>
                                        <b>Khuyến mãi</b>
                                    </label>
                                    <div class="caption">
                                        <div class="order-col">
                                            <div>Phần trăm khuyến mãi</div></br>
                                            <div style="font-size: 20px; color: #d10024"><strong><%= (int) (discount * 100)%>%</strong></div></br>
                                        </div>
                                    </div>
                                    <div class="caption">
                                        <p><%= des%></p>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                                <div class="order-col">
                                    <div><strong>Tổng hóa đơn</strong></div>
                                    <div>
                                        <%
                                            if (discount > 0) {
                                        %> 
                                        <strong class="order-total"  style="text-decoration: line-through;"><%= currencyVN.format(total)%></strong>
                                        <%
                                            }
                                        %>
                                        <strong class="order-total"><%= currencyVN.format(total * (1 - discount))%></strong>
                                    </div>
                                </div>
                                <div class="order-col">
                                    <div><strong>Phí giao hàng</strong></div>
                                    <%
                                        if (total >= 360000) {
                                            ship = 0;
                                    %>
                                    <div><strong class="order-total">Miễn phí</strong></div>
                                    <%
                                    } else {
                                    %>
                                    <input id="inputShip" hidden="" value="<%= currencyVN.format(ship)%>">
                                    <div><strong id="shipp" class="order-total"><%= currencyVN.format(ship)%></strong></div>
                                        <%
                                            }
                                        %>
                                </div>
                                <div class="order-col">
                                    <div><strong>Tổng thành tiền</strong></div>
                                    <input id="inputTotal" hidden="" value="<%= currencyVN.format(total * (1 - discount))%>">
                                    <input id="outputTotal" hidden="" value="<%= currencyVN.format(total * (1 - discount)+ ship)%>">
                                    <div><strong id="total" class="order-total"><%= currencyVN.format(total * (1 - discount) + ship)%></strong></div>
                                </div>
                            </div>
                            <div class="payment-method">
                                <p>Phương thức thanh toán</p>
                                <div class="input-radio">
                                    <input checked="" type="radio" name="payment" id="payment-1" value="cash">
                                    <label for="payment-1">
                                        <span></span>
                                        Thanh toán khi nhận hàng
                                    </label>
                                    <div class="caption">
                                        <p>Khi chọn phương thức thanh toán này, quý khách hàng sẽ trả tiền mặt cho nhân viên giao hàng ngay khi nhận được đơn hàng của mình.</p>
                                    </div>
                                </div>
                                <div class="input-radio">
                                    <input type="radio" name="payment" id="payment-3" value="paypal">
                                    <label for="payment-3">
                                        <span></span>
                                        <b style="color: #003087">Pay</b><b style="color: #009cde">Pal</b>
                                    </label>
                                    <div class="caption">
                                        <p>Khi chọn phương thức thanh toán này, quý khách hàng sẽ thanh toán đơn hàng qua ứng dụng <b style="color: #003087">Pay</b><b style="color: #009cde">Pal</b>.</p>
                                    </div>
                                </div>
                            </div>
                            <input name="action" value="Order" type="hidden"/>
                            <a style="color: white; cursor: pointer; " onclick="submitForm()" class="primary-btn order-submit">Đặt hàng</a>
                            <%}%>
                        </div>
                    </form>
                    <!-- /Order Details -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->
        <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>