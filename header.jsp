<%-- 
    Document   : header
    Created on : Sep 20, 2022, 3:56:09 PM
    Author     : PC
--%>

<%@page import="dto.BookDTO"%>
<%@page import="cart.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Header Page</title>
        <meta charset="utf-8">
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

        <!-- jQuery Plugins -->
        <script src="JS/jquery.min.js"></script>
        <script src="JS/bootstrap.min.js"></script>
        <script src="JS/slick.min.js"></script>
        <script src="JS/nouislider.min.js"></script>
        <script src="JS/jquery.zoom.min.js"></script>
        <script src="JS/main.js"></script>
    </head>
    <body>
        <!-- Header -->

        <header class="header trans_300">

            <!-- Top Navigation -->

            <div class="top_nav">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="top_nav_left">
                                <ul class="header-links pull-left">
                                    <li><a href="#"><i class="fa fa-phone"></i> +021-95-51-84</a></li>
                                    <li><a href="#"><i class="fa fa-envelope-o"></i> email@email.com</a></li>
                                    <li><a href="#"><i class="fa fa-map-marker"></i> 1734 Stonecoal Road</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-md-6 text-right">
                            <div class="top_nav_right">
                                <ul class="top_nav_menu">

                                    <!-- Welcome / My Account -->
                                    <li class="currency">
                                        <a href="#">
                                            Welcome: V-Family										
                                        </a>
                                    </li>

                                    <!--Welcome loginUser-->
                                    <li class="account">
                                        <a href="#">
                                            My Account
                                            <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul class="account_selection">
                                            <li><a href="JSP/LoginPage/login.jsp"><i class="fa fa-sign-in" aria-hidden="true"></i>Sign In</a></li>
                                            <li><a href="JSP/LoginPage/register.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i>Register</a>
                                            </li>
                                        </ul>
                                    </li>
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
                                <a href="#">colo<span>shop</span></a>
                            </div>
                            <nav class="navbar">
                                <!-- SEARCH BAR -->

                                <div>
                                    <div class="header-search" style="margin-top: 15px; margin-right: 40px">
                                        <%
                                            String search = (String) request.getParameter("searchBook");
                                            if(search == null){
                                                search = "";
                                            }
                                        %>
                                        <form action="MainController">
                                            <input class="input" type="text" value="<%= search%>" name="searchBook" placeholder="Search here" style="width: 350px">
                                            <input class="search-btn" type="submit" name="action" value="SearchBook" style="width: 100px"/>
                                        </form>
                                    </div>
                                </div>
                                <!-- /SEARCH BAR -->

                                <!-- ACCOUNT -->
                                <div class="clearfix">
                                    <%
                                        Cart cart = (Cart) session.getAttribute("CART");
                                        if (cart != null && cart.getCart().size() > 0) {

                                    %>
                                    <div class="header-ctn">
                                        <!-- Cart -->
                                        <div class="dropdown">
                                            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true" style="cursor: pointer">
                                                <i class="fa fa-shopping-cart"></i>
                                                <span>Your Cart</span>
                                                <div class="qty">${sessionScope.SIZE}</div>
                                            </a>
                                            <%  
                                                float total = 0;
                                                for (BookDTO book : cart.getCart().values()) {
                                                    total += book.getPrice() * book.getQuantity();
                                            %>
                                            <div class="cart-dropdown">
                                                <div class="cart-list">
                                                    <div class="product-widget">
                                                        <div class="product-img">
                                                            <img src="<%= book.getImg()%>" alt="">
                                                        </div>
                                                        <div class="product-body">
                                                            <h3 class="product-name"><a href="#"><%= book.getName()%></a></h3>
                                                            <h4 class="product-price"><span class="qty"><%= book.getQuantity()%></span><%= book.getName()%></h4>
                                                        </div>
                                                        <button class="delete"><i class="fa fa-close"></i></button>
                                                    </div>
                                                </div>
                                                <div class="cart-summary">
                                                    <small>${sessionScope.SIZE} Item(s) selected</small>
                                                    <h5>SUBTOTAL: <%= total%>$</h5>
                                                </div>
                                                <div class="cart-btns">
                                                    <a href="JSP/CartPage/viewCart.jsp">View Cart</a>
                                                    <a href="JSP/CheckOutPage/checkout.jsp">Checkout<i class="fa fa-arrow-circle-right"></i></a>
                                                </div>
                                            </div>
                                            <%
                                                }
                                            %> 
                                        </div>
                                        <!-- /Cart -->
                                    </div>
                                    <%
                                        }
                                    %> 
                                </div>
                                <!-- /ACCOUNT -->

                            </nav>
                        </div>
                    </div>
                </div>
            </div>

        </header>

</html>
