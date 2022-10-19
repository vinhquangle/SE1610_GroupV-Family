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
        <title>Manage Page</title>
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div style="text-align: center;" id="content">
            <p style=" height: 80px; font-size: 90px; text-align: center; color: #494f57;">Book Store Management</p>
            <img style="margin-top: 20px; height: 450px;" src="IMG/manage.png"/>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
                console.log(a + ", " + index + ", " + b + ", " + cusID + ", " + use + ", " + email + ", " + name + ", " + phone + ", " + addr + ", " + point + ", " +password + ", " + confirm);
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
            #inputisbn::-webkit-outer-spin-button,
            #inputisbn::-webkit-inner-spin-button {
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

            .pagination a:hover:not(.active) {background-color: #ddd;}
        </style>
    </body>
</html>
