<%-- 
    Document   : checkout
    Created on : Sep 20, 2022, 10:21:44 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Check Out Page</title>
    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
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
                            <div class="billing-details">
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
                            <div class="order-notes">
                                <textarea class="input" placeholder="Ghi chú"></textarea>
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
                                            String totalS = new String();
                                            Locale localeVN = new Locale("vi", "VN");
                                            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                            for (BookDTO book : cart.getCart().values()) {
                                                totalS = currencyVN.format(book.getPrice() * book.getQuantity());
                                    %>
                                    <div class="order-col">
                                        <div><%= book.getQuantity()%>x <%= book.getName()%></div>
                                        <div><%= totalS%></div>
                                    </div>
                                    <%}%>
                                </div>
                                <div class="payment-method">
                                    <div class="input-radio">
                                        <input checked="" name="method" type="radio" id="payment-4" value="ship">
                                        <label for="payment-4">
                                            <span></span>
                                            <b>Giao hàng tận nơi (có thể mất phí)</b>
                                        </label>
                                        <div class="caption">
                                            <div class="order-col">
                                                <div>Phí giao hàng</div></br>
                                                <div><strong>FREE</strong></div></br>
                                            </div>
                                        </div>
                                        <div class="caption">
                                            <p>Đơn hàng của quý khách sẽ được giao theo địa chỉ chỉ định (đối với đơn hàng trên 350k sẽ được miễn phí giao hàng)</p>
                                        </div>
                                    </div> 
                                    <div class="input-radio">
                                        <input name="method" type="radio" id="payment-5" value="store">
                                        <label for="payment-5">
                                            <span></span>
                                            <b>Nhận tại cửa hàng</b>
                                        </label>
                                        <div class="caption">
                                            <p>Quý khách sẽ nhận hàng tại cửa hàng(miễn phí ship) </p>
                                        </div>
                                    </div> 
                                </div>
                                <div class="order-col">
                                    <div><strong>Tổng thành tiền</strong></div>
                                    <div><strong class="order-total"><%= currencyVN.format(total)%></strong></div>
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
                                    <input type="radio" name="payment" id="payment-2" value="momo">
                                    <label for="payment-2">
                                        <span></span>
                                        Momo
                                    </label>
                                    <div class="caption">
                                        <p>Khi chọn phương thức thanh toán này, quý khách hàng sẽ thanh toán đơn hàng qua ứng dụng <b style="color: #a50064;">Momo</b>.</p>
                                    </div>
                                </div>
                                <div class="input-radio">
                                    <input type="radio" name="payment" id="payment-3" value="paypal">
                                    <label for="payment-3">
                                        <span></span>
                                        Paypal
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