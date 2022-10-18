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
    private static final String CREATE = "INSERT INTO [tblCustomer](customerID,Name,[Password],Email,[Address],Phone,Point,[Status],[Delete])\n"
            + "VALUES (?,?,?,?,?,?,?,0,0)";
    private static final String CHECK_CUSTOMER_ID = "SELECT customerID FROM tblCustomer WHERE customerID LIKE ?";
    private static final String CHECK_CUSTOMER_EMAIL = "SELECT [Email] FROM tblCustomer WHERE [Email] LIKE ?";

    public boolean createAccount(CustomerDTO customer) throws ClassNotFoundException, SQLException, NamingException {
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
                ptm.setInt(7, customer.getPoint());
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

    public boolean checkCustomerID(String customerID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_CUSTOMER_ID);
                ptm.setString(1, customerID);
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

    public boolean checkCustomerEmail(String customerEmail) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_CUSTOMER_EMAIL);
                ptm.setString(1, customerEmail);
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

    