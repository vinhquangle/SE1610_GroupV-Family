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

    private static final String LOGIN = "SELECT name, email, [address], phone, point, [status], [delete] FROM tblCustomer WHERE customerID LIKE ? AND [password] LIKE ?";
    private static final String UPDATE_STATUS = "UPDATE tblCustomer SET [status] = ? WHERE customerID LIKE ?";
    private static final String CREATE = "INSERT INTO [tblCustomer](customerID, name, password , email, address, phone, point, [status], [delete]) \n"
            + "VALUES (?,?,?,?,?,?,0,1,0)";
    private static final String CHECK_CUSTOMER_ID = "SELECT customerID FROM tblCustomer WHERE customerID LIKE ?";
    private static final String CHECK_CUSTOMER_EMAIL = "SELECT [Email] FROM tblCustomer WHERE [Email] LIKE ?";
    private static final String CUSTOMER = "SELECT customerID, password, name, email, [address], phone, point, [status], [delete] FROM tblCustomer WHERE [Email] LIKE ?";
    private static final String GET_CUSTOMER = "SELECT customerID, password, name, email, [address], phone, point, [status], [delete] FROM tblCustomer";
    private static final String UPDATE = "UPDATE tblCustomer SET customerID = ?, name = ?, password = ?, email = ?, [address] = ?, phone = ?, point = ?, [status] = ?, [delete] = ? WHERE customerID = ?";
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

    public CustomerDTO getCustomer(String email) throws SQLException {
        CustomerDTO cus = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CUSTOMER);
                ptm.setString(1, email);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String customerID = rs.getString("customerID");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    email = rs.getString("email");
                    String addr = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    cus = new CustomerDTO(customerID, name, password, email, addr, phone, point, status, delete);
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
     public List<CustomerDTO> getlistCustomer() throws SQLException {
        List<CustomerDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CUSTOMER);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String customerID = rs.getString("customerID");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String addr = rs.getString("address");
                    String phone = rs.getString("phone");
                    int point = rs.getInt("point");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    list.add(new CustomerDTO(customerID, name, password, email, addr, phone, point, status, delete));
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
     //method này dùng để add Customer của admin

     //method này dùng để update Customer trong viewprofilePage - Huu Hieu
     public boolean updateCustomer(CustomerDTO cus) throws SQLException{
         boolean check = false;
         Connection conn= null;
         PreparedStatement ptm= null;
         try{
             conn = DBUtils.getConnection();
             if(conn!=null){
                 ptm = conn.prepareCall(UPDATE);
                 ptm.setString(1, cus.getCustomerID());
                 ptm.setString(2, cus.getName());
                 ptm.setString(3, cus.getPassword());
                 ptm.setString(4, cus.getEmail());
                 ptm.setString(5, cus.getAddress());
                 ptm.setString(6, cus.getPhone());
                 ptm.setInt(7, cus.getPoint());
                 ptm.setString(8, cus.getStatus());
                 ptm.setString(9, cus.getDelete());
                 check= ptm.executeUpdate()>0?true:false;
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
