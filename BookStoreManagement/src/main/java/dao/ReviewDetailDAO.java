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
            + "WHERE d.[Status] LIKE ?";
    public List<ReviewDetailDTO> loadReview(String st) throws SQLException{
        List<ReviewDetailDTO> listReview = new ArrayList<ReviewDetailDTO>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_REVIEW);
                ptm.setString(1, st);
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
}
