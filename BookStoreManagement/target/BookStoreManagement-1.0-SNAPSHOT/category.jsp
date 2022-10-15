<%-- 
    Document   : category
    Created on : Oct 15, 2022, 5:08:33 PM
    Author     : ownhi
--%>

<%@page import="dto.CategoryErrorDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Management</title>
    </head>
    <body>
        <% 
            CategoryErrorDTO cateError= (CategoryErrorDTO) request.getAttribute("CATE_ERROR");
            if(cateError== null){
                cateError= new CategoryErrorDTO();
            }
            String message = (String )session.getAttribute("MESSAGE");
            if(message == null){
                message = "";
            }
         %>
         <form action="CreateCategoryController" method="POST">
             Category ID <input type="text" name="cateID"/><br>
            Name <input type="text" name="name"/><br>
            <input type="hidden" name="status" value="1" readonly=""/><br>
            <input type="submit" name="action" value="Add" /> 
         </form>
         <%= cateError.getCategoryIDError()%></br>
         
         <%= cateError.getNameError()%>
             <%= message%>
    </body>
</html>
