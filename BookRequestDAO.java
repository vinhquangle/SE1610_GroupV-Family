/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BRequestDetailDTO;
import dto.BookRequestDTO;
import dto.PublisherDTO;
import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author Quang Vinh
 */
public class BookRequestDAO {

    private static final String SEARCH_REQUEST = "SELECT    r.requestID, r.staffID, r.[Date], r.[Status], r.[Delete], s.Name AS 'sName', "
            + "s.[Password], s.Phone, s.[Role], s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM        ( tblBookRequest r LEFT JOIN tblStaff s ON r.staffID LIKE s.staffID)\n"
            + "WHERE r.requestID = ?";
    private static final String GET_REQUEST_ID = "SELECT TOP 1 requestID FROM tblBookRequest ORDER BY requestID DESC";
    private static final String CREATE_REQUEST = "INSERT INTO tblBookRequest (staffID, [Date], [Status], [Delete]) VALUES  (?,?,?,?)";
    

    public List<BookRequestDTO> getListRequest(String search) throws SQLException {
        List<BookRequestDTO> listRequest = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_REQUEST);
                ptm.setString(1, search);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String sName = rs.getString("sName");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String dob = rs.getString("Date-of-birth");
                    String phone = rs.getString("phone");
                    String sStatus = rs.getString("sStatus");
                    String sDelete = rs.getString("sDelete");
                    String date = rs.getString("date");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    listRequest.add(new BookRequestDTO(requestID, new StaffDTO(staffID, sName, password, role, phone, dob, sStatus, sDelete), date, status, delete));
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
        return listRequest;
    }

    //Ham nay dung de them add request vào tblRequest - Quang Vinh
    public int createRequest(String staffID, String status) throws SQLException {
        int requestID = 0;
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        PreparedStatement ptm2 = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE_REQUEST);
                ptm.setString(1, staffID);
                ptm.setString(2, date);
                ptm.setString(3, status);
                ptm.setString(4, "0");
                check = ptm.executeUpdate() > 0 ? true : false;
                //Lay ra id cua request vua Add
                ptm2 = conn.prepareStatement(GET_REQUEST_ID);
                rs = ptm2.executeQuery();
                while(rs.next()){
                    requestID = rs.getInt("requestID");
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
        return requestID;
    }
}
