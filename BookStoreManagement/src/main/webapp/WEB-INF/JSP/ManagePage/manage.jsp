<%-- 
    Document   : manage
    Created on : Oct 2, 2022, 1:25:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Manage Page</title>         
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div id="content"></div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function load(a) {
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/"+a,
                    type: "get", //send it through get method
                    data: {
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
        </script> 
    </body>
</html>
