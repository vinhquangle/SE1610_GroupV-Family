/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookRequestDTO;
import dto.BookResponseDTO;
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
public class BookResponseDAO {
    private static final String SEARCH_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete], rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete', s.[Name] AS 'sName', s.[Password] AS 'sPass', s.[Role] AS 'sRole', s.[Phone] AS 'sPhone', s.[Date-of-birth] AS 'sDate', s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete' \n" +
                                                    "FROM (tblBookResponse rp LEFT JOIN tblBookRequest rq ON  rp.requestID LIKE rq.requestID) LEFT JOIN tblStaff s\n" +
                                                    "ON rp.staffID LIKE s.staffID\n" +
                                                    "WHERE rp.responseID=?";
    
   public List<BookResponseDTO> searchBookResponse(String txtSearch) throws SQLException {
        List<BookResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_RESPONSE);
                ptm.setString(1, txtSearch );
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String sName = rs.getString("sName");
                    String sPass = rs.getString("sPass");
                    String sRole = rs.getString("sRole");
                    String sPhone = rs.getString("sPhone");
                    String sDate = rs.getString("sDate");
                    String sStatus = rs.getString("sStatus");
                    String sDelete = rs.getString("sDelete");
                    list.add( new BookResponseDTO(responseID, new BookRequestDTO(requestID, new StaffDTO(staffID, sName, sPass, sRole, sPhone, sDate, sStatus,sDelete), rqDate, rqStatus, rqDelete), new StaffDTO(staffID, sName, sPass, sRole, sPhone, sDate, sStatus, sDelete), date, status, delete));
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
