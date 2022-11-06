
<%@page import="aes.MyAES"%>
<%@page import="dto.BookDTO"%>
<%@page import="cart.Cart"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="dto.CustomerDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
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
        <link rel="stylesheet" type="text/css" href="STYLES/bootstrap4/bootstrap.min.css" />
        <style>
            #emptyList{
                font-size: 20px;
                text-align: center;
            }
        </style>
        <!-- jQuery Plugins -->
    </head>
    <body>
        <script>
            window.addEventListener('load', () => {
                const query = window.location.search;
                const urlParams = new URLSearchParams(query);
                var choosen = urlParams.get('index');
                if (choosen === null) {
                    choosen = "1";
                } else {
                    on(choosen);
                }
                document.getElementById(choosen).classList.add("active");
            });
            function change() {
                var x = document.getElementById("pass");
                if (x.type === "password") {
                    x.type = "text";
                    document.getElementById("pass").value = document.getElementById("passr").value;
                } else {
                    x.type = "password";
                    document.getElementById("pass").value = "********************";
                }
            }
            function on(a) {
                if (a === "1") {
                    document.getElementById(a).classList.add("active");
                    document.getElementById('2').classList.remove("active");
                    document.getElementById('3').classList.remove("active");
                    document.getElementById('form1').style.display = "inline-block";
                    document.getElementById('form2').style.display = "none";
                    document.getElementById('form3').style.display = "none";
                }
                if (a === "2") {
                    document.getElementById(a).classList.add("active");
                    document.getElementById('1').classList.remove("active");
                    document.getElementById('3').classList.remove("active");
                    document.getElementById('form1').style.display = "none";
                    document.getElementById('form2').style.display = "inline-block";
                    document.getElementById('form3').style.display = "none";
                }
                if (a === "3") {
                    document.getElementById(a).classList.add("active");
                    document.getElementById('2').classList.remove("active");
                    document.getElementById('1').classList.remove("active");
                    document.getElementById('form1').style.display = "none";
                    document.getElementById('form2').style.display = "none";
                    document.getElementById('form3').style.display = "inline-block";
                }
            }
            function edit(a) {
                if (a === "edit") {
                    document.getElementsByClassName('b')[0].readOnly = false;
                    document.getElementsByClassName('b')[1].readOnly = false;
                    document.getElementsByClassName('b')[2].readOnly = false;
                    document.getElementsByClassName('b')[3].readOnly = false;
                    document.getElementsByClassName('b')[0].style.border = "2px solid black";
                    document.getElementsByClassName('b')[1].style.border = "2px solid black";
                    document.getElementsByClassName('b')[2].style.border = "2px solid black";
                    document.getElementsByClassName('b')[3].style.border = "2px solid black";
                    document.getElementById("edit").style.display = "none";
                    document.getElementById("save").style.display = "inline-block";
                } else if (a === "save") {
                    document.getElementsByClassName('b')[0].readOnly = true;
                    document.getElementsByClassName('b')[1].readOnly = true;
                    document.getElementsByClassName('b')[2].readOnly = true;
                    document.getElementsByClassName('b')[3].readOnly = true;
                    document.getElementsByClassName('b')[0].style.border = "1px solid #ced4da";
                    document.getElementsByClassName('b')[1].style.border = "1px solid #ced4da";
                    document.getElementsByClassName('b')[2].style.border = "1px solid #ced4da";
                    document.getElementsByClassName('b')[3].style.border = "1px solid #ced4da";
                    document.getElementById("edit").style.display = "inline-block";
                    document.getElementById("save").style.display = "none";
                }
            }
            function edit1(a) {
                if (a === "edit1") {
                    document.getElementsByClassName('c')[0].readOnly = false;
                    document.getElementsByClassName('c')[1].readOnly = false;
                    document.getElementsByClassName('c')[2].readOnly = false;
                    document.getElementsByClassName('c')[0].style.border = "2px solid black";
                    document.getElementsByClassName('c')[1].style.border = "2px solid black";
                    document.getElementsByClassName('c')[2].style.border = "2px solid black";
                    document.getElementById("edit1").style.display = "none";
                    document.getElementById("save1").style.display = "inline-block";
                } else if (a === "save1") {
                    document.getElementsByClassName('c')[0].readOnly = true;
                    document.getElementsByClassName('c')[1].readOnly = true;
                    document.getElementsByClassName('c')[2].readOnly = true;
                    document.getElementsByClassName('c')[0].style.border = "1px solid #ced4da";
                    document.getElementsByClassName('c')[1].style.border = "1px solid #ced4da";
                    document.getElementsByClassName('c')[2].style.border = "1px solid #ced4da";
                    document.getElementById("edit1").style.display = "inline-block";
                    document.getElementById("save1").style.display = "none";
                }
            }
        </script>
        <style>
            #form2, #form3 {
                display: none;
            }
            #choosen a.active{
                color: white;
                background-color: #15161d;
            }
            .pan{
                font-weight: bold; 
                padding: 15px; 
                display: block; 
                border: 2px solid #15161d; 
                margin-left: 10px; 
                margin-bottom: 20px; 
                font-size: 20px; 
                text-decoration: none; 
                color: #15161d
            }
            .changePass{
                background-color: #15161d;
                border: none;
                color: white;
                padding: 20px; 
                font-size: 30px;
            }
            .changePass:hover{
                color: #15161d;
                background-color: white;
                border: 2px solid #15161d;
            }
            #save, #save1{
                display: none;
            }
        </style>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <%            if (cus != null) {
        %>
        <%@include file="customerProfile.jsp" %>
        <%            } else if (staff != null) {
        %> 
        <%@include file="staffProfile.jsp" %>
        <%}%>
        <!-- /content -->
        <%@include file="../HeaderFooterPage/footer.jsp" %>
    </body>
</html>