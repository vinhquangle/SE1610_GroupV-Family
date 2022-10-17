<%-- 
    Document   : orderDetails
    Created on : Oct 17, 2022, 6:45:41 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Details Page</title>
        <link rel="stylesheet" href="css/orderDetail.css">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="card">

            <div class="title">Purchase Receipt</div>
            <div class="info">
                <div class="row">
                    <div class="col-7">
                        <span id="heading">Date</span><br>
                        <span id="details">17 October 2022</span>
                    </div>
                    <div class="col-5 pull-right">
                        <span id="heading">Order No.</span><br>
                        <span id="details">012j1gvs356c</span>
                    </div>
                </div>      
            </div>      
            <div class="pricing">
                <div class="row">
                    <div class="col-9">
                        <span id="name">Quà tặng cuộc sống</span></br>
                        <span id="name">Đánh thức sức mạnh bản thân</span>
                    </div>
                    <div class="col-3">
                        <span id="price">70.000đ</span></br>
                        <span id="price">80.000đ</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-9">
                        <span id="name">Shipping</span>
                    </div>
                    <div class="col-3">
                        <span id="price">30.000đ</span>
                    </div>
                </div>
            </div>
            <div class="total">
                <div class="row">
                    <div class="col-9 pull-right">Totals</div>
                    <div class="col-3"><big>180.000đ</big></div>
                </div>
            </div>           
            <div class="tracking">
                <div>On progress</div>
            </div>            
        </div>
    </div>
</body>
</html>
