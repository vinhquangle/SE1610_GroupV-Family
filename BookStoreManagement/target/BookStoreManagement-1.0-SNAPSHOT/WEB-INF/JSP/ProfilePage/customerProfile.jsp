<%-- 
    Document   : customerProfile
    Created on : Oct 16, 2022, 9:40:04 PM
    Author     : PCPV
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="dto.OrderDetailDTO"%>
<%@page import="dto.OrderDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.CustomerErrorDTO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="aes.MyAES"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
        <title>Profile</title>
    </head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <body>
        <%
            List<OrderDTO> listOrder = (List<OrderDTO>) request.getAttribute("ORDER");
            MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
            String AES_decryptedStr = myCipher.AES_decrypt(cus.getPassword());//Giải mã AES
            CustomerErrorDTO cusError = new CustomerErrorDTO();
            String mess = new String();
            try {
                cusError = (CustomerErrorDTO) request.getAttribute("CUSTOMER_ERROR");
                mess = (String) request.getAttribute("MESSAGE");
            } catch (Exception e) {
            }
            String name = new String();
            String email = new String();
            String phone = new String();
            String addr = new String();
            if (cusError != null) {
                name = cusError.getNameError();
                email = cusError.getEmailError();
                phone = cusError.getPhoneError();
                addr = cusError.getAddressError();
            }
            if (mess == null) {
                mess = "";
            }
        %>
        <!-- Modal -->
        <div class="modal" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Thông tin đơn hàng</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div style="overflow: auto; height: 700px;" id="mcontent" class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div class="row" style="margin-top: 180px; margin-bottom: 30px; width: 100.8%">
            <div style="background-color: #15161d; text-align: center; height: 65px;" class="col-md-12">
                <p style="margin-top: auto; color: white; display: inline-block; font-size: 35px;">Trang cá nhân</p>
            </div>
        </div>
        <div style="height: 550px; width: 100.8%;" class="row">
            <div class="col-md-3" style="border-right: 2px solid red; text-align: center;">
                <img style="height: 300px; margin-bottom: 30px;" src="IMG/user.png">
                <div id="choosen">
                    <a onclick="on('1')" id="1" class="pan" href="#" >Thông tin tài khoản</a>
                    <a onclick="on('2')" id="2" class="pan" href="#" >Thông tin cá nhân</a>
                    <a onclick="on('3')" id="3" class="pan" href="#" >Lịch sử giao dịch</a>
                </div>
            </div>
            <div id="form1" class="col-md-9">
                <div class="container bootstrap snippet" style="background-color: white; height: 500px;" >
                    <div>            
                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <form class="form" action="ViewProfileCusController" method="POST" id="registrationForm">
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="account"><h2 style="font-size: 25px;">Tài khoản</h2></label>
                                        <input readonly="" style="height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control" value="<%= cus.getCustomerID()%>" >
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="point"><h2 style="font-size: 25px;">Điểm</h2></label>
                                        <input readonly="" style="height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control" value="<%= cus.getPoint()%>" >
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="password"><h2 style="font-size: 25px;">Mật khẩu</h2></label>
                                        <input readonly=""  id="pass" style="height: 50px; font-size: 20px; background-color: white;" type="password" class="form-control" value="********************" >
                                        <div><input type="checkbox" onclick="change()">Show Password</div>
                                        <input type="hidden" id="passr" value="<%= AES_decryptedStr%>">
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="status"><h2 style="font-size: 25px;">Trạng thái</h2></label>
                                        <%
                                            if (cus.getStatus().equals("1")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 20px; color: green; background-color: white;" type="text" class="form-control" id="first_name" value="Online" >
                                        <%
                                        } else if (cus.getStatus().equals("0")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 20px; color: red; background-color: white;" type="text" class="form-control" id="first_name" value="Offline" >
                                        <%
                                            }
                                        %>
                                    </div>                          
                                </form> 
                            </div><!--/tab-pane-->
                        </div><!--/tab-pane-->
                    </div><!--/tab-content-->
                </div>
                <div style="text-align: center;">
                    <form method="POST" action="ChangePasswordController">
                        <button class="changePass">Thay đổi mật khẩu</button>
                        <input type="hidden" name="action" value="">
                    </form>
                </div>
            </div>    
            <div id="form2" class="col-md-9">
                <div class="container bootstrap snippet" style="background-color: white; height: 500px;" >     
                    <div class="tab-content">
                        <p style="left: 300px; position: absolute; text-align: center; color: white; background-color: #81b560; width: 50%; border-radius: 10px; font-size: 20px; font-weight: bold;" ><%=mess%></p>
                        <div class="tab-pane active" id="home">
                            <form id="cusForm" class="form" action="ViewProfileController" method="GET" id="registrationForm">
                                <div class="row">
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="account"><h2 style="font-size: 25px;">Họ và tên</h2></label>
                                        <input name="name" readonly="" style="margin-bottom: 5px; height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control b" value="<%= cus.getName()%>" >
                                        <p style="text-align: center; color: white; background-color: #d10024; width: 70%; margin: auto; border-radius: 10px;" ><%= name%></p>
                                    </div>
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="point"><h2 style="font-size: 25px;">Email</h2></label>
                                        <input name="email" readonly="" style="margin-bottom: 5px; height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control b" value="<%= cus.getEmail()%>" >
                                        <p style="text-align: center; color: white; background-color: #d10024; width: 70%; margin: auto; border-radius: 10px;" ><%= email%></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="password"><h2 style="font-size: 25px;">Số điện thoại</h2></label>
                                        <input name="phone" readonly="" id="pass" style="margin-bottom: 5px; height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control b" value="<%= cus.getPhone()%>" >
                                        <p style="text-align: center; color: white; background-color: #d10024; width: 70%; margin: auto; border-radius: 10px;" ><%= phone%></p>
                                    </div>
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="status"><h2 style="font-size: 25px;">Địa chỉ</h2></label>
                                        <input name="address" readonly="" style="margin-bottom: 5px; height: 50px; font-size: 20px; background-color: white;" type="text" class="form-control b" value="<%= cus.getAddress()%>" >
                                        <p style="text-align: center; color: white; background-color: #d10024; width: 70%; margin: auto; border-radius: 10px;" ><%= addr%></p>
                                    </div>
                                </div>
                                <input name="action" type="hidden" value="">
                                <input name="index" type="hidden" value="2">
                            </form> 
                        </div><!--/tab-pane-->
                    </div><!--/tab-pane-->
                </div>
                <div style="text-align: center;">
                    <button onclick="edit('edit')" class="changePass" id="edit">Chỉnh sửa thông tin</button>
                    <button onclick="edit('save')" onmousedown="saveProfile('cusForm')" class="changePass" id="save">Lưu thông tin</button>
                </div>
            </div>    
            <script>
                function saveProfile(a) {
                    document.getElementById(a).submit();
                }
                function loadDetail(orderID) {
                    document.getElementById("mcontent").innerHTML = "";
                    $.ajax({
                        url: "/BookStoreManagement/ViewOrderDetailController",
                        type: "post", //send it through get method
                        data: {
                            orderID: orderID
                        },
                        success: function (data) {
                            var row = document.getElementById("mcontent");
                            row.innerHTML += data;
                        },
                        error: function (xhr) {
                            //Do Something to handle error
                        }
                    });
                }
            </script>
            <div style="overflow: auto" id="form3" class="col-md-9">
                <div style="background-color: white; height: 500px; width: 99%; margin-left: 10px; font-size: 14px;" >           
                    <div style="background-color: #15161d; color: white; border-radius: 100px; text-align: center;" class="row">
                        <div style="border: 1px solid white;" class="col-md-1">Mã hóa đơn</div>
                        <div style="border: 1px solid white;" class="col-md-2">Ngày</div>
                        <div style="border: 1px solid white;" class="col-md-2">Phí giao hàng</div>
                        <div style="border: 1px solid white;" class="col-md-2">Tổng thành tiền</div>
                        <div style="border: 1px solid white;" class="col-md-2">Ghi chú</div>
                        <div style="border: 1px solid white;" class="col-md-2">Tình trạng</div>
                        <div style="border: 1px solid white;" class="col-md-1"></div>
                    </div>
                    <%
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                        if (!listOrder.isEmpty()) {
                            for (OrderDTO orderDTO : listOrder) {
                                if(orderDTO.getDescription()==null){
                                    orderDTO.setDescription("");
                                }
                    %>
                    <div style="padding-bottom: 30px; padding-top: 30px; color: black; border-radius: 100px; text-align: center; border: 1px solid #15161d; margin-top: 5px; font-size: 20px; font-weight: bold" class="row">
                        <div class="col-md-1"><%= orderDTO.getOrderID()%></div>
                        <div class="col-md-2"><%= orderDTO.getDate()%></div>
                        <div class="col-md-2"><%= currencyVN.format(orderDTO.getDeliveryCost())%></div>
                        <div class="col-md-2"><%= currencyVN.format(orderDTO.getTotal())%></div>
                        <div class="col-md-2"><%= orderDTO.getDescription()%></div>
                        <%
                            if (orderDTO.getStatus() == null) {
                        %>
                        <div style="color: #d68829;" class="col-md-2">Đang tiến hành</div>
                        <%
                        } else if (orderDTO.getStatus().equals("1")) {
                        %>
                        <div style="color: green;" class="col-md-2">Hoàn thành</div>
                        <%
                        } else if (orderDTO.getStatus().equals("0")) {
                        %>
                        <div style="color: red;" class="col-md-2">Hủy bỏ</div>
                        <%
                            }
                        %>
                        <div class="col-md-1"><button onclick="loadDetail(<%= orderDTO.getOrderID()%>)" data-bs-toggle="modal" data-bs-target="#exampleModalCenter" id="but" ><i class="fa fa-info-circle" aria-hidden="true"></i></button></div>
                    </div>
                    <%
                            }
                        }
                    %>
                    <style>
                        #but{
                            padding: 2px;
                            color: #15161d;
                            border-radius: 50%;
                        }
                        #but:hover{
                            background-color: black;
                            color: white;
                        }
                        @media (min-width: 768px) {
                            .modal-xl {
                                width: 90%;
                                max-width:1200px;
                            }
                        }
                    </style>
                </div>
            </div>  
        </div>  
    </body>
</html>
