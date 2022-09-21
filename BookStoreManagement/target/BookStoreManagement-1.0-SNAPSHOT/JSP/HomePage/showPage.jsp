<%-- 
    Document   : home
    Created on : Sep 18, 2022, 11:23:13 AM
    Author     : PC
--%>

<%@page import="dto.CategoryDTO"%>
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
            List<CategoryDTO> listCate = new ArrayList<>();
            List<PublisherDTO> listPub = new ArrayList<>();
            try {
                list = (List<BookDTO>) session.getAttribute("LIST_BOOK");
                listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
                listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
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
                        <%
                            for(int i=0; i < listCate.size(); i++){
                        %>  
                            <div class="checkbox-filter">
                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        <a style="font-size:17px; text-transform: uppercase" href="MainController?action=Category&cateID=<%= listCate.get(i).getCategoryID() %>"> <%= listCate.get(i).getName() %> </a>
                                        <form action="MainController">
                                            <input type="submit" name="action" value="Category" style="display:none"  />
                                        </form>
                                    </label>
                                </div>
                            </div>
                        <%
                            }
                        %>
                        </div>
                        <!-- /aside Widget -->

                        <!-- aside Widget -->
                        <div class="aside">
                            <h3 class="aside-title">Publishers</h3>
                       <%
                            for(int i=0; i < listPub.size(); i++){
                        %>  
                            <div class="checkbox-filter">
                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <span></span>
                                        <a style="font-size:17px; text-transform: uppercase" href="MainController?action=Publisher&pubID=<%= listPub.get(i).getPublisherID() %>"> <%= listPub.get(i).getName() %> </a>
                                        <form action="MainController">
                                            <input type="submit" name="action" value="Publisher" style="display:none" />
                                        </form>
                                    </label>
                                </div>
                            </div>
                        <%
                            }
                        %>
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
                        <!-- store products -->
                        <div class="row">
                            <!-- product -->
                            <%
                                for (int i = 0; i < list.size(); i++) {
                            %>
                            <div class="col-md-4 col-xs-6 gg">
                                <div class="product">
                                    <div class="product-img">
                                        <img src="<%= list.get(i).getImg()%>" alt="">
                                    </div>
                                    <div class="product-body">
                                        <p class="product-category"><%= list.get(i).getCategoryID() %></p>
                                        <h3 class="product-name"><a href="#"><%= list.get(i).getName() %></a></h3>
                                        <h4 class="product-price"><%= list.get(i).getPrice() %></h4>
                                        <div class="product-btns">
                                            <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick
                                                    view</span></button>
                                        </div>
                                    </div>
                                    <div class="add-to-cart">
                                        <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to
                                            cart</button>
                                    </div>
                                </div>
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
