*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author vungo
 */
//Code thêm vô dao/StaffDAO. Tất cả function dưới là mới hết
public class StaffDAO {
    public static final String CREATE_STAFF=
"INSERT INTO tblStaff(staffID,[Name],[Password],[Role],[Phone],[Date-of-birth],[Status],[Delete])\n" +
"VALUES(?,?,?,?,?,?,0,0)";
    public static final String CHECK_STAFF_ID="SELECT staffID FROM tblStaff WHERE staffID LIKE ?";
    public static final String LOAD_ALL_STAFF=
"SELECT staffID,[Name],[Password],[Role],[Phone],[Date-of-birth],[Status],[Delete] FROM tblStaff";
    public static final String SEARCH_STAFF=
"SELECT s.staffID AS staffS,s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS\n" +
",s.[Phone] AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS \n" +
"FROM tblStaff s WHERE s.staffID =? OR s.[Name] LIKE ? OR s.[Role] LIKE ? OR s.[Phone] \n" +
"LIKE ? OR s.[Date-of-birth] LIKE ?";
public StaffDTO checkLogin(String userID, String password) throws SQLException {
        String LOGIN = "SELECT staffID, name, [role], [Date-of-birth], phone, [status], [delete] \n" +
"FROM tblStaff WHERE staffID=? AND [password]= ?";
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

public boolean createStaff(StaffDTO staff) throws SQLException{
        boolean check=false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try{
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(CREATE_STAFF);
                ptm.setString(1, staff.getStaffID());
                ptm.setString(2, staff.getName());
                ptm.setString(3, staff.getPassword());
                ptm.setString(4, staff.getRole());
                ptm.setString(5, staff.getPhone());
                ptm.setString(6, staff.getDateOfBirth());
                check=ptm.executeUpdate()>0?true:false;
            }
        }catch(Exception e){
            
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
public boolean checkStaffID(String staffID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_STAFF_ID);
                ptm.setString(1, staffID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
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
                ptm = conn.prepareCall(LOAD_ALL_STAFF);
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
                ptm.setString(2, "%"+search+"%");
                ptm.setString(3, "%"+search+"%");
                ptm.setString(4, "%"+search+"%");
                ptm.setString(5, "%"+search+"%");
                rs = ptm.executeQuery();
                while(rs.next()){
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = rs.getString("passS");
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String status=rs.getString("statusS");
                    String delete= rs.getString("deleteS");
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
}