<%-- 
    Document   : createCart
    Created on : Oct 23, 2022, 3:36:01 PM
    Author     : Thịnh
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Cart</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <!-- Custom stlylesheet -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <%@include file="headerCart.jsp" %>
        <%            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            List<CustomerDTO> listCus = (List<CustomerDTO>) session.getAttribute("LIST_CUS");
        %>
        <script>
            window.onload = function () {
                loadCreate('', '1');
                loadAdd('1', 'F5', '1');
                document.getElementById("search-form").onsubmit = function () {
                    return false;
                };
            };
        </script>
        <div style="margin-top: 180px; margin-left: 10px; margin-right: 10px;" class="row">
            <div class="col-md-6">
                <div style="margin-bottom: 30px; margin-top: 30px; text-align: center;" class="row">
                    <div class="col-md-12">
                        <form id="search-form" action="#" method="POST">
                            <input id="searchB" class="input" type="text"  placeholder="Tìm sách theo tiêu đề, tác giả hoặc ISBN" style="width: 380px">
                            <input onclick="loadCreate(document.getElementById('searchB').value, '1')" type="submit" name="button" value="Tìm kiếm" name="action" style="width: 100px; height: 40px; background-color: #1e1e27; color: white; font-weight: bold; border: none;">
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div style="background-color: #1e1e27; color: white; font-weight: bold; font-size: 15px; border-radius: 10px;" class="row">
                            <div class="col-md-2"></div>
                            <div style="border-left: 2px solid white;" class="col-md-4">Tiêu đề</div>
                            <div style="border-left: 2px solid white;" class="col-md-2">Hiện còn</div>
                            <div style="border-left: 2px solid white;" class="col-md-2">Giá bán</div>
                            <div style="border-left: 2px solid white;" class="col-md-2"></div>
                        </div>
                        <div id="createContent"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div style="border: 1px solid #dedede; height: 100%; margin-left: 10px; margin-top: 100px;" class="col-md-12">
                        <div class="section-title text-center">
                            <h3 class="title">Đơn hàng của bạn</h3>
                        </div>
                        <div class="order-summary">
                            <div class="order-col">
                                <div style="width: 700px;"><strong>Sản phẩm</strong></div>
                                <div style="width: 150px;"><strong>Tổng</strong></div>
                                <div></div>
                            </div>
                            <div id="cartContent" class="order-products"></div>
                            <div class="input-radio">
                                <input checked="" name="method" type="radio" id="payment-5" value="store">
                                <label for="payment-5">
                                    <span></span>
                                    <b>Nhận tại cửa hàng</b>
                                </label>
                                <div class="caption">
                                    <p>Quý khách sẽ nhận hàng tại cửa hàng(miễn phí ship)</p>
                                </div>
                            </div>
                            <div class="order-col">
                                <div><strong>Tổng thành tiền</strong></div>
                                <div><strong class="order-total"><input id="total" readonly="" style="border: none; width: 100%" value="<%= currencyVN.format(0)%>"></strong></div>
                            </div>
                            <form action="CheckoutController" method="POST">
                                <div class="order-col">
                                    <div style="width: 100%; text-align: center; position: relative;"><strong>Số điện thoại: <input name="phone" value="" id="myInput" onkeyup="searchPhone(document.getElementById('myInput').value)"><i onclick="clean()" class="fa fa-pencil-square-o" style="cursor: pointer; margin-left: 10px; font-size: 20px;"></i></strong><br>
                                        <ul id="myUL">
                                            <%
                                                for (CustomerDTO customer : listCus) {
                                            %>
                                            <li>
                                                <a onclick="loadCus('<%= customer.getCustomerID()%>', '<%= customer.getPhone()%>')" href="#"><%= customer.getPhone()%><br><%= customer.getName()%></a>
                                            </li>
                                            <%
                                                }
                                            %>
                                        </ul>
                                    </div>
                                </div>
                                <div class="order-col">
                                    <div id="cusContent" style="width: 100%; text-align: center; position: relative;"></div>
                                </div>
                                <button id="checkout" name="action" value="Store" style="color: white; cursor: pointer; width: 100%;" disabled="" class="primary-btn order-submit">Thanh toán</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            if (document.getElementById("myInput").blur() === true) {
                document.getElementById("myUL").style.display = "none";
            }
            function clean() {
                document.getElementById("cusContent").innerHTML = "";
                document.getElementById("myInput").focus();
                document.getElementById("myInput").readOnly = false;
                document.getElementById("myInput").style.border = "1px solid #cccccc";
                document.getElementById("checkout").setAttribute("disabled", "");
            }
            function searchPhone(phone) {
                var input, filter, ul, li, a, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                ul = document.getElementById("myUL");
                ul.style.display = "inline-block";
                li = ul.getElementsByTagName("li");
                for (i = 0; i < li.length; i++) {
                    a = li[i].getElementsByTagName("a")[0];
                    txtValue = a.textContent || a.innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        li[i].style.display = "";
                    } else {
                        li[i].style.display = "none";
                    }
                }
                var phoneno = /^[0-9]{9,11}$/;
                if ((phone.match(phoneno))) {
                    document.getElementById("checkout").removeAttribute("disabled");
                } else {
                    document.getElementById("checkout").setAttribute("disabled", "");
                }
            }
            function loadCus(cusID, phone) {
                document.getElementById("cusContent").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/AddCustomerCartController",
                    type: "post", //send it through get method
                    data: {
                        cusID: cusID
                    },
                    success: function (data) {
                        var row = document.getElementById("cusContent");
                        row.innerHTML += data;
                        document.getElementById("myUL").style.display = "none";
                        document.getElementById("myInput").value = phone;
                        document.getElementById("myInput").readOnly = true;
                        document.getElementById("myInput").style.border = "none";
                        document.getElementById("nameCus").readOnly = true;
                        document.getElementById("nameCus").style.border = "none";
                        document.getElementById("checkout").removeAttribute("disabled");
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                }
                );
            }
            function loadCreate(searchBook, index) {
                document.getElementById("createContent").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/CreateCartController",
                    type: "post", //send it through get method
                    data: {
                        searchBook: searchBook,
                        index: index
                    },
                    success: function (data) {
                        var row = document.getElementById("createContent");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                }
                );
            }
            function loadAdd(quantity, isbn, quantityCheck, use) {
                document.getElementById("cartContent").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/InsertBookCart",
                    type: "post", //send it through get method
                    data: {
                        quantity: quantity,
                        isbn: isbn,
                        quantityCheck: quantityCheck,
                        use: use
                    },
                    success: function (data) {
                        var row = document.getElementById("cartContent");
                        row.innerHTML += data;
                        try {
                            var total = document.getElementById("totalS").value;
                        } catch (exception) {
                            var total = 0;
                        }
                        document.getElementById("total").value = total;
                        $('#eModal').modal('show');
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                }
                );
            }
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if ((charCode < 48 || charCode > 57))
                    return false;

                return true;
            }
            function add(a) {
                var quantity = (Number(document.getElementById(a).value) + 1);
                document.getElementById(a).value = quantity;
            }
            function subtract(a) {
                var quantity = (Number(document.getElementById(a).value));
                if (quantity > 1) {
                    quantity -= 1;
                    document.getElementById(a).value = quantity;
                }
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
            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            #nameCus{
                border-radius: 10px;
                border: 1px solid #cccccc;
                padding: 2px;
                color: #d10024;
            }
            #myInput{
                border-radius: 10px;
                border: 1px solid #cccccc;
                padding: 2px;
                color: #d10024;
            }
            #myUL {
                list-style-type: none;
                padding: 0;
                margin: 0;
                display: none;
                position: absolute;
                width: 45%;
                margin: auto;
                left: 32.5%;
                z-index: 5;
            }
            #myUL li a {
                border: 1px solid #ddd;
                margin-top: -1px; /* Prevent double borders */
                background-color: #1d1d26;
                padding: 12px;
                text-decoration: none;
                font-size: 18px;
                color: white;
                display: block;
            }
            #myUL li a:hover:not(.header) {
                background-color: #eee;
                color: #1d1d26;
            }
            #checkout:disabled{
                background-color: #c1c1c1;
            }
        </style>
    </body>
    <%@include file="../HeaderFooterPage/footer.jsp" %> 
</html>
