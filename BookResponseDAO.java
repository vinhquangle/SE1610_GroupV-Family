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
    private static final String SEARCH_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.responseID LIKE ?\n" +
                                                        "AND dbo.ufn_removeMark(s.Name) LIKE ?)";
    private static final String SEARCH_RESPONSE_NULL = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.responseID IS NULL\n" +
                                                        "AND dbo.ufn_removeMark(s.Name) LIKE ?)";
    private static final String GET_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete], rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete', s.[Name] AS 'sName', s.[Password] AS 'sPass', s.[Role] AS 'sRole', s.[Phone] AS 'sPhone', s.[Date-of-birth] AS 'sDate', s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete' \n" +
                                                    "FROM tblBookResponse rp, tblBookRequest rq, tblStaff s\n"+
                                                    "WHERE rp.requestID LIKE rq.requestID AND rp.staffID LIKE s.staffID";
                                               
    private static final String UPDATE = "UPDATE tblBookResponse SET rp.[Status]\n" +
                                                    "FROM tblBookResponse rp\n"+
                                                    "WHERE rp.responseID=?";
    private static final String LOAD_RESPONSE_BY_STATUS ="SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.[Status] LIKE ?";
    private static final String LOAD_RESPONSE_BY_9_STATUS ="SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.[Status] LIKE ?" +
                                                        "ORDER BY rp.[status] DESC\n" +
                                                        "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String LOAD_RESPONSE_BY_STATUS_NULL = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.[Status] IS NULL";
    private static final String LOAD_RESPONSE_BY_STATUS_NULL_9 = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n" +
                                                        "rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n" +
                                                        "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n" +
                                                        "FROM ((tblBookResponse rp \n" +
                                                        "LEFT JOIN tblBookRequest rq ON rp.requestID LIKE rq.requestID)\n" +
                                                        "LEFT JOIN tblStaff s ON rp.staffID LIKE s.staffID)\n" +
                                                        "WHERE rp.[Status] IS NULL" +
                                                        "ORDER BY rp.[status] DESC\n" +
                                                        "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
   public List<BookResponseDTO> searchBookResponse(String txtSearch, String st) throws SQLException  {
        List<BookResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_RESPONSE);
                ptm.setString(1, st);
                ptm.setString(2, "%" + txtSearch + "%");
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
                    StaffDTO staff = new StaffDTO(staffID, sName, sPass, sRole, sPhone, sDate, sStatus, sDelete);
                    list.add( new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, date, status, delete));
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
   public List<BookResponseDTO> searchBookResponseNull(String txtSearch) throws SQLException  {
        List<BookResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_RESPONSE_NULL);
                ptm.setString(1, "%" + txtSearch + "%");
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
                    StaffDTO staff = new StaffDTO(staffID, sName, sPass, sRole, sPhone, sDate, sStatus, sDelete);
                    list.add( new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, date, status, delete));
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
    public List<BookResponseDTO> loadResponseByStatus(String st) throws SQLException  {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE_BY_STATUS);
                ptm.setString(1, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String Date = rs.getString("Date");
                    String Status = rs.getString("Status");
                    String Delete = rs.getString("Delete");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete= rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String Role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    StaffDTO staff = new StaffDTO(staffID, staffName, "***", Role, staffPhone, dob, staffStatus, staffDelete);
                    listResponse.add(new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, Date, Status, Delete));
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
        return listResponse;
    }
        public List<BookResponseDTO> load9ResponseByStatus(String st, int page) throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE_BY_9_STATUS);
                ptm.setString(1, st);
                ptm.setInt(2, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String Date = rs.getString("Date");
                    String Status = rs.getString("Status");
                    String Delete = rs.getString("Delete");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete= rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String Role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    StaffDTO staff = new StaffDTO(staffID, staffName, "***", Role, staffPhone, dob, staffStatus, staffDelete);
                    listResponse.add(new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, Date, Status, Delete));
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
        return listResponse;
    }
    public List<BookResponseDTO> loadResponseByStatusNull() throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE_BY_STATUS_NULL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String Date = rs.getString("Date");
                    String Status = rs.getString("Status");
                    String Delete = rs.getString("Delete");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete= rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String Role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    StaffDTO staff = new StaffDTO(staffID, staffName, "***", Role, staffPhone, dob, staffStatus, staffDelete);
                    listResponse.add(new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, Date, Status, Delete));
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
        return listResponse;
    }

    public List<BookResponseDTO> load9ResponseByStatusNull(int page) throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE_BY_STATUS_NULL_9);
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String Date = rs.getString("Date");
                    String Status = rs.getString("Status");
                    String Delete = rs.getString("Delete");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete= rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String Role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    StaffDTO staff = new StaffDTO(staffID, staffName, "***", Role, staffPhone, dob, staffStatus, staffDelete);
                    listResponse.add(new BookResponseDTO(responseID, new BookRequestDTO(requestID, staff, rqDate, rqStatus, rqDelete), staff, Date, Status, Delete));
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
        return listResponse;
    }
}
