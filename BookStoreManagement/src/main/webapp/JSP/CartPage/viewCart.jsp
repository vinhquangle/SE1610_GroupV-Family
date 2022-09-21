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
        <link type="text/css" rel="stylesheet" href="../../CSS/styleViewCart.css"/>
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
        
        <h1 style="margin-top: 200px; font-size: 40px; color: red">Your selected items</h1>    
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Edit</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                
                    <form action="MainController">
                        <tr>
                            <td>aaaa</td>
                            <td>aaaa</td>
                            <td>aaaa</td>
                            <td>aaaa</td>
                            <td>aaaa</td>
                            <td>aaaa</td>
                            <!--Edit-->
                            <td>                       
                                <input type="submit" name="action" value="Edit"/>
                            </td>
                            <!--Remove-->
                            <td>                       
                                <input type="submit" name="action" value="Remove"/>
                            </td>                           
                        </tr>
                    </form>

                </tbody>
            </table>                       
        <h1 style="font-size: 35px; margin-left: 40px">Total: $</h1>


        <a style="font-size: 20px; margin-left: 45%; color: #0000ff" href="../HomePage/homePage.jsp">Continue shopping</a>

    
    <!-- FOOTER -->
    <%@include file="../HeaderFooterPage/footer.jsp" %>


</body>
</html>
