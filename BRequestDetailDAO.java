/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BRequestDetailDTO;
import dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class BRequestDetailDAO {

    private static final String CREATE_REQUEST_DETAIL = "INSERT INTO tblBRequestDetail (requestID, ISBN, publisherID, categoryID, Name, [Author-name], Quantity, [Status], [Delete])\n"
            + "VALUES    (?,?,?,?,?,?,?,?,?)";

    //Ham nay dung de them add request vào tblRequestDetail - Quang Vinh
    public boolean createRequestDetail(int requestID, BookDTO book, String status) throws SQLException {
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE_REQUEST_DETAIL);
                ptm.setInt(1, requestID);
                ptm.setString(2, book.getIsbn());
                ptm.setString(3, book.getPublisher().getPublisherID());
                ptm.setString(4, book.getCategory().getCategoryID());
                ptm.setString(5, book.getName());
                ptm.setString(6, book.getAuthorName());
                ptm.setInt(7, book.getQuantity());
                ptm.setString(8, status);
                ptm.setString(9, "0");
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
