<%-- 
    Document   : response
    Created on : Oct 28, 2022, 12:02:10 AM
    Author     : ownhi
--%>

<%@page import="dto.BookResponseDTO"%>
<%@page import="java.util.List"%>
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
            
           String search = request.getParameter("search");
           if (search == null) {
               search = "";
           }
        %>
        <h1>Welcome to request page</h1>
        
        <form action="SearchResponseController">
            Search<input type="text" name="search" value="<%= search%>"/>
            <input type="submit" value="Search"/>
        </form>
        <%
            List<BookResponseDTO> listResponse = (List<BookResponseDTO>) request.getAttribute("LIST_RESPONSE");
            if (listResponse != null) {
                if (listResponse.size() > 0) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Response ID</th>
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
                        for ( BookResponseDTO responseDTO: listResponse) {
                %>    
            <form action="SearchResponseController">
                    <tr>
                    <td><%= ++count%></td>
                    <td>
                        <input type="number" name="responseID" value="<%= responseDTO.getResponseID()%>" readonly=""/>
                    </td>
                    <td>
                        <input type="number" name="requestID" value="<%= responseDTO.getRequest().getRequestID() %>" readonly=""/>
                    </td>                   
                    <td>
                        <input type="text" name="sName" value="<%= responseDTO.getStaff().getName()%>"/>
                    </td>
                    <td>
                        <input type="date" name="date" value="<%= responseDTO.getDate()%>"/>
                    </td>
                    <td><input type="number" name="status" value="<%= responseDTO.getStatus()%>"/></td>
                    <td><input type="number" name="delete" value="<%= responseDTO.getDelete()%>"/></td>                    
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
