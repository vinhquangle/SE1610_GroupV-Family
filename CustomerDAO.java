        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CustomerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class CustomerDAO {

    private static final String UPDATE = "UPDATE tblCustomer SET name=?, password=?, email=?, [address]=?, phone=?, point=?, [status]=?, [delete]=? WHERE customerID=?";


    //method này dùng để update Customer trong viewprofilePage - Huu Hieu

    public boolean updateCustomer(CustomerDTO cus) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, cus.getName());
                ptm.setString(2, cus.getPassword());
                ptm.setString(3, cus.getEmail());
                ptm.setString(4, cus.getAddress());
                ptm.setString(5, cus.getPhone());
                ptm.setInt(6, cus.getPoint());
                ptm.setString(7, cus.getStatus());
                ptm.setString(8, cus.getDelete());
                ptm.setString(9, cus.getCustomerID());
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
