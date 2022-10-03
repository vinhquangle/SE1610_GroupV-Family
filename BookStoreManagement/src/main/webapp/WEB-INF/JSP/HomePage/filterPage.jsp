<%-- 
    Document   : filterPage
    Created on : Sep 27, 2022, 4:48:34 PM
    Author     : Admin
--%>

<%@page import="dto.PublisherDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.CategoryDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- aside Widget -->
        <%                            List<CategoryDTO> listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
        %>
        <div class="aside">
            <h3 class="aside-title">Categories</h3>
            <div class="checkbox-filter">
                <%
                    for (CategoryDTO cateDTO : listCate) {
                %>
                <div class="input-checkbox">
                    <label for="category-6">
                        <form action="FilterCategoryController" method="POST">
                            <input type="hidden" name="cateID" value="<%= cateDTO.getCategoryID()%>"/>
                            <button class="hover" style="border: none; background: none;"><%= cateDTO.getName()%></button>
                        </form>
                    </label>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <%
            List<PublisherDTO> listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
        %>
        <div class="aside">
            <h3 class="aside-title">Publishers</h3>
            <div class="checkbox-filter">
                <%
                    for (PublisherDTO publisherDTO : listPub) {
                %>
                <div class="input-checkbox">                              
                    <label for="category-6">
                        <form action="FilterPublisherController" method="POST">
                            <input type="hidden" name="pubID" value="<%= publisherDTO.getPublisherID()%>"/>
                            <button class="hover" style="border: none; background: none;"><%= publisherDTO.getName()%></button>
                        </form>
                    </label>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="aside">
            <h3 class="aside-title">Price</h3>
            <div class="checkbox-filter">
                <div class="input-checkbox">                              
                    <label for="category-6">
                        <form action="FilterPriceController" method="POST">
                            <input type="hidden" value="100000" name="max"/>
                            <input type="hidden" value="0" name="min"/>
                            <button class="hover" style="border: none; background: none;" name="mess" value="0 - 100.000">0đ - 100.000đ</button>
                        </form>
                    </label>
                </div>
                <div class="input-checkbox">
                    <label for="category-6">
                        <form action="FilterPriceController" method="POST">
                            <input type="hidden" value="200000" name="max"/>
                            <input type="hidden" value="100000" name="min"/>
                            <button class="hover" style="border: none; background: none;" name="mess" value="100.000 - 200.000" >100.000đ - 200.000đ</button>
                        </form>
                    </label>
                </div>
                <div class="input-checkbox">
                    <label for="category-6">
                        <form action="FilterPriceController" method="POST">
                            <input type="hidden" value="1000000" name="max"/>
                            <input type="hidden" value="200000" name="min"/>
                            <button class="hover" style="border: none; background: none;" name="mess" value="200.000 - Above">200.000đ - Above</button>
                        </form>
                    </label>
                </div>
            </div>
        </div>
    </body>
</html>
