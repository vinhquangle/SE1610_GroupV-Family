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
}
