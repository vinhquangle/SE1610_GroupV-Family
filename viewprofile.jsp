<%-- 
    Document   : viewprofile
    Created on : Oct 16, 2022, 1:36:54 AM
    Author     : ownhi
--%>

<%@page import="dto.BookDTO"%>
<%@page import="cart.Cart"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="dto.CustomerDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Profile Customer Page</title>
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
    <link type="text/css" rel="stylesheet" href="CSS/stylefix.css" />

    <link rel="stylesheet" type="text/css" href="STYLES/bootstrap4/bootstrap.min.css" />
    <link href="PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="STYLES/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="STYLES/responsive.css" />
    <link rel="stylesheet" type="text/css" href="STYLES/bootstrap4/bootstrap.min.css" />
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
                                <li><a href="#"><i class="fa fa-envelope-o"></i> hotro@nspn.com</a></li>
                                <li><a href="#"><i class="fa fa-map-marker"></i>Thành phố Hồ Chí Minh</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-6 text-right">
                        <div class="top_nav_right">
                            <ul class="top_nav_menu">
                                <!-- Welcome / My Account -->
                                <%
                                    CustomerDTO cus = new CustomerDTO();
                                    try {
                                        cus = (CustomerDTO) session.getAttribute("LOGIN_CUS");
                                    } catch (Exception e) {
                                    }
                                    if(cus == null){
                                        response.sendRedirect("LoginController");
                                            return;
                                    }
                                %>
                                <li class="currency">
                                    <a href="#">
                                        Chào mừng <%= cus.getName()%>										
                                    </a>
                                </li>   
                                <li class="account">
                                    <a href="ViewProfileCusController">
                                        Tài khoản
                                        <i class="fa fa-angle-down"></i>
                                    </a>
                                    <ul class="account_selection">
                                        <li>
                                            <a href="LogoutController?cusID=<%= cus.getCustomerID()%>"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a>
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
                                                        <a href="GetController?">Phương<span>Nam</span></a>
                                                    </div>
                                                    <nav class="navbar">
                                                        <!-- SEARCH BAR -->

                                                        <!-- ACCOUNT -->
                                                        <div class="clearfix">
                                                            <div class="header-ctn">
                                                                <!-- Cart -->
                                                                <div class="dropdown">
                                                                    <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true" style="cursor: pointer">
                                                                        <i class="fa fa-shopping-cart"></i>
                                                                        <span>Giỏ hàng</span>

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
                                                                                Locale localeVN = new Locale("vi", "VN");
                                                                                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                                                                String totalV = new String();
                                                                                float total = 0;
                                                                                Cart cart = (Cart) session.getAttribute("CART");
                                                                                if (cart != null && cart.getCart().size() > 0) {
                                                                        %>
                                                                        <div class="cart-list">
                                                                            <%
                                                                                for (BookDTO book : cart.getCart().values()) {
                                                                                    total += book.getPrice() * book.getQuantity();
                                                                            %>
                                                                            <div class="product-widget">
                                                                                <div class="product-img">
                                                                                    <img src="<%= book.getImg()%>" alt="">
                                                                                </div>
                                                                                <div class="product-body">
                                                                                    <form action="LoadController" method="POST">
                                                                                        <input type="hidden" name="isbn" value="<%= book.getIsbn()%>" >
                                                                                        <h3 onclick="this.parentNode.submit();" class="product-name"> <a style="cursor: pointer;"><%= book.getName()%></a></h3>
                                                                                    </form>
                                                                                    <h4 class="product-price"><span class="qty">Số lượng: <%= book.getQuantity()%> </span></h4>
                                                                                </div>
                                                                            </div>
                                                                            <%
                                                                                }
                                                                            %>
                                                                            <div class="cart-summary">
                                                                                <small>${sessionScope.SELECT} sản phẩm  </small>
                                                                                <h5>Tổng tiền: <%= totalV = currencyVN.format(total)%></h5>
                                                                            </div>
                                                                        </div>
                                                                        <%
                                                                        } else {
                                                                        %>
                                                                        <p id="emptyList">Trống</p>
                                                                        <%       }
                                                                            if (cart == null || cart.getCart().isEmpty() || (cus == null)) {
                                                                        %>
                                                                        <style>
                                                                            #checkout{
                                                                                display: none;
                                                                            }
                                                                            #viewC{
                                                                                width: 100%;
                                                                            }
                                                                        </style>
                                                                        <%}
                                                                            } catch (Exception e) {
                                                                            }
                                                                        %> 
                                                                        <div class="cart-btns">
                                                                            <a style="margin: auto; float: left;" id="viewC" href="AddBookCartController?action=View">Xem giỏ hàng</a>
                                                                            <a style="margin: auto;" id="checkout" href="CheckoutController?action=Checkout">Thanh toán <i class="fa fa-arrow-circle-right"></i></a>
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
                                    <!-- ket thuc Header -->

                                    <!-- aside Widget -->
                                    <div class="row" style="margin-top: 180px">
                                        <div class="col-md-3" style="border-right: 2px solid red;">
                                        <div class="aside" style="margin-left: 70px;">
                                            <h3 class="aside-title">Menu</h3>
                                            <div class="checkbox-filter">

                                                <div class="input-checkbox">
                                                    <label for="category-6">
                                                        <form action="FilterCategoryController" method="POST">
                                                            <input type="hidden" name="cateID" value=""/>
                                                            <button class="hover" style="border: none; background: none;">Order History</button>
                                                        </form>
                                                    </label>
                                                </div>

                                            </div>
                                        </div>   
                                        </div>
                                        <!-- ket thuc aside -->

                                        <!-- Phần nội dung tài khoản -->
                                        <div class="col-md-9">
                                        <div class="container bootstrap snippet" style="background-color: white;" >
                                            <div>            
                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="home">
                                                        <form class="form" action="ViewProfileCusController" method="POST" id="registrationForm">
                                                            <div class="form-group">
                                                                <div class="col-xs-6">
                                                                    <label for="first_name"><h4>Tên tài khoản</h4></label>
                                                                    <input type="text" class="form-control" name="cusID" id="first_name" value="<%= cus.getCustomerID()%>" >
                                                                </div>
                                                            </div>
                                                            <div class="form-group">

                                                                <div class="col-xs-6">
                                                                    <label for="last_name"><h4>Họ và Tên</h4></label>
                                                                    <input type="text" class="form-control" name="name" id="last_name" value="<%= cus.getName()%>">
                                                                </div>
                                                            </div>

                                                            <div class="form-group">

                                                                <div class="col-xs-6">
                                                                    <label for="phone"><h4>Số điện thoại</h4></label>
                                                                    <input type="text" class="form-control" name="phone" id="phone" value="<%= cus.getPhone()%>" >
                                                                </div>
                                                            </div>

                                                            <div class="form-group">
                                                                <div class="col-xs-6">
                                                                    <label for="location"><h4>Địa Chỉ</h4></label>
                                                                    <input type="text" class="form-control" name="address" id="mobile"value="<%= cus.getAddress()%>" >
                                                                </div>
                                                            </div>
                                                            <div class="form-group">

                                                                <div class="col-xs-6">
                                                                    <label for="email"><h4>Email</h4></label>
                                                                    <input type="text" class="form-control" name="email" id="email" value="<%= cus.getEmail()%>" >
                                                                </div>
                                                            </div>
                                                            <div class="form-group">

                                                                <div class="col-xs-6">
                                                                    <label for="email"><h4>Point</h4></label>
                                                                    <input type="text" class="form-control" id="location" name="point" value="<%= cus.getPoint()%>" readonly="" >
                                                                </div>
                                                                <input type="hidden" name="status" value="<%= cus.getStatus() %>">
                                                                 <input type="hidden" name="delete" value="<%= cus.getDelete()%>">
                                                            </div>
                                                            <div class="form-group">

                                                                <div class="col-xs-6">
                                                                    <label for="password"><h4>Mật khẩu</h4></label>
                                                                    <input type="password" class="form-control" name="password" id="password" value="<%= cus.getPassword()%>" readonly="" >
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <div class="col-xs-12">
                                                                    <br>
                                                                    <input class="btn btn-lg btn-success" type="submit" name="action" value="Lưu thay đổi">
                                                                </div>
                                                            </div>
                                                        </form>

                                                        <hr>

                                                    </div><!--/tab-pane-->
                                                </div><!--/tab-pane-->
                                            </div><!--/tab-content-->
                                        </div> 
                                        </div>    
                                    </div>
                                    <!-- /content -->
                                   <%@include file="../HeaderFooterPage/footer.jsp" %>
                                    </body>
                                    </html>
