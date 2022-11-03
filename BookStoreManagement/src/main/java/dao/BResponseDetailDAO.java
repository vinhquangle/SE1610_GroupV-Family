/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BResponseDetailDTO;
import dto.BookDTO;
import dto.BookRequestDTO;
import dto.BookResponseDTO;
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
public class BResponseDetailDAO {

    private static final String LOAD_DETAIL_BY_RESPONSE_ID = "SELECT rpD.responseDetailID, rpD.responseID, rpD.ISBN, rpD.publisherID, rpD.categoryID, rpD.Name, rpD.[Author-name], rpD.Quantity, rpD.Price, rpD.[Status], rpD.[Delete],\n"
            + "rp.requestID, rp.staffID, rp.[Date] AS 'rpDate', rp.[Status] AS 'rpStatus', rp.[Delete] AS 'rpDelete',\n"
            + "st.Name AS 'sName', st.[Role], st.[Phone], st.[Date-of-birth] AS 'dob', st.[Status] AS 'sStatus', st.[Delete] AS 'sDelete',\n"
            + "b.reviewID, b.Price AS 'bookPrice', b.[Image], b.Quantity AS 'bookQuantity', b.[Description], b.[status] AS 'bookStatus',\n"
            + "p.Name AS 'pubName', p.[status] AS 'pubStatus',\n"
            + "c.Name AS 'cateName', c.[status] AS 'cateStatus',\n"
            + "r.Rate, r.Times, r.[status] AS 'reviewStatus',\n"
            + "rq.staffID AS 'rqStaffID', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name as 'rStaffName',s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'staffDob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM ((((((((tblBResponseDetail rpD \n"
            + "LEFT JOIN tblBookResponse rp ON rpD.responseID = rp.responseID)\n"
            + "LEFT JOIN tblStaff st ON rp.staffID = st.staffID)\n"
            + "LEFT JOIN tblBook b ON rpD.ISBN = b.ISBN)\n"
            + "LEFT JOIN tblPublisher p ON b.publisherID = p.publisherID)\n"
            + "LEFT JOIN tblCategory c ON b.categoryID = c.categoryID)\n"
            + "LEFT JOIN tblReview r ON b.reviewID = r.reviewID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.responseID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "WHERE rpD.responseID LIKE ?";
    private static final String INSERT_RESPONSE_DETAIL = "INSERT INTO [tblBResponseDetail](responseID,ISBN,publisherID,categoryID,Name,[Author-name],Quantity,Price,[Status],[Delete])\n"
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String QUANTITY_CHECK = "SELECT Quantity FROM [tblBResponseDetail] WHERE ISBN LIKE ? AND responseID LIKE ?";

    public boolean insertResponseDetail(int responseID, BookDTO book, String status, String delete) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_RESPONSE_DETAIL);
            ptm.setInt(1, responseID);
            ptm.setString(2, book.getIsbn());
            ptm.setString(3, book.getPublisher().getPublisherID());
            ptm.setString(4, book.getCategory().getCategoryID());
            ptm.setString(5, book.getName());
            ptm.setString(6, book.getAuthorName());
            ptm.setInt(7, book.getQuantity());
            ptm.setDouble(8, book.getPrice());
            ptm.setString(9, status);
            ptm.setString(10, delete);
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

    public List<BResponseDetailDTO> loadDetailByResponseID(String responseId) throws SQLException {
        List<BResponseDetailDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_DETAIL_BY_RESPONSE_ID);
                ptm.setString(1, responseId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseDetailID = rs.getString("responseDetailID");
                    String responseID = rs.getString("responseID");
                    String isbn = rs.getString("ISBN");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("Name");
                    String author = rs.getString("Author-name");
                    int quantity = Integer.parseInt(rs.getString("Quantity"));
                    double price = Double.parseDouble(rs.getString("Price"));
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String rpDate = rs.getString("rpDate");
                    String rpStatus = rs.getString("rpStatus");
                    String rpDelete = rs.getString("rpDelete");
                    String sName = rs.getString("sName");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dob = rs.getString("dob");
                    String sStatus = rs.getString("sStatus");
                    String sDelete = rs.getString("sDelete");
                    String reviewID = rs.getString("reviewID");
                    double bookPrice = Double.parseDouble(rs.getString("bookPrice"));
                    String img = rs.getString("Image");
                    int bookQuantity = Integer.parseInt(rs.getString("bookQuantity"));
                    String des = rs.getString("Description");
                    String bookStatus = rs.getString("bookStatus");
                    String pubName = rs.getString("pubName");
                    String pubStatus = rs.getString("pubStatus");
                    String cateName = rs.getString("cateName");
                    String cateStatus = rs.getString("cateStatus");
                    double rate = Double.parseDouble(rs.getString("Rate"));
                    int times = Integer.parseInt(rs.getString("Times"));
                    String reviewStatus = rs.getString("reviewStatus");
                    String rqStaffID = rs.getString("rqStaffID");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String rStaffName = rs.getString("rStaffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String staffDob = rs.getString("staffDob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    list.add(new BResponseDetailDTO(responseDetailID,
                            new BookResponseDTO(responseID,
                                    new BookRequestDTO(requestID,
                                            new StaffDTO(staffID, sName, "***", role, phone, dob, sStatus, sDelete), rqDate, rqStatus, rqDelete),
                                    new StaffDTO(rqStaffID, rStaffName, "***", staffRole, staffPhone, staffDob, staffStatus, staffDelete), rpDate, rpStatus, rpDelete),
                            new BookDTO(isbn,
                                    new PublisherDTO(publisherID, pubName, pubStatus),
                                    new CategoryDTO(categoryID, cateName, cateStatus),
                                    new ReviewDTO(reviewID, rate, times, reviewStatus), name, author, bookPrice, des, img, bookQuantity, bookStatus), quantity, price, status, delete));
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
    
    public int quantityCheck(String isbn, String responseID) throws SQLException{
        int quantity = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(QUANTITY_CHECK);
                ptm.setString(1, isbn);
                ptm.setString(2, responseID);
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
}
