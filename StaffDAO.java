/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class StaffDAO {

    private static final String LOGIN = "SELECT name, [role], [Date-of-birth], phone, [status], [delete] FROM tblStaff WHERE staffID LIKE ? AND [password] LIKE ?";
    private static final String UPDATE_STATUS_ONLINE = "UPDATE tblStaff SET [status] = ? WHERE staffID LIKE ?";
    private static final String CHECK_CUSTOMER_IDinSTAFF="SELECT staffID FROM tblStaff WHERE staffID=?";
    

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
                ptm = conn.prepareStatement(UPDATE_STATUS_ONLINE);
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

//check duplicate ID for customer in tblStaff
    public boolean checkCusIDinStaff(String customerID) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(CHECK_CUSTOMER_IDinSTAFF);
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
}
