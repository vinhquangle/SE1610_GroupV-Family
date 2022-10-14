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
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div style="text-align: center;" id="content">
            <p style=" height: 80px; font-size: 90px; text-align: center; color: #494f57;">Book Store Management</p>
            <img style="margin-top: 20px; height: 450px;" src="IMG/manage.png"/>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function load(a, b, isbn, isbnN, title, author, pub, cate, quan, price, img, status, use) {
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "get", //send it through get method
                    data: {
                        use: use,
                        searchBook: b,
                        isbn: isbn,
                        isbnN: isbnN,
                        title: title,
                        author: author,
                        pub: pub,
                        cate: cate,
                        quan: quan,
                        price: price,
                        img: img,
                        status: status
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
            function book(a, c, use) {
                var option = confirm('Are you sure to ' + use + " ?");
                if (option === true) {
                    var b = document.getElementById("searchBook").value;
                    var isbn = document.getElementsByClassName(c)[0].value;
                    var isbnN = document.getElementsByClassName(c)[1].value;
                    var title = document.getElementsByClassName(c)[2].value;
                    var author = document.getElementsByClassName(c)[3].value;
                    var pub = document.getElementsByClassName(c)[4].value;
                    var cate = document.getElementsByClassName(c)[5].value;
                    var quan = document.getElementsByClassName(c)[6].value;
                    var price = document.getElementsByClassName(c)[7].value;
                    var img = document.getElementsByClassName(c)[8].value;
                    var status = document.getElementsByClassName(c)[9].value;
                    load(a, b, isbn, isbnN, title, author, pub, cate, quan, price, img, status, use);
                }
            }
            function publisher(a, b, publisherID, pubB, name, status, use) {
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "get", //send it through get method
                    data: {
                        use: use,
                        searchPublisher: b,
                        publisherID: publisherID, 
                        pubB: pubB,
                        name : name,
                        status: status
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
            function updatePub(a, c, use) {
                var option = confirm('Are you sure to ' + use + " ?");
                if (option === true) {
                    var b = document.getElementById("searchPublisher").value;
                    var publisherID = document.getElementsByClassName(c)[0].value;
                    var pubB = document.getElementsByClassName(c)[1].value;
                    var name = document.getElementsByClassName(c)[2].value;                
                    var status = document.getElementsByClassName(c)[3].value;
                    publisher(a, b, publisherID, pubB, name, status, use);
                }
            }
            
            function add(a, b, c, use) {
                document.getElementById("form-add").addEventListener("click", function (event) {
                    event.preventDefault();
                });
                document.getElementById("mcontent").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "get", //send it through get method
                    data: {
                        
                    },
                    success: function (data) {
                        var row = document.getElementById("mcontent");
                        row.innerHTML += "data";
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
        </script>
        <style>
            #edit:hover{
                background-color: #ff6600;
                color: white;
            }
            #remove:hover{
                background-color: red;
                color: white;
            }
            #recover:hover{
                background-color: green;
                color: white;
            }
            #inputisbn::-webkit-outer-spin-button,
            #inputisbn::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
        </style>
    </body>
</html>
