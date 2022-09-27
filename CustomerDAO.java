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
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class CustomerDAO {

    private static final String LOGIN = "SELECT name, email, [address], phone, point, [status], [delete] FROM tblCustomer WHERE customerID LIKE ? AND [password] LIKE ?";
    private static final String UPDATE_STATUS_ONLINE = "UPDATE tblCustomer SET [status] = ? WHERE customerID LIKE ?";
    private static final String CHECK_CUSTOMER_ID="SELECT customerID FROM tblCustomer WHERE customerID=?";
    private static final String CHECK_CUSTOMER_EMAIL="SELECT [Email] FROM tblCustomer WHERE [Email]=?";
    private static final String REGISTER="INSERT INTO [tblCustomer](customerID,Name,[Password],Email,[Address],"
 + "Phone,Point,[Status],[Delete]) VALUES (?,?,?,?,?,?,?,?,?)";
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
                ptm = conn.prepareStatement(UPDATE_STATUS_ONLINE);
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

//check duplicate ID for customer 
    public boolean checkCustomerID(String customerID) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(CHECK_CUSTOMER_ID);
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

//check duplicate email for customer 
    public boolean checkCustomerEmail(String customerEmail) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(CHECK_CUSTOMER_EMAIL);
                ptm.setString(1, customerEmail);
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

    public boolean register(CustomerDTO cus) throws SQLException{
        boolean check=false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(REGISTER);
                ptm.setString(1,cus.getCustomerID());
                ptm.setString(2,cus.getName());
                ptm.setString(3,cus.getPassword());
                ptm.setString(4,cus.getEmail());
                ptm.setString(5,cus.getAddress());
                ptm.setString(5,cus.getPhone());
                ptm.setInt(6,cus.getPoint());
                ptm.setBoolean(7, true);
                ptm.setBoolean(8, false);
                ptm.setBoolean(8, false);
                check=ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
}
