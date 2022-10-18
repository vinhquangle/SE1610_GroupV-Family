<%-- 
    Document   : searchStaff
    Created on : Oct 15, 2022, 4:59:47 PM
    Author     : vungo
--%>

<%@page import="java.util.List"%>
<%@page import="dto.StaffDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Staff Page</title>
    </head>
    <body>
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

                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    for (StaffDTO staff : listStaff) {
                %>
            <form action="UpdateAccountController">
                <tr>
                    <td><%=count++%></td>
                    <td><input type="text" name="staffID" value="<%=staff.getStaffID()%>" readonly=""/></td>
                    <td><input type="text" name="name" value="<%=staff.getName()%>" /></td>
                    <td><input type="text" name="password" value="<%=staff.getPassword()%>" readonly=""/></td>
                    <td><input type="text" name="role" value="<%=staff.getRole()%>" /></td>
                    <td><input type="text" name="phone" value="<%=staff.getPhone()%>" /></td>
                    <td><input type="text" name="dateOfBirth" value="<%=staff.getDateOfBirth()%>" readonly=""/></td>
                    <td><input type="text" name="status" value="<%=staff.getStatus()%>" readonly=""/></td>
                    <td><input type="text" name="delete" value="<%=staff.getDelete()%>"/></td>
                    <td>
                        <input type="submit" name="action" value="Update"/>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
                       </form>
        </table>
        <%
            }
        %>

    </body>
</html>