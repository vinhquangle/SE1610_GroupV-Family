<%-- 
    Document   : productDetails
    Created on : Sep 20, 2022, 10:07:18 PM
    Author     : PC
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="dto.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <title>Product Details Page</title>
    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <%            BookDTO book = (BookDTO) session.getAttribute("BOOK_DETAIL");
            List<BookDTO> sameCate = (List<BookDTO>) session.getAttribute("SAME_CATE");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(book.getPrice());
            String messAdd = (String) request.getAttribute("MESSAG_ADD");
            if (messAdd == null) {
                messAdd = "";
            }
            String messFail = (String) request.getAttribute("MESSAG_FAIL");
            if (messFail == null) {
                messFail = "";
            }
        %>
        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section" style="margin-top: 180px; ">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">All Categories</a></li>
                            <li>
                                <form style="display: inline-block;" method="POST" action="FilterCategoryController">
                                    <input type="hidden" name="cateID" value="<%= book.getCategory().getCategoryID()%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= book.getCategory().getName()%></a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /BREADCRUMB -->     
        <div style="background-color: #009933;">
            <p style="color: white; font-size: 20px; text-align: center;"><%= messAdd%></p>
        </div>
        <div style="background-color: #cc0000;">
            <p style="color: white; font-size: 20px; text-align: center;"><%= messFail%></p>
        </div>
        <!-- Button trigger modal -->
        <!-- SECTION -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <!-- Product main img -->
                    <div class="col-md-7">
                        <div id="product-main-img">
                            <div class="product-preview">
                                <img style="height: 400px; width: 500px" src="<%= book.getImg()%>" alt="">
                            </div>                           
                        </div>
                    </div>
                    <!-- /Product main img -->
                    <!-- Product details -->
                    <div class="col-md-5">
                        <div class="product-details" style="margin-top: 30px">
                            <h2 class="product-name"><%= book.getName()%></h2>                           
                            <div>
                                <h3 class="product-price"><%= price%></h3>
                                <span class="product-available"><%= book.getQuantity()%> In Stock</span>
                                <input id="stock" type="hidden" value="<%= book.getQuantity()%>"/>
                            </div>
                            <ul class="product-links">
                                <li>ISBN:   <b><%= book.getIsbn()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Author:   <b><%= book.getAuthorName()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Publisher:
                                    <form style="display: inline-block;" method="POST" action="FilterPublisherController">
                                        <input type="hidden" name="pubID" value="<%= book.getPublisher().getPublisherID() %>" /> 
                                        <a style="cursor: pointer;" onclick="this.parentNode.submit();"><b><%= book.getPublisher().getName() %></b></a>
                                    </form>
                                </li>                               
                            </ul>

                            <ul class="product-links">
                                <li>Category:
                                    <form style="display: inline-block;" method="POST" action="FilterCategoryController">
                                        <input type="hidden" name="cateID" value="<%= book.getCategory().getCategoryID()%>" /> 
                                        <a style="cursor: pointer;" onclick="this.parentNode.submit();"><b><%= book.getCategory().getName()%></b></a>
                                    </form>
                                </li>
                            </ul>
                                    <style>
                                        b:hover{
                                            color: red;
                                        }
                                    </style>
                            <div class="add-to-cart" style="margin-top: 10px">
                                <div class="qty-label">
                                    Quantity
                                    <div class="input-number">
                                        <input onkeyup="check()" name="quantity" type="number" id="quantity1" value="1">
                                        <span class="qty-up" onclick="add()">+</span>
                                        <span class="qty-down" onclick="subtract()">-</span>
                                    </div>
                                </div>
                            </div>
                            <script>
                                var stock = Number(document.getElementById("stock").value);
                                function add() {
                                    var cur = document.getElementById("quantity1").value;
                                    if (cur < stock) {
                                        var quantity = (Number(document.getElementById("quantity").value) + 1);
                                        document.getElementById("quantity").value = quantity;
                                        quantity = (Number(document.getElementById("quantity1").value) + 1);
                                        document.getElementById("quantity1").value = quantity;
                                    }
                                }
                                function subtract() {
                                    var quantity = (Number(document.getElementById("quantity").value));
                                    if (quantity > 1) {
                                        quantity -= 1
                                        document.getElementById("quantity").value = quantity;
                                    }
                                    quantity = (Number(document.getElementById("quantity1").value));
                                    if (quantity > 1) {
                                        quantity -= 1
                                        document.getElementById("quantity1").value = quantity;
                                    }
                                }
                                function cart() {
                                    var quantity = document.getElementById("quantity1").value;
                                    document.getElementById("quantity").value = quantity;
                                }
                                function check() {
                                    var cur = document.getElementById("quantity1").value;
                                    if (cur > stock) {
                                        cur = Math.floor(cur / 10);
                                        document.getElementById("quantity1").value = cur;
                                    } else if (cur < 0) {
                                        document.getElementById("quantity1").value = 1;
                                    }
                                }
                            </script>
                            <div  class="add-to-cart" style="margin-top: 10px">  
                                <form action="AddBookCartController" method="POST" style="display: inline-block">
                                    <input name="quantity" id="quantity" type="hidden" value="1">
                                    <input name="isbn" value="<%= book.getIsbn()%>" type="hidden">
                                    <input name="quantityCheck" value="<%= book.getQuantity()%>" type="hidden">
                                    <button onclick="cart()" name="action" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                                </form>                               
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>Buy Now</button>                              
                            </div>
                        </div>
                    </div>
                    <!-- /Product details -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->
        <!-- Section -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <%
                        if (!sameCate.isEmpty()) {
                    %>
                    <div class="col-md-12">
                        <div class="section-title text-center">
                            <h3 class="title">You might also enjoy</h3>
                        </div>
                    </div>
                    <%
                        for (BookDTO bookDTO : sameCate) {
                            price = currencyVN.format(bookDTO.getPrice());
                    %>
                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <form style="width: 100%; display: inline-block;" method="POST" action="LoadController">
                                    <input type="hidden" name="isbn" value="<%= bookDTO.getIsbn()%>" /> 
                                    <a class="product-img" style="cursor: pointer;" onclick="this.parentNode.submit();"><img style="height: 250px;" src="<%= bookDTO.getImg()%>" alt=""></a>
                                </form>
                            </div>
                            <div class="product-body">
                                <p class="product-category"><%= bookDTO.getCategory().getName()%></p>
                                <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="product-name" >
                                    <form style="display: inline-block;" method="POST" action="LoadController">
                                        <input type="hidden" name="isbn" value="<%=bookDTO.getIsbn()%>" /> 
                                        <a style="cursor: pointer; color: black; font-weight: bold;" onclick="this.parentNode.submit();"><%= bookDTO.getName()%></a>
                                    </form>
                                </h3>                                <h4 class="product-price"><%= price%></h4>                                
                                <form style="display: inline-block;" method="POST" action="LoadController">
                                    <input type="hidden" name="isbn" value="<%=bookDTO.getIsbn()%>" /> 
                                    <div class="product-btns">
                                        <button class="quick-view"><a style="cursor: pointer; color: black;" onclick="this.parentNode.submit();"><i class="fa fa-eye"></i><span class="tooltipp">view details</span></button>
                                    </div>
                                </form>
                            </div>
                            <div class="add-to-cart">
                                <form style="display: inline-block;" method="POST" action="LoadController">
                                    <input type="hidden" name="isbn" value="<%= bookDTO.getIsbn()%>" /> 
                                    <button type="submit" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>add to cart</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                    <!-- /product -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /Section -->
        <%@include file="../HeaderFooterPage/footer.jsp" %>
        <!-- jQuery Plugins -->
        <script src="../../JS/main.js"></script>
    </body>
</html>