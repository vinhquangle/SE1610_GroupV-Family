<%-- 
    Document   : register
    Created on : Sep 22, 2022, 9:19:44 PM
    Author     : Admin
--%>

<%@page import="dto.CustomerErrorDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Customer Page</title>
        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="CSS/bootstrap.min.css" />

        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="CSS/nouislider.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="CSS/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="CSS/style.css"/>
        <link type="text/css" rel="stylesheet" href="CSS/styleCreate.css"/>
        <link href="PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Recaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
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
            <%
                String error = (String) request.getAttribute("ERROR");
                if (error == null) {
                    error = "";
                }
                CustomerErrorDTO cusError = (CustomerErrorDTO) request.getAttribute("CUSTOMER_ERROR");
                if (cusError == null) {
                    cusError = new CustomerErrorDTO();
                }
            %>
            
            <form action="RegisterController" method="POST">
                <div class="information">
                    Email<input type="text" name="email" required="" style="margin-left: 50px;" placeholder="Enter Email"/><%=cusError.getEmailError()%></br>
                    Customer ID<input type="text" name="customerID" required=""  style="margin-left: 42px;" placeholder="Enter CustomerID"/> <%=cusError.getCustomerIDError()%></br>
                    Full Name<input type="text" name="fullName" required="" placeholder="Enter FullName"/><%=cusError.getNameError()%></br>
                    Password<input type="password" name="password" style="margin-left: 23px;" required="" placeholder="Enter Password"/></br>
                    Confirm<input type="password" name="confirm" style="margin-left: 33px;" required="" placeholder="Enter Password again"/><%=cusError.getConfirmError()%></br>
                </div>
                
                <div style="display: block; width: 300px;margin-left: auto; margin-right: auto;" class="g-recaptcha" data-sitekey="6LfxTU4gAAAAANL7i9yWhE0_BtD9TgxTRtdY06Vc"></div></br>
                <p style="
                                   color: white;
                                   background-color: red;
                                   font-weight: bold;
                                   width: 350px;
                                   margin: auto;
                                   border-radius: 100px;
                                   margin-bottom: 5px;
                                   text-align: center;
                                   " id="error"><%=error%></p>
                <div class="container mt-3">
                    <button class="btn btn-secondary" type="submit" name="action">Register</button>
                    <button class="btn btn-secondary" type="reset">Reset</button>
                </div>

                
            </form>
            <p style="border-radius: 100px;  font-size: 15px; margin-top: 20px; margin-left: auto; margin-right: auto; width: 150px; text-align: center"><a href="GetController?action=Get">RETURN HOME</p>
            <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>