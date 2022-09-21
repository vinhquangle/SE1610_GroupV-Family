<%-- 
    Document   : checkout
    Created on : Sep 20, 2022, 10:21:44 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <title>Check Out Page</title>

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="../../CSS/style.css"/>
        <link rel="stylesheet" type="text/css" href="../../STYLES/bootstrap4/bootstrap.min.css" />
        <link href="../../PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" type="text/css" href="../../STYLES/main_styles.css" />

        
        <!-- jQuery Plugins -->
        <script src="../../JS/jquery.min.js"></script>
        <script src="../../JS/bootstrap.min.js"></script>
        <script src="../../JS/slick.min.js"></script>
        <script src="../../JS/nouislider.min.js"></script>
        <script src="../../JS/jquery.zoom.min.js"></script>
        <script src="../../JS/main.js"></script>


    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>

        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row" style="margin-top: 200px">
                    <div class="col-md-12">
                        <h3 class="breadcrumb-header">Checkout</h3>
                        <ul class="breadcrumb-tree">
                            <li><a href="#">Home</a></li>
                            <li class="active">Checkout</li>
                        </ul>
                    </div>
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /BREADCRUMB -->

        <!-- SECTION -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">

                    <div class="col-md-7">
                        <!-- Shipping Details -->
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title">Shipping address</h3>
                            </div>
                            <div class="form-group">
                                <input class="input" type="text" name="first-name" placeholder="First Name">
                            </div>
                            <div class="form-group">
                                <input class="input" type="text" name="last-name" placeholder="Last Name">
                            </div>
                            <div class="form-group">
                                <input class="input" type="email" name="email" placeholder="Email">
                            </div>
                            <div class="form-group">
                                <input class="input" type="text" name="address" placeholder="Address">
                            </div>
                            <div class="form-group">
                                <input class="input" type="text" name="city" placeholder="City">
                            </div>                           
                            <div class="form-group">
                                <div class="input-checkbox">
                                    <input type="checkbox" id="create-account">
                                    <label for="create-account">
                                        <span></span>
                                        Create Account?
                                    </label>
                                    <div class="caption">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.</p>
                                        <input class="input" type="password" name="password" placeholder="Enter Your Password">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /Shipping Details -->

                        <!-- Order notes -->
                        <div class="order-notes">
                            <textarea class="input" placeholder="Order Notes"></textarea>
                        </div>
                        <!-- /Order notes -->
                    </div>

                    <!-- Order Details -->
                    <div class="col-md-5 order-details">
                        <div class="section-title text-center">
                            <h3 class="title">Your Order</h3>
                        </div>
                        <div class="order-summary">
                            <div class="order-col">
                                <div><strong>PRODUCT</strong></div>
                                <div><strong>TOTAL</strong></div>
                            </div>
                            <div class="order-products">
                                <div class="order-col">
                                    <div>1x Product Name Goes Here</div>
                                    <div>$980.00</div>
                                </div>
                                <div class="order-col">
                                    <div>2x Product Name Goes Here</div>
                                    <div>$980.00</div>
                                </div>
                            </div>
                            <div class="order-col">
                                <div>Shiping</div>
                                <div><strong>FREE</strong></div>
                            </div>
                            <div class="order-col">
                                <div><strong>TOTAL</strong></div>
                                <div><strong class="order-total">$2940.00</strong></div>
                            </div>
                        </div>
                        <div class="payment-method">
                            <div class="input-radio">
                                <input type="radio" name="payment" id="payment-1">
                                <label for="payment-1">
                                    <span></span>
                                    Direct Bank Transfer
                                </label>
                                <div class="caption">
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                </div>
                            </div>
                            <div class="input-radio">
                                <input type="radio" name="payment" id="payment-2">
                                <label for="payment-2">
                                    <span></span>
                                    Momo
                                </label>
                                <div class="caption">
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                </div>
                            </div>
                            <div class="input-radio">
                                <input type="radio" name="payment" id="payment-3">
                                <label for="payment-3">
                                    <span></span>
                                    Paypal System
                                </label>
                                <div class="caption">
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                </div>
                            </div>
                        </div>
                        <div class="input-checkbox">
                            <input type="checkbox" id="terms">
                            <label for="terms">
                                <span></span>
                                I've read and accept the <a href="#">terms & conditions</a>
                            </label>
                        </div>
                        <a href="#" class="primary-btn order-submit">Place order</a>
                    </div>
                    <!-- /Order Details -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->

        <%@include file="../HeaderFooterPage/footer.jsp" %>


    </body>
</html>
