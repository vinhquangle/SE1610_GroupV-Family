<%-- 
    Document   : productDetails
    Created on : Sep 20, 2022, 10:07:18 PM
    Author     : PC
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dto.ReviewDetailDTO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="dto.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <title>Product Details Page</title>
    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <%            BookDTO book = (BookDTO) session.getAttribute("BOOK_DETAIL");
            List<ReviewDetailDTO> listReviewDetail = (List<ReviewDetailDTO>) session.getAttribute("LIST_REVIEW_DETAIL");
            List<ReviewDetailDTO> listReviewBook = new ArrayList<ReviewDetailDTO>();
            List<BookDTO> sameCate = (List<BookDTO>) session.getAttribute("SAME_CATE");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(book.getPrice());
            for (ReviewDetailDTO reviewDetailDTO : listReviewDetail) {
                if (reviewDetailDTO.getReview().getReviewID().equals(book.getReview().getReviewID())) {
                    listReviewBook.add(reviewDetailDTO);
                }
            }
            if (listReviewBook.isEmpty()) {
                listReviewBook = new ArrayList<ReviewDetailDTO>();
            }
        %>
        <!-- BREADCRUMB -->
        <div id="breadcrumb" class="section" style="margin-top: 180px; ">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="breadcrumb-tree" style="font-weight: bold">
                            <li><a href="GetController?">Trang chủ</a></li>
                            <li><a href="GetController?">Thể loại</a></li>
                            <li>
                                <form style="display: inline-block;" method="GET" action="FilterCategoryController">
                                    <input type="hidden" name="cateID" value="<%= book.getCategory().getCategoryID()%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= book.getCategory().getName()%></a>
                                </form>
                            </li>
                            <li>
                                <form style="display: inline-block;" method="GET" action="LoadController">
                                    <input type="hidden" name="isbn" value="<%= book.getIsbn()%>" /> 
                                    <a style="cursor: pointer;" onclick="this.parentNode.submit();"><%= book.getName()%></a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /BREADCRUMB -->     
        <!-- Button trigger modal -->
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
                                <img style="height: 400px; width: 500px" src="<%= book.getImg()%>" alt="">
                            </div>                           
                        </div>
                    </div>
                    <!-- /Product main img -->
                    <!-- Product details -->
                    <div class="col-md-5">
                        <div class="product-details" style="margin-top: 30px">
                            <h2 class="product-name"><%= book.getName()%></h2>                           
                            <div>
                                <h3 class="product-price"><%= price%></h3>
                                <span class="product-available">Hiện còn: <%= book.getQuantity()%></span>
                                <input id="stock" type="hidden" value="<%= book.getQuantity()%>"/>
                            </div>
                            <ul class="product-links">
                                <li>ISBN:   <b><%= book.getIsbn()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Tác giả:   <b><%= book.getAuthorName()%></b></li>                               
                            </ul>
                            <ul class="product-links">
                                <li>Nhà xuất bản:
                                    <form style="display: inline-block;" method="GET" action="FilterPublisherController">
                                        <input type="hidden" name="pubID" value="<%= book.getPublisher().getPublisherID()%>" /> 
                                        <a style="cursor: pointer;" onclick="this.parentNode.submit();"><b><%= book.getPublisher().getName()%></b></a>
                                    </form>
                                </li>                               
                            </ul>

                            <ul class="product-links">
                                <li>Thể loại:
                                    <form style="display: inline-block;" method="GET" action="FilterCategoryController">
                                        <input type="hidden" name="cateID" value="<%= book.getCategory().getCategoryID()%>" /> 
                                        <a style="cursor: pointer;" onclick="this.parentNode.submit();"><b><%= book.getCategory().getName()%></b></a>
                                    </form>
                                </li>
                            </ul>
                            <style>
                                b:hover{
                                    color: red;
                                }
                            </style>
                            <div class="add-to-cart" style="margin-top: 10px">
                                <div class="qty-label">
                                    Số lượng
                                    <div class="input-number">
                                        <input onkeypress="return isNumberKey(event)" onkeyup="check()" name="quantity" type="number" id="quantity1" value="1">
                                        <span class="qty-up" onclick="add()">+</span>
                                        <span class="qty-down" onclick="subtract()">-</span>
                                    </div>
                                </div>
                            </div>
                            <script>
                                var stock = Number(document.getElementById("stock").value);
                                function add() {
                                    var cur = document.getElementById("quantity1").value;
                                    if (cur < stock) {
                                        var quantity = (Number(document.getElementById("quantity").value) + 1);
                                        document.getElementById("quantity").value = quantity;
                                        quantity = (Number(document.getElementById("quantity1").value) + 1);
                                        document.getElementById("quantity1").value = quantity;
                                    }
                                }
                                function subtract() {
                                    var quantity = (Number(document.getElementById("quantity").value));
                                    if (quantity > 1) {
                                        quantity -= 1
                                        document.getElementById("quantity").value = quantity;
                                    }
                                    quantity = (Number(document.getElementById("quantity1").value));
                                    if (quantity > 1) {
                                        quantity -= 1
                                        document.getElementById("quantity1").value = quantity;
                                    }
                                }
                                function cart() {
                                    var quantity = document.getElementById("quantity1").value;
                                    document.getElementById("quantity").value = quantity;
                                }
                                function check() {
                                    var cur = document.getElementById("quantity1").value;
                                    if (cur > stock) {
                                        cur = Math.floor(cur / 10);
                                        document.getElementById("quantity1").value = cur;
                                    } else if (cur < 0) {
                                        document.getElementById("quantity1").value = 1;
                                    }
                                }
                                function isNumberKey(evt) {
                                    var charCode = (evt.which) ? evt.which : event.keyCode;
                                    if ((charCode < 48 || charCode > 57))
                                        return false;

                                    return true;
                                }

                            </script>
                            <div  class="add-to-cart" style="margin-top: 10px">  
                                <form action="AddBookCartController" method="GET" style="display: inline-block">
                                    <input name="quantity" id="quantity" type="hidden" value="1">
                                    <input name="isbn" value="<%= book.getIsbn()%>" type="hidden">
                                    <input name="quantityCheck" value="<%= book.getQuantity()%>" type="hidden">
                                    <button onclick="cart()" name="action" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng</button>
                                </form>                         
                            </div>
                        </div>
                    </div>
                    <!-- /Product details -->
                    <!-- Product tab -->
                    <div class="col-md-12">
                        <div id="product-tab">
                            <!-- product tab nav -->
                            <ul class="tab-nav">
                                <li class="active"><a data-toggle="tab" href="#tab1">Mô Tả</a></li>
                                <li><a data-toggle="tab" href="#tab3">Nhận xét (<%= book.getReview().getTimes()%>)</a></li>
                            </ul>
                            <!-- /product tab nav -->

                            <!-- product tab content -->
                            <div class="tab-content">
                                <!-- tab1  -->
                                <div id="tab1" class="tab-pane fade in active">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <p> <%= book.getDescription()%></p>
                                        </div>
                                    </div>
                                </div>
                                <!-- /tab1  -->
                                <!-- tab3  -->
                                <div id="tab3" class="tab-pane fade in">
                                    <div class="row">
                                        <!-- Rating -->
                                        <div class="col-md-3">
                                            <div id="rating">
                                                <div class="rating-avg">
                                                    <span><%= book.getReview().getRate()%></span>
                                                    <div class="rating-stars">
                                                        <i class="fa fa-star"></i>
                                                    </div>
                                                </div>
                                                <ul class="rating">
                                                    <li>
                                                        <div class="rating-stars">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                        </div>
                                                        <div class="rating-progress">
                                                            <%                                                                int time = 0;
                                                                int percent5 = 0;
                                                                for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                                    if (reviewDetailDTO.getRate() == 5) {
                                                                        time++;
                                                                    }
                                                                }
                                                                if (book.getReview().getTimes() > 0) {
                                                                    percent5 = (int) (time * 100 / book.getReview().getTimes());
                                                                }
                                                            %>
                                                            <div style="width: <%= percent5%>%;"></div>
                                                        </div>
                                                        <span class="sum"><%= time%></span>
                                                    </li>
                                                    <li>
                                                        <div class="rating-stars">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star-o"></i>
                                                        </div>
                                                        <div class="rating-progress">
                                                            <%
                                                                time = 0;
                                                                int percent4 = 0;
                                                                for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                                    if (reviewDetailDTO.getRate() == 4) {
                                                                        time++;
                                                                    }
                                                                }
                                                                if (book.getReview().getTimes() > 0) {
                                                                    percent4 = (int) (time * 100 / book.getReview().getTimes());
                                                                }
                                                            %>
                                                            <div style="width: <%= percent4%>%;"></div>
                                                        </div>
                                                        <span class="sum"><%= time%></span>
                                                    </li>
                                                    <li>
                                                        <div class="rating-stars">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                        </div>
                                                        <div class="rating-progress">
                                                            <%
                                                                time = 0;
                                                                int percent3 = 0;
                                                                for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                                    if (reviewDetailDTO.getRate() == 3) {
                                                                        time++;
                                                                    }
                                                                }
                                                                if (book.getReview().getTimes() > 0) {
                                                                    percent3 = (int) (time * 100 / book.getReview().getTimes());
                                                                }
                                                            %>
                                                            <div style="width: <%= percent3%>%;"></div>
                                                        </div>
                                                        <span class="sum"><%= time%></span>
                                                    </li>
                                                    <li>
                                                        <div class="rating-stars">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                        </div>
                                                        <div class="rating-progress">
                                                            <%
                                                                time = 0;
                                                                int percent2 = 0;
                                                                for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                                    if (reviewDetailDTO.getRate() == 2) {
                                                                        time++;
                                                                    }
                                                                }
                                                                if (book.getReview().getTimes() > 0) {
                                                                    percent2 = (int) (time * 100 / book.getReview().getTimes());
                                                                }
                                                            %>
                                                            <div style="width: <%= percent2%>%;"></div>
                                                        </div>
                                                        <span class="sum"><%= time%></span>
                                                    </li>
                                                    <li>
                                                        <div class="rating-stars">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                            <i class="fa fa-star-o"></i>
                                                        </div>
                                                        <div class="rating-progress">
                                                            <%
                                                                time = 0;
                                                                int percent1 = 0;
                                                                for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                                    if (reviewDetailDTO.getRate() == 1) {
                                                                        time++;
                                                                    }
                                                                }
                                                                if (book.getReview().getTimes() > 0) {
                                                                    percent1 = (int) (time * 100 / book.getReview().getTimes());
                                                                }
                                                            %>
                                                            <div style="width: <%= percent1%>%;"></div>
                                                        </div>
                                                        <span class="sum"><%= time%></span>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!-- /Rating -->
                                        <!-- Reviews -->
                                        <div class="col-md-6">
                                            <div id="reviews">
                                                <%
                                                    for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                        int start = (int) reviewDetailDTO.getRate();
                                                        int startEmpty = 5 - start;
                                                %>
                                                <ul class="reviews">
                                                    <li>
                                                        <div class="review-heading">
                                                            <h5 class="name"><%= reviewDetailDTO.getCustomer().getName()%></h5>
                                                            <p class="date"><%= reviewDetailDTO.getDate()%></p>
                                                            <div class="review-rating">
                                                                <%
                                                                    for (int i = 0; i < start; i++) {
                                                                %>
                                                                <i class="fa fa-star"></i>
                                                                <%
                                                                    }
                                                                %>
                                                                <%
                                                                    for (int i = 0; i < startEmpty; i++) {
                                                                %>
                                                                <i class="fa fa-star-o"></i>
                                                                <%
                                                                    }
                                                                %>
                                                            </div>
                                                        </div>
                                                        <div class="review-body">
                                                            <p><%= reviewDetailDTO.getDescription()%></p>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <%
                                                    }
                                                %>
                                                <ul class="reviews-pagination">
                                                    <li class="active">1</li>
                                                    <li><a href="#">2</a></li>
                                                    <li><a href="#">3</a></li>
                                                    <li><a href="#">4</a></li>
                                                    <li><a href="#"><i class="fa fa-angle-right"></i></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!-- /Reviews -->
                                        <!-- Review Form -->
                                        <div class="col-md-3">
                                            <div id="review-form">
                                                <form class="review-form">
                                                    <input class="input" type="text" placeholder="Your Name">
                                                    <input class="input" type="email" placeholder="Your Email">
                                                    <textarea class="input" placeholder="Your Review"></textarea>
                                                    <div class="input-rating">
                                                        <span>Your Rating: </span>
                                                        <div class="stars">
                                                            <input id="star5" name="rating" value="5" type="radio"><label for="star5"></label>
                                                            <input id="star4" name="rating" value="4" type="radio"><label for="star4"></label>
                                                            <input id="star3" name="rating" value="3" type="radio"><label for="star3"></label>
                                                            <input id="star2" name="rating" value="2" type="radio"><label for="star2"></label>
                                                            <input id="star1" name="rating" value="1" type="radio"><label for="star1"></label>
                                                        </div>
                                                    </div>
                                                    <button class="primary-btn">Submit</button>
                                                </form>
                                            </div>
                                        </div>
                                        <!-- /Review Form -->
                                    </div>
                                </div>
                                <!-- /tab3  -->
                            </div>
                            <!-- /product tab content  -->
                        </div>
                    </div>
                    <!-- /product tab -->
                </div>
                <!-- /row -->
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
                <%
                    if (!sameCate.isEmpty()) {
                %>
                <div style="margin-top: 120px; border-top: 2px solid #e4e7ed; padding: 40px" class="col-md-12">
                    <div class="section-title text-center">
                        <h3 style="color: #1e1e26;" class="title">Sách cùng thể loại</h3>
                    </div>
                </div>
                <%
                    String controllerAdd = (String) request.getAttribute("CONTROLLER");
                    if (controllerAdd == null) {
                        controllerAdd = "";
                    }
                    for (BookDTO bookDTO : sameCate) {
                        price = currencyVN.format(bookDTO.getPrice());
                %>
                <!-- product -->
                <div class="col-md-3 col-xs-6">
                    <div class="product">
                        <div class="product-img">
                            <form style="width: 100%; display: inline-block;" method="GET" action="LoadController">
                                <input type="hidden" name="isbn" value="<%= bookDTO.getIsbn()%>" /> 
                                <a class="product-img" style="cursor: pointer;" onclick="this.parentNode.submit();"><img style="height: 250px;" src="<%= bookDTO.getImg()%>" alt=""></a>
                            </form>
                        </div>
                        <div class="product-body">
                            <p class="product-category"><%= bookDTO.getCategory().getName()%></p>
                            <h3 style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 230px;" class="product-name" >
                                <form style="display: inline-block;" method="GET" action="LoadController">
                                    <input type="hidden" name="isbn" value="<%=bookDTO.getIsbn()%>" /> 
                                    <a style="cursor: pointer; color: black; font-weight: bold;" onclick="this.parentNode.submit();"><%= bookDTO.getName()%></a>
                                </form>
                            </h3>                                <h4 class="product-price"><%= price%></h4>                                
                            <form style="display: inline-block;" method="GET" action="LoadController">
                                <input type="hidden" name="isbn" value="<%=bookDTO.getIsbn()%>" /> 
                                <div class="product-btns">
                                    <button class="quick-view"><a style="cursor: pointer; color: black;" onclick="this.parentNode.submit();"><i class="fa fa-eye"></i><span class="tooltipp">Xem chi tiết</span></button>
                                </div></form>
                        </div>
                        <div class="add-to-cart">
                            <form style="display: inline-block;" method="GET" action="AddBookCartController">
                                <input type="hidden" name="isbn" value="<%= bookDTO.getIsbn()%>" /> 
                                <input name="quantity" id="quantity" type="hidden" value="1">
                                <input name="quantityCheck" value="<%= bookDTO.getQuantity()%>" type="hidden">
                                <input name="controller" value="<%= controllerAdd%>" type="hidden">
                                <button name="action" value="Product" type="submit" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i>Thêm vào giỏ</button>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }
                %>
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