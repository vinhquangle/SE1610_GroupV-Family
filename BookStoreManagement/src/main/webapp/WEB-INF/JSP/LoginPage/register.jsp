<%-- 
    Document   : register
    Created on : Sep 22, 2022, 9:19:44 PM
    Author     : Admin
--%>

<%@page import="dto.CustomerErrorDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background-image: url(https://cdn.pixabay.com/photo/2016/02/16/21/07/books-1204029_960_720.jpg)">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/dist/mdb5/standard/core.min.css">
        <link rel='stylesheet' id='roboto-subset.css-css'  href='https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/mdb5/fonts/roboto-subset.css?ver=3.9.0-update.5' type='text/css' media='all' />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">        
        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="CSS/nouislider.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="CSS/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="CSS/style.css"/>
        <link type="text/css" rel="stylesheet" href="CSS/styleCreate.css"/>
        <link href="PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Recaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body style="background-image: url(https://cdn.pixabay.com/photo/2016/02/16/21/07/books-1204029_960_720.jpg);">
        <!-- TOP HEADER -->
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
            CustomerErrorDTO cusError = (CustomerErrorDTO) request.getAttribute("CUSTOMER_ERROR");
            if (cusError == null) {
                cusError = new CustomerErrorDTO();
            }
            String messModal = (String) request.getAttribute("MODAL");
            if (messModal == null) {
                messModal = "";
            } else {
        %>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#exampleModal').modal('show');
            });
        </script>
        <%
            }
        %>
        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Xác nhận Email</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="RegisterController" method="POST">
                        <div class="modal-body">
                            <p><%= messModal%></p>
                            <input name="verify" placeholder="Nhập mã xác minh">
                            <input name="action" value="" style="display: none;">
                            <input name="email" value="<%= request.getParameter("email")%>" style="display: none;">
                            <input name="customerID" value="<%= request.getParameter("customerID")%>" style="display: none;">
                            <input name="fullName" value="<%= request.getParameter("fullName")%>" style="display: none;">
                            <input name="phone" value="<%= request.getParameter("phone")%>" style="display: none;">
                            <input name="address" value="<%=  request.getParameter("address")%>" style="display: none;">
                            <input name="passwordr" value="<%= request.getParameter("passwordr")%>" style="display: none;">
                            <input name="confirm" value="<%= request.getParameter("confirm")%>" style="display: none;">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Xác minh</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <style>
            .bt{
                border-radius: 100px; 
                margin-bottom: 5px;
                width: 400px;
            }
            #verify{
                background-color: green;
                color: white;
                font-weight: bold;
            }
            #verify:hover{
                background-color: white;
                color: green;
            }
            #verify:first-child:hover{
                color: green;
            }              
            h1, h2, h3 {
                font-family: 'Kaushan Script', cursive;
            }
        </style>
        <script>
            function change(a, b) {
                document.getElementById(a).style.backgroundColor = "white";
                document.getElementById(b).style.display = "none";
                document.getElementById(a).style.display = "block";
            }
            function change1(a, b, c) {
                document.getElementById(a).style.backgroundColor = "transparent";
                if (document.getElementById(b).value != "") {
                    document.getElementById(a).style.backgroundColor = "white";
                    document.getElementById(a).style.fontSize = "15px";
                    document.getElementById(c).style.display = "inline-block";
                } else {
                    document.getElementById(c).style.display = "none";
                }
            }
            function resetInput() {
                document.getElementById("e").value = "";
                document.getElementById("a").value = "";
                document.getElementById("n").value = "";
                document.getElementById("p").value = "";
                document.getElementById("d").value = "";
                document.getElementById("s").value = "";
                document.getElementById("r").value = "";
            }
        </script>
        <%
            String email = request.getParameter("email");
            String customerID = request.getParameter("customerID");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("passwordr");
            String confirm = request.getParameter("confirm");
            if (email == null) {
                email = "";
            } else {%>
        <style>
            #email{
                display: none;
            }
        </style>
        <% }
            if (customerID == null) {
                customerID = "";
            } else {%>
        <style>
            #acc{
                display: none;
            }
        </style>
        <% }
            if (fullName == null) {
                fullName = "";
            } else {%>
        <style>
            #name{
                display: none;
            }
        </style>
        <% }
            if (phone == null) {
                phone = "";
            } else {%>
        <style>
            #phone{
                display: none;
            }
        </style>
        <% }
            if (address == null) {
                address = "";
            } else {%>
        <style>
            #add{
                display: none;
            }
        </style>
        <% }
            if (password == null) {
                password = "";
            } else {%>
        <style>
            #password{
                display: none;
            }
        </style>
        <% }
            if (confirm == null) {
                confirm = "";
            } else {%>
        <style>
            #rpassword{
                display: none;
            }
        </style>
        <% }
        %>
        <div style="margin-top: 30px;" class="container">
            <form style="border-radius: 30px; text-align: center; background-color: white; height: 850px; width: 500px; margin: auto;" action="RegisterController" method="POST">
                </br>
                <h1>Đăng kí</h1>
                <div class="form-outline" style="text-align: center; display: inline-block; width: 22rem;">
                    <i id="b" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="e" onfocus="change('email', 'b')" onblur="change1('email', 'e', 'b')" style="border: 1px solid black; margin: auto;" class="form-control" type="text" name="email" maxlength="50" value="<%= email%>">
                    <label id="email" style="color: #666666;" class="form-label" for="form12" >Địa chỉ Email</label>
                    <p style="color: #cc0000;"><%=cusError.getEmailError()%></p>
                </div>
                <div id="content"></div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="c" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="a" onfocus="change('acc', 'c')" onblur="change1('acc', 'a', 'c')" style="border: 1px solid black;" class="form-control" id="form11" type="text" name="customerID" value="<%= customerID%>">
                    <label id="acc" style="color: #666666;" class="form-label" for="form11" >Tài khoản</label>
                    <p style="color: #cc0000;"><%=cusError.getCustomerIDError()%></p>
                </div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="f" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="n" onfocus="change('name', 'f')" onblur="change1('name', 'n', 'f')" style="border: 1px solid black;" class="form-control" id="form11" type="text" name="fullName"value="<%= fullName%>">
                    <label id="name" style="color: #666666;" class="form-label" for="form11" >Họ và tên</label>
                    <p style="color: #cc0000;"><%=cusError.getNameError()%></p>
                </div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="z" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="p" onfocus="change('phone', 'z')" onblur="change1('phone', 'p', 'z')" style="border: 1px solid black;" class="form-control" id="form11" type="tel" name="phone" value="<%= phone%>">
                    <label id="phone" style="color: #666666;" class="form-label" for="form11" >Số điện thoại</label>
                    <p style="color: #cc0000;"><%=cusError.getPhoneError()%></p>
                </div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="j" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="d" onfocus="change('add', 'j')" onblur="change1('add', 'd', 'j')" style="border: 1px solid black;" class="form-control" id="form11" type="text" name="address" value="<%= address%>">
                    <label id="add" style="color: #666666;" class="form-label" for="form11" >Địa chỉ</label>
                    <p style="color: #cc0000;"><%=cusError.getAddressError()%></p>
                </div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="k" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="s" onfocus="change('password', 'k')" onblur="change1('password', 's', 'k')" style="border: 1px solid black;" class="form-control" id="form11" type="password" name="passwordr" value="<%= password%>">
                    <label id="password" style="color: #666666;" class="form-label" for="form11" >Mật khẩu</label>
                    <p style="color: #cc0000;"><%=cusError.getPasswordError()%></p>
                </div>
                <div class="form-outline" style="display: inline-block; width: 22rem;">
                    <i id="u" style="display: none; width: 1px; height: 1px;"></i>
                    <input id="r" onfocus="change('rpassword', 'u')" onblur="change1('rpassword', 'r', 'u')" style="border: 1px solid black;" class="form-control" id="form11" type="password" name="confirm" value="<%= confirm%>">
                    <p style="color: #cc0000;"><%=cusError.getConfirmError()%></p>
                    <label id="rpassword" style="color: #666666;" class="form-label" for="form11" >Nhập lại mật khẩu</label>
                </div>
                <div style="display: block; width: 300px; margin-left: auto; margin-right: auto;" class="g-recaptcha" data-sitekey="6LfxTU4gAAAAANL7i9yWhE0_BtD9TgxTRtdY06Vc"></div></br>
                <p style="
                   color: white;
                   background-color: red;
                   font-weight: bold;
                   width: 350px;
                   margin: auto;
                   border-radius: 100px;
                   margin-bottom: 5px;
                   text-align: center;
                   " id="error"><%=error%></p>
                <div style="border-radius: 30px; background-color: white; height: 100px; width: 500px;" class="container mt-3">
                    <button class="bt btn btn-block mybtn btn-primary tx-tfm" type="submit" name="action">Create Account</button>
                    <button onclick="resetInput()" class="bt btn btn-block mybtn btn-primary tx-tfm">Reset</button>
                </div>
                <p style="border-radius: 30px; background-color: white; display: block; width: 150px;  font-size: 15px; margin-top: 20px; margin-left: auto; margin-right: auto; text-align: center"><a style="text-decoration: none;" href="GetController?">RETURN HOME</p></form>          
        </div>
    </body>
</html>