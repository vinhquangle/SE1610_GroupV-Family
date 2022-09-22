<%-- 
    Document   : home
    Created on : Sep 18, 2022, 11:23:13 AM
    Author     : PC
--%>

<%@page import="dto.BookDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.PublisherDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Show Page</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Colo Shop Template">
        <meta name="viewport" content="width=device-width, initial-scale=1">        
    </head>

    <body>
        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section" style="margin-top: 180px; ">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="#">Home</a></li>
                            <li><a href="#">All Categories</a></li>
                            <li><a href="#">Accessories</a></li>
                            <li class="active"><a href="#">Headphones (227,490 Results)</a></li>
                        </ul>
                    </div>
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /BREADCRUMB -->
        <%
            List<BookDTO> list = new ArrayList<>();
            try {
                list = (List<BookDTO>) session.getAttribute("LIST_BOOK");
            } catch (Exception e) {

            }
        %>
        <!-- SECTION -->
        <div class="section">
            <!-- container -->
            <div class="container" style="margin-top: 20px">
                <!-- row -->
                <div class="row">
                    <!-- ASIDE -->
                    <div id="aside" class="col-md-3">
                        <!-- aside Widget -->
                        <div class="aside">
                            <h3 class="aside-title">Categories</h3>
                            <div class="checkbox-filter">

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>

                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        Smartphones
                                    </label>
                                </div>
                            </div>
                        </div>
                        <!-- /aside Widget -->

                        <!-- aside Widget -->
                        <div class="aside">
                            <h3 class="aside-title">Top selling</h3>
                            <div class="product-widget">
                                <div class="product-img">
                                    <img src="IMG/product01.png" alt="">
                                </div>
                                <div class="product-body">
                                    <p class="product-category">Category</p>
                                    <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                    <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                </div>
                            </div>

                            <div class="product-widget">
                                <div class="product-img">
                                    <img src="IMG/product01.png" alt="">
                                </div>
                                <div class="product-body">
                                    <p class="product-category">Category</p>
                                    <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                    <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                </div>
                            </div>

                            <div class="product-widget">
                                <div class="product-img">
                                    <img src="IMG/product02.png" alt="">
                                </div>
                                <div class="product-body">
                                    <p class="product-category">Category</p>
                                    <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                    <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                </div>
                            </div>
                        </div>
                        <!-- /aside Widget -->
                    </div>
                    <!-- /ASIDE -->
                    
                    <!-- STORE -->
                    <div id="store" class="col-md-9">
                        <!-- store top filter -->
                        <div class="store-filter clearfix">
                            <div class="store-sort">
                                <label>
                                    Sort By:
                                    <select class="input-select">
                                        <option value="0">Price</option>
                                        <option value="1">Alphabet</option>
                                    </select>
                                </label>

                                <label>
                                    Show:
                                    <select class="input-select">
                                        <option value="0">20</option>
                                        <option value="1">50</option>
                                    </select>
                                </label>
                            </div>

                        </div>
                        <!-- /store top filter -->
                        <style>
                            .gg{
                                height: 550px;
                            }
                            .gg img{
                                height: 300px;
                            }
                        </style>
                        <h1 style="font-size: 20px; color: red">
                            <%
                                String message = (String) request.getAttribute("MESSAGE");
                                if (message == null) {
                                    message = "";
                                }
                            %>
                            <%= message%>
                        </h1>
                        <!-- store products -->
                        <div class="row">
                            
                            <!-- product -->
                            
                            <%
                                for (int i = 0; i < list.size(); i++) {
                            %>
                            <div class="col-md-4 col-xs-6 gg">
                                <form action="MainController">
                                    <div class="product">
                                        <div class="product-img">
                                            <img src="<%= list.get(i).getImg()%>" alt="">
                                            <input type="hidden" name="img" value="<%= list.get(i).getImg()%>" readonly="">
                                        </div>
                                        <div class="product-body">
                                            <input type="hidden" name="isbn" value="<%= list.get(i).getIsbn()%>" readonly="">
                                            <p class="product-category"><%= list.get(i).getCategoryID()%></p>
                                            <input type="hidden" name="categoryID" value="<%= list.get(i).getCategoryID()%>" readonly="">
                                            <h3 class="product-name"><a href="#"><%= list.get(i).getName()%></a>
                                                <input type="hidden" name="name" value="<%= list.get(i).getName()%>" readonly="">
                                            </h3>
                                            
                                            <h4 class="product-price"><%= list.get(i).getPrice()%>
                                                <input type="hidden" name="price" value="<%= list.get(i).getPrice()%>" readonly="">
                                            </h4>
                                            
                                            <p class="product-category">Quantity</p>
                                            <select name="cmbQuantity">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>               
                                            </select>
                                            <div class="product-btns">
                                                <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick
                                                        view</span></button>
                                            </div>
                                        </div>
                                        <div class="add-to-cart">
                                            <input type="hidden" name="action" value="AddBook"/>
                                            <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                                        </div>
                                    </div>
                                </form>

                            </div>
                            <!-- /product -->


                            <!-- /product -->

                            <%
                                }
                            %>
                        </div>
                        <!-- /store products -->


                    </div>
                    <!-- /STORE -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->

    </body>
</html>
