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
                                    <span style="font-size: 14px;" class="mini-click-non">Product Management</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageBookController')" title="Book" href="#"><i class="fa fa-book sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Book</span></a></li>    
                                    <li><a onclick="load('ManageBookController')" title="Book Requesting" href="#"><i class="fa fa-heart-o sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Book Requesting</span></a></li>
                                    <li><a onclick="load('ManageBookController')" title="Book Responding" href="#"><i class="fa fa-level-down sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Book Responding</span></a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" style="padding-right: 35px;">
                                    <i class="fa big-icon fa-users icon-wrap"></i>
                                    <span style="font-size: 14px;"  class="mini-click-non">Account Management</span>
                                </a>
                                <ul class="submenu-angle" aria-expanded="true">
                                    <li><a onclick="load('ManageCustomerController')" title="Customer" href="#"><i class="fa fa-user sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Customer</span></a></li>
                                    <li><a onclick="load('ManageCustomerController')" title="Staff and Deliverer" href="#"><i class="fa fa-male sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Staff and Deliverer</span></a></li>                              
                                </ul>
                            </li>                          
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-shopping-cart icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Order Management</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCustomerController')" title="Completed" href="#"><i class="fa fa-check sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Completed</span></a></li>                                
                                    <li><a onclick="load('ManageCustomerController')" title="On process" href="#"><i class="fa fa-location-arrow sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">On process</span></a></li>
                                    <li><a onclick="load('ManageCustomerController')" title="Cancelled" href="#"><i class="fa fa-ban sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Cancelled</span></a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-newspaper-o icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Publisher Management</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCustomerController')" title="Publisher" href="#"><i class="fa fa-pencil sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Publisher List</span></a></li>                                                                    
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-list-alt icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Category Management</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCategoryController')" title="Category" href="#"><i class="fa fa-list-ol sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">List Category</span></a></li>                                                                    
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#" aria-expanded="false">
                                    <i class="fa big-icon fa-credit-card-alt icon-wrap"></i>
                                    <span style="font-size: 14px;" class="mini-click-non">Revenue Management</span></a>
                                <ul class="submenu-angle" aria-expanded="false">
                                    <li><a onclick="load('ManageCustomerController')" title="Revenue" href="#"><i class="fa fa-money sub-icon-mg" aria-hidden="true"></i> <span class="mini-sub-pro">Revenue</span></a></li>                                                                    
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
            </nav>
        </div>
    </body>
</html>