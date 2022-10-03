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
        <title>View Cart Page</title>
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
                <!-- row -->
                <%                        CategoryDTO cate = (CategoryDTO) request.getAttribute("CATEGORY");
                    if (cate != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">All Categories</a></li>
                            <li><a href="FilterCategoryController?cateID=<%= cate.getCategoryID()%>&cateName=<%= cate.getName()%>"><%= cate.getName()%></a></li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                    PublisherDTO pub = (PublisherDTO) request.getAttribute("PUBLISHER");
                    if (pub != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">All Publisher</a></li>
                            <li><a href="FilterPublisherController?pubID=<%= pub.getPublisherID()%>&pubName=<%= pub.getName()%>"><%= pub.getName()%></a></li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                    String max = (String) request.getAttribute("MAX");
                    String min = (String) request.getAttribute("MIN");
                    String me = (String) request.getAttribute("MESS");
                    if (max != null && min != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">Price</a></li>
                            <li><a href="FilterPriceController?min=<%= min%>&max=<%= max%>&mess=<%= me%>"><%= me%></a></li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                    int size = 0;
                    try {
                        size = (int) session.getAttribute("SIZE");
                    } catch (Exception e) {
                        size = 0;
                    }
                    String messAdd = (String) request.getAttribute("MESSAG_ADD");
                    if (messAdd == null) {
                        messAdd = "";
                    }
                    String messFail = (String) request.getAttribute("MESSAG_FAIL");
                    if (messFail == null) {
                        messFail = "";
                    }
                %>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <div style="background-color: #009933;">
            <p style="color: white; font-size: 20px; text-align: center;"><%= messAdd%></p>
        </div>
        <div style="background-color: #cc0000;">
            <p style="color: white; font-size: 20px; text-align: center;"><%= messFail%></p>
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
                        <h1 style="font-family: monaco; color: #1f1f21; font-size:60px; display: inline-block; width: 400px;">Shopping Cart</h1>         
                        <h1 style="margin-top: 15px; color: #1f1f21; display: inline-block; float: right; font-size: 40px;"><%= size%> Items</h1>
                        <style>
                            .cart{
                                margin: auto;
                                text-align: center;
                                color: black;
                            }
                        </style>
                        <%
                            Cart cart = (Cart) session.getAttribute("CART");
                            if (cart != null && cart.getCart().size() > 0) {
                        %>
                        <div style="border: 1px solid black; border-radius: 10px; margin-top: 15px;" class="container">
                            <div style="margin-top: 5px;" class="row">
                                <div class="col-md-7 cart">
                                    <p class="cart">Books</p>
                                </div>
                                <div class="col-md-1 cart">
                                    <p class="cart">Quantity</p>
                                </div>
                                <div class="col-md-3 cart">
                                    <p class="cart">Subtotal</p>
                                </div>
                                <div class="col-md-1 cart">
                                </div>
                            </div>
                        </div>
                        <%
                            total = 0;
                            Locale localeVN = new Locale("vi", "VN");
                            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                            String price = new String();
                            String totalS = new String();
                            for (BookDTO book : cart.getCart().values()) {
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
                            <div style="margin: auto; width: 75px;" class="">
                                <div class="product-details">
                                    <form action="EditBookCartController" method="POST">
                                        <div class="input-number">
                                            <input type="number" name="quantity" id="quantity" value="<%= book.getQuantity()%>">
                                            <span class="qty-up" onclick="add()">+</span>
                                            <span class="qty-down" onclick="subtract()">-</span>
                                        </div>
                                        <input type="hidden" name="isbn" value="<%= book.getIsbn()%>" readonly="">
                                        <button  data-bs-target="#myModal" type="submit" style=" margin-top: 1px; color: white; background-color: #009933; width: 50px; text-align: center;">Save</button>
                                    </form> 
                                </div>
                            </div>
                            <script>

                                function add() {
                                    var quantity = (Number(document.getElementById("quantity").value) + 1);
                                    document.getElementById("quantity").value = quantity;
                                }
                                function subtract() {
                                    var quantity = (Number(document.getElementById("quantity").value));
                                    if (quantity > 1) {
                                        quantity -= 1
                                        document.getElementById("quantity").value = quantity;
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
                                        <button type="submit" name="isbn" value="<%= book.getIsbn()%>" style="border: none;" class="fa-solid fa-trash-can"></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                            totalS = currencyVN.format(total);
                        %> 
                        <h1 class="head-title" style="margin-top: 4%">Total: <%= totalS%></h1>
                        <%
                        } else {
                        %>
                        <p style="font-size: 60px; font-family: monospace; color: #d2d2d2;" id="emptyList">-------Empty List-------</p>
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
                            <a href="GetController?"><p class="con">Continue shopping</p></a>
                            <a href="GetController?"><p class="check">Checkout <i class="fa fa-arrow-circle-right"></i></p></a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- FOOTER -->
            <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>