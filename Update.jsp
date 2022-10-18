<%-- 
    Document   : Update
    Created on : Oct 12, 2020, 8:29:35 PM
    Author     : trinh
--%>

<%@page import="dto.StaffDTO"%>
<%@page import="dto.CustomerDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Account</title>
    </head>
    <body>
        <form action="UpdateController" method="get">
            <table>
                <tr>
                    <td>staffID</td>
                    <td>
                        <input type="text" name="id" readonly>
                    </td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td>
                        <input type="text" name="name" required="">
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <input type="text" name="password" required="">
                    </td>
                </tr>
                <tr>
                    <td>Dob</td>
                    <td><input type="text" name="dob" required=""></td>
                </tr>
                <tr>
                    <td><button type="submit">Update</button></td>
                </tr>
            </table>
        </form>
    </body>
</html>

