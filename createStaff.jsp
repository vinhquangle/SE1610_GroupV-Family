<%-- 
    Document   : create
    Created on : Oct 14, 2022, 3:22:49 PM
    Author     : vungo
--%>
<%@page import="dto.StaffErrorDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.StaffDTO"%>

<!--
    Thêm link cho createStaff trên view của admin để create new staff
    <a href="RegisterStaffController?action=RegisterStaff"><i class="fa fa-user-plus" aria-hidden="true"></i>Đăng kí nhân viên</a>
-->
<!--Đây là code thô chưa có front end nhưng chức năng thì oke rồi á-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Staff</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
            StaffErrorDTO staffError = (StaffErrorDTO) request.getAttribute("STAFF_ERROR");
            if (staffError == null) {
                staffError = new StaffErrorDTO();
            }
            String msg= (String)request.getAttribute("REGISTER_STAFF");
            String staffID = request.getParameter("staffID");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String dateOfBirth = request.getParameter("dateOfBirth"); 
            if (staffID == null) {
                staffID = "";
            } 
            if (name == null) {
                name = "";
            } 
            if (phone == null) {
                phone = "";
            }
            if (dateOfBirth == null) {
                dateOfBirth = "";
            } 
        %>
        <form action="RegisterStaffController" method="POST">
            Mã Nhân Viên <input type="text" name="staffID" required="" value="<%=staffID%>"/><%=staffError.getStaffIDError()%></br>
            Họ và Tên Nhân Viên <input type="text" name="name" required=""value="<%=name%>"/><%=staffError.getNameError()%></br>
            Chức vụ <select name="role" required="">
            <option>Nhân Viên</option>
            <option>Vận Chuyển</option>
                </select></br>
            Số Điện Thoại <input type="text" name="phone" required=""value="<%=phone%>"/><%=staffError.getPhoneError()%></br>
            Ngày Sinh<input type="date" name="dateOfBirth" required=""value="<%=dateOfBirth%>"/><%=staffError.getDateOfBirthError()%></br>
            Mật khẩu <input type="password" name="password" required=""/><%=staffError.getPasswordError()%></br>
            Nhập lại mật khẩu <input type="password" name="confirm" required=""/><%=staffError.getConfirmError()%></br>
            <input type="submit" name="action" value="Register"/>
            <input type="reset" value="Reset">
        </form>
            <%=msg%>
            <a href="searchStaff.jsp">Search Staff</a>
    </body>
</html>
