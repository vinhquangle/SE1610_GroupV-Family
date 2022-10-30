/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class BookRequestDAO {

    private static final String INSERT_REQUEST = "INSERT INTO [tblBookRequest](staffID,[Date],[Status],[Delete])\n"
            + "VALUES (?,?,?,?)\n"
            + "SELECT MAX(requestID) AS 'requestID' FROM [tblBookRequest]";

    public int insertRequest(String staffID, String status, String delete) throws SQLException {
        int requestID = 0;
        String date = java.time.LocalDate.now().toString();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_REQUEST);
            ptm.setString(1, staffID);
            ptm.setString(2, date);
            ptm.setString(3, status);
            ptm.setString(4, delete);
            rs = ptm.executeQuery();
            while (rs.next()) {
                requestID = rs.getInt("requestID");
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
                ptm.close();
            }
        }
        return requestID;
    }
}
