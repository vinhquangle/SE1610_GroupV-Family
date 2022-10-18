<%-- 
    Document   : customerProfile
    Created on : Oct 16, 2022, 9:40:04 PM
    Author     : PCPV
--%>

<%@page import="dto.StaffErrorDTO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="aes.MyAES"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%        MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
            String AES_decryptedStr = myCipher.AES_decrypt(staff.getPassword());//Giải mã AES
            LocalDate localDate = LocalDate.now();
            StaffErrorDTO staffError = new StaffErrorDTO();
            String mess = new String();
            try {
                staffError = (StaffErrorDTO) request.getAttribute("STAFF_ERROR");
                mess = (String) request.getAttribute("MESSAGE");
            } catch (Exception e) {
            }
            String name = new String();
            String phone = new String();
            String dob = new String();
            if (staffError != null) {
                name = staffError.getNameError();
                phone = staffError.getPhoneError();
                dob = staffError.getDateOfBirthError();
            }
            if (mess == null) {
                mess = "";
            }
        %>
        <div  class="row" style="margin-top: 180px; margin-bottom: 30px; width: 100.8%">
            <div style="background-color: #15161d; text-align: center; height: 50px;" class="col-md-12">
                <p style="margin-top: auto; color: white; display: inline-block; font-size: 30px;">Trang cá nhân</p>
            </div>
        </div>
        <div style="height: 550px; width: 100.8%;" class="row">
            <div class="col-md-3" style="border-right: 2px solid red; text-align: center;">
                <img style="height: 300px; margin-bottom: 30px;" src="IMG/user.png">
                <div id="choosen">
                    <a onclick="on('1')" id="1" class="pan" href="#" >Thông tin tài khoản</a>
                    <a onclick="on('2')" id="2" class="pan" href="#" >Thông tin cá nhân</a>
                    <a onclick="on('3')" id="3" class="pan" href="#" style="display: none" >Lịch sử giao dịch</a>
                </div>
            </div>
            <div id="form1" class="col-md-9">
                <div class="container bootstrap snippet" style="background-color: white; height: 500px;" >
                    <div>            
                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <form class="form" action="ViewProfileCusController" method="POST" id="registrationForm">
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="account"><h2>Tài khoản</h2></label>
                                        <input readonly="" style="height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control" value="<%= staff.getStaffID()%>" >
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="point"><h2>Vai trò</h2></label>
                                        <%
                                            if (staff.getRole().equals("Admin")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control" value="Quản trị viên" >
                                        <%
                                        } else if (staff.getRole().equals("Staff")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control" value="Nhân viên" >
                                        <%
                                        } else if (staff.getRole().equals("Deliverer")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control" value="Người giao hàng" >
                                        <%
                                            }
                                        %>
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="password"><h2>Mật khẩu</h2></label>
                                        <input readonly=""  id="pass" style="height: 50px; font-size: 25px; background-color: white;" type="password" class="form-control" value="********************" >
                                        <div><input type="checkbox" onclick="change()">Show Password</div>
                                        <input type="hidden" id="passr" value="<%= AES_decryptedStr%>">
                                    </div>
                                    <div style="margin-top: 80px;" class="col-xs-6">
                                        <label for="status"><h2>Trạng thái</h2></label>
                                        <%
                                            if (staff.getStatus().equals("1")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 25px; color: green; background-color: white;" type="text" class="form-control" id="first_name" value="Online" >
                                        <%
                                        } else if (staff.getStatus().equals("0")) {
                                        %>
                                        <input readonly="" style="height: 50px; font-size: 25px; color: red; background-color: white;" type="text" class="form-control" id="first_name" value="Offline" >
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
                            <form id="staffForm" class="form" action="ViewProfileController" method="GET" id="registrationForm">
                                <div class="row">
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="account"><h2>Họ và tên</h2></label>
                                        <input name="name" readonly="" style="margin-bottom: 5px; height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control c" value="<%= staff.getName()%>" >
                                        <p style="text-align: center; color: white; background-color: #db5246; width: 70%; margin: auto; border-radius: 10px;" ><%= name%></p>
                                    </div>
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="point"><h2>Ngày sinh</h2></label>
                                        <input name="dob" readonly="" style="margin-bottom: 5px; height: 50px; font-size: 25px; background-color: white;" type="date" class="form-control c"  min="<%= localDate.minusYears(80)%>" max="<%= localDate.minusYears(18)%>" value="<%= staff.getDateOfBirth()%>" >
                                        <p style="text-align: center; color: white; background-color: #db5246; width: 70%; margin: auto; border-radius: 10px;" ><%= dob %></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div style="margin-top: 80px;" class="col-md-6">
                                        <label for="password"><h2>Số điện thoại</h2></label>
                                        <input name="phone" readonly="" id="pass" style="margin-bottom: 5px; height: 50px; font-size: 25px; background-color: white;" type="text" class="form-control c" value="<%= staff.getPhone()%>" >
                                        <p style="text-align: center; color: white; background-color: #db5246; width: 70%; margin: auto; border-radius: 10px;" ><%= phone %></p>
                                    </div>
                                    <input name="action" type="hidden" value="">
                                    <input name="index" type="hidden" value="2">
                                </div>
                            </form> 
                        </div><!--/tab-pane-->
                    </div><!--/tab-pane-->
                </div>
                <div style="text-align: center;">
                    <button onclick="edit1('edit1')" class="changePass" id="edit1">Chỉnh sửa thông tin</button>
                    <button onclick="edit1('save1')" onmousedown="saveProfile('staffForm')" class="changePass" id="save1">Lưu thông tin</button>
                </div>
                <script>
                    function saveProfile(a) {
                        document.getElementById(a).submit();
                    }
                </script>
            </div>
        </div>  
    </body>
</html>
