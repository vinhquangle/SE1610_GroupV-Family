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

    private static final String LOAD_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "ORDER BY rp.[Date] DESC, rp.responseID DESC, rp.[Delete] ASC";
    private static final String LOAD_9_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "ORDER BY rp.[Date] DESC, rp.responseID DESC, rp.[Delete] ASC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String SEARCH_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "WHERE rp.responseID LIKE ? OR rp.requestID LIKE ? OR rp.staffID LIKE ?\n"
            + "OR rq.staffID LIKE ? OR dbo.ufn_removeMark(t.Name) LIKE ? OR dbo.ufn_removeMark(s.Name) LIKE ?\n"
            + "ORDER BY rp.[Date] DESC, rp.responseID DESC, rp.[Delete] ASC";
    private static final String SEARCH_9_RESPONSE = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "WHERE rp.responseID LIKE ? OR rp.requestID LIKE ? OR rp.staffID LIKE ?\n"
            + "OR rq.staffID LIKE ? OR dbo.ufn_removeMark(t.Name) LIKE ? OR dbo.ufn_removeMark(s.Name) LIKE ?\n"
            + "ORDER BY rp.[Date] DESC, rp.responseID DESC, rp.[Delete] ASC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_RESPONSE_BY_ID = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "WHERE rp.responseID LIKE ?";
    private static final String GET_RESPONSE_BY_REQUEST = "SELECT rp.responseID, rp.requestID, rp.staffID, rp.[Date], rp.[Status], rp.[Delete],\n"
            + "t.Name, t.[Role], t.Phone, t.[Date-of-birth] AS 'dobT', t.[status] AS 'staffStatusT', t.[delete] AS 'staffDeleteT',\n"
            + "rq.staffID AS 'staffIDR', rq.[Date] AS 'rqDate', rq.[Status] AS 'rqStatus', rq.[Delete] AS 'rqDelete',\n"
            + "s.Name AS 'staffName', s.[Role] AS 'staffRole', s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete'\n"
            + "FROM (((tblBookResponse rp\n"
            + "LEFT JOIN tblStaff t ON rp.staffID = t.staffID)\n"
            + "LEFT JOIN tblBookRequest rq ON rp.requestID = rq.requestID)\n"
            + "LEFT JOIN tblStaff s ON rq.staffID = s.staffID)\n"
            + "WHERE rp.requestID LIKE ? AND rp.[Status] LIKE ?";
    private static final String UPDATE_STATUS = "UPDATE [tblBookResponse] SET [Delete] = ?\n"
            + "WHERE responseID = ?";
    private static final String INSERT_RESPONSE = "INSERT INTO [tblBookResponse](requestID,staffID,[Date],[Status],[Delete])\n"
            + "VALUES (?,?,?,?,?)\n"
            + "SELECT MAX(responseID) AS 'responseID' FROM [tblBookResponse]";

    public int insertResponse(String requestID, String staffID, String status, String delete) throws SQLException {
        int responseID = 0;
        String date = java.time.LocalDate.now().toString();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_RESPONSE);
            ptm.setString(1, requestID);
            ptm.setString(2, staffID);
            ptm.setString(3, date);
            ptm.setString(4, status);
            ptm.setString(5, delete);
            rs = ptm.executeQuery();
            while (rs.next()) {
                responseID = rs.getInt("responseID");
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
        return responseID;
    }

    public List<BookResponseDTO> loadResponse() throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_RESPONSE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    listResponse.add(new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete));
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

    public List<BookResponseDTO> load9Response(int page) throws SQLException {
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
                ptm = conn.prepareStatement(LOAD_9_RESPONSE);
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    listResponse.add(new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete));
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

    public List<BookResponseDTO> searchResponse(String search) throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_RESPONSE);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                ptm.setString(3, "%" + search + "%");
                ptm.setString(4, "%" + search + "%");
                ptm.setString(5, "%" + search + "%");
                ptm.setString(6, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    listResponse.add(new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete));
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

    public List<BookResponseDTO> search9Response(String search, int page) throws SQLException {
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
                ptm = conn.prepareStatement(SEARCH_9_RESPONSE);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                ptm.setString(3, "%" + search + "%");
                ptm.setString(4, "%" + search + "%");
                ptm.setString(5, "%" + search + "%");
                ptm.setString(6, "%" + search + "%");
                ptm.setInt(7, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    listResponse.add(new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete));
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

    public BookResponseDTO getResponseByID(String responseId) throws SQLException {
        BookResponseDTO response = new BookResponseDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_RESPONSE_BY_ID);
                ptm.setString(1, responseId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    response = new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete);
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
        return response;
    }

    public boolean updateResponse(BookResponseDTO response) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_STATUS);
                ptm.setString(1, response.getDelete());
                ptm.setString(2, response.getResponseID());
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

    public List<BookResponseDTO> loadResponseByRequest(String requestId, String st) throws SQLException {
        List<BookResponseDTO> listResponse = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_RESPONSE_BY_REQUEST);
                ptm.setString(1, requestId);
                ptm.setString(2, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String responseID = rs.getString("responseID");
                    String requestID = rs.getString("requestID");
                    String staffID = rs.getString("staffID");
                    String date = rs.getString("Date");
                    String status = rs.getString("Status");
                    String delete = rs.getString("Delete");
                    String name = rs.getString("Name");
                    String role = rs.getString("Role");
                    String phone = rs.getString("Phone");
                    String dobT = rs.getString("dobT");
                    String staffStatusT = rs.getString("staffStatusT");
                    String staffDeleteT = rs.getString("staffDeleteT");
                    String staffIDR = rs.getString("staffIDR");
                    String rqDate = rs.getString("rqDate");
                    String rqStatus = rs.getString("rqStatus");
                    String rqDelete = rs.getString("rqDelete");
                    String staffName = rs.getString("staffName");
                    String staffRole = rs.getString("staffRole");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    listResponse.add(new BookResponseDTO(responseID,
                            new BookRequestDTO(requestID,
                                    new StaffDTO(staffIDR, staffName, "***", staffRole, staffPhone, dob, staffStatus, staffDelete), rqDate, rqStatus, rqDelete),
                            new StaffDTO(staffID, name, phone, role, phone, dobT, staffStatusT, staffDeleteT), date, status, delete));
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
