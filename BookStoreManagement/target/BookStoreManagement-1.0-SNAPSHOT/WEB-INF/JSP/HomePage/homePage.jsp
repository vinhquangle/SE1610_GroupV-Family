<%-- 
    Document   : home
    Created on : Sep 18, 2022, 11:23:13 AM
    Author     : PC
--%>

<%@page import="dto.BookDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.PublisherDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Colo Shop Template">
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <link type="text/css" rel="stylesheet" href="CSS/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="STYLES/bootstrap4/bootstrap.min.css" />
    </head>

    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <div style="margin-top: 180px; background-color: white;" id="breadcrumb" class="section" >
            <!-- container -->
            <div class="container">
                <!-- row -->
                <%                        CategoryDTO cate = (CategoryDTO) request.getAttribute("CATEGORY");
                    if (cate != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">All Categories</a></li>
                            <li>
                                <form style="display: inline-block;" method="POST" action="FilterCategoryController">
                                    <input type="hidden" name="cateID" value="<%= cate.getCategoryID()%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= cate.getName()%></a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                    PublisherDTO pub = (PublisherDTO) request.getAttribute("PUBLISHER");
                    if (pub != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">All Publisher</a></li>
                            <li>
                                <form style="display: inline-block;" method="POST" action="FilterPublisherController">
                                    <input type="hidden" name="pubID" value="<%= pub.getPublisherID()%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= pub.getName()%></a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                    String max = (String) request.getAttribute("MAX");
                    String min = (String) request.getAttribute("MIN");
                    String me = (String) request.getAttribute("MESS");
                    if (max != null && min != null) {
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Home</a></li>
                            <li><a href="GetController?">Price</a></li>
                            <li>
                                <form style="display: inline-block;" method="POST" action="FilterPriceController">
                                    <input type="hidden" name="min" value="<%= min%>" /> 
                                    <input type="hidden" name="max" value="<%= max%>" /> 
                                    <input type="hidden" name="mess" value="<%= me%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= me%></a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                %>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <%@include file = "filterPage.jsp" %>
                </div>
                <%            String mess = (String) request.getAttribute("MESSAGE");
                    if (mess == null) {
                %>
                <div class="col-md-9">
                    <%@include file = "showPage.jsp" %>
                </div>
                <%        } else {
                %>
                <div class="col-md-9">
                    <p style="margin-top:100px; font-size: 100px; text-align: center;"><%= mess%></p>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <%@include file="../HeaderFooterPage/footer.jsp" %>

    </body>
</html>