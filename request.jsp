<%-- 
    Document   : customer
    Created on : Sep 29, 2022, 6:25:14 PM
    Author     : PC
--%>

<%@page import="java.util.List"%>
<%@page import="dto.BookRequestDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Request Page</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <%
            
           String search = request.getParameter("searchRequest");
           if (search == null) {
               search = "";
           }
        %>
        <h1>Welcome to request page</h1>
        
        <form action="SearchRequestController">
            Search<input type="text" name="searchRequest" value="<%= search%>"/>
            <input type="submit" value="Search"/>
        </form>
        <%
            List<BookRequestDTO> listRequest = (List<BookRequestDTO>) request.getAttribute("LIST_REQUEST");
            if (listRequest != null) {
                if (listRequest.size() > 0) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Request ID</th>
                    <th>Staff Name</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%
                        int count = 0;
                        for (BookRequestDTO requestDTO : listRequest) {
                %>    
            <form action="SearchRequestController">
                <tr>
                    <td><%= ++count%></td>
                    <td>
                        <input type="number" name="requestID" value="<%= requestDTO.getRequestID()%>" readonly=""/>
                    </td>
                    <td>
                        <input type="text" name="sName" value="<%= requestDTO.getStaff().getName()%>"/>
                    </td>
                    <td>
                        <input type="date" name="date" value="<%= requestDTO.getDate()%>"/>
                    </td>
                    <td><input type="number" name="status" value="<%= requestDTO.getStatus()%>"/></td>
                    <td><input type="number" name="delete" value="<%= requestDTO.getDelete()%>"/></td>                    
                    <!--Update-->
                    <td>
                        
                    </td>
                </tr>
            </form>
            <%
        }
            %>

        </tbody>
    </table>
    <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
    %>
    <%= error%>
    <%
            }
        }
    %>
</body>

</html>
