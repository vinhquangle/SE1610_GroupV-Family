<%-- 
    Document   : publisher
    Created on : Oct 12, 2022, 9:10:02 PM
    Author     : PC
--%>

<%@page import="dto.PublisherErrorDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Publisher</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <h1> Create Publisher </h1>
        <%
            PublisherErrorDTO pubError = (PublisherErrorDTO) request.getAttribute("PUB_ERROR");
            if (pubError == null) {
                pubError = new PublisherErrorDTO();
            }
            String message = (String )session.getAttribute("MESSAGE");
            if(message == null){
                message = "";
            }
        %>
        <form action="CreatePublisherController" method="POST">
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
                            <div class="information">
                                Publisher ID<input type="text" name="publisherID" required=""  style="margin-left: 35px;" placeholder="Enter publisher ID"/></br>
                                <%= pubError.getPublisherIDError()%></br>
                                Publisher name<input type="text" name="name" required="" placeholder="Enter Publisher name"/></br>
                                <%= pubError.getNameError()%></br>               
                                <input type="hidden" name="status" value="true" style="margin-left: 49px;" readonly=""/></br>
                            </div>                                                  
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">               
                        <button style="display: none;" type="button" class="btn btn-danger"></button>
                        <button type="submit" name="action" class="btn btn-primary">Create</button>
                        <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Reset</button>                       
                    </div>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        </form>

        <%= message%>
    </body>

</html>
