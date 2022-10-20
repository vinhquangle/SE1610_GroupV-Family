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
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class StaffDAO {
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

