/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import dto.CategoryDTO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.PromotionDTO;
import dto.PublisherDTO;
import dto.ReviewDTO;
import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class OrderDetailDAO {

    private static final String LOAD_ORDER_DETAIL_BY_ORDER_ID = "SELECT d.detailID, d.orderID, d.ISBN, d.Price, d.Quantity, d.Total, d.[status],\n"
            + "b.Name AS 'bookName', b.publisherID, b.categoryID, b.reviewID, b.[Author-name], b.Price AS 'bookPrice', b.[Image], b.Quantity AS 'bookQuantity', b.[Description], b.[status] AS 'bookStatus',\n"
            + "ct.Name AS 'cateName', ct.[status] AS 'cateStatus',\n"
            + "pu.Name AS 'pubName', pu.[status] AS 'puStatus',\n"
            + "r.Rate, r.Times, r.[status] AS 'reviewStatus',\n"
            + "o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total AS 'orderTotal', o.[Description] AS 'orderDes', o.[status] AS 'orderStatus', o.[Delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[Delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[Delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffIDP', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNameP', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[Delete] AS 'staffDeleteP'\n"
            + "FROM ((((((((tblOrderDetail d \n"
            + "LEFT JOIN tblBook b ON d.ISBN LIKE b.ISBN )\n"
            + "LEFT JOIN tblCategory ct ON b.categoryID LIKE ct.categoryID)\n"
            + "LEFT JOIN tblPublisher pu ON b.publisherID LIKE pu.publisherID)\n"
            + "LEFT JOIN tblReview r ON b.reviewID LIKE r.reviewID)\n"
            + "LEFT JOIN tblOrder o ON d.orderID LIKE o.orderID)\n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID\n"
            + "WHERE d.orderID LIKE ?";
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO [tblOrderDetail](orderID,ISBN,Name,publisherID,categoryID,[Author-name],Price,Quantity,Total,[Status])\n"
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";

    public List<OrderDetailDTO> loadAllOrderDetailByOrderID(String orderId) throws SQLException {
        List<OrderDetailDTO> listOrderDetail = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_DETAIL_BY_ORDER_ID);
                ptm.setString(1, orderId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String detailID = rs.getString("detailID");
                    String orderID = rs.getString("orderID");
                    String isbn = rs.getString("ISBN");
                    double price = rs.getDouble("Price");
                    int quantity = rs.getInt("Quantity");
                    double total = rs.getDouble("Total");
                    String status = rs.getString("status");
                    String bookName = rs.getString("bookName");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String reviewID = rs.getString("reviewID");
                    String author = rs.getString("Author-name");
                    double bookPrice = rs.getDouble("bookPrice");
                    String img = rs.getString("Image");
                    int bookQuantity = rs.getInt("bookQuantity");
                    String des = rs.getString("Description");
                    String bookStatus = rs.getString("bookStatus");
                    String cateName = rs.getString("cateName");
                    String cateStatus = rs.getString("cateStatus");
                    String pubName = rs.getString("pubName");
                    String puStatus = rs.getString("puStatus");
                    double rate = rs.getDouble("Rate");
                    int times = rs.getInt("Times");
                    String reviewStatus = rs.getString("reviewStatus");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String date = rs.getString("Date");
                    double subtotal = rs.getDouble("Subtotal");
                    double orderDiscount = rs.getDouble("orderDiscount");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double orderTotal = rs.getDouble("orderTotal");
                    String orderDes = rs.getString("orderDes");
                    String orderStatus = rs.getString("orderStatus");
                    String delete = rs.getString("Delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String addr = rs.getString("Address");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffIDP = rs.getString("staffIDP");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double condition = rs.getDouble("Condition");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNameP = rs.getString("staffNameP");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrderDetail.add(new OrderDetailDTO(detailID,
                            new OrderDTO(orderID,
                                    new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                                    new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                                    new PromotionDTO(promotionID,
                                            new StaffDTO(staffIDP, staffNameP, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), addr, subtotal, orderDiscount, deliCost, date, orderTotal, orderDes, orderStatus, delete),
                            new BookDTO(isbn,
                                    new PublisherDTO(publisherID, pubName, puStatus),
                                    new CategoryDTO(categoryID, cateName, cateStatus),
                                    new ReviewDTO(reviewID, rate, times, reviewStatus), bookName, author, bookPrice, des, img, bookQuantity, bookStatus), price, quantity, total, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrderDetail;
    }

    public boolean insertOrderDetail(int orderID, BookDTO book, double total, String status) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_ORDER_DETAIL);
            ptm.setInt(1, orderID);
            ptm.setString(2, book.getIsbn());
            ptm.setString(3, book.getName());
            ptm.setString(4, book.getPublisher().getPublisherID());
            ptm.setString(5, book.getCategory().getCategoryID());
            ptm.setString(6, book.getAuthorName());
            ptm.setDouble(7, book.getPrice());
            ptm.setInt(8, book.getQuantity());
            ptm.setDouble(9, total);
            ptm.setString(10, status);
            check = ptm.executeUpdate() > 0 ? true : false; 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                ptm.close();
            }
        }
        return check;
    }
}
