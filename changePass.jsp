<%-- 
    Document   : changePass
    Created on : Oct 14, 2022, 4:16:40 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/changePass.css">
    </head>
    <body>
        <div class="mainDiv">
            <div class="cardStyle">
                <form action="" method="post" name="signupForm" id="signupForm">

                    <img src="" id="signupLogo"/>

                    <h2 class="formTitle">
                        Thay đổi mật khẩu
                    </h2>

                    <div class="inputDiv">
                        <label class="inputLabel" for="password">Mật khẩu mới</label>
                        <input type="password" id="password" name="password" placeholder="Nhập mật khẩu mới" required>
                    </div>

                    <div class="inputDiv">
                        <label class="inputLabel" for="confirmPassword">Nhập lại mật khẩu</label>
                        <input type="password" id="confirmPassword" placeholder=" Xin mời nhập lại mật khẩu" name="confirmPassword">
                    </div>

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
