<%-- 
    Document   : login
    Created on : Sep 20, 2022, 5:26:15 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link rel="stylesheet" href="../../CSS/styleLogin.css" type="text/css">
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
                            <form action="MainController" method="POST" name="login">
                                <div class="form-group">
                                    <label for="exampleInputID">ID</label>
                                    <input class="form-control" placeholder="Input ID" type="text" name="userID" required=""/>

                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword">Password</label>
                                    <input class="form-control" placeholder="Input Password" type="password" name="password" required=""/>

                                </div>
                                <select class="roleID" style="width: 150px; height: 40px; margin-left: 130px; border-radius: 40px 40px 40px 40px; text-align: center">
                                    <option value>Customer</option>
                                    <option value="1">Staff</option>               
                                </select>
                                <div class="form-group">
                                    <p class="text-center">By signing up you accept our <a href="#">Terms Of Use</a></p>
                                </div>
                                <div class="g-recaptcha" data-sitekey="6LcBw1QgAAAAAOyN86F_O-RuE1CAUtm-FnQyFdIY"></div></br>
                                <div class="form-group" style="margin-left: 35%; color: red">
                                    ${requestScope.ERROR}</br>
                                </div>
                                <div class="col-md-12 text-center ">
                                    <input class="btn btn-block mybtn btn-primary tx-tfm" type="submit" name="action" value="Login"/>                                               
                                </div>
                                <div class="col-md-12 ">
                                    <div class="login-or">
                                        <hr class="hr-or">
                                        <span class="span-or">or</span>
                                    </div>
                                </div>
                                <div class="col-md-12 mb-3">
                                    <p class="text-center">
                                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:8080/Assignment_SE162013/LoginGoogleController&response_type=code
                                           &client_id=912386786816-lgr9vgsiq2k35cl22m9bkvr6k5kli3a0.apps.googleusercontent.com&approval_prompt=force" class="google btn mybtn">
                                            <i class="fa fa-google-plus">
                                            </i> Login using Google
                                        </a>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <p class="text-center">Don't have account? <a href="createUser.jsp" id="signup">Sign up here</a></p>
                                </div>                   
                                <button type="reset" class=" btn btn-block mybtn btn-primary tx-tfm" value="Reset">Reset</button>
                                <button class=" btn btn-block mybtn btn-primary tx-tfm"><a style="color: white" href="../HomePage/homePage.jsp">Return Home</a> </button>
                            </form>

                        </div>
                    </div>

                </div>
            </div>
        </div>  
        
    </body>
</html>
