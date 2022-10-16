<%-- 
    Document   : customer
    Created on : Oct 16, 2022, 6:22:03 PM
    Author     : ownhi
--%>

<%@page import="dto.CustomerErrorDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <h1>Create new user:</h1>
        <% 
            CustomerErrorDTO cusError= (CustomerErrorDTO)request.getAttribute("CUS_ERROR");
            if(cusError== null){
                cusError= new CustomerErrorDTO();
            }
            String message = (String )session.getAttribute("MESSAGE");
            if(message == null){
                message = "";
            }
         %>
         <form action="AddCustomerController">
             Cus ID<input type="text" name="cusID"/></br>
            <%= cusError.getCustomerIDError() %>
            Full Name<input type="text" name="name"/> </br>
                        <%= cusError.getNameError() %>
            Password<input type="text" name="password" /></br>
                        <%= cusError.getPasswordError()%>
            Confirm<input type="password" name="confirm" /></br>
                        <%= cusError.getConfirmError() %>
            Address<input type="text" name="address" ></br>
                        <%= cusError.getAddressError()%>
            Phone<input type="text" name="phone"/></br>
                        <%= cusError.getPhoneError()%>
            Email<input type="text" name="email" /></br>      
                        <%= cusError.getEmailError()%>
            <input type="submit" name="action" value="Add"/>
            <input type="reset" value="Reset" />
         </form>
         <%= message %>
    </body>
</html>
