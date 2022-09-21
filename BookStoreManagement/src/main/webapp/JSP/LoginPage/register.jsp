<%-- 
    Document   : register
    Created on : Sep 20, 2022, 9:32:36 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Customer Page</title>
        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="../../CSS/bootstrap.min.css" />

        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="../../CSS/nouislider.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="../../CSS/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="../../CSS/style.css"/>
        <link type="text/css" rel="stylesheet" href="../../CSS/styleCreate.css"/>
        <link href="../../PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        
    </head>
    <body>
        <!-- HEADER -->
        <header>
            <!-- TOP HEADER -->
            <div id="top-header">
                <div class="container">
                    <ul class="header-links pull-left">
                        <li><a href="#"><i class="fa fa-phone"></i>034. 223 3002</a></li>
                        <li><a href="#"><i class="fa fa-envelope-o"></i>Contact admin@gmail.com</a></li>
                        <li><a href="#"><i class="fa fa-map-marker"></i> FPT University, Ward 9, HCMC</a></li>
                    </ul>
                    <ul class="header-links pull-right">
                        <li><a href="#"><i class="fa fa-dollar"></i> USD</a></li>
                        <li><a href="#"><i class="fa fa-user-o"></i> My Account</a></li>
                    </ul>
                </div>
            </div>
            <!-- /TOP HEADER -->
            
            <h1 style="color: red; font-size: 40px; margin-top: 30px">Register Here</h1>
            <form action="MainController" method="POST">
                <div class="information">
                    User ID<input type="text" name="userID" required=""  style="margin-left: 42px;" placeholder="Enter UserID"/></br>

                    Full Name<input type="text" name="fullName" required="" placeholder="Enter FullName"/></br>

                    Role ID<input type="text" name="roleID" value="US" style="margin-left: 43px;" readonly=""/></br>
                    Password<input type="password" name="password" style="margin-left: 23px;" required="" placeholder="Enter Password"/></br>
                    Confirm<input type="password" name="confirm" style="margin-left: 33px;" required="" placeholder="Enter Password again"/></br>

                    Status<input type="text" value="true" style="margin-left: 47px;" readonly=""/></br>
                </div>
                <input type="submit" name="action" value="Create"/>
                <input type="reset" value="Reset"/>
            </form>
            
            
            <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>
