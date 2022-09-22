<%-- 
    Document   : viewCart
    Created on : Sep 20, 2022, 9:10:23 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>

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
                margin-top: 200px;
                font-size: 40px; 
                color: red;
                margin-left: 40%;
            }
            .table-viewcart{
                text-align: center;
                margin-left: 33%;
                margin-top: 80px;
                margin-bottom: 50px;
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
                text-align: center;
                border: 2px solid #000000;
            }
        </style>
        <h1 class="head-title">Your selected books</h1>    
        <table class="table-viewcart" border="1" style="margin-left: 12%">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ISBN</th>
                    <th>Book Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Edit Quantity</th>
                    <th>Remove Book</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    float total = 0;
                    for (BookDTO book : cart.getCart().values()) {
                        total += book.getPrice() * book.getQuantity();
                %>
            <form action="http://localhost:8080/BookStoreManagement/MainController">
                <tr>
                    <td><%= count++%></td>
                    <td>
                        <input type="text" name="isbn" value="<%= book.getIsbn()%>" readonly="">
                    </td>
                    <td><%= book.getName()%></td>
                    <td><%= book.getPrice()%></td>
                    <td>
                        <input type="number" name="quantity" value="<%= book.getQuantity()%>" required="" min="1">
                    </td>
                    <td><%= book.getPrice() * book.getQuantity()%></td>
                    <!--Edit-->
                    <td>                       
                        <input type="submit" name="action" value="EditQuantity"/>
                    </td>
                    <!--Remove-->
                    <td>                       
                        <input type="submit" name="action" value="RemoveBook"/>
                    </td>                           
                </tr>
            </form>
            <%
                }
            %>
        </tbody>
    </table>                       
            <h1 class="head-title" style="margin-top: 4%">Total: <%= total%> VND</h1>


    <a style="font-size: 20px; margin-left: 45%; color: #0000ff" href="JSP/HomePage/homePage.jsp">Continue shopping</a>


    <!-- FOOTER -->
    <%@include file="../HeaderFooterPage/footer.jsp" %>


</body>
</html>
