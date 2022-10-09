<%-- 
    Document   : product
    Created on : Sep 29, 2022, 6:43:39 PM
    Author     : PC
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Book List</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <!-- The Modal -->
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Modal Heading</h4>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">

                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">               
                        <button style="display: none;" type="button" class="btn btn-danger"></button>
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Add</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="product-status mg-tb-15">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="product-status-wrap">
                            <h4 style="display: inline-block;">Book List</h4>
                            <div style="display: inline-block; float: right;" class="add-product">
                                <button style="border: none; color: #edeef1; background-color: #009933;" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                                    <img style=" height: 20px; width: 20px;" src="IMG/plus.png"/>
                                    <b>Add new book</b>
                                </button>
                            </div>
                            <table>
                                <tr>
                                    <th>ISBN</th>
                                    <th>Book Title</th>
                                    <th>Author</th>
                                    <th>Publisher</th>
                                    <th>Category</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>IMG</th>
                                    <th>Setting</th>
                                </tr>
                                <%
                                    List<BookDTO> list = new ArrayList<>();
                                    try {
                                        list = (List<BookDTO>) session.getAttribute("LIST_BOOK");
                                    } catch (Exception e) {
                                    } finally {
                                        Locale localeVN = new Locale("vi", "VN");
                                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                        for (int i = 0; i < list.size(); i++) {
                                            String price = currencyVN.format(list.get(i).getPrice());
                                %>
                                <tr>
                                    <td><%= list.get(i).getIsbn()%></td>                                   
                                    <td><%= list.get(i).getName()%></td>
                                    <td><%= list.get(i).getAuthorName()%></td>
                                    <td><%= list.get(i).getPublisher().getName()%></td>
                                    <td><%= list.get(i).getCategory().getName()%></td>
                                    <td><%= list.get(i).getQuantity()%></td>
                                    <td><%= price%></td>
                                    <td><img src="<%= list.get(i).getImg()%>" alt="" /></td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </table>
                            <div class="custom-pagination">
                                <nav aria-label="Page navigation example">
                                    <ul class="pagination">
                                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
    </body>

</html>
