<%-- 
    Document   : login
    Created on : Sep 22, 2022, 9:07:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Login Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link rel="stylesheet" href="CSS/styleLogin.css" type="text/css">
    </head>
    <body style="background-image: url(https://www.unleash.ai/wp-content/uploads/2021/12/1412-read.jpg);">
        <%
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
        <div  class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thông báo</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <%= messModal%>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <div style="margin-top: 120px;" class="container"> 
            <div class="row">
                <div class="col-md-5 mx-auto">
                    <div id="first">
                        <div class="myform form ">
                            <div class="logo mb-3">
                                <div class="col-md-12 text-center">
                                    <h1>Login</h1>
                                </div>
                            </div>
                            <%
                                String error = (String) request.getAttribute("ERROR");
                                if (error == null) {
                                    error = "";
                                }
                                String success = (String) request.getAttribute("CREATE_SUCCESS");
                                if (success == null) {
                                    success = "";
                                }
                                String userID = request.getParameter("userID");
                                String pass = request.getParameter("password");
                                if (userID == null) {
                                    userID = "";
                                }
                                if (pass == null) {
                                    pass = "";
                                }
                            %>
                            <script>
                                function resetInput() {
                                    document.getElementById("a").value = "";
                                    document.getElementById("b").value = "";
                                }
                            </script>
                            <form action="LoginController" method="POST">
                                <div class="form-group">
                                    <input id="a" class="form-control" placeholder="Tài khoản" type="text" name="userID" value="<%= userID%>"/>
                                </div>
                                <div class="form-group">
                                    <input id="b" class="form-control" placeholder="Mật khẩu" type="password" name="password" value="<%= pass%>"/>
                                </div>
                                <div style="display: block; width: 300px;" class="g-recaptcha" data-sitekey="6LfxTU4gAAAAANL7i9yWhE0_BtD9TgxTRtdY06Vc"></div></br>
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
                                <div style="margin-bottom: 10px;" class="col-md-12 text-center ">
                                    <button class="btn btn-block mybtn btn-primary tx-tfm" type="submit" name="action">Đăng nhập</button>                                           
                                </div>
                                <button onclick="resetInput()" style="width: 380px; margin: auto;" class="btn btn-block mybtn btn-primary tx-tfm" value="Reset">Làm mới</button>
                            </form>
                            <div class="col-md-12 ">
                                <div class="login-or">
                                    <hr class="hr-or">
                                    <span class="span-or">or</span>
                                </div>
                            </div>
                            <div class="col-md-12 mb-3">
                                <p class="text-center">                           
                                    <a class="google btn mybtn" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/BookStoreManagement/LoginGoogleController&response_type=code
                                       &client_id=922870629634-leena0bfu4bdqqv10op6nsl8485gt6le.apps.googleusercontent.com&approval_prompt=force">
                                        <i class="fa fa-google-plus">
                                        </i> Đăng nhập bằng tải khoản Google
                                    </a>
                                </p>
                            </div>
                            <div class="form-group">
                                <p class="text-center"><a href="LoginController?action=Forgot" id="signup">Quên mật khẩu?</a></p>
                            </div>                   
                            <div class="form-group">
                                <p class="text-center">Bạn chưa có tài khoản? <a href="RegisterController?action=Register" id="signup">Đăng kí tại đây</a></p>
                            </div>                   
                            <button class=" btn btn-block mybtn btn-primary tx-tfm"><a style="color: white" href="GetController?">Trở về trang chủ</a> </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>                                
    </body>
</html>
