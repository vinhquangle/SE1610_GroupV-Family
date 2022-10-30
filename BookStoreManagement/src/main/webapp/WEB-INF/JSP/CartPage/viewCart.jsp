<%-- 
    Document   : viewCart
    Created on : Sep 20, 2022, 9:10:23 PM
    Author     : PC
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="../../CSS/bootstrap.min.css" />
        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="../../CSS/font-awesome.min.css" />
        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="../../CSS/style.css" />
        <link rel="stylesheet" type="text/css" href="../../STYLES/bootstrap4/bootstrap.min.css" />
        <link href="../../PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="../../STYLES/main_styles.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" /> 
        <!-- jQuery Plugins -->
        <script src="../../JS/jquery.min.js"></script>
        <script src="../../JS/bootstrap.min.js"></script>
        <script src="../../JS/slick.min.js"></script>
        <script src="../../JS/nouislider.min.js"></script>
        <script src="../../JS/jquery.zoom.min.js"></script>
        <script src="../../JS/main.js"></script>
    </head>
    <body>
        <!-- HEADER -->
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <style>
            .head-title{
                display: block;
                font-size: 40px;
                color: red;
                margin-top: 200px;
                text-align: center;
            }
            .qu{
                width: 45px;
                text-align: center;
                margin: auto;
            }
            .check{
                font-size: 20px;
                color: white;
                text-align: center;
                background-color: #cc0000;
                width: 220px;
                padding: 5px;
                display: inline-block;
                border: 2px solid black;
                float: right;
                margin-right: 150px;
            }
            .con{
                font-size: 20px;
                color: white;
                text-align: center;
                background-color: #009933;
                width: 220px;
                padding: 5px;
                display: inline-block;
                border: 2px solid black;
                margin-left: 150px;
            }
            .check:hover{
                color: #cc0000;
                background-color: white;
            }
            .con:hover{
                color: #009933;
                background-color: white;
            }
        </style>
        <div style="margin-top: 180px; background-color: white;" id="breadcrumb" class="section" >
            <!-- container -->
            <div class="container">
                <%                    String messAdd = (String) request.getAttribute("MESSAG_ADD");
                    if (messAdd == null) {
                        messAdd = "";
                    }
                    String messFail = (String) request.getAttribute("MESSAG_FAIL");
                    if (messFail == null) {
                        messFail = "";
                    }
                    String messWarning = (String) request.getAttribute("MESSAG_WARNING");
                    if (messWarning == null) {
                        messWarning = "";
                    }
                %>
                <!-- /row -->

                <div style="background-color: #009933;">
                    <p style="color: white; font-size: 20px; text-align: center;"><%= messAdd%></p>
                </div>
                <div style="background-color: #cc0000;">
                    <p style="color: white; font-size: 20px; text-align: center;"><%= messFail%></p>
                </div>
                <div style="background-color: #ff6600;">
                    <p style="color: white; font-size: 20px; text-align: center;"><%= messWarning%></p>
                </div>
                <!-- The Modal -->
                <div class="modal" id="myModal">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title">Modal Heading</h4>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <!-- Modal body -->
                            <div class="modal-body">
                                Modal body..
                            </div>
                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- The Modal -->
                <div class="container">
                    <div class="row">
                        <div class="col-md-3">
                            <%@include file = "../HomePage/filterPage.jsp" %>
                        </div>
                        <div class="col-md-9">
                            <div style="height: 110px; border-bottom: 2px solid black; margin-bottom: 5px; color: #1f1f21;">
                                <img  src="IMG/book.png" style="display: inline-block; height: 100px;"/>
                                <h1 style="font-family: monaco; color: #1f1f21; font-size:60px; display: inline-block; width: 400px;">Giỏ hàng</h1>         
                                <h1 style="margin-top: 15px; color: #1f1f21; display: inline-block; float: right; font-size: 40px;">${sessionScope.SELECT} Sản phẩm</h1>
                                <style>
                                    .cart{
                                        margin: auto;
                                        text-align: center;
                                        color: black;
                                    }
                                </style>
                                <%                            Cart cart = (Cart) session.getAttribute("CART");
                                    if (cart != null && cart.getCart().size() > 0) {
                                %>
                                <div style="border: 1px solid black; border-radius: 10px; margin-top: 15px;" class="container">
                                    <div style="margin-top: 5px;" class="row">
                                        <div class="col-md-7 cart">
                                            <p class="cart">Sách</p>
                                        </div>
                                        <div class="col-md-1 cart">
                                            <p class="cart">Số lượng</p>
                                        </div>
                                        <div class="col-md-3 cart">
                                            <p class="cart">Tổng</p>
                                        </div>
                                        <div class="col-md-1 cart">
                                        </div>
                                    </div>
                                </div>
                                <%
                                    total = 0;
                                    int count = 0;
                                    String price = new String();
                                    String totalS = new String();
                                    Locale localeVN = new Locale("vi", "VN");
                                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                    for (BookDTO book : cart.getCart().values()) {
                                        count++;
                                        total += book.getPrice() * book.getQuantity();
                                        price = currencyVN.format(book.getPrice());
                                        totalS = currencyVN.format(book.getPrice() * book.getQuantity());
                                %>
                                <div style="margin-top: 5px; margin-bottom: 35px;" class="row">
                                    <div class="col-md-3">
                                        <div class="product-preview">
                                            <img style="height: 200px; width: 180px;" src="<%= book.getImg()%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="product-details" style="margin-top: 30px">
                                            <h2 class="product-name"><%= book.getName()%></h2>
                                            <div>
                                                <h3 class="product-price"><%= price%></h3>
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin: auto; width: 80px;" class="">
                                        <div class="product-details">
                                            <form action="EditBookCartController" method="POST">
                                                <div class="input-number">
                                                    <input onkeypress="return isNumberKey(event)" type="number" name="quantity" id="<%= count%>" value="<%= book.getQuantity()%>">
                                                    <span class="qty-up" onclick="add(<%= count%>)">+</span>
                                                    <span class="qty-down" onclick="subtract(<%= count%>)">-</span>
                                                </div>
                                                <input type="hidden" name="isbn" value="<%= book.getIsbn()%>" readonly="">
                                                <button  data-bs-target="#myModal" type="submit" style=" margin-top: 1px; color: white; background-color: #009933; width: 50px; text-align: center;">Lưu</button>
                                            </form> 
                                        </div>
                                    </div>
                                    <script>
                                        function isNumberKey(evt) {
                                            var charCode = (evt.which) ? evt.which : event.keyCode;
                                            if ((charCode < 48 || charCode > 57))
                                                return false;

                                            return true;
                                        }
                                        function add(a) {
                                            var quantity = (Number(document.getElementById(a).value) + 1);
                                            document.getElementById(a).value = quantity;
                                        }
                                        function subtract(a) {
                                            var quantity = (Number(document.getElementById(a).value));
                                            if (quantity > 1) {
                                                quantity -= 1;
                                                document.getElementById(a).value = quantity;
                                            }
                                        }
                                    </script>
                                    <div class="cart col-md-3">
                                        <div class="product-details">
                                            <h3 style="margin: auto;" class="product-price"><%= totalS%></h3>
                                        </div>
                                    </div>
                                    <div style="width: 20px; margin: auto;">
                                        <div class="product-details">
                                            <form action="RemoveBookCartController" method="POST">
                                                <button type="submit" name="isbn" value="<%= book.getIsbn()%>" title="Xóa" style="border: none;" class="fa-solid fa-trash-can"></button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <%
                                    }
                                    totalS = currencyVN.format(total);
                                %> 
                                <h1 class="head-title" style="margin-top: 4%">Tổng tiền: <%= totalS%></h1>
                                <%
                                } else {
                                %>
                                <p style="font-size: 60px; font-family: monospace; color: #d2d2d2;" id="emptyList">----Danh sách trống----</p>
                                <style>
                                    .check{
                                        display: none;
                                    }
                                    .con{
                                        margin:auto;
                                    }
                                    #father{
                                        text-align: center;
                                    }
                                </style>
                                <%
                                    }
                                %>
                                <div style="margin-top: 50px;" id="father">
                                    <a href="GetController?"><p class="con">Tiếp tục mua sắm</p></a>
                                    <a href="CheckoutController?action=Checkout"><p class="check">Thanh toán <i class="fa fa-arrow-circle-right"></i></p></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /container -->
            </div>
        </div>
        <!-- FOOTER -->
        <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>