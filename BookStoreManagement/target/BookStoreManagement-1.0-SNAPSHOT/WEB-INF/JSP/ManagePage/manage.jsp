<%-- 
    Document   : manage
    Created on : Oct 2, 2022, 1:25:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Manage</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">  
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
        <link rel="stylesheet" href="https://unpkg.com/charts.css/dist/charts.min.css">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            #emptyList{
                font-size: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div style="text-align: center;" id="content">
            <p style=" height: 80px; font-size: 90px; text-align: center; color: #494f57;">Book Store Management</p>
            <img style="margin-top: 20px; height: 450px;" src="IMG/manage.png"/>
        </div>

        <script>
            function load(a, index, b, isbn, isbnN, use, title, author, pub, cate, quan, price, img, status, des) {
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        use: use,
                        index: index,
                        searchBook: b,
                        isbn: isbn,
                        isbnN: isbnN,
                        title: title,
                        author: author,
                        pub: pub,
                        cate: cate,
                        quan: quan,
                        price: price,
                        img: img,
                        status: status,
                        des: des
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function book(a, use, index) {
                var b = document.getElementById("searchBook").value;
                var isbn = document.getElementById("isbn").value;
                var isbnN = document.getElementById("isbnN").value;
                var title = document.getElementById("name").value;
                var author = document.getElementById("author").value;
                var pub = document.getElementById("publisher").value;
                var cate = document.getElementById("category").value;
                var quan = document.getElementById("quantity").value;
                var price = document.getElementById("price").value;
                var img = document.getElementById("img").value;
                var status = document.getElementById("status").value;
                var des = document.getElementById("des").value;
                load(a, index, b, isbn, isbnN, use, title, author, pub, cate, quan, price, img, status, des);
            }
            function loadBook(a, search, isbn, index, use) {
                load(a, index, search, isbn, null, use);
            }
            function add(a, c) {
                var b = document.getElementById("searchBook").value;
                var use = "add";
                var isbnN = null;
                var isbn = document.getElementsByClassName(c)[0].value;
                var name = document.getElementsByClassName(c)[1].value;
                var publisher = document.getElementsByClassName(c)[2].value;
                var category = document.getElementsByClassName(c)[3].value;
                var author = document.getElementsByClassName(c)[4].value;
                var price = document.getElementsByClassName(c)[5].value;
                var image = document.getElementsByClassName(c)[6].value;
                var quantity = document.getElementsByClassName(c)[7].value;
                var des = document.getElementsByClassName(c)[8].value;
                load(a, "", b, isbn, isbnN, use, name, author, publisher, category, quantity, price, image, "1", des);
            }
            function loadPub(a, index, b, publisherID, use, pubB, name, status) {
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        use: use,
                        searchPublisher: b,
                        publisherID: publisherID,
                        pubB: pubB,
                        name: name,
                        status: status
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function updatePub(a, search, pubID, index, use) {
                loadPub(a, index, search, pubID, use);
            }
            function pub(a, use, index) {
                var b = document.getElementById("searchPublisher").value;
                var pubB = document.getElementById("pubB").value;
                var pubName = document.getElementById("pubName").value;
                loadPub(a, index, b, pubB, use, pubB, pubName, '1');
            }
            function addPub(a, c) {
                var b = document.getElementById("searchPublisher").value;
                var use = "add";
                var pubB = null;
                var pubID = document.getElementsByClassName(c)[0].value;
                var pubName = document.getElementsByClassName(c)[1].value;
                loadPub(a, "", b, pubID, use, pubB, pubName, "1");
            }
            function loadCate(a, index, b, categoryID, use, cateE, name, status) {
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        use: use,
                        searchCategory: b,
                        categoryID: categoryID,
                        cateE: cateE,
                        name: name,
                        status: status
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function updateCate(a, search, cateID, index, use) {
                loadCate(a, index, search, cateID, use);
            }
            function cate(a, use, index) {
                var b = document.getElementById("searchCategory").value;
                var cateE = document.getElementById("cateE").value;
                var cateName = document.getElementById("cateName").value;
                loadCate(a, index, b, cateE, use, cateE, cateName, '1');
            }
            function addCate(a, c) {
                var b = document.getElementById("searchCategory").value;
                var use = "add";
                var cateE = null;
                var cateID = document.getElementsByClassName(c)[0].value;
                var cateName = document.getElementsByClassName(c)[1].value;
                loadCate(a, "", b, cateID, use, cateE, cateName, "1");
            }
            function loadStaff(a, index, b, staffID, use, role, name, phone, dob, password, confirm) {
                console.log(a + ", " + index + ", " + b + ", " + staffID + ", " + use + ", " + role + ", " + name + ", " + phone + ", " + dob + ", " + password + ", " + confirm);
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        use: use,
                        searchStaff: b,
                        staffID: staffID,
                        role: role,
                        name: name,
                        phone: phone,
                        dob: dob,
                        password: password,
                        confirm: confirm
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function updateStaff(a, search, staffID, index, use) {
                loadStaff(a, index, search, staffID, use);
            }
            function staff(a, use, index) {
                var b = document.getElementById("searchStaff").value;
                var staffID = document.getElementById("staffID").value;
                var role = document.getElementById("role").value;
                var name = document.getElementById("name").value;
                var phone = document.getElementById("phone").value;
                var dob = document.getElementById("dob").value;
                loadStaff(a, index, b, staffID, use, role, name, phone, dob);
                console.log(b + "," + staffID + "," + role + "," + name + "," + phone + "," + dob + ",");
            }
            function addStaff(a, c) {
                var b = document.getElementById("searchStaff").value;
                var use = "add";
                var name = document.getElementsByClassName(c)[0].value;
                var staffID = document.getElementsByClassName(c)[1].value;
                var password = document.getElementsByClassName(c)[2].value;
                var confirm = document.getElementsByClassName(c)[3].value;
                var role = document.getElementsByClassName(c)[4].value;
                var phone = document.getElementsByClassName(c)[5].value;
                var dob = document.getElementsByClassName(c)[6].value;
                loadStaff(a, "", b, staffID, use, role, name, phone, dob, password, confirm);
            }
            function loadCus(a, index, b, cusID, use, email, name, phone, addr, point, password, confirm) {
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        use: use,
                        searchCus: b,
                        cusID: cusID,
                        email: email,
                        name: name,
                        phone: phone,
                        point: point,
                        addr: addr,
                        password: password,
                        confirm: confirm
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function updateCus(a, search, cusID, index, use) {
                loadCus(a, index, search, cusID, use);
            }
            function cus(a, use, index) {
                var b = document.getElementById("searchCus").value;
                var cusID = document.getElementById("cusID").value;
                var point = document.getElementById("point").value;
                var name = document.getElementById("name").value;
                var phone = document.getElementById("phone").value;
                var email = document.getElementById("email").value;
                var addr = document.getElementById("addr").value;
                loadCus(a, index, b, cusID, use, email, name, phone, addr, point);
            }
            function addCus(a, c) {
                var b = document.getElementById("searchCus").value;
                var use = "add";
                var name = document.getElementsByClassName(c)[0].value;
                var cusID = document.getElementsByClassName(c)[1].value;
                var password = document.getElementsByClassName(c)[2].value;
                var confirm = document.getElementsByClassName(c)[3].value;
                var email = document.getElementsByClassName(c)[4].value;
                var phone = document.getElementsByClassName(c)[5].value;
                var point = document.getElementsByClassName(c)[6].value;
                var addr = document.getElementsByClassName(c)[7].value;
                loadCus(a, "", b, cusID, use, email, name, phone, addr, point, password, confirm);
            }
            function loadOrder(a, index, status, b, use, orderID) {
                var edit = $('input[type="radio"][name="setStatus"]:checked').val();
                document.getElementById("content").innerHTML = "";
                if (index === "") {
                    index = "1";
                }
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        searchOrder: b,
                        status: status,
                        use: use,
                        orderID: orderID,
                        edit: edit
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function loadRevenue(a, date) {
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        date: date
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        date = notice.split('-');
                        document.getElementById("day").innerHTML = date[2];
                        document.getElementById("month").innerHTML = date[1];
                        document.getElementById("year").innerHTML = date[0];
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function loadPromotion(a, index, searchPromotion, promotionID, use, dateS, dateE, condition, percent, des) {
                if (index === "") {
                    index = "1";
                }
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        searchPromotion: searchPromotion,
                        promotionID: promotionID,
                        use: use,
                        dateS: dateS,
                        dateE: dateE,
                        condition: condition,
                        percent: percent,
                        des: des
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        document.getElementById(index).classList.add("active");
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function loadRequest(a, indexR, tab, searchRequest, requestID, use, isbn, quantity, searchBook, index) {
                if (index === "") {
                    index = "1";
                }
                if (indexR === "") {
                    indexR = "1";
                }
                if (tab === "") {
                    tab = "tab02";
                }
                var tabs;
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        indexR: indexR,
                        searchRequest: searchRequest,
                        requestID: requestID,
                        use: use,
                        isbn: isbn,
                        quantity: quantity,
                        tab: tab,
                        searchBook: searchBook
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        if (tab === "tab02") {
                            tabs = "tab01";
                            document.getElementById("searchBook").style.display = "none";
                            document.getElementById("searchButton").style.display = "none";
                        } else if (tab === "tab01") {
                            tabs = "tab02";
                            document.getElementById("searchBook").style.display = "inline-block";
                            document.getElementById("searchButton").style.display = "inline-block";
                        }
                        document.getElementById(tab).classList.add("active");
                        document.getElementById(tab).classList.add("font-weight-bold");
                        document.getElementById(tabs).classList.add("text-muted");
                        document.getElementById(tab + "1").classList.add("show");
                        $(document).ready(function () {
                            $(".tabs").click(function () {
                                $(".tabs").removeClass("active");
                                $(".tabs h6").removeClass("font-weight-bold");
                                $(".tabs h6").addClass("text-muted");
                                $(this).removeClass("text-muted");
                                $(this).children("h6").removeClass("text-muted");
                                $(this).children("h6").addClass("font-weight-bold");
                                $(this).addClass("font-weight-bold");
                                $(this).addClass("active");
                                current_fs = $(".active");
                                next_fs = $(this).attr('id');
                                if (next_fs === "tab02") {
                                    document.getElementById("searchBook").style.display = "none";
                                    document.getElementById("searchButton").style.display = "none";
                                } else if (next_fs === "tab01") {
                                    document.getElementById("searchBook").style.display = "inline-block";
                                    document.getElementById("searchButton").style.display = "inline-block";
                                }
                                next_fs = "#" + next_fs + "1";
                                $("fieldset").removeClass("show");
                                $(next_fs).addClass("show");
                                current_fs.animate({}, {
                                    step: function () {
                                        current_fs.css({
                                            'display': 'none',
                                            'position': 'relative'
                                        });
                                        next_fs.css({
                                            'display': 'block'
                                        });
                                    }
                                });
                            });
                        });
                        if (index != null) {
                            var classIndex = index - 1;
                        } else {
                            var classIndex = indexR - 1;
                        }
                        try {
                            document.getElementById(indexR).classList.add("active");
                        } catch (exception) {
                        }
                        try {
                            document.getElementsByClassName("index")[classIndex].classList.add("active");
                        } catch (exception) {
                        }
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add" || use === "addBook" || use === "searchAdd" || use === "editBook" || use === "removeBook") {
                            $('#myModal').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
            function loadResponse(a, index, b, use, responseID, indexR, searchRequest, requestID, isbn, quantity, price, quantityCheck) {
                if (indexR === "") {
                    indexR = "1";
                }
                if (index === "") {
                    index = "1";
                }
                document.getElementById("content").innerHTML = "";
                $.ajax({
                    url: "/BookStoreManagement/" + a,
                    type: "post", //send it through get method
                    data: {
                        index: index,
                        searchResponse: b,
                        use: use,
                        responseID: responseID,
                        requestID: requestID,
                        indexR: indexR,
                        searchRequest: searchRequest,
                        isbn: isbn,
                        quantity: quantity,
                        price: price,
                        quantityCheck: quantityCheck
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML += data;
                        if (indexR != null || indexR > 0) {
                            var classIndex = indexR - 1;
                        } else {
                            var classIndex = 0;
                        }
                        try {
                            document.getElementById(index).classList.add("active");
                        } catch (exception) {

                        }
                        try {
                            document.getElementsByClassName("indexR")[classIndex].classList.add("active");
                        } catch (exception) {

                        }
                        if (use === "load" || use === "remove" || use === "recover" || use === "edit") {
                            $('#exampleModalCenter').modal('show');
                        } else if (use === "add" || use === "search" || use === "create") {
                            $('#myModal').modal('show');
                        } else if (use === "choosen" || use === "save") {
                            $('#modal2').modal('show');
                        }
                        var modal = document.getElementById("makeModal").value;
                        if (modal !== null && modal !== "") {
                            alert(modal);
                        }
                    },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
                });
            }
        </script>
        <style>
            #edit:hover{
                background-color: #ff6600;
                color: white;
            }
            #remove:hover{
                background-color: red;
                color: white;
            }
            #recover:hover{
                background-color: green;
                color: white;
            }
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
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
            #line-example-1 {
                height: 500px;
                max-width: 80%;
                margin: 0 auto;
                background-color: white;
            }
            fieldset {
                display: none;
            }
            fieldset.show {
                display: block;
            }
            select:focus, input:focus {
                -moz-box-shadow: none !important;
                -webkit-box-shadow: none !important;
                box-shadow: none !important;
                border: 1px solid #2196F3 !important;
                outline-width: 0 !important;
                font-weight: 400;
            }
            button:focus {
                -moz-box-shadow: none !important;
                -webkit-box-shadow: none !important;
                box-shadow: none !important;
                outline-width: 0;
            }
            .tabs {
                margin: 2px 5px 0px 5px;
                padding-bottom: 10px;
                cursor: pointer;
            }
            .tabs:hover, .tabs.active {
                border-bottom: 1px solid #2196F3;
            }
            a:hover {
                text-decoration: none;
                color: #1565C0;
            }
            .box {
                margin-bottom: 10px;
                border-radius: 5px;
                padding: 10px;
            }
            .line {
                background-color: #CFD8DC;
                height: 1px;
                width: 100%;
            }
            @media screen and (max-width: 768px) {
                .tabs h6 {
                    font-size: 12px;
                }
            }
        </style>
    </body>
</html>
