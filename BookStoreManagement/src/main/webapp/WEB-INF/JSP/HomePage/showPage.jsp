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
                    <form method="GET" action="SortController">
                        <select name="sort" onchange="this.form.submit()" class="input-select">
                            <option disabled selected hidden>Sắp xếp theo:</option>
                            <option value="0">Giá bán (Tăng dần)</option>
                            <option value="1">Giá bán (Giảm dần)</option>
                            <option value="2">Tiêu đề (A -> Z)</option>
                            <option value="3">Tiêu đề (Z -> A)</option>
                        </select>
                        <input style="display: none" name="searchBook" value="<%= search%>">
                        <input style="display: none" name="cateID" value="<%= cateN%>">
                        <input style="display: none" name="pubID" value="<%= pubN%>">
                        <input style="display: none" name="max" value="<%= min%>">
                        <input style="display: none" name="min" value="<%= max%>">
                        <input style="display: none" name="mess" value="<%= me%>">
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
                String show = "";
                String controllerAdd = (String) request.getAttribute("CONTROLLER");
                String index = (String) request.getParameter("index");
                if (index == null) {
                    index = "";
                }
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                for (int i = 0; i < list.size(); i++) {
                    String price = currencyVN.format(list.get(i).getPrice());
            %>
            <div class="col-md-4 col-xs-6 gg <%= show%>">
                <div class="product">
                    <div class="product-img">
                        <form style="display: inline-block;" method="GET" action="LoadController">
                            <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                            <a class="product-img" style="cursor: pointer;" onclick="this.parentNode.submit();"><img src="<%= list.get(i).getImg()%>" alt=""></a>
                        </form>
                    </div>
                    <div class="product-body">
                        <p class="product-category"><%= list.get(i).getCategory().getName()%></p>
                        <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="product-name" >
                            <form style="display: inline-block;" method="GET" action="LoadController">
                                <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                                <a style="cursor: pointer; color: black; font-weight: bold;" onclick="this.parentNode.submit();"><%= list.get(i).getName()%></a>
                            </form>
                        </h3>
                        <h4 class="product-price"><%= price%></h4>                                    
                        <div class="product-btns">
                            <form style="display: inline-block;" method="GET" action="LoadController">
                                <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                                <div class="product-btns">
                                    <button class="quick-view"><a style="cursor: pointer; color: black;" onclick="this.parentNode.submit();"><i class="fa fa-eye"></i><span class="tooltipp">Xem chi tiết</span></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="add-to-cart">
                        <form style="display: inline-block;" method="GET" action="AddBookCartController">
                            <input name="quantity" id="quantity" type="hidden" value="1">
                            <input type="hidden" name="isbn" value="<%=list.get(i).getIsbn()%>" /> 
                            <input name="quantityCheck" value="<%= list.get(i).getQuantity()%>" type="hidden">
                            <input name="controller" value="<%= controllerAdd%>" type="hidden">
                            <input name="index" value="<%= index%>" type="hidden">
                            <button type="submit" value="Home" name="action" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>Thêm vào giỏ</button>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </body>
</html>