<%-- 
    Document   : header
    Created on : Sep 20, 2022, 3:56:09 PM
    Author     : PC
--%>

<%@page import="dto.PublisherDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.CustomerDTO"%>
<%@page import="dto.StaffDTO"%>
<%@page import="javax.smartcardio.Card"%>
<%@page import="cart.Cart"%>
<%@page import="dto.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Header Page</title>
        <meta charset="utf-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="CSS/bootstrap.min.css" />

        <!-- Slick -->
        <link type="text/css" rel="stylesheet" href="CSS/slick.css" />
        <link type="text/css" rel="stylesheet" href="CSS/slick-theme.css" />

        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="CSS/nouislider.min.css" />

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="CSS/font-awesome.min.css" />

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="CSS/style.css" />

        <link rel="stylesheet" type="text/css" href="STYLES/bootstrap4/bootstrap.min.css" />
        <link href="PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="STYLES/main_styles.css" />
        <link rel="stylesheet" type="text/css" href="STYLES/responsive.css" />
        <style>
            #emptyList{
                font-size: 20px;
                text-align: center;
            }
        </style>
        <!-- jQuery Plugins -->
        <script src="JS/jquery.min.js"></script>
        <script src="JS/bootstrap.min.js"></script>
        <script src="JS/slick.min.js"></script>
        <script src="JS/nouislider.min.js"></script>
        <script src="JS/jquery.zoom.min.js"></script>
        <script src="JS/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <!-- Header -->
        <header style="z-index: 50;" class="header trans_300">
            <!-- Top Navigation -->
            <div class="top_nav">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="top_nav_left">
                                <ul class="header-links pull-left">
                                    <li><a href="#"><i class="fa fa-phone"></i>1900-6656</a></li>
                                    <li><a href="#"><i class="fa fa-envelope-o"></i> hotro@nhasachphuongnam.com</a></li>
                                    <li><a href="#"><i class="fa fa-map-marker"></i> Ho Chi Minh City</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-md-6 text-right">
                            <div class="top_nav_right">
                                <ul class="top_nav_menu">
                                    <!-- Welcome / My Account -->
                                    <%
                                        StaffDTO staff = new StaffDTO();
                                        CustomerDTO cus = new CustomerDTO();
                                        try {
                                            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
                                            cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
                                        } catch (Exception e) {
                                        }
                                        if (staff == null && cus == null) {
                                    %>
                                    <li class="currency">
                                        <a href="LoadManageController?action=manage">
                                            Manage										
                                        </a>
                                    </li>
                                    <li class="currency">
                                        <a href="#">
                                            Welcome To BookStore										
                                        </a>
                                    </li>
                                    <li class="account">
                                        <a href="#">
                                            My Account
                                            <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul class="account_selection">
                                            <li><a href="LoginController?action=Login"><i class="fa fa-sign-in" aria-hidden="true"></i>Sign In</a></li>
                                            <li><a href="RegisterController?action=Register"><i class="fa fa-user-plus" aria-hidden="true"></i>Register</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <%
                                    } else if (cus != null) {
                                    %>
                                    <li class="currency">
                                        <a href="#">
                                            Welcome <%= cus.getName()%>										
                                        </a>
                                    </li>   
                                    <li class="account">
                                        <a href="#">
                                            My Account
                                            <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul class="account_selection">
                                            <li><a href="LogoutController?cusID=<%= cus.getCustomerID()%>"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a></li>
                                                <%
                                                } else {
                                                %>
                                            <li class="currency">
                                                <a href="#">
                                                    Welcome <%= staff.getName()%>										
                                                </a>
                                            </li>
                                            <li class="account">
                                                <a href="#">
                                                    My Account
                                                    <i class="fa fa-angle-down"></i>
                                                </a>
                                                <ul class="account_selection">
                                                    <li><a href="LogoutController?staffID=<%= staff.getStaffID()%>"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a></li>
                                                        <%
                                                            }
                                                        %>
                                                </ul>
                                            </li>
                                            <!--Welcome loginUser-->
                                        </ul>
                                        </div>
                                        </div>
                                        </div>
                                        </div>
                                        </div>       
                                        <!-- Main Navigation -->
                                        <div class="main_nav_container">
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-lg-12 text-right">
                                                        <div class="logo_container">
                                                            <a href="GetController?">book<span>store</span></a>
                                                        </div>
                                                        <nav class="navbar">
                                                            <!-- SEARCH BAR -->

                                                            <div>
                                                                <div class="header-search" style="margin-top: 15px; margin-right: 40px">
                                                                    <%
                                                                        String search = (String) request.getParameter("searchBook");
                                                                        if (search == null) {
                                                                            search = "";
                                                                        }
                                                                        float total = 0;
                                                                    %>
                                                                    <form action="SearchBookController" method="POST">
                                                                        <input class="input" type="text" value="<%= search%>" name="searchBook" placeholder="Search by title, author or ISBN" style="width: 350px">
                                                                        <input class="search-btn" type="hidden" name="action" value="SearchBook" style="width: 100px"/>
                                                                        <input class="search-btn" type="submit" name="button" value="Search" style="width: 100px"/>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                            <!-- /SEARCH BAR -->

                                                            <!-- ACCOUNT -->
                                                            <div class="clearfix">
                                                                <div class="header-ctn">
                                                                    <!-- Cart -->
                                                                    <div class="dropdown">
                                                                        <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true" style="cursor: pointer">
                                                                            <i class="fa fa-shopping-cart"></i>
                                                                            <span>Your Cart</span>

                                                                            <%
                                                                                if (session.getAttribute("SIZE") != null && (int) session.getAttribute("SIZE") > 0) {
                                                                                    int count = (int) session.getAttribute("SIZE");
                                                                            %>
                                                                            <div class="qty"><%= count%></div>
                                                                            <%
                                                                                }
                                                                            %>
                                                                        </a>
                                                                        <div class="cart-dropdown">
                                                                            <%
                                                                                try {
                                                                                    Cart cart = (Cart) session.getAttribute("CART");
                                                                                    if (cart != null && cart.getCart().size() > 0) {
                                                                            %>
                                                                            <div class="cart-list">

                                                                                <%           for (BookDTO book : cart.getCart().values()) {
                                                                                        total += book.getPrice() * book.getQuantity();
                                                                                %>
                                                                                <div class="product-widget">
                                                                                    <div class="product-img">
                                                                                        <img src="<%= book.getImg()%>" alt="">
                                                                                    </div>
                                                                                    <div class="product-body">
                                                                                        <h3 class="product-name"><a href="MainController?action=Load&&isbn=<%= book.getIsbn()%>"><%= book.getName()%></a></h3>
                                                                                        <h4 class="product-price"><span class="qty"><%= book.getQuantity()%></span><%= book.getName()%></h4>
                                                                                    </div>
                                                                                    <button class="delete"><i class="fa fa-close"></i></button>
                                                                                </div>
                                                                                <%
                                                                                    }
                                                                                %>
                                                                                <div class="cart-summary">
                                                                                    <small>${sessionScope.SELECT} Item(s) selected  </small>
                                                                                    <h5>SUBTOTAL: <%= total%> VNĐ  </h5>
                                                                                </div>
                                                                            </div>
                                                                            <%
                                                                            } else {
                                                                            %>
                                                                            <p id="emptyList">Empty List</p>
                                                                            <%       }
                                                                            %>

                                                                            <%} catch (Exception e) {
                                                                                }
                                                                            %> 
                                                                            <div class="cart-btns">
                                                                                <a href="AddBookCartController?action=View">View Cart</a>
                                                                                <a href="JSP/CheckOutPage/checkout.jsp">Checkout <i class="fa fa-arrow-circle-right"></i></a>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                    <!-- /Cart -->
                                                                </div> 
                                                            </div>
                                                            <!-- /ACCOUNT -->

                                                        </nav>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        </header>
                                        </body>
                                        </html>