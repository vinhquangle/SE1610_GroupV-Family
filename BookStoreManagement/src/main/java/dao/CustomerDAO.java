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
import javax.naming.NamingException;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class CustomerDAO {

    private static final String LOGIN = "SELECT name, email, [address], phone, point, [status], [delete] FROM tblCustomer WHERE customerID LIKE ? AND [password] LIKE ?";
    private static final String UPDATE_STATUS = "UPDATE tblCustomer SET [status] = ? WHERE customerID LIKE ?";
    private static final String CREATE = "INSERT INTO [tblCustomer](customerID,Name,[Password],Email,[Address],Phone,Point,[Status],[Delete]) \n"
            + "VALUES (?,?,?,?,?,?,0,0,0)";

    public CustomerDTO checkLogin(String userID, String password) throws SQLException {
        CustomerDTO cus = null;
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
                    String email = rs.getString("email");
                    String addr = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    cus = new CustomerDTO(userID, name, password, email, addr, phone, point, status, delete);
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
        return cus;
    }

    public boolean updateStatusOnline(String cusID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String status = "1";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, cusID);
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

    public boolean updateStatusOffline(String cusID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String status = "0";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, cusID);
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

    public boolean addAccount(CustomerDTO customer) throws ClassNotFoundException, SQLException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, customer.getCustomerID());
                ptm.setString(2, customer.getName());
                ptm.setString(3, customer.getPassword());
                ptm.setString(4, customer.getEmail());
                ptm.setString(5, customer.getAddress());
                ptm.setString(6, customer.getPhone());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
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
