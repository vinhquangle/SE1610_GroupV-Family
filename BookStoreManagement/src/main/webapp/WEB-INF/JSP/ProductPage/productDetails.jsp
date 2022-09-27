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
                            <li><a href="FilterCategoryController?cateID=<%= book.getCategory().getCategoryID()%>&cateName=<%= book.getCategory().getName()%>"><%= book.getCategory().getName()%></a></li>
                            <li class="active"><a href="LoadController?&isbn=<%=book.getIsbn()%>"><%= book.getName()%></a></li>
                        </ul>
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
                                <h3 class="product-price"><%= book.getPrice()%>đ</h3>
                                <span class="product-available">In Stock</span>
                            </div>
                            <ul class="product-links">
                                <li>ISBN:   <b><%= book.getIsbn()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Author:   <b><%= book.getAuthorName()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Publisher:   <b><%= book.getPublisher().getName()%></b></li>                               
                            </ul>

                            <ul class="product-links">
                                <li>Category:   <b><%= book.getCategory().getName()%></b></li>
                            </ul>

                            <div class="add-to-cart" style="margin-top: 10px">
                                <div class="qty-label">
                                    Quantity
                                    <div class="input-number">
                                        <input name="quantity" type="number" id="quantity1" value="1">
                                        <span class="qty-up" onclick="add()">+</span>
                                        <span class="qty-down" onclick="subtract()">-</span>
                                    </div>
                                </div>
                            </div>
                            <script>
                                function add() {
                                    var quantity = (Number(document.getElementById("quantity").value) + 1);
                                    document.getElementById("quantity").value = quantity;
                                    quantity = (Number(document.getElementById("quantity1").value) + 1);
                                    document.getElementById("quantity1").value = quantity;
                                }
                                function subtract() {
                                    var quantity = (Number(document.getElementById("quantity").value));
                                    if (quantity == 1) {

                                    } else {
                                        quantity -= 1
                                        document.getElementById("quantity").value = quantity;
                                    }
                                    quantity = (Number(document.getElementById("quantity1").value));
                                    if (quantity == 1) {

                                    } else {
                                        quantity -= 1
                                        document.getElementById("quantity1").value = quantity;
                                    }
                                }
                            </script>
                            <div  class="add-to-cart" style="margin-top: 10px">  
                                <form action="AddBookCartController" method="POST" style="display: inline-block">
                                    <input name="quantity" id="quantity" value="1" type="hidden">
                                    <input name="isbn" id="quantity" value="<%= book.getIsbn()%>" type="hidden">
                                    <button name="action" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                                </form>
                                <form action="MainController" method="POST" style="display: inline-block">
                                    <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>Buy Now</button>
                                </form>  
                            </div>
                            <style>

                            </style>
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
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                        for (BookDTO bookDTO : sameCate) {
                             String price = currencyVN.format(bookDTO.getPrice());
                    %>
                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <a class="product-img" href="LoadController?&isbn=<%= bookDTO.getIsbn()%>"><img style="height: 250px;" src="<%= bookDTO.getImg()%>" alt=""></a>
                            </div>
                            <div class="product-body">
                                <p class="product-category"><%= bookDTO.getCategory().getName()%></p>
                                <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="hover product-name"><a style="color: black;" href="LoadController?&isbn=<%=bookDTO.getIsbn()%>"></a><%= bookDTO.getName()%></h3>
                                <h4 class="product-price"><%= price %></h4>                                
                                <div class="product-btns">                                   
                                    <button class="quick-view"><a style="color: black;" href="LoadController?&isbn=<%= bookDTO.getIsbn()%>"><i class="fa fa-eye"></i><span class="tooltipp">view details</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
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