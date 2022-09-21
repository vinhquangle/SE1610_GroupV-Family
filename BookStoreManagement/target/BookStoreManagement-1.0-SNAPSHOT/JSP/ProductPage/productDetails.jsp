<%-- 
    Document   : productDetails
    Created on : Sep 20, 2022, 10:07:18 PM
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

        <title>Product Details Page</title>

        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="../../CSS/bootstrap.min.css" />

        <!-- Slick -->
        <link type="text/css" rel="stylesheet" href="../../CSS/slick.css" />
        <link type="text/css" rel="stylesheet" href="../../CSS/slick-theme.css" />

        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="../../CSS/nouislider.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="../../CSS/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="../../CSS/style.css"/>
        <link rel="stylesheet" type="text/css" href="../../STYLES/bootstrap4/bootstrap.min.css" />
        <link href="../../PLUGINS/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" type="text/css" href="../../STYLES/main_styles.css" />
        <link rel="stylesheet" type="text/css" href="../../STYLES/responsive.css" />
        
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
        <div id="breadcrumb" class="section" style="margin-top: 180px; ">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="#">Home</a></li>
                            <li><a href="#">All Categories</a></li>
                            <li><a href="#">Accessories</a></li>
                            <li class="active"><a href="#">Headphones (227,490 Results)</a></li>
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
                    <!-- Product main img -->
                    <div class="col-md-7">
                        <div id="product-main-img">
                            <div class="product-preview">
                                <img style="height: 400px; width: 500px" src="https://salt.tikicdn.com/cache/w1200/media/catalog/product/5/_/5.u5552.d20170929.t183543.444888.jpg" alt="">
                            </div>                           
                        </div>
                    </div>
                    <!-- /Product main img -->

                    <!-- Product details -->
                    <div class="col-md-5">
                        <div class="product-details" style="margin-top: 30px">
                            <h2 class="product-name">product name goes here</h2>                           
                            <div>
                                <h3 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h3>
                                <span class="product-available">In Stock</span>
                            </div>
                            <ul class="product-links">
                                <li>ISBN:</li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Author: </li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Publisher: </li>                               
                            </ul>

                            <ul class="product-links">
                                <li>Category:</li>
                            </ul>

                            <div class="add-to-cart" style="margin-top: 10px">
                                <div class="qty-label">
                                    Qty
                                    <div class="input-number">
                                        <input type="number">
                                        <span class="qty-up">+</span>
                                        <span class="qty-down">-</span>
                                    </div>
                                </div>
                            </div>

                            <div class="add-to-cart" style="margin-top: 10px">  
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>Buy Now</button>
                            </div>

                        </div>
                    </div>
                    <!-- /Product details -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->

        <!-- Section -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">

                    <div class="col-md-12">
                        <div class="section-title text-center">
                            <h3 class="title">You might also enjoy</h3>
                        </div>
                    </div>

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="https://product.hstatic.net/1000237375/product/bia_900x900_dbb77079df0641a5a3c1e4a8064fa6ab_master.jpg" alt="">
                                <div class="product-label">
                                    <span class="sale">-30%</span>
                                </div>
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>                                
                                <div class="product-btns">                                   
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="https://cdn0.fahasa.com/media/catalog/product/b/_/b_a-thi_n-t_i-b_n-tr_i-k_-_i_n-b_n-ph_i_1.jpg" alt="">
                                <div class="product-label">
                                    <span class="new">NEW</span>
                                </div>
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-btns">                                   
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <div class="clearfix visible-sm visible-xs"></div>

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_48045.jpg" alt="">
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-btns">                                   
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="https://cdn0.fahasa.com/media/catalog/product/i/m/image_217480.jpg" alt="">
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-btns">                                   
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /Section -->

        <%@include file="../HeaderFooterPage/footer.jsp" %>

        <!-- jQuery Plugins -->
        <script src="../../JS/main.js"></script>

    </body>
</html>
