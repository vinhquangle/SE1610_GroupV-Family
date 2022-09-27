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
    </head>

    <body>

        <%@include file="../HeaderFooterPage/header.jsp" %>
        <%            String mess = (String) request.getAttribute("MESSAGE");
            if (mess == null) {
        %>
        <%@include file = "showPage.jsp" %>
        <%        } else {
        %>
        <p style="margin-top:200px; font-size: 100px; text-align: center;"><%= mess%></p>
        <%
            }
        %>
        <%@include file="../HeaderFooterPage/footer.jsp" %>

    </body>
</html>