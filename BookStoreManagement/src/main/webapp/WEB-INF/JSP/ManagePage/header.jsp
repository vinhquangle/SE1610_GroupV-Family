<%-- 
    Document   : header
    Created on : Sep 29, 2022, 6:37:58 PM
    Author     : PC
--%>

<%@page import="dto.StaffDTO"%>
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
        <link rel="stylesheet" href="CSS/bootstrap.min.css">
        <!-- Font Ăwesome
                    ============================================ -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- animate CSS
                    ============================================ -->
        <link rel="stylesheet" href="CSS/animate.css">
        <!-- main CSS
                    ============================================ -->
        <link rel="stylesheet" href="CSS/main.css">
        <!-- metisMenu CSS
                    ============================================ -->
        <link rel="stylesheet" href="CSS/metisMenu.min.css">
        <link rel="stylesheet" href="CSS/metisMenu-vertical.css">
        <!-- style CSS
                    ============================================ -->
        <link rel="stylesheet" href="CSS/styleAdmin.css">
        <!-- responsive CSS
                    ============================================ -->
        <link rel="stylesheet" href="CSS/responsive.css">
        <!-- modal CSS
                    ============================================ -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head> 
    <body style="background-color: transparent;">    
        <%@include file = "sideBoard.jsp" %>
        <!-- Start Welcome area -->
        <div class="all-content-wrapper" >
            <div class="header-advance-area">
                <div  class="header-top-area">
                    <div class="container-fluid">
                        <div style="background-color: #494f57;" class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="header-top-wraper">
                                    <div class="row">
                                        <div class="col-lg-1 col-md-0 col-sm-1 col-xs-12">
                                            <div class="menu-switcher-pro" style="margin-left: 20px">
                                                <button style="color: white;" type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
                                                    <i class="fa fa-bars"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-7 col-sm-6 col-xs-12">
                                            <div class="header-top-menu tabl-d-n">
                                                <ul class="nav navbar-nav mai-top-nav" >
                                                    <li style="margin: auto;" class="nav-item"><a style="margin: auto;"href="GetController?" class="nav-link">Home</a>
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
                                                            <span class="admin-name">Name</span>
                                                        </a>
                                                        <ul style="background-color: white;" role="menu" class="dropdown-header-top author-log dropdown-menu animated zoomIn">     
                                                            <style>
                                                                #out{
                                                                    color: #494f57;
                                                                }
                                                                #out:hover{
                                                                    color: white;
                                                                }
                                                            </style>
                                                            <li><a id="out" href="LogoutController?staffID="><span class="fa fa-lock author-log-ic out"></span><b>Đăng xuất</b></a></li>
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
                    <div style="margin-bottom: 70px;" class="container-fluid">
                    </div>
                </div>
            </div>

            <!-- jquery
                    ============================================ -->
            <script src="JS/jquery-1.11.3.min.js"></script>
            <!-- bootstrap JS
                        ============================================ -->
            <script src="JS/bootstrap.min.js"></script>
            <!-- meanmenu JS
                        ============================================ -->
            <script src="JS/jquery.meanmenu.js"></script>
            <!-- sticky JS
                        ============================================ -->
            <script src="JS/jquery.sticky.js"></script>
            <!-- metisMenu JS
                        ============================================ -->
            <script src="JS/metisMenu.min.js"></script>
            <script src="JS/metisMenu-active.js"></script>
            <!-- main JS
                        ============================================ -->
            <script src="JS/main.js"></script>
            <script>
                (function ($) {
                    "use strict";

                    /*----------------------------
                     jQuery MeanMenu
                     ------------------------------ */
                    jQuery('nav#dropdown').meanmenu();
                    /*----------------------------
                     jQuery myTab
                     ------------------------------ */
                    $('#myTab a').click(function (e) {
                        e.preventDefault()
                        $(this).tab('show')
                    });
                    $('#myTab3 a').click(function (e) {
                        e.preventDefault()
                        $(this).tab('show')
                    });
                    $('#myTab4 a').click(function (e) {
                        e.preventDefault()
                        $(this).tab('show')
                    });

                    $('#single-product-tab a').click(function (e) {
                        e.preventDefault()
                        $(this).tab('show')
                    });

                    $('[data-toggle="tooltip"]').tooltip();

                    $('#sidebarCollapse').on('click', function () {
                        $('#sidebar').toggleClass('active');

                    });
                    // Collapse ibox function
                    $('#sidebar ul li').on('click', function () {
                        var button = $(this).find('i.fa.indicator-mn');
                        button.toggleClass('fa-plus').toggleClass('fa-minus');

                    });
                    /*-----------------------------
                     Menu Stick
                     ---------------------------------*/
                    $(".sicker-menu").sticky({topSpacing: 0});

                    $('#sidebarCollapse').on('click', function () {
                        $("body").toggleClass("mini-navbar");
                        SmoothlyMenu();
                    });
                    $(document).on('click', '.header-right-menu .dropdown-menu', function (e) {
                        e.stopPropagation();
                    });


                    /*----------------------------
                     wow js active
                     ------------------------------ */
                    new WOW().init();

                    /*----------------------------
                     owl active
                     ------------------------------ */
                    $("#owl-demo").owlCarousel({
                        autoPlay: false,
                        slideSpeed: 2000,
                        pagination: false,
                        navigation: true,
                        items: 4,
                        /* transitionStyle : "fade", */    /* [This code for animation ] */
                        navigationText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
                        itemsDesktop: [1199, 4],
                        itemsDesktopSmall: [980, 3],
                        itemsTablet: [768, 2],
                        itemsMobile: [479, 1],
                    });

                    /*----------------------------
                     price-slider active
                     ------------------------------ */
                    $("#slider-range").slider({
                        range: true,
                        min: 40,
                        max: 600,
                        values: [60, 570],
                        slide: function (event, ui) {
                            $("#amount").val("£" + ui.values[ 0 ] + " - £" + ui.values[ 1 ]);
                        }
                    });
                    $("#amount").val("£" + $("#slider-range").slider("values", 0) +
                            " - £" + $("#slider-range").slider("values", 1));

                    /*--------------------------
                     scrollUp
                     ---------------------------- */
                    $.scrollUp({
                        scrollText: '<i class="fa fa-angle-up"></i>',
                        easingType: 'linear',
                        scrollSpeed: 900,
                        animation: 'fade'
                    });

                })(jQuery);
            </script>
            <!-- modal JS
                        ============================================ -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>