<%-- 
    Document   : searchStaff
    Created on : Oct 15, 2022, 4:59:47 PM
    Author     : vungo
--%>

<%@page import="java.util.List"%>
<%@page import="dto.StaffDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<<<<<<< HEAD
=======
<!--Đây là code thô chưa có front end nhưng chức năng thì oke rồi á-->
>>>>>>> origin/Ngọc-Thy-Branch
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Staff Page</title>
    </head>
    <body>
<<<<<<< HEAD
        <%
            String searchStaff = request.getParameter("search");
            if (searchStaff == null) {
                searchStaff = "";
            }
        %> 
        <form action="SearchController">
            Search <input type="text" name="searchStaff" value="<%=searchStaff%>">
            <input type="submit" name="action" value="Search" />
        </form> 
        <%
            List<StaffDTO> listStaff = (List<StaffDTO>) session.getAttribute("LIST_STAFF");
            if (listStaff != null && listStaff.size() > 0) {
        %>
        <table border="1">
=======
        <!--Bảng danh sách nhân viên-->
            <%
            StaffDTO loginStaff= (StaffDTO) session.getAttribute("LOGIN_STAFF");
            //xac thuc phan quyen
            if(loginStaff ==null ||  !loginStaff.getRole().equals("Admin")){
                response.sendRedirect("login.jsp");// nhớ sửa đường dẫn giống với file Thịnh gửi
                return;
            }
            String searchStaff= request.getParameter("search");
            if(searchStaff==null){
                searchStaff="";
            }
        %> 
            <form action="SearchController">
            Search <input type="text" name="searchStaff" value="<%=searchStaff%>">
            <input type="submit" name="action" value="Search" />
        </form> 
            <%
                List<StaffDTO> listStaff= (List<StaffDTO>)session.getAttribute("LIST_STAFF");
                if(listStaff!=null && listStaff.size()>0){
            %>
                <table border="1">
>>>>>>> origin/Ngọc-Thy-Branch
            <thead>
                <tr>
                    <th>No</th>
                    <th>Staff ID</th>
                    <th>Staff Name</th>
                    <th>Staff Password</th>
                    <th>Staff Role</th>
                    <th>Staff Phone</th>
                    <th>Staff Date of Birth</th>
                    <th>Staff Status</th>
                    <th>Staff Delete Status</th>
                    <th>Update</th>
<<<<<<< HEAD

=======
                    <th>Delete</th>
>>>>>>> origin/Ngọc-Thy-Branch
                </tr>
            </thead>
            <tbody>
                <%
<<<<<<< HEAD
                    int count = 1;
                    for (StaffDTO staff : listStaff) {
                %>
            <form action="UpdateAccountController">
=======
                    int count=1;
                    for (StaffDTO staff : listStaff) { 
                %>
>>>>>>> origin/Ngọc-Thy-Branch
                <tr>
                    <td><%=count++%></td>
                    <td><input type="text" name="staffID" value="<%=staff.getStaffID()%>" readonly=""/></td>
                    <td><input type="text" name="name" value="<%=staff.getName()%>" /></td>
                    <td><input type="text" name="password" value="<%=staff.getPassword()%>" readonly=""/></td>
                    <td><input type="text" name="role" value="<%=staff.getRole()%>" /></td>
                    <td><input type="text" name="phone" value="<%=staff.getPhone()%>" /></td>
                    <td><input type="text" name="dateOfBirth" value="<%=staff.getDateOfBirth()%>" readonly=""/></td>
<<<<<<< HEAD
                    <td><input type="text" name="status" value="<%=staff.getStatus()%>" readonly=""/></td>
                    <td><input type="text" name="delete" value="<%=staff.getDelete()%>"/></td>
                    <td>
                        <input type="submit" name="action" value="Update"/>
                    </td>
=======
                    <td><input type="text" value="<%=staff.getStatus()%>" readonly=""/></td>
                    <td><input type="text" value="<%=staff.getDelete()%>"/></td>
                    <!--Udapte-->
                    <td>
                            <input type="submit" name="action" value="Update"/>
                            <input type="hidden" name="searchStaff" value="<%=searchStaff%>"/>
                    </td>
                    <td>
                            <a href="DeleteController?action=Delete&userID=<%=staff.getStaffID()%>&search=<%=searchStaff%>">Delete</a> 
                            
                        </td>
                        
>>>>>>> origin/Ngọc-Thy-Branch
                </tr>
                <%
                    }
                %>
            </tbody>
<<<<<<< HEAD
                       </form>
        </table>
        <%
            }
        %>

    </body>
</html>
=======
        </table>
            <%     
                }
            %>
            
    </body>
</html>
>>>>>>> origin/Ngọc-Thy-Branch
