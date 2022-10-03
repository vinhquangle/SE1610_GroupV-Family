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
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
    </head>
    <body>
        <style>
            .hover:hover{
                color: red;
                text-decoration: none;
            }
        </style>
        <div class="store-filter clearfix">
            <div class="store-sort">
                <label>
                    <form method="POST" action="SortController">
                        <select name="sort" onchange="this.form.submit()" class="input-select">
                            <option disabled selected hidden>Sort By</option>
                            <option value="0">Price (Ascending)</option>
                            <option value="1">Price (Decreasing)</option>
                            <option value="2">Title (A to Z)</option>
                            <option value="3">Title (Z to A)</option>
                        </select>
                    </form>
                </label>
                <label>
                    <%
                        String sort = (String) request.getAttribute("SORT");
                        if (sort == null) {
                            sort = "";
                        }
                    %>                  
                    <div><b><%= sort%></b></div>
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
            #addCart{
                text-decoration: none;

            }
        </style>
        <!-- store products -->
        <div class="row">
            <!-- product -->
            <%
                List<BookDTO> list = new ArrayList<>();
                try {
                    list = (List<BookDTO>) session.getAttribute("LIST_BOOK");
                } catch (Exception e) {
                }
                int count = list.size();
                if (count <= 18) {%>
            <style>
                #show{
                    display: none;
                }
            </style>
            <%   }
                String show = "";
                Locale localeVN = new Locale("vi", "VN");
                count = 0;
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                for (int i = 0; i < list.size(); i++) {
                    String price = currencyVN.format(list.get(i).getPrice());
                    count++;
                    if (count <= 18) {
                        show = "one";
                    } else {
                        show = "two";
                    }
            %>
            <div class="col-md-4 col-xs-6 gg <%= show%>">
                <div class="product">
                    <div class="product-img">
                        <form style="display: inline-block;" method="POST" action="LoadController">
                            <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                            <a class="product-img" style="cursor: pointer;" onclick="this.parentNode.submit();"><img src="<%= list.get(i).getImg()%>" alt=""></a>
                        </form>
                    </div>
                    <div class="product-body">
                        <p class="product-category"><%= list.get(i).getCategory().getName()%></p>
                        <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="product-name" >
                            <form style="display: inline-block;" method="POST" action="LoadController">
                                <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                                <a style="cursor: pointer; color: black; font-weight: bold;" onclick="this.parentNode.submit();"><%= list.get(i).getName()%></a>
                            </form>
                        </h3>
                        <h4 class="product-price"><%= price%></h4>                                    
                        <div class="product-btns">
                            <form style="display: inline-block;" method="POST" action="LoadController">
                                <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                                <div class="product-btns">
                                    <button class="quick-view"><a style="cursor: pointer; color: black;" onclick="this.parentNode.submit();"><i class="fa fa-eye"></i><span class="tooltipp">view details</span></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="add-to-cart">
                        <form style="display: inline-block;" method="POST" action="LoadController">
                            <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                            <button type="submit" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>add to cart</button>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <div class="row">
            <div style="text-align: center" class="col-md-12 col-xs-6">
                <button onclick="showMore()" id="show">Show more</button>
            </div>
            <div style="text-align: center" class="col-md-12 col-xs-6">
                <button onclick="showLess()" id="showL">Show less</button>
            </div>
            <style>
                .two{
                    display: none;
                }
                #show{
                    border: 2px solid #cc0000;
                    background-color: #cc0000; 
                    color: white; 
                    padding: 8px;
                }
                #show:hover{
                    background-color: white;
                    color: #cc0000;
                }             
                #showL{
                    border: 2px solid #009966;
                    background-color: #009966; 
                    color: white; 
                    padding: 8px;
                    display: none;
                }
                #showL:hover{
                    background-color: white;
                    color: #009966;
                }             
            </style>
            <script>
                function showMore() {
                    var collection = document.getElementsByClassName("two");
                    for (var i = 0; i < collection.length; i++) {
                        collection[i].style.display = "inline-block";
                    }
                    document.getElementById("showL").style.display = "inline-block";
                    document.getElementById("show").style.display = "none";
                }
                function showLess() {
                    var collection = document.getElementsByClassName("two");
                    for (var i = 0; i < collection.length; i++) {
                        collection[i].style.display = "none";
                    }
                    document.getElementById("showL").style.display = "none";
                    document.getElementById("show").style.display = "inline-block";
                }
            </script>
            <!-- /store products -->
            <!-- /container -->
    </body>
</html>