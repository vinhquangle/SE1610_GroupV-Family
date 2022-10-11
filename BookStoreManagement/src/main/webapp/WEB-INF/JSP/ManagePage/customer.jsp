<%-- 
    Document   : customer
    Created on : Sep 29, 2022, 6:25:14 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Customer</title>
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
        <div class="product-status mg-tb-15" style="margin-top: 50px; height: 405px;">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="product-status-wrap">
                            <h4>Manage Customer Account</h4>
                            <div style="display: inline-block; float: right;" class="add-product">
                                <button style="border: none; color: #edeef1; background-color: #009933;" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                                    <img style=" height: 20px; width: 20px;" src="IMG/plus.png"/>
                                    <b>Add new customer account</b>
                                </button>
                            </div>
                            <table>
                                <tr>
                                    <th>customerID</th>
                                    <th>Name</th>
                                    <th>Password</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                    <th>Point</th>
                                    <th>Status</th>
                                    <th>Setting</th>
                                </tr>
                                <tr>
                                    <td>123</td>                                   
                                    <td>Jewelery Title 1</td>
                                    <td>Name</td>
                                    <td>Name</td>
                                    <td>C1</td>
                                    <td>100</td>
                                    <td>$15</td>
                                    <td>
                                        <button class="pd-setting">Online</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>123</td>                                   
                                    <td>Jewelery Title 1</td>
                                    <td>Name</td>
                                    <td>Name</td>
                                    <td>C1</td>
                                    <td>100</td>
                                    <td>$15</td>
                                    <td>
                                        <button class="pd-setting">Offline</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>123</td>                                   
                                    <td>Jewelery Title 1</td>
                                    <td>Name</td>
                                    <td>Name</td>
                                    <td>C1</td>
                                    <td>100</td>
                                    <td>$15</td>
                                    <td>
                                        <button class="pd-setting">Online</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>

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
