<%-- 
    Document   : footer
    Created on : Sep 20, 2022, 3:56:45 PM
    Author     : PC
--%>

<%@page import="dto.PublisherDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link rel = "icon" href ="https://cdn-icons-png.flaticon.com/512/1903/1903162.png" type = "image/x-icon">
    </head>
    <body>
        <!-- FOOTER -->
        <footer id="footer">
            <!-- top footer -->
            <div class="section">
                <!-- container -->
                <div class="container">
                    <!-- row -->
                    <div class="row">
                        <div class="col-md-3 col-xs-6">
                            <div class="footer">
                                <h3 class="footer-title">Về chúng tôi</h3>
                                <p>Nhà sách trực tuyến Phương Nam</p>
                                <ul class="footer-links">
                                    <li><a href="#"><i class="fa fa-map-marker"></i>TP Hồ Chí Minh</a></li>
                                    <li><a href="#"><i class="fa fa-phone"></i>1900-6656</a></li>
                                    <li><a href="#"><i class="fa fa-envelope-o"></i>hotro@nspn.com</a></li>
                                </ul>
                            </div>
                        </div>
                        <%                            List<CategoryDTO> listCateFooter = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
                        %>
                        <div class="col-md-3 col-xs-6">
                            <div class="footer">
                                <h3 class="footer-title">Thể loại</h3>
                                <ul class="footer-links">
                                    <%
                                        for (CategoryDTO cateDTO : listCateFooter) {
                                    %>
                                    <form action="FilterCategoryController" method="GET">
                                        <input type="hidden" name="cateID" value="<%= cateDTO.getCategoryID()%>"/>
                                        <button class="hover" style="border: none; background: none;"><%= cateDTO.getName()%></button>
                                    </form>
                                    <%
                                        }
                                    %>
                                </ul>
                            </div>
                        </div>
                        <%
                            List<PublisherDTO> listPubFooter = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
                        %>
                        <div class="col-md-3 col-xs-6">
                            <div class="footer">
                                <h3 class="footer-title">Nhà xuất bản</h3>
                                <ul class="footer-links">
                                    <%
                                        for (PublisherDTO publisherDTO : listPubFooter) {
                                    %>
                                    <form action="FilterPublisherController" method="GET">
                                        <input type="hidden" name="pubID" value="<%= publisherDTO.getPublisherID()%>"/>
                                        <button class="hover" style="border: none; background: none;"><%= publisherDTO.getName()%></button>
                                    </form>
                                    <%
                                        }
                                    %>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-3 col-xs-6">
                            <div class="footer">
                                <h3 class="footer-title">Dịch vụ</h3>
                                <ul class="footer-links">
                                    <li><a href="ViewProfileController?action=Profile">Tài khoản</a></li>
                                    <li><a href="#">Giỏ hàng</a></li>
                                    <li><a href="#">Về chúng tôi</a></li>
                                    <li><a href="#">Liên hệ</a></li>
                                    <li><a href="#">Giúp đỡ</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!-- /row -->
                </div>
                <!-- /container -->
            </div>
            <!-- /top footer -->

            <!-- bottom footer -->
            <div id="bottom-footer" class="section">
                <div class="container">
                    <!-- row -->
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <ul class="footer-payments">
                                <li><a href="#"><i class="fa fa-cc-paypal"></i></a></li>
                                <li><a href="#"><i class="fa fa-cc-visa"></i></a></li>
                                <li><a href="#"><i class="fa fa-credit-card"></i></a></li>
                                <li><a href="#"><i class="fa fa-map-marker"></i></a></li>
                                <li><a href="#"><i class="fa fa-envelope-o"></i></a></li>
                                <li><a href="#"><i class="fa fa-phone"></i></a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /row -->
                </div>
                <!-- /container -->
            </div>
            <!-- /bottom footer -->
        </footer>
        <!-- /FOOTER -->
    </body>
</html>