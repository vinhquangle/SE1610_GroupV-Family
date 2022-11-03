/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BRequestDetailDTO;
import dto.BookDTO;
import dto.BookRequestDTO;
import dto.CategoryDTO;
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
public class BRequestDetailDAO {

    private static final String INSERT_REQUEST_DETAIL = "INSERT INTO [tblBRequestDetail](requestID,ISBN,publisherID,categoryID,Name,[Author-name],Quantity,[Status],[Delete])\n"
            + "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String LOAD_DETAIL_BY_REQUEST_ID = "SELECT d.requestDetailID, d.requestID, d.ISBN, d.publisherID, d.categoryID, d.Name, d.[Author-name], d.Quantity, d.[Status], d.[Delete],\n"
            + "b.reviewID, b.Price, b.[Description], b.[Image], b.Quantity AS 'bookQuantity', b.[Status] AS 'bStatus',\n"
            + "c.Name AS 'category', c.[Status] AS 'cStatus',\n"
            + "p.Name AS 'publisher', p.[Status] AS 'pStatus',\n"
            + "v.Rate, v.Times, v.[Status] AS 'vStatus',\n"
            + "r.staffID, r.[Date], r.[Status] AS 'rStatus', r.[Delete] AS 'rDelete', \n"
            + "s.Name AS 'staffName', s.[Role], s.Phone, s.[Date-of-birth] AS 'dob', s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM ((((((tblBRequestDetail d \n"
            + "LEFT JOIN tblBook b ON d.ISBN = b.ISBN)\n"
            + "LEFT JOIN tblCategory c ON b.categoryID = c.categoryID)\n"
            + "LEFT JOIN tblPublisher p ON b.publisherID = p.publisherID)\n"
            + "LEFT JOIN tblReview v ON b.reviewID = v.reviewID)\n"
            + "LEFT JOIN tblBookRequest r ON d.requestID = r.requestID)\n"
            + "LEFT JOIN tblStaff s ON r.staffID = s.staffID)\n"
            + "WHERE d.requestID LIKE ?";
    private static final String QUANTITY_CHECK = "SELECT Quantity FROM [tblBRequestDetail] WHERE ISBN LIKE ? AND requestID LIKE ?";
    private static final String UPDATE_STATUS = "UPDATE [tblBRequestDetail] SET [Status] = ? WHERE ISBN LIKE ? AND requestID LIKE ?";
    private static final String CHECK_STATUS = "SELECT * FROM [tblBRequestDetail] WHERE [Status] LIKE ? AND requestID LIKE ?";

    public boolean checkStatus(String st, String requestID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(CHECK_STATUS);
            ptm.setString(1, st);
            ptm.setString(2, requestID);
            rs = ptm.executeQuery();
             while (rs.next()) {
                 check = true;
             }
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

    public boolean insertRequestDetail(int requestID, BookDTO book, String status, String delete) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_REQUEST_DETAIL);
            ptm.setInt(1, requestID);
            ptm.setString(2, book.getIsbn());
            ptm.setString(3, book.getPublisher().getPublisherID());
            ptm.setString(4, book.getCategory().getCategoryID());
            ptm.setString(5, book.getName());
            ptm.setString(6, book.getAuthorName());
            ptm.setInt(7, book.getQuantity());
            ptm.setString(8, status);
            ptm.setString(9, delete);
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

    public List<BRequestDetailDTO> loadDetailByRequest(String requestId) throws SQLException {
        List<BRequestDetailDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_DETAIL_BY_REQUEST_ID);
                ptm.setString(1, requestId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String requestDetailID = rs.getString("requestDetailID");
                    String requestID = rs.getString("requestID");
                    String isbn = rs.getString("ISBN");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("Name");
                    String author = rs.getString("Author-name");
                    int quantity = rs.getInt("Quantity");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("Rate");
                    int times = rs.getInt("Times");
                    String vStatus = rs.getString("vStatus");
                    double price = rs.getDouble("Price");
                    String des = rs.getString("Description");
                    String img = rs.getString("Image");
                    int bookQuantity = rs.getInt("bookQuantity");
                    String bStatus = rs.getString("bStatus");
                    String category = rs.getString("category");
                    String cStatus = rs.getString("cStatus");
                    String publisher = rs.getString("publisher");
                    String pStatus = rs.getString("pStatus");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String rStatus = rs.getString("rStatus");
                    String rDelete = rs.getString("rDelete");
                    String staffName = rs.getString("staffName");
                    String password = "***";
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dob = rs.getString("dob");
                    String sStatus = rs.getString("sStatus");
                    String sDelete = rs.getString("sDelete");
                    list.add(new BRequestDetailDTO(requestDetailID, new BookRequestDTO(requestID, new StaffDTO(staffID, staffName, password, role, phone, dob, sStatus, sDelete), date, rStatus, rDelete), new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pStatus), new CategoryDTO(categoryID, category, cStatus), new ReviewDTO(reviewID, rate, times, vStatus), name, author, price, des, img, bookQuantity, bStatus), quantity, status, delete));
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
        return list;
    }

    public int quantityCheck(String isbn, String requestId) throws SQLException {
        int quantity = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(QUANTITY_CHECK);
                ptm.setString(1, isbn);
                ptm.setString(2, requestId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    quantity = rs.getInt("Quantity");
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
        return quantity;
    }

    public boolean updateStatus(String st, String isbn, String requestId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, st);
                ptm.setString(2, isbn);
                ptm.setString(3, requestId);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
}
