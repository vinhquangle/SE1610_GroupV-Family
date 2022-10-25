/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class DBUtils {

    public static  Connection getConnection() throws ClassNotFoundException, SQLException, NamingException, NamingException, NamingException, NamingException, NamingException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=BookStoreManagement";
        conn = DriverManager.getConnection(url, "sa", "12345");
        return conn;
    }
}   
