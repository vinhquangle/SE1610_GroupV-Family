<%-- 
    Document   : createPromotion
    Created on : Oct 27, 2022, 4:18:11 PM
    Author     : vqphi
--%>

<%@page import="dto.CustomerDTO"%>
<%@page import="dao.CustomerDAO"%>
<%@page import="dto.PromotionDTO"%>
<%@page import="dao.PromotionDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Promotion</title>
    </head>
    <%
        CustomerDTO loginCustomerDTO = (CustomerDTO)session.getAttribute("LOGIN_USER");
        if(loginCustomerDTO!=null){
            String search = (String)request.getParameter("search");
            if(search==null){
                search= "";
            }
        }
    %>
    <body>
        <%
            PromotionDTO proDTO = new PromotionDTO();
            String msg = (String)request.getAttribute("CREATE_PROMOTION");
            String staffID = loginCustomerDTO.getCustomerID();
            String dateStart = request.getParameter("DateStart");
            String dateEnd = request.getParameter("DateEnd");
            String description = request.getParameter("Description");
            String condition = request.getParameter("Condition");
            String discount = request.getParameter("Discount");
            String status = request.getParameter("Status");
        %>
        <form action="CreatePromotionController" method="POST">
            staffID <input type="text" name="StaffID" value="<%=staffID%>"/>
            dateStart <input type="text" name="DateStart" vaue="<%=dateStart%>"/>
            dateEnd <input type="text" name="DateEnd" vaue="<%=dateEnd%>"/>
            description <input type="text" name="Description" vaue="<%=description%>"/>
            condition<input type="text" name="Condition" vaue="<%=condition%>"/>
            discount <input type="text" name="Discount" vaue="<%=discount%>"/>
            status <input type="text" name="Status" vaue="<%=status%>"/>
        </form>
        <%=msg%>
        <a href="crudPromotion.jsp">Create Promotion</a>
    </body>
</html>
