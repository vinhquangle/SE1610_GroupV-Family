<%-- 
    Document   : crudPromotion
    Created on : Oct 26, 2022, 3:40:09 PM
    Author     : vungo
--%>
<%@page import="dto.StaffDTO"%>
<!--: -->
<%@page import="java.util.List"%>
<%@page import="dto.PromotionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion Page</title>
    </head>
    <body>
        <%
            StaffDTO loginStaff= (StaffDTO) session.getAttribute("LOGIN_STAFF");
            //xac thuc phan quyen
            if(loginStaff ==null ||  !loginStaff.getRole().equals("Staff")){
                response.sendRedirect("login.jsp");// nhớ sửa đường dẫn giống với file Thịnh gửi
                return;
            }
            String message= (String)request.getAttribute("MESSAGE_MODIFY");
            if(message==null){
                message="";
            }
            String searchP= request.getParameter("searchP");
            if(searchP==null){
                searchP="";
            }
        %> 
            <form action="SearchPromotionController">
            Search <input type="text" name="searchP" value="<%=searchP%>">
            <input type="submit" name="action" value="SearchP" /></br>
            <!--Dùng để update toàn bộ status của promotion thành 1 nếu 
            endDate của nó nhỏ hơn ngày hôm nay-->
            <input type="submit" name="action" value="UpdateStatus"/> 
            
            </form>
            
        <h1>List Promotion</h1>
        <%
            List<PromotionDTO> listPromotion=(List<PromotionDTO>)session.getAttribute("PROMOTION_LIST");
            if(listPromotion!=null && listPromotion.size()>0){
        %>
            <table border="1">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã giảm giá</th>
                    <th>Mã nhân viên(Người tạo)</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Mô tả</th>
                    <th>Điều kiện đơn hàng</th>
                    <th>% Giảm giá</th>
                    <th>Tình trạng mã giảm giá</th>
                    <th>Cập nhật</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count=1;
                    for (PromotionDTO pro : listPromotion) {
                %>
                
                <tr>
                    <td><%=count++%></td>
                    <td><input type="text" name="promoID" value="<%=pro.getPromotionID()%>" readonly=""></td>
                    <td><input type="text" name="staffS" value="<%=pro.getStaffID().getStaffID()%>" readonly=""></td>
                    <td><input type="date" name="startpromo" value="<%=pro.getDateStart()%>"></td>
                    <td><input type="date" name="endpromo" value="<%=pro.getDateEnd()%>"></td>
                    <td><input type="text" name="des" value="<%=pro.getDescription()%>"></td>
                    <td><input type="number" name="condipromo" value="<%=pro.getCondition()%>"></td>
                    <td><input type="number" name="discount" value="<%=pro.getDiscount()%>"></td>
                    <td><input type="text" name="statuspromo" value="<%=pro.getStatus()%>"readonly=""></td>
           
                </tr>
                
                <%            
                        }
                %>
                
            </tbody>
        </table>
        <%        
            }
        %>
        <%=message%>

    </body>
</html>
