/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

<<<<<<< HEAD
=======
import dto.CustomerDTO;
>>>>>>> origin/Quốc-Phi-Branch
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
public class StaffDAO {
<<<<<<< HEAD
    private static final String UPDATE = "UPDATE tblStaff SET name=?, password=?, role=?, phone=?, [Date-of-birth]=?, [status]=?, [delete]=? WHERE staffID=?";

    //method này dùng để update Staff trong viewprofilePage - Huu Hieu
    public boolean updateStaff(StaffDTO staff) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, staff.getName());
                ptm.setString(2, staff.getPassword());
                ptm.setString(3, staff.getRole());
                ptm.setString(4, staff.getPhone());
                ptm.setString(5, staff.getDateOfBirth());
                ptm.setString(6, staff.getStatus());
                ptm.setString(7, staff.getDelete());
                ptm.setString(8, staff.getStaffID());

=======

    private static final String LOGIN = "SELECT name, [role], [Date-of-birth], phone, [status], [delete] FROM tblStaff WHERE staffID LIKE ? AND [password] LIKE ?";
    private static final String UPDATE_STATUS = "UPDATE tblStaff SET [status] = ? WHERE staffID LIKE ?";
    private static final String CHECK_STAFF_ID = "SELECT staffID FROM tblStaff WHERE staffID LIKE ?";
    private static final String GET_STAFF = "SELECT staffID, Name, [Password], [Role], Phone, [Date-of-birth], [Status], [Delete] FROM tblStaff";
    public static final String SEARCH_STAFF=
"SELECT staffID,[Name],[Password],[Role],[Phone],[Date-of-birth],[Status],[Delete] FROM tblStaff"
+"WHERE staffID LIKE ? OR [Name] LIKE ? OR [Role] LIKE ? OR [Phone] LIKE ? OR [Date-of-birth] LIKE ?";
    public static final String UPDATE_ACCOUNT_STAFF = "UPDATE tblStaff SET name=?, password=?, role=?, phone=?, [Date-of-birth]=?, [status]=?, [delete]=? WHERE staffID=?";

    public StaffDTO checkLogin(String userID, String password) throws SQLException {
        StaffDTO staff = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    String dob = rs.getString("Date-of-birth");
                    String phone = rs.getString("phone");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    staff = new StaffDTO(userID, name, password, role, phone, dob, status, delete);
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
        return staff;
    }

    public boolean updateStatusOnline(String staffID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String status = "1";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, staffID);
>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD
}

=======

    public boolean updateStatusOffline(String staffID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String status = "0";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, staffID);
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
    public boolean checkCusIDinStaff(String customerID) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(CHECK_STAFF_ID);
                ptm.setString(1, customerID);
                rs = ptm.executeQuery();
                if(rs.next()){
                    check=true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(rs!=null) rs.close();
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
    
    public List<StaffDTO> getListStaff() throws SQLException {
        List<StaffDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(GET_STAFF);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String staffID = rs.getString("staffID");
                    String name = rs.getString("Name");
                    String password=rs.getString("Password");
                    String role=rs.getString("Role");
                    String phone=rs.getString("Phone");
                    String dateOfBirth=rs.getString("Date-of-birth");
                    String status=rs.getString("Status");
                    String delete = rs.getString("Delete");
                    list.add(new StaffDTO(staffID, name, password, role, phone, dateOfBirth, status, delete));
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
    
    public List<StaffDTO> searchStaff(String search) throws SQLException{
        List<StaffDTO> listSearch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if(conn != null){
                ptm = conn.prepareStatement(SEARCH_STAFF);
                ptm.setString(1, "%"+search+"%");
                rs = ptm.executeQuery();
                while(rs.next()){
                    String staffID = rs.getString("staffID");
                    String name = rs.getString("name");
                    String password = rs.getString("Password");
                    String roleID = rs.getString("roleID");
                    String phone = rs.getString("Phone");
                    String dateOfBirth = rs.getString("Date-of-birth");
                    String status=rs.getString("Status");
                    String delete= rs.getString("delete");
                    listSearch.add(new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, status, delete));
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
        
        return listSearch;
    }
    
    public boolean updateStaff(StaffDTO staff) throws SQLException{
         boolean check = false;
         Connection conn= null;
         PreparedStatement ptm= null;
         try{
             conn = DBUtils.getConnection();
             if(conn!=null){
                 ptm = conn.prepareStatement(UPDATE_ACCOUNT_STAFF);
                 ptm.setString(1, staff.getName());
                 ptm.setString(2, staff.getPassword());
                 ptm.setString(3, staff.getRole());
                 ptm.setString(4, staff.getPhone());
                 ptm.setString(5, staff.getDateOfBirth());
                 ptm.setString(6, staff.getStatus());
                 ptm.setString(7, staff.getDelete());
                 ptm.setString(8, staff.getStaffID());
                 
                 check= ptm.executeUpdate()>0 ? true : false;
             }
         }catch(Exception e){
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
>>>>>>> origin/Quốc-Phi-Branch
