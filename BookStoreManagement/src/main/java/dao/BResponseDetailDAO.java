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
        private static final String LOAD_RESPONSE_BY_RESPONSEID = "SELECT rpD.responseDetailID, rpD.responseID, rpD.ISBN, rpD.publisherID, rpD.categoryID, rpD.Name, rpD.[Author-name], rpD.Quantity, rpD.Price, rpD.[Status], rpD.[Delete],\n" +
                                                                    "b.reviewID, b.Price AS 'bookPrice', b.[Image], b.Quantity AS 'bookQuantity', b.[Description], b.[status] AS 'bookStatus',\n" +
                                                                    "s.Name AS 'staffName', s.staffID as 'staffStaffID',s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'staffDob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n" +
                                                                    "r.Rate, r.Times, r.[status] AS 'reviewStatus',\n" +
                                                                    "p.Name AS 'pubName', p.[status] AS 'pubStatus',\n" +
                                                                    "c.Name AS 'cateName', c.[status] AS 'cateStatus',\n" +
                                                                    "rq.requestID AS 'rqRequestID', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                                    "rp.[Date] AS 'rpDate', rp.[Status] AS 'rpStatus', rp.[Delete] AS 'rpDelete'\n" +
                                                                    "FROM (((((((tblBResponseDetail rpD \n" +
                                                                    "LEFT JOIN tblBookResponse rp ON rpD.responseID LIKE rp.responseID)\n" +
                                                                    "LEFT JOIN tblBook b ON rpD.ISBN LIKE b.ISBN)\n" +
                                                                    "LEFT JOIN tblPublisher p ON b.publisherID LIKE p.publisherID)\n" +
                                                                    "LEFT JOIN tblCategory c ON b.categoryID LIKE c.categoryID)\n" +
                                                                    "LEFT JOIN tblReview r ON b.reviewID LIKE r.reviewID)\n" +
                                                                    "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                                    "LEFT JOIN tblBookRequest rq ON rp.responseID LIKE rq.requestID)\n" +
                                                                    "WHERE rpD.responseID LIKE ?";
        
    public List<BResponseDetailDTO> loadAllBResponseDetailByResponseID(String responseId) throws SQLException {
        List<BResponseDetailDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE_BY_RESPONSEID);
                ptm.setString(1, responseId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseDetailID = rs.getString("responseDetailID");
                    String responseID = rs.getString("responseID");
                    String ISBN = rs.getString("ISBN");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String Name = rs.getString("Name");
                    String Author_name = rs.getString("Author-name");
                    int Quantity = Integer.parseInt(rs.getString("Quantity"));
                    double  Price = Double.parseDouble(rs.getString("Price"));
                    String Status = rs.getString("Status");
                    String Delete = rs.getString("Delete");
                    String reviewID = rs.getString("reviewID");
                    double bookPrice = Double.parseDouble(rs.getString("bookPrice"));
                    String Image = rs.getString("Image");
                    int bookQuantity = Integer.parseInt(rs.getString("bookQuantity"));
                    String Description = rs.getString("Description");
                    String bookStatus = rs.getString("bookStatus");
                    String staffName = rs.getString("staffName");
                    String staffStaffID = rs.getString("staffStaffID");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String staffDob = rs.getString("staffDob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    double Rate = Double.parseDouble(rs.getString("Rate"));
                    int Times = Integer.parseInt(rs.getString("Times"));
                    String reviewStatus = rs.getString("reviewStatus");
                    String pubName = rs.getString("pubName");
                    String pubStatus = rs.getString("pubStatus");
                    String cateName = rs.getString("cateName");
                    String cateStatus = rs.getString("cateStatus");
                    String rqRequestID = rs.getString("rqRequestID");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String rpDate = rs.getString("rpDate");
                    String rpStatus = rs.getString("rpStatus");
                    String rpDelete = rs.getString("rpDelete");
                    PublisherDTO pub = new PublisherDTO(publisherID, pubName, pubStatus);
                    CategoryDTO cate = new CategoryDTO(categoryID, cateName, cateStatus);
                    ReviewDTO review = new ReviewDTO(reviewID, Rate, Times, reviewStatus);
                    StaffDTO staff = new StaffDTO(staffStaffID, staffName, "***", staffRole, staffPhone, staffDob, staffStatus, staffDelete);
                    BookRequestDTO request= new BookRequestDTO(rqRequestID, staff, rqDate, rqStatus, rqDelete);
                    BookResponseDTO response = new BookResponseDTO(responseID, request, staff, rpDate, rpStatus, rpDelete);
                    BookDTO book = new BookDTO(ISBN, pub, cate, review, Name, Author_name, bookPrice, Description, Image, bookQuantity, bookStatus);
                    list.add(new BResponseDetailDTO(responseDetailID, response, book, Quantity, Price, Status, Delete));           
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
        }
