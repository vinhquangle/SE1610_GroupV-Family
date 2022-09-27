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
            .table-viewcart{

                width: 98%;
                margin: auto;
            }
            .table-viewcart thead{
                background-color: #ff0000;
                color: white;
            }
            .table-viewcart tr th{
                padding: 10px;
                text-align: center;
                border: 2px solid #000000;
            }
            .table-viewcart tr td{
                padding: 5px;
                border: 2px solid #000000;
                text-align: center;
            }
            .qu{
                width: 45px;
                text-align: center;
                margin: auto;
            }
            .edit{
                background-color: #009933;
                color: white;
                width: 35px;
            }
            .remove{
                background-color: #cc0000;
                color: white;
                width: 70px;
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
        <h1 class="head-title">Your selected books</h1>  
        <%            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null && cart.getCart().size() > 0) {
        %>
        <table class="table-viewcart" border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ISBN</th>
                    <th>Book Name</th>
                    <th>Price</th>
                    <th class="qu">Quantity</th>
                    <th>Total</th>
                    <th>Edit</th>
                    <th>Remove</th>
                </tr>
            </thead>           
            <tbody>    
                <%int count = 1;
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
                <tr>
            <form action="EditBookCartController" method="POST">
                <td><%= count++%></td>
                <td>
                    <input type="text" name="isbn" value="<%= book.getIsbn()%>" readonly="">
                </td>
                <td><%= book.getName()%></td>
                <td><%= price%></td>
                <td>
                    <input class="qu" type="number" name="quantity" value="<%= book.getQuantity()%>" required="" min="1">
                </td>
                <td><%=totalS%></td>
                <!--Edit-->
                <td>                       
                    <button type="submit" class="edit">Edit</button>
                </td>
            </form>
            <!--Remove-->
            <form action="RemoveBookCartController" method="POST">
                <td>                       
                    <button type="submit" class="remove" name="isbn" value="<%= book.getIsbn()%>">Remove</button>
                </td>     
            </form>                           
        </tr>
        <%
            }
            totalS = currencyVN.format(total);
        %>
    </tbody>
</table>  
<h1 class="head-title" style="margin-top: 4%">Total: <%= totalS%></h1>

<%
} else {
%>
<p id="emptyList">Empty List</p>
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
<div id="father">
    <a href="GetController?"><p class="con">Continue shopping</p></a>
    <a href="GetController?"><p class="check">Checkout <i class="fa fa-arrow-circle-right"></i></p></a>
</div>
<!-- FOOTER -->
<%@include file="../HeaderFooterPage/footer.jsp" %>


</body>
</html>