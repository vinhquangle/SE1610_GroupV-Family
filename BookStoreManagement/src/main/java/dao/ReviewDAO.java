/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.ReviewDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilities.DBUtils;

/**
 *
 * @author PCPV
 */
public class ReviewDAO {

    private static final String CREATE = "INSERT INTO[tblReview](Rate,Times,[Status]) VALUES (0,0,1)\n"
            + "SELECT MAX(reviewID) AS 'reviewID' FROM tblReview";
    private static final String GET_RATE_TIME = "SELECT Rate, Times\n"
            + "FROM tblReview\n"
            + "WHERE reviewID LIKE ?";
    private static final String UPDATE_REVIEW = "UPDATE tblReview\n"
            + "SET Rate = ?, Times = ?\n"
            + "WHERE reviewID = ?";

    public ReviewDTO createReview() throws SQLException {
        ReviewDTO review = new ReviewDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(CREATE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String reviewID = rs.getString("reviewID");
                    review = new ReviewDTO(reviewID, 0, 0, "1");
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

    public ReviewDTO getRateTime(String reviewID) throws SQLException {
        ReviewDTO review = new ReviewDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(GET_RATE_TIME);
                ptm.setString(1, reviewID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    double rate = rs.getDouble("Rate");
                    int times = rs.getInt("Times");
                    review = new ReviewDTO(reviewID, rate, times, "1");
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

    public boolean updateReview(ReviewDTO review) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_REVIEW);
                ptm.setDouble(1, review.getRate());
                ptm.setInt(2, review.getTimes());
                ptm.setString(3, review.getReviewID());
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
