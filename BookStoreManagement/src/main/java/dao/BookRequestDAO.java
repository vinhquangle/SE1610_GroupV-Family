/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookRequestDTO;
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
public class BookRequestDAO {

    private static final String LOAD_REQUEST = "SELECT b.requestID, b.staffID, b.[Date], b.[Status], b.[Delete], s.Name, s.[Role], s.Phone, s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM tblBookRequest b LEFT JOIN tblStaff s ON b.staffID = s.staffID\n"
            + "WHERE b.[Status] LIKE ? AND b.[Delete] LIKE ?\n"
            + "ORDER BY b.[Date] DESC ";
    private static final String LOAD_9_REQUEST = "SELECT b.requestID, b.staffID, b.[Date], b.[Status], b.[Delete], s.Name, s.[Role], s.Phone, s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM tblBookRequest b LEFT JOIN tblStaff s ON b.staffID = s.staffID\n"
            + "WHERE b.[Status] LIKE ? AND b.[Delete] LIKE ?\n"
            + "ORDER BY b.[Status] ASC, b.[Date] DESC, b.requestID DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_REQUEST = "SELECT b.requestID, b.staffID, b.[Date], b.[Status], b.[Delete], s.Name, s.[Role], s.Phone, s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM tblBookRequest b LEFT JOIN tblStaff s ON b.staffID = s.staffID\n"
            + "WHERE b.requestID LIKE ?";
    private static final String INSERT_REQUEST = "INSERT INTO [tblBookRequest](staffID,[Date],[Status],[Delete])\n"
            + "VALUES (?,?,?,?)\n"
            + "SELECT MAX(requestID) AS 'requestID' FROM [tblBookRequest]";
    private static final String SEARCH_REQUEST = "SELECT r.requestID, r.staffID, r.[Date], r.[Status], r.[Delete], s.Name AS 'sName',\n"
            + "s.[Password], s.Phone, s.[Role], s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM (tblBookRequest r LEFT JOIN tblStaff s ON r.staffID LIKE s.staffID)\n"
            + "WHERE (r.requestID LIKE ? OR s.Name LIKE ? OR r.[Date] LIKE ?\n"
            + "OR dbo.ufn_removeMark(s.Name) LIKE ?) AND r.[Status] LIKE ? AND r.[Delete] LIKE ?\n"
            + "ORDER BY r.[status] ASC, r.[Date] DESC, r.requestID DESC\n";
    private static final String SEARCH_9_REQUEST = "SELECT r.requestID, r.staffID, r.[Date], r.[Status], r.[Delete], s.Name AS 'sName',\n"
            + "s.[Password], s.Phone, s.[Role], s.[Date-of-birth], s.[Status] AS 'sStatus', s.[Delete] AS 'sDelete'\n"
            + "FROM (tblBookRequest r LEFT JOIN tblStaff s ON r.staffID LIKE s.staffID)\n"
            + "WHERE (r.requestID LIKE ? OR s.Name LIKE ? OR r.[Date] LIKE ?\n"
            + "OR dbo.ufn_removeMark(s.Name) LIKE ?) AND r.[Status] LIKE ? AND r.[Delete] LIKE ?\n"
            + "ORDER BY r.[status] ASC, r.[Date] DESC, r.requestID DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String UPDATE_DELETE = "UPDATE [tblBookRequest] SET [Delete] = ?\n"
            + "WHERE [requestID] = ?";
    private static final String UPDATE_STATUS = "UPDATE [tblBookRequest] SET [Status] = ? WHERE requestID LIKE ?";

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

    public List<BookRequestDTO> loadRequest(String st, String del) throws SQLException {
        List<BookRequestDTO> listRequest = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(LOAD_REQUEST);
            ptm.setString(1, st);
            ptm.setString(2, del);
            rs = ptm.executeQuery();
            while (rs.next()) {
                String requestID = rs.getString("requestID");
                String staffID = rs.getString("staffID");
                String date = rs.getString("Date");
                String status = rs.getString("Status");
                String delete = rs.getString("Delete");
                String name = rs.getString("Name");
                String role = rs.getString("Role");
                String password = "***";
                String phone = rs.getString("phone");
                String dob = rs.getString("Date-of-birth");
                String sStatus = rs.getString("sStatus");
                String sDelete = rs.getString("sDelete");
                listRequest.add(new BookRequestDTO(requestID, new StaffDTO(staffID, name, password, role, phone, dob, sStatus, sDelete), date, status, delete));
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
        return listRequest;
    }

    public List<BookRequestDTO> load9Request(String st, String del, int page) throws SQLException {
        List<BookRequestDTO> listRequest = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(LOAD_9_REQUEST);
            ptm.setString(1, st);
            ptm.setString(2, del);
            ptm.setInt(3, (page - 1) * 9);
            rs = ptm.executeQuery();
            while (rs.next()) {
                String requestID = rs.getString("requestID");
                String staffID = rs.getString("staffID");
                String date = rs.getString("Date");
                String status = rs.getString("Status");
                String delete = rs.getString("Delete");
                String name = rs.getString("Name");
                String role = rs.getString("Role");
                String password = "***";
                String phone = rs.getString("phone");
                String dob = rs.getString("Date-of-birth");
                String sStatus = rs.getString("sStatus");
                String sDelete = rs.getString("sDelete");
                listRequest.add(new BookRequestDTO(requestID, new StaffDTO(staffID, name, password, role, phone, dob, sStatus, sDelete), date, status, delete));
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
        return listRequest;
    }

    public BookRequestDTO getRequestById(String requestId) throws SQLException {
        BookRequestDTO request = new BookRequestDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(GET_REQUEST);
            ptm.setString(1, requestId);
            rs = ptm.executeQuery();
            while (rs.next()) {
                String requestID = rs.getString("requestID");
                String staffID = rs.getString("staffID");
                String date = rs.getString("Date");
                String status = rs.getString("Status");
                String delete = rs.getString("Delete");
                String name = rs.getString("Name");
                String role = rs.getString("Role");
                String password = "***";
                String phone = rs.getString("phone");
                String dob = rs.getString("Date-of-birth");
                String sStatus = rs.getString("sStatus");
                String sDelete = rs.getString("sDelete");
                request = new BookRequestDTO(requestID, new StaffDTO(staffID, name, password, role, phone, dob, sStatus, sDelete), date, status, delete);
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
        return request;
    }
    //Ham nay dung de search request by id, ten nhan vien, ngay

    public List<BookRequestDTO> searchRequest(String txtSearch, String st, String del) throws SQLException {
        List<BookRequestDTO> listRequest = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_REQUEST);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, st);
                ptm.setString(6, del);
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

    //Ham nay dung de search 9 request by id, ten nhan vien, ngay
    public List<BookRequestDTO> search9Request(String txtSearch, int page, String st, String del) throws SQLException {
        List<BookRequestDTO> listRequest = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_9_REQUEST);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, st);
                ptm.setString(6, del);
                ptm.setInt(7, (page - 1) * 9);
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

    public boolean updateRequest(BookRequestDTO request) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_DELETE);
                ptm.setString(1, request.getDelete());
                ptm.setString(2, request.getRequestID());
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

    public boolean updateStatus(String st, String requestID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_STATUS);
                ptm.setString(1, st);
                ptm.setString(2, requestID);
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
