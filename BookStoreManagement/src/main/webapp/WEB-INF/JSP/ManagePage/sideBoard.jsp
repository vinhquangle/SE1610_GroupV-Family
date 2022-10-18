<%-- 
    Document   : SideBoard
    Created on : Oct 2, 2022, 12:08:07 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SideBoard Page</title>
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
    <body>
        <div class="left-sidebar-pro">
            <nav id="sidebar" class="">
                <div class="sidebar-header">
                    <a href="index.html"><img class="main-logo" src="img/logo/logo.png" alt="" /></a>
                    <strong><img src="img/logo/logosn.png" alt="" /></strong>
                </div>
                <div class="left-custom-menu-adp-wrap comment-scrollbar">
                    <nav class="sidebar-nav left-sidebar-menu-pro">
                        <ul class="metismenu" id="menu1">
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-product-hunt icon-wrap"></i> 
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí sách</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageBookController', '')" title="Book" href="#"><i class="fa fa-book sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Sách</span></a></li>    
                                    <li><a onclick="load('ManageBookController')" title="Book Requesting" href="#"><i class="fa fa-heart-o sub-icon-mg" aria-hidden="true"></i><span class="mini-sub-pro">Yêu cầu nhập sách</span></a></li>
                                    <li><a onclick="load('ManageBookController')" title="Book Responding" href="#"><i class="fa fa-level-down sub-icon-mg" aria-hidden="true"></i><span class="mini-sub-pro">Phản hồi nhập sách</span></a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" style="padding-right: 35px;">
                                    <i class="fa big-icon fa-users icon-wrap"></i>
                                    <span style="font-size: 14px;"  class="mini-click-non">Quản lí tài khoản</span>
                                </a>
                                <ul class="submenu-angle" aria-expanded="true">
                                    <li><a onclick="loadCus('ManageCustomerController','')" title="Customer" href="#"><i class="fa fa-user sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Khách hàng</span></a></li>
                                    <li><a onclick="loadStaff('ManageStaffController','')" title="Staff and Deliverer" href="#"><i class="fa fa-male sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Nhân viên</span></a></li>                              
                                </ul>
                            </li>                          
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-shopping-cart icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí đơn hàng</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCustomerController')" title="Completed" href="#"><i class="fa fa-check sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Hoàn thành</span></a></li>                                
                                    <li><a onclick="load('ManageCustomerController')" title="On process" href="#"><i class="fa fa-location-arrow sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Đang tiến hành</span></a></li>
                                    <li><a onclick="load('ManageCustomerController')" title="Cancelled" href="#"><i class="fa fa-ban sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Hủy bỏ</span></a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-newspaper-o icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí nhà xuất bản</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="loadPub('ManagePublisherController', '')" title="Publisher" href="#"><i class="fa fa-pencil sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Nhà xuất bản</span></a></li>                                                                    
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-list-alt icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí thể loại</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="loadCate('ManageCategoryController', '')" title="Category" href="#"><i class="fa fa-list-ol sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Thể loại</span></a></li>                                                                    
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false" style="padding-right: 32px">
                                    <i class="fa big-icon fa-bullhorn icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí khuyến mãi</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a title="Promotion" href="#"><i class="fa fa-bullhorn sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Khuyến mãi</span></a></li>                                                                    
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-credit-card-alt icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Quản lí doanh thu</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCustomerController')" title="Revenue" href="#"><i class="fa fa-money sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Doanh thu</span></a></li>                                                                    
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
            </nav>
        </div>
    </body>
</html>