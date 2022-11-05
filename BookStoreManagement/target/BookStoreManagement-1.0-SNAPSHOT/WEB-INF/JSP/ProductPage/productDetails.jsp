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

        <title>Details</title>
    </head>
    <body>
        <%@include file="../HeaderFooterPage/header.jsp" %>
        <%            BookDTO book = (BookDTO) session.getAttribute("BOOK_DETAIL");
            List<ReviewDetailDTO> listReviewBook = (List<ReviewDetailDTO>) session.getAttribute("LIST_REVIEW_DETAIL");
            List<BookDTO> sameCate = (List<BookDTO>) session.getAttribute("SAME_CATE");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(book.getPrice());
        %>
        <script>
            $(window).on('load', function () {
                loadReview('',<%= book.getReview().getReviewID()%>);
            });
        </script>
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
                                <form action="AddBookCartController" method="POST" style="display: inline-block">
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
                                            <p>
                                                <i class="fa fa-quote-left" aria-hidden="true"></i>
                                                <%= book.getDescription()%>
                                                <i class="fa fa-quote-right" aria-hidden="true"></i>
                                            </p>
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
                                            </div>
                                        </div>
                                        <script>
                                            var count = 0;
                                            function show(a) {
                                                if (count === 0) {
                                                    document.getElementById(a).style.display = "inline-block";
                                                    count++;
                                                } else if (count > 0) {
                                                    document.getElementById(a).style.display = "none";
                                                    count = 0;
                                                }
                                            }
                                            function loadReview(index, reviewId, use, reviewDetailID) {
                                                console.log(reviewId);
                                                document.getElementById("reviews").innerHTML = "";
                                                if (index === "") {
                                                    index = "1";
                                                }
                                                $.ajax({
                                                    url: "/BookStoreManagement/LoadReviewController",
                                                    type: "post", //send it through get method
                                                    data: {
                                                        index: index,
                                                        reviewId: reviewId,
                                                        use: use,
                                                        reviewDetailID: reviewDetailID
                                                    },
                                                    success: function (data) {
                                                        var row = document.getElementById("reviews");
                                                        row.innerHTML += data;
                                                        document.getElementById(index).classList.add("active");
                                                        for (var i = 0; i < document.getElementsByClassName("tagA").length; i++) {
                                                            document.getElementsByClassName("tagA")[i].style.display = "none";
                                                        }
                                                    },
                                                    error: function (xhr) {
                                                        //Do Something to handle error
                                                    }
                                                });
                                            }
                                        </script>
                                        <style>
                                            .pagination {
                                                display: inline-block;
                                            }

                                            .pagination a {
                                                color: black;
                                                float: left;
                                                padding: 8px 16px;
                                                text-decoration: none;
                                            }

                                            .pagination a.active {
                                                background-color: #15161d;
                                                color: white;
                                            }

                                            .pagination a:hover:not(.active) {background-color: #ddd;}
                                        </style>
                                        <!-- /Reviews -->
                                        <%
                                            ReviewDetailDTO review = new ReviewDetailDTO();
                                            if (staff == null) {
                                        %>
                                        <!-- Review Form -->
                                        <div class="col-md-3">
                                            <div id="review-form">
                                                <form style="text-align: center;" action="AddReviewController" method="POST" class="review-form">
                                                    <input hidden="" name="reviewID" value="<%= book.getReview().getReviewID()%>">
                                                    <input hidden="" name="isbn" value="<%= book.getIsbn()%>">                                                    
                                                    <%
                                                        boolean check = false;
                                                        if (cus != null) {
                                                    %>
                                                    <input readonly="" class="input" type="text" placeholder="Tên của bạn" value="<%= cus.getName()%>">
                                                    <input readonly="" class="input" type="email" placeholder="Địa chỉ email của bạn" value="<%= cus.getEmail()%>">
                                                    <% for (ReviewDetailDTO reviewDetailDTO : listReviewBook) {
                                                            if (reviewDetailDTO.getCustomer().getCustomerID().equals(cus.getCustomerID())) {
                                                                review = reviewDetailDTO;
                                                                check = true;
                                                            }
                                                        }
                                                        if (check) {
                                                            double star = review.getRate();
                                                            double noStar = 5 - star;
                                                            if (review.getDescription() == null) {
                                                                review.setDescription("");
                                                            }
                                                            String des = request.getParameter("review");
                                                            if (des == null || des == "") {
                                                                des = review.getDescription();
                                                            }
                                                    %>
                                                    <textarea id="review" readonly="" name="review" class="input" placeholder="Phần phê bình"><%= des%></textarea>
                                                    <div class="input-rating">
                                                        <span>Đánh giá: </span>
                                                        <div id="starsed" class="stars">
                                                            <%
                                                                for (int i = 0; i < star; i++) {
                                                            %>
                                                            <i style="color: #d10024" class="fa fa-star"></i>
                                                            <%
                                                                }
                                                            %>
                                                            <%
                                                                for (int i = 0; i < noStar; i++) {
                                                            %>
                                                            <i style="color: #e4e8f1" class="fa fa-star-o"></i>
                                                            <%
                                                                }
                                                            %>
                                                        </div>
                                                        <input hidden="" name="reviewDetailID" value="<%= review.getReviewDetailID()%>">
                                                        <div id="stars" class="stars">
                                                            <input id="star5" name="rating" value="5" type="radio"><label for="star5"></label>
                                                            <input id="star4" name="rating" value="4" type="radio"><label for="star4"></label>
                                                            <input id="star3" name="rating" value="3" type="radio"><label for="star3"></label>
                                                            <input id="star2" name="rating" value="2" type="radio"><label for="star2"></label>
                                                            <input id="star1" name="rating" value="1" type="radio"><label for="star1"></label>
                                                        </div>
                                                    </div>
                                                    <button type="button" onclick="edit()" class="primary-btn" id="edite">Chỉnh sửa</button>
                                                    <button name="action" value="Edit" class="primary-btn" id="save">Lưu</button>
                                                    <style>
                                                        #save{
                                                            display: none;
                                                        }
                                                        #stars{
                                                            display: none;
                                                        }
                                                    </style>
                                                    <script>
                                                        function edit() {
                                                            document.getElementById("review").readOnly = false;
                                                            document.getElementById("review").style.border = "2px solid black";
                                                            document.getElementById("save").style.display = "inline-block";
                                                            document.getElementById("stars").style.display = "inline-block";
                                                            document.getElementById("edite").style.display = "none";
                                                            document.getElementById("starsed").style.display = "none";
                                                        }
                                                    </script>
                                                    <%
                                                    } else {
                                                        String reviews = request.getParameter("review");
                                                        if (reviews == null) {
                                                            reviews = "";
                                                        }
                                                    %>
                                                    <textarea name="review" class="input" placeholder="Phần phê bình"><%= reviews%></textarea>
                                                    <div class="input-rating">
                                                        <span>Đánh giá: </span>
                                                        <div class="stars">
                                                            <input id="star5" name="rating" value="5" type="radio"><label for="star5"></label>
                                                            <input id="star4" name="rating" value="4" type="radio"><label for="star4"></label>
                                                            <input id="star3" name="rating" value="3" type="radio"><label for="star3"></label>
                                                            <input id="star2" name="rating" value="2" type="radio"><label for="star2"></label>
                                                            <input id="star1" name="rating" value="1" type="radio"><label for="star1"></label>
                                                        </div>
                                                    </div>
                                                    <button name="action" value="Add" class="primary-btn">Tạo đánh giá</button>
                                                    <%
                                                        }
                                                    } else if (cus == null) {
                                                    %>
                                                    <button name="action" value="Add" class="primary-btn">Tạo đánh giá</button>
                                                    <%
                                                        }
                                                    %>
                                                </form>
                                            </div>
                                        </div>
                                        <!-- /Review Form -->
                                        <%
                                            }
                                        %>
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
                            <form style="display: inline-block;" method="POST" action="AddBookCartController">
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