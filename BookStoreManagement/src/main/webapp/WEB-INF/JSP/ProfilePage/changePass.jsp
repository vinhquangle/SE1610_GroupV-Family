<%-- 
    Document   : changePass
    Created on : Oct 16, 2022, 1:03:43 PM
    Author     : PCPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password Page</title>
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <link rel="stylesheet" href="CSS/changePass.css">
        <script src="JS/jquery.min.js"></script>
    </head>
    <body style="background-image: url(https://www.unleash.ai/wp-content/uploads/2021/12/1412-read.jpg);">
        <%
            String current = request.getParameter("current");
            String newPass = request.getParameter("new");
            String confirm = request.getParameter("confirm");
            String mess1 = (String) request.getAttribute("mess1");
            String mess2 = (String) request.getAttribute("mess2");
            String mess3 = (String) request.getAttribute("mess3");
            if (current == null) {
                current = "";
            }
            if (newPass == null) {
                newPass = "";
            }
            if (confirm == null) {
                confirm = "";
            }
            if (mess1 == null) {
                mess1 = "";
            }
            if (mess2 == null) {
                mess2 = "";
            }
            if (mess3 == null) {
                mess3 = "";
            }
        %>
        <style>
            .mess1, .mess2, .mess3{
                text-align: center;
                color: red;
            }
        </style>
        <div style="background-color: transparent; margin-top: 220px;" class="mainDiv">
            <div class="cardStyle">
                <form action="ChangePasswordController" method="POST" name="signupForm" id="signupForm">
                    <img src="" id="signupLogo"/>
                    <h2 class="formTitle">
                        Thay đổi mật khẩu
                    </h2>
                    <div class="inputDiv">
                        <label class="inputLabel" for="password">Mật khẩu hiện tại</label>
                        <input type="password" id="password" name="current" placeholder="Nhập mật khẩu hiện tại" value="<%= current%>">
                        <div class="mess1"><%= mess1%></div>
                    </div>
                    <div class="inputDiv">
                        <label class="inputLabel" for="password">Mật khẩu mới</label>
                        <input type="password" id="password" name="new" placeholder="Nhập mật khẩu mới" value="<%= newPass%>">
                        <div class="mess2"><%= mess2%></div>
                    </div>
                    <div class="inputDiv">
                        <label class="inputLabel" for="confirmPassword">Nhập lại mật khẩu</label>
                        <input type="password" id="confirmPassword" placeholder=" Xin mời nhập lại mật khẩu" name="confirm" value="<%= confirm%>">
                        <div class="mess3"><%= mess3%></div>
                    </div>
                    <input name="action" value="Change" type="hidden">
                    <div class="buttonWrapper">
                        <button type="submit" id="submitButton" class="submitButton pure-button pure-button-primary">
                            <span>Lưu</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
