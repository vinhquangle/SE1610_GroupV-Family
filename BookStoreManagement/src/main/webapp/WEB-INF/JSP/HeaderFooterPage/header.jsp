<%-- 
    Document   : header
    Created on : Sep 20, 2022, 3:56:09 PM
    Author     : PC
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
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
        <link type="text/css" rel="stylesheet" href="CSS/stylefix.css" />

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
        <%            String messModal = (String) request.getAttribute("MODAL");
            if (messModal == null) {
                messModal = "";
            } else {
        %>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#exampleModal').modal('show');
            });
        </script>
        <%
            }
            String messi = (String) request.getAttribute("MESS");
            if (messi != null) {
        %>
        <script>
            window.addEventListener("load", function () {
                alert(document.getElementById("messi").value);
            }, );
        </script>
        <input type="hidden" id="messi" value="<%= messi%>">
        <%
            }
        %>
        <!-- Modal -->
        <div  class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thông báo</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <%= messModal%>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
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
                                            Quản lí										
                                        </a>
                                    </li>
                                    <li class="currency">
                                        <a href="#">
                                            Chào mừng đến với Phương Nam										
                                        </a>
                                    </li>
                                    <li class="account">
                                        <a href="#">
                                            Tài khoản
                                            <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul style="width: 120px;" class="account_selection">
                                            <li><a href="LoginController?action=Login"><i class="fa fa-sign-in" aria-hidden="true"></i>Đăng nhập</a></li>
                                            <li><a href="RegisterController?action=Register"><i class="fa fa-user-plus" aria-hidden="true"></i>Đăng kí</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <%
                                    } else if (cus != null) {
                                    %>
                                    <li class="currency">
                                        <a href="#">
                                            Chào mừng <%= cus.getName()%>										
                                        </a>
                                    </li>   
                                    <li class="account">
                                        <a href="#">
                                            Tài khoản
                                            <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul style="width: 120px;"  class="account_selection">
                                            <li><a href="ViewProfileController?action=Profile"><i class="fa fa-user" aria-hidden="true"></i>Tài khoản</a></li>
                                            <li><a href="LogoutController?cusID=<%= cus.getCustomerID()%>"><i class="fa fa-sign-out" aria-hidden="true"></i>Đăng xuất</a></li>
                                                <%
                                                } else {
                                                %>

                                            <li class="currency">
                                                <a href="#">
                                                    Chào mừng <%= staff.getName() %>										
                                                </a>
                                            </li>
                                            <li class="account">
                                                <a href="#">
                                                    Tài khoản
                                                    <i class="fa fa-angle-down"></i>
                                                </a>    
                                                <ul style="width: 120px;"  class="account_selection">
                                                    <li><a href="ViewProfileController?action=Profile"><i class="fa fa-user" aria-hidden="true"></i>Tài khoản</a></li>
                                                    <li><a href="LogoutController?staffID=<%= staff.getStaffID()%>"><i class="fa fa-sign-out" aria-hidden="true"></i>Đăng xuất</a></li>
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
                                                            <a href="GetController?">Phương<span>Nam</span></a>
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
                                                                    <form action="SearchBookController" method="GET">
                                                                        <input class="input" type="text" value="<%= search%>" name="searchBook" placeholder="Tìm sách theo tiêu đề, tác giả hoặc ISBN" style="width: 360px">
                                                                        <input class="search-btn" type="submit" name="button" value="Tìm kiếm" style="width: 100px"/>
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
                                                                                        <form action="LoadController" method="GET">
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
                                                                                    <h5>Tổng tiền: <%= currencyVN.format(total)%></h5>
                                                                                </div>
                                                                            </div>
                                                                            <%
                                                                            } else {
                                                                            %>
                                                                            <p id="emptyList">Trống</p>
                                                                            <%       }
                                                                                if (cart == null || cart.getCart().isEmpty() || (cus == null && staff == null)) {
                                                                            %>
                                                                            <style>
                                                                                #checkout{
                                                                                    display: none;
                                                                                }
                                                                                #viewC{
                                                                                    width: 100%;
                                                                                    margin: auto;   
                                                                                }
                                                                            </style>
                                                                            <%}
                                                                                } catch (Exception e) {
                                                                                }
                                                                            %> 
                                                                            <div class="cart-btns">
                                                                                <a id="viewC" href="AddBookCartController?action=View">Xem giỏ hàng</a>
                                                                                <a id="checkout" href="CheckoutController?action=Checkout">Thanh toán <i class="fa fa-arrow-circle-right"></i></a>
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