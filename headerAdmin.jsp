<%-- 
    Document   : header
    Created on : Sep 29, 2022, 6:37:58 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header Page</title>
        <!-- Google Fonts
                    ============================================ -->
        <link href="https://fonts.googleapis.com/css?family=Play:400,700" rel="stylesheet">

        <!-- Bootstrap CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/bootstrap.min.css">

        <!-- Font Ăwesome
                    ============================================ -->
        <link rel="stylesheet" href="css/font-awesome.min.css">

        <!-- animate CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/animate.css">


        <!-- main CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/main.css">


        <!-- metisMenu CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/metisMenu/metisMenu.min.css">
        <link rel="stylesheet" href="css/metisMenu/metisMenu-vertical.css">

        <!-- style CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/styleAdmin.css">
        <!-- responsive CSS
                    ============================================ -->
        <link rel="stylesheet" href="css/responsive.css">
    </head>
    <body>
        <%@include file="sideBoard.jsp" %>
        <!-- Start Welcome area -->
        <div class="all-content-wrapper" >

            <div class="header-advance-area">
                <div class="header-top-area">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="header-top-wraper">
                                    <div class="row">
                                        <div class="col-lg-1 col-md-0 col-sm-1 col-xs-12">
                                            <div class="menu-switcher-pro" style="margin-left: 20px">
                                                <button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
                                                    <i class="fa fa-bars"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-7 col-sm-6 col-xs-12">
                                            <div class="header-top-menu tabl-d-n">
                                                <ul class="nav navbar-nav mai-top-nav" >
                                                    <li class="nav-item"><a href="#" class="nav-link">Home</a>
                                                    </li>                                               
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                                            <div class="header-right-info">
                                                <ul class="nav navbar-nav mai-top-nav header-right-menu">

                                                    <li class="nav-item">
                                                        <a href="#" data-toggle="dropdown" role="button" aria-expanded="false" class="nav-link dropdown-toggle">
                                                            <i class="fa fa-user adminpro-user-rounded header-riht-inf" aria-hidden="true"></i>
                                                            <span class="admin-name">Admin name</span>
                                                            <i class="fa fa-angle-down adminpro-icon adminpro-down-arrow"></i>
                                                        </a>
                                                        <ul role="menu" class="dropdown-header-top author-log dropdown-menu animated zoomIn">
                                                            <li><a href="register.html"><span class="fa fa-home author-log-ic"></span>Register</a>
                                                            </li>
                                                            <li><a href="#"><span class="fa fa-user author-log-ic"></span>My Profile</a>
                                                            </li>                                                        
                                                            <li><a href="login.html"><span class="fa fa-lock author-log-ic"></span>Log Out</a>
                                                            </li>
                                                        </ul>
                                                    </li>

                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="breadcome-area">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="breadcome-list single-page-breadcome"  style="margin-top: 40px;">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                            <div class="breadcome-heading" style="margin-left: 20px">
                                                <form role="search" class="">
                                                    <input type="text" placeholder="Search..." class="form-control">
                                                    <a href=""><i class="fa fa-search"></i></a>
                                                </form>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                            <ul class="breadcome-menu" >
                                                <li><a href="#">Home</a> <span class="bread-slash">/</span>
                                                </li>
                                                <li><span class="bread-blod">Customer Management</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- jquery
                    ============================================ -->
            <script src="js/vendor/jquery-1.11.3.min.js"></script>
            <!-- bootstrap JS
                        ============================================ -->
            <script src="js/bootstrap.min.js"></script>

            <!-- meanmenu JS
                        ============================================ -->
            <script src="js/jquery.meanmenu.js"></script>

            <!-- sticky JS
                        ============================================ -->
            <script src="js/jquery.sticky.js"></script>


            <!-- metisMenu JS
                        ============================================ -->
            <script src="js/metisMenu/metisMenu.min.js"></script>
            <script src="js/metisMenu/metisMenu-active.js"></script>


            <!-- main JS
                        ============================================ -->
            <script src="js/mainAdmin.js"></script>
    </body>
</html>
