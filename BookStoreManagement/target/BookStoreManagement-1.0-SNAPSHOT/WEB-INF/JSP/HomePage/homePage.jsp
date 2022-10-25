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
                <%                    CategoryDTO cate = (CategoryDTO) request.getAttribute("CATEGORY");
                    String cateN = new String();
                    if (cate != null) {
                        cateN = cate.getName();

                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Trang chủ</a></li>
                            <li><a href="GetController?">Thể loại</a></li>
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
                    String pubN = new String();
                    if (pub != null) {
                        pubN = pub.getName();
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Trang chủ</a></li>
                            <li><a href="GetController?">Nhà xuất bản</a></li>
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
                    String me = new String();
                    if (max != null && min != null && max != "" && min != "") {
                        me = (String) request.getAttribute("MESS");
                %>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Trang chủ</a></li>
                            <li><a href="GetController?">Giá bán</a></li>
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
                    } else {
                        max = "";
                        min = "";
                        me = "";
                    }
                %>
                <!-- /row -->
                <div class="row">
                    <div class="col-md-3">
                        <%@include file = "filterPage.jsp" %>
                    </div>
                    <%  int count = (int) session.getAttribute("COUNT_BOOK");
                        String controller = (String) request.getAttribute("CONTROLLER");
                        int pagenum = 0;
                        if (count != 0) {
                            if ((count % 9) == 0) {
                                pagenum = count / 9;
                            } else {
                                pagenum = (count / 9) + 1;
                            }
                        }
                        String mess = (String) request.getAttribute("MESSAGE");
                        if (mess == null) {
                    %>
                    <div class="col-md-9">
                        <%@include file = "showPage.jsp" %>
                        <div style="text-align: center;" class="row">
                            <div class="col-md-12">
                                <div class="pagination">
                                    <%                                        for (int i = 1; i <= pagenum; i++) {
                                    %>
                                    <a id="<%=i%>" onclick="this.parentNode.submit();" href="<%=controller%>index=<%=i%>"><%=i%></a>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                        <%        } else {
                        %>
                        <div style="text-align: center;" class="col-md-9">
                            <p style="margin-top:100px; font-size: 100px; text-align: center;"><%= mess%></p>
                            <img style="margin: auto; width: 200px;" src="https://cdn-icons-png.flaticon.com/512/2496/2496231.png"/>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <script>
                        window.addEventListener('load', () => {
                            const query = window.location.search;
                            const urlParams = new URLSearchParams(query);
                            var product = urlParams.get('index');
                            if (product === null) {
                                product = "1";
                            }
                            document.getElementById(product).classList.add("active");
                        });
                    </script>
                    <style>
                        .pagination {
                            display: inline-block;
                        }

                        .pagination a {
                            color: black;
                            float: left;
                            padding: 8px 16px;
                            text-decoration: none;
                        }

                        .pagination a.active {
                            background-color: #15161d;
                            color: white;
                        }

                        .pagination a:hover:not(.active) {background-color: #ddd;}
                    </style>
                </div>
            </div>
        </div>
        <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>    
</html>