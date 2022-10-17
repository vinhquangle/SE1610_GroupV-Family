<%-- 
    Document   : promotion
    Created on : Oct 17, 2022, 11:14:16 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Promotion</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <style>
        table tr th{
            width: 500px;
        }
    </style>
    <body>

        <%@include file="header.jsp" %>
        <div class="product-status mg-tb-15" style="margin-top: 50px;">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="product-status-wrap">
                            <h4>Manage Promotion</h4>

                            <table>
                                <tr>
                                    <th>promotionID</th>
                                    <th>staffID</th>
                                    <th>Start-Date</th>
                                    <th>End-Date</th>
                                    <th>Description</th>
                                    <th>Condition</th>
                                    <th>Discount</th>
                                    <th>Status</th>
                                    <th>Setting</th>
                                </tr>
                                <tr>
                                    <td>PR1</td>                                   
                                    <td>S1</td>
                                    <td>17-10-2022</td>
                                    <td>31-10-2022</td>
                                    <td>Giảm 15.000đ cho hóa đơn có trị giá từ 500.000đ</td>
                                    <td>Tổng hóa đơn trị giá 500.000đ</td>
                                    <td>Giảm 15.000đ</td>
                                    <td>
                                        <button class="pd-setting">Online</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>PR2</td>                                   
                                    <td>S2</td>
                                    <td>17-10-2022</td>
                                    <td>25-10-2022</td>
                                    <td>Lượt sử dụng có hạn. Nhanh tay kẻo lỡ bạn nhé. Giảm 10% cho hóa đơn từ 3 cuốn sách (Tối đa 100.000đ)</td>
                                    <td>Hóa đơn có từ 3 cuốn sách trở lên</td>
                                    <td>Giảm 10%</td>
                                    <td>
                                        <button class="pd-setting">Online</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>PR3</td>                                   
                                    <td>S3</td>
                                    <td>17-10-2022</td>
                                    <td>31-11-2022</td>                                        
                                    <td>Lượt sử dụng có hạn. Nhanh tay kẻo lỡ bạn nhé. Giảm 5% khi thanh toán bằng PayPal (Tối đa 100.000đ)</td>
                                    <td>Thanh toán bằng PayPal</td>
                                    <td>Giảm 5%</td>
                                    <td>
                                        <button class="pd-setting">Online</button>
                                    </td>
                                    <td>
                                        <button data-toggle="tooltip" title="Edit" class="pd-setting-ed"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                        <button data-toggle="tooltip" title="Trash" class="pd-setting-ed"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>PR4</td>                                   
                                    <td>S4</td>
                                    <td>17-10-2022</td>
                                    <td>31-11-2022</td>
                                    <td>Lượt sử dụng có hạn. Nhanh tay kẻo lỡ bạn nhé. Giảm 10% khi thanh toán bằng Momo (Tối đa 150.000đ)</td>
                                    <td>Thanh toán bằng Momo</td>
                                    <td>Giảm 10%</td>
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
    </div>
    <%@include file="footer.jsp" %>
</body>

</html>
