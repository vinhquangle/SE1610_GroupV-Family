<%-- 
    Document   : product
    Created on : Sep 29, 2022, 6:43:39 PM
    Author     : PC
--%>

<%@page import="dto.PublisherDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.BookErrorDTO"%>
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
    <style>
        /* Modal styles */
        .modal .modal-dialog {
            max-width: 400px;
        }
        .modal .modal-header, .modal .modal-body, .modal .modal-footer {
            padding: 20px 30px;
        }
        .modal .modal-content {
            border-radius: 3px;
        }
        .modal .modal-footer {
            background: #ecf0f1;
            border-radius: 0 0 3px 3px;
        }
        .modal .modal-title {
            display: inline-block;
        }
        .modal .form-control {
            border-radius: 2px;
            box-shadow: none;
            border-color: #dddddd;
        }
        .modal textarea.form-control {
            resize: vertical;
        }
        .modal .btn {
            border-radius: 2px;
            min-width: 100px;
        }	
        .modal form label {
            font-weight: normal;
        }
    </style>
    <body>
        <%
            BookErrorDTO bookError = (BookErrorDTO) request.getAttribute("BOOK_ERROR");
            if (bookError == null) {
                 bookError = new BookErrorDTO();
            }
            List<CategoryDTO> listCate = (List<CategoryDTO>) session.getAttribute("LIST_CATE");
            List<PublisherDTO> listPub = (List<PublisherDTO>) session.getAttribute("LIST_PUB");
        %>
        <!-- The Modal -->
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Add Book</h4>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">
                        <form action="AddBookManageController">

                            <div class="form-group">
                                <label>ISBN</label>
                                <input name="isbn" type="text" class="form-control" required>
                            </div>
                            <%= bookError.getIsbnError()%></br>
                            <div class="form-group">
                                <label>Book Title</label>
                                <input name="name" type="text" class="form-control" required>
                            </div>
                            <%= bookError.getNameError()%></br>
                            <%
                                for (PublisherDTO publisherDTO : listPub) {
                            %>
                            <div class="form-group">
                                <label>Publisher</label>
                                <select name="publisher" class="form-select" aria-label="Default select example">                                    
                                        <option value="<%= publisherDTO.getPublisherID()%>"><%= publisherDTO.getName()%></option>                                    
                                </select>
                            </div>
                            <%
                                }
                            %>
                            
                            <%
                                for (CategoryDTO cateDTO : listCate) {
                            %>
                            <div class="form-group">
                                <label>Category</label>
                                <select name="category" class="form-select" aria-label="Default select example">
                                    <option value="<%= cateDTO.getCategoryID()%>"><%= cateDTO.getName()%></option>
                                </select>
                            </div>
                            <%
                                }
                            %>
                            <div class="form-group">
                                <label>Author</label>
                                <textarea name="author-name" class="form-control" required></textarea>
                            </div>
                            <%= bookError.getAuthorNameError()%></br>
                            <div class="form-group">
                                <label>Price</label>
                                <input name="price" type="number" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label>Image</label>
                                <input name="image" type="text" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label>Quantity</label>
                                <input name="quantity" type="number" class="form-control" required>
                            </div>


                            <div class="modal-footer">
                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                <input type="submit" name="action" class="btn btn-success" value="Add">
                            </div>
                        </form>
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
