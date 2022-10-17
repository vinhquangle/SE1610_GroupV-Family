<%-- 
    Document   : searchCustomerAccount
    Created on : Oct 17, 2022, 8:33:57 AM
    Author     : vqphi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="dto.CustomerDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Account Customer</title>
    </head>
    <body>
        <%
        String searchCustomerAccount= request.getParameter("search");
        if(searchCustomerAccount==null){
            searchCustomerAccount="";
        }
        %> 
        <form action="SearchCustomerController">
            Search <input type="text" name="search" value="<%=searchCustomerAccount%>"/>
            <input type="submit" name="action" value="Search" />
        </form> 
        <%
            List<CustomerDTO> listCustomer= (List<CustomerDTO>)request.getAttribute("LIST_CUSTOMER");
            if(listCustomer!=null){
                if(listCustomer.size()>0){
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>CustomerID</th>
                    <th>Customer Name</th>
                    <th>Password</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Phone</th>
                    <th>Point</th>
                    <th>Status</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count=1;
                    for (CustomerDTO cus : listCustomer) { 
                %>
                <tr>
                    <td><%=count++%></td>
                    <td><input type="text" name="customerID" value="<%=cus.getCustomerID()%>" readonly=""/></td>
                    <td><input type="text" name="Name" value="<%=cus.getName()%>"/></td>
                    <td><%=cus.getPassword()%></td>
                    <td><input type="text" name="Email" value="<%=cus.getEmail()%>"/></td>
                    <td><input type="text" name="Address" value="<%=cus.getAddress()%>" /></td>
                    <td><input type="text" name="Phone" value="<%=cus.getPhone()%>" readonly=""/></td>
                    <td><input type = "text" name="Point" value="<%=cus.getPoint()%>"readonly=""/></td>
                    <td><input type="text" name="Status" value="<%=cus.getStatus()%>" readonly=""/></td>
                    <td><input type="text" name="Delete" value="<%=cus.getDelete()%>"/></td>

                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%     
                }
            }
        %>

    </body>
</html>
