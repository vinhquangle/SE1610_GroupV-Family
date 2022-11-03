/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CustomerDTO;
import dto.ReviewDTO;
import dto.ReviewDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author PCPV
 */
public class ReviewDetailDAO {

    private static final String LOAD_REVIEW = "SELECT d.reviewDetailID, d.reviewID, d.customerID, d.[Description], d.rate, d.[date], d.[status] AS 'rdStatus', r.rate AS 'Rates', r.times, r.[status] AS 'rStatus', c.[password], c.name, c.email, c.[address], c.phone, c.point, c.[status] AS 'cusStatus', c.[delete] \n"
            + "FROM (tblReviewDetail d LEFT JOIN tblReview r\n"
            + "ON d.reviewID LIKE r.reviewID) LEFT JOIN tblCustomer c \n"
            + "ON d.customerID LIKE c.customerID\n"
            + "WHERE d.[Status] LIKE ? AND d.reviewID LIKE ?";
    private static final String LOAD_REVIEW_BY_ID = "SELECT d.reviewDetailID, d.reviewID, d.customerID, d.[Description], d.rate, d.[date], d.[status] AS 'rdStatus', r.rate AS 'Rates', r.times, r.[status] AS 'rStatus', c.[password], c.name, c.email, c.[address], c.phone, c.point, c.[status] AS 'cusStatus', c.[delete] \n"
            + "FROM (tblReviewDetail d LEFT JOIN tblReview r\n"
            + "ON d.reviewID LIKE r.reviewID) LEFT JOIN tblCustomer c \n"
            + "ON d.customerID LIKE c.customerID\n"
            + "WHERE d.reviewDetailID LIKE ?";
    private static final String LOAD_5_REVIEW = "SELECT d.reviewDetailID, d.reviewID, d.customerID, d.[Description], d.rate, d.[date], d.[status] AS 'rdStatus', r.rate AS 'Rates', r.times, r.[status] AS 'rStatus', c.[password], c.name, c.email, c.[address], c.phone, c.point, c.[status] AS 'cusStatus', c.[delete]\n"
            + "FROM (tblReviewDetail d LEFT JOIN tblReview r\n"
            + "ON d.reviewID LIKE r.reviewID) LEFT JOIN tblCustomer c\n"
            + "ON d.customerID LIKE c.customerID\n"
            + "WHERE d.[Status] LIKE ? AND d.reviewID LIKE ?\n"
            + "ORDER BY d.[date] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 5 ROWS ONLY";
    private static final String ADD_REVIEW = "INSERT INTO [tblReviewDetail] (reviewID, customerID, [Description], [Rate], [Date], [Status])\n"
            + "VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_REVIEW = "UPDATE tblReviewDetail\n"
            + "SET Rate = ?, customerID = ?, [Description] = ?, [Date] = ?, [Status] = ?\n"
            + "WHERE reviewDetailID LIKE ?";
    private static final String UPDATE_STATUS_REVIEW = "UPDATE tblReviewDetail\n"
            + "SET [Status] = ?\n"
            + "WHERE reviewDetailID LIKE ?";

    public List<ReviewDetailDTO> loadReview(String st, String reviewId) throws SQLException {
        List<ReviewDetailDTO> listReview = new ArrayList<ReviewDetailDTO>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_REVIEW);
                ptm.setString(1, st);
                ptm.setString(2, reviewId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String reviewDetailID = rs.getString("reviewDetailID");
                    String reviewID = rs.getString("reviewID");
                    String customerID = rs.getString("customerID");
                    String description = rs.getString("Description");
                    double rate = rs.getDouble("rate");
                    String date = rs.getString("date");
                    String rdStatus = rs.getString("rdStatus");
                    double rates = rs.getDouble("Rates");
                    int times = rs.getInt("times");
                    String rStatus = rs.getString("rStatus");
                    String password = "*****";
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String cusStatus = rs.getString("cusStatus");
                    String delete = rs.getString("delete");
                    listReview.add(new ReviewDetailDTO(reviewDetailID, new ReviewDTO(reviewID, rates, times, rStatus), new CustomerDTO(customerID, name, password, email, address, phone, point, cusStatus, delete), description, rate, date, rdStatus));
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
        return listReview;
    }

    public ReviewDetailDTO getReviewById(String reviewDetailId) throws SQLException {
        ReviewDetailDTO review = new ReviewDetailDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_REVIEW_BY_ID);
                ptm.setString(1, reviewDetailId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String reviewDetailID = rs.getString("reviewDetailID");
                    String reviewID = rs.getString("reviewID");
                    String customerID = rs.getString("customerID");
                    String description = rs.getString("Description");
                    double rate = rs.getDouble("rate");
                    String date = rs.getString("date");
                    String rdStatus = rs.getString("rdStatus");
                    double rates = rs.getDouble("Rates");
                    int times = rs.getInt("times");
                    String rStatus = rs.getString("rStatus");
                    String password = "*****";
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String cusStatus = rs.getString("cusStatus");
                    String delete = rs.getString("delete");
                    review = new ReviewDetailDTO(reviewDetailID, new ReviewDTO(reviewID, rates, times, rStatus), new CustomerDTO(customerID, name, password, email, address, phone, point, cusStatus, delete), description, rate, date, rdStatus);
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
        return review;
    }

    public List<ReviewDetailDTO> loadReview5(String st, String reviewId, int page) throws SQLException {
        List<ReviewDetailDTO> listReview = new ArrayList<ReviewDetailDTO>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_5_REVIEW);
                ptm.setString(1, st);
                ptm.setString(2, reviewId);
                ptm.setInt(3, (page - 1) * 5);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String reviewDetailID = rs.getString("reviewDetailID");
                    String reviewID = rs.getString("reviewID");
                    String customerID = rs.getString("customerID");
                    String description = rs.getString("Description");
                    double rate = rs.getDouble("rate");
                    String date = rs.getString("date");
                    String rdStatus = rs.getString("rdStatus");
                    double rates = rs.getDouble("Rates");
                    int times = rs.getInt("times");
                    String rStatus = rs.getString("rStatus");
                    String password = "*****";
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String cusStatus = rs.getString("cusStatus");
                    String delete = rs.getString("delete");
                    listReview.add(new ReviewDetailDTO(reviewDetailID, new ReviewDTO(reviewID, rates, times, rStatus), new CustomerDTO(customerID, name, password, email, address, phone, point, cusStatus, delete), description, rate, date, rdStatus));
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
        return listReview;
    }

    public boolean createReview(String reviewID, String cusID, String des, double rate, String date) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(ADD_REVIEW);
                ptm.setString(1, reviewID);
                ptm.setString(2, cusID);
                ptm.setString(3, des);
                ptm.setDouble(4, rate);
                ptm.setString(5, date);
                ptm.setString(6, "1");
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

    public boolean updateReview(String reviewDetailID, String cusID, String des, double rate, String date) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_REVIEW);
                ptm.setDouble(1, rate);
                ptm.setString(2, cusID);
                ptm.setString(3, des);
                ptm.setString(4, date);
                ptm.setString(5, reviewDetailID);
                ptm.setString(6, "1");
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
    public boolean setStatus(String st, String reviewDetailID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_STATUS_REVIEW);
                ptm.setString(1, st);
                ptm.setString(2, reviewDetailID);
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
