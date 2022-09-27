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
        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link rel="stylesheet" href="CSS/styleLogin.css" type="text/css">
    </head>
    <body style="background-image: url(https://wallpapers.com/images/high/old-library-books-drawing-ulme7n8ce4bsuabu-ulme7n8ce4bsuabu.jpg?fbclid=IwAR3FNktVvVOQ0QSmixptb1_rRJoVgNbsPPdTla4IednSiXTPeTFPrfLY8w4)">
        <div class="container"> 
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
                            %>
                            <form action="LoginController" method="POST">
                                <select name="role" class="roleID" style="display: block; width: 150px; height: 40px; margin-bottom: 10px; margin-right: auto; margin-left: auto; border-radius: 40px 40px 40px 40px; text-align: center ">
                                    <option value="0">Customer</option>
                                    <option value="1">Staff</option>               
                                </select>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Enter Account" type="text" name="userID" required=""/>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Enter Password" type="password" name="password" required=""/>
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
                                <button type="reset" style="width: 380px; margin: auto;" class="btn btn-block mybtn btn-primary tx-tfm" value="Reset">Reset</button>
                                <div style="margin-top: 10px;" class="col-md-12 text-center ">
                                    <button class="btn btn-block mybtn btn-primary tx-tfm" type="submit" name="action">Login</button>                                           
                                </div>
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
                                            </i> Login with Google
                                        </a>
                                </p>
                            </div>
                            <div class="form-group">
                                <p class="text-center">Don't have account? <a href="RegisterController?action=Register" id="signup">Sign up here</a></p>
                            </div>                   
                            <button class=" btn btn-block mybtn btn-primary tx-tfm"><a style="color: white" href="GetController?">Return Home</a> </button>
                        </div>
                    </div>

                </div>
            </div>
        </div>  

    </body>
</html>
