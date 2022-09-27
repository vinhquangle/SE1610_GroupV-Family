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
                        <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="product-name" ><a style="color: black;" href="LoadController?&isbn=<%=list.get(i).getIsbn()%>"><%= list.get(i).getName()%></a></h3>
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
        <!-- /container -->
    </body>
</html>