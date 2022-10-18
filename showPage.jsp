<%-- 
    Document   : home
    Created on : Sep 18, 2022, 11:23:13 AM
    Author     : PC
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
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
        <style>
            .hover:hover{
                color: red;
            }
        </style>
        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section" style="margin-top: 180px; ">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
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
                        <%
                            List<CategoryDTO> listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
                        %>
                        <div class="aside">
                            <h3 class="aside-title">Categories</h3>
                            <div class="checkbox-filter">
                                <%                                    for (CategoryDTO cateDTO : listCate) {
                                %>
                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <form action="FilterCategoryController" method="GET">
                                            <input type="hidden" name="cateID" value="<%= cateDTO.getCategoryID()%>"/>
                                            <input type="hidden" name="cateName" value="<%= cateDTO.getName()%>"/>
                                            <button class="hover" style="border: none; background: none;"><%= cateDTO.getName()%></button>
                                        </form>
                                    </label>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <%
                            List<PublisherDTO> listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
                        %>
                        <div class="aside">
                            <h3 class="aside-title">Publishers</h3>
                            <div class="checkbox-filter">
                                <%
                                    for (PublisherDTO publisherDTO : listPub) {
                                %>
                                <div class="input-checkbox">                              
                                    <label for="category-6">
                                        <form action="FilterPublisherController" method="POST">
                                            <input type="hidden" name="pubID" value="<%= publisherDTO.getPublisherID()%>"/>
                                            <input type="hidden" name="pubName" value="<%= publisherDTO.getName()%>"/>
                                            <button class="hover" style="border: none; background: none;"><%= publisherDTO.getName()%></button>
                                        </form>
                                    </label>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <div class="aside">
                            <h3 class="aside-title">Price</h3>
                            <div class="checkbox-filter">
                                <div class="input-checkbox">                              
                                    <label for="category-6">
                                        <form action="FilterPriceController" method="POST">
                                            <input type="hidden" value="100000" name="max"/>
                                            <input type="hidden" value="0" name="min"/>
                                            <button class="hover" style="border: none; background: none;">0đ - 100,000đ</button>
                                        </form>
                                    </label>
                                </div>
                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <form action="FilterPriceController" method="POST">
                                            <input type="hidden" value="200000" name="max"/>
                                            <input type="hidden" value="100000" name="min"/>
                                            <button class="hover" style="border: none; background: none;">100,000đ - 200,000đ</button>
                                        </form>
                                    </label>
                                </div>
                                <div class="input-checkbox">
                                    <label for="category-6">
                                        <form action="FilterPriceController" method="POST">
                                            <input type="hidden" value="1000000" name="max"/>
                                            <input type="hidden" value="200000" name="min"/>
                                            <button class="hover" style="border: none; background: none;">200,000 - Above</button>
                                        </form>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <!-- /aside Widget -->
                    </div>
                    <!-- /ASIDE -->

                    <!-- STORE-->
                    
                    <div id="store" class="col-md-9">
                        <!-- store top filter -->
                        <div class="store-filter clearfix">
                            SORT BY : 
                            <form method="GET" action="SortController">
                                <select name="data" id="data">
                                    <option value='1'>Price from high to low</option>
                                    <option value='2'>Price from low to high</option>
                                    <option value='3'>Alphabet from A-Z</option>
                                    <option value='4'>Alphabet from Z-A</option>
                                    <input type="hidden" name="cateID" value="<%=request.getParameter("cateID")%>">
                                    <input type="hidden" name="pubID" value="<%=request.getParameter("pubID")%>">
                                </select>
                                <input type='submit' value='Submit'>
                            </form>
                        </div>
                        <!-- /store top filter -->
                        <style>
                            .gg{
                                height: 550px;
                            }
                            .gg img{
                                height: 300px;
                            }
                            #addCart{
                                text-decoration: none;

                            }
                        </style>
                        <!-- store products -->
                        <div class="row">
                            <!-- product -->
                            <%
                                Locale localeVN = new Locale("vi", "VN");
                                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                for (int i = 0; i < list.size(); i++) {
                                    String price = currencyVN.format(list.get(i).getPrice());
                            %>
                            <div class="col-md-4 col-xs-6 gg">
                                <div class="product">
                                    <div class="product-img">
                                        <a class="product-img" href="LoadController?&isbn=<%=list.get(i).getIsbn()%>"><img src="<%= list.get(i).getImg()%>" alt=""></a>
                                    </div>
                                    <div class="product-body">
                                        <p class="product-category"><%= list.get(i).getCategory().getName()%></p>
                                        <h3 class="product-name" ><a style="color: black;" href="LoadController?&isbn=<%=list.get(i).getIsbn()%>"><%= list.get(i).getName()%></a></h3>
                                        <h4 class="product-price"><%= price%></h4>                                    
                                        <div class="product-btns">
                                            <button class="quick-view"><a style="color: black;" href="LoadController?&isbn=<%=list.get(i).getIsbn()%>"><i class="fa fa-eye"></i><span class="tooltipp">view details</span></button>
                                        </div>
                                    </div>
                                    <div class="add-to-cart">
                                        <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>add to cart</button>
                                    </div>
                                </div>
                            </div>
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