/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import dto.CategoryDTO;
import dto.CustomerDTO;
import dto.PublisherDTO;
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
public class BookDAO {

    
    
    private static final String FILTERBYPUB = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.publisherID LIKE ?";
    private static final String FILTERBYCATE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.categoryID LIKE ?";
    private static final String FILTERPRICE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.price BETWEEN ? AND ?";
   

   

    public List<BookDTO> filterbyPub(String pubID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYPUB);
                ptm.setString(1, pubID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String category = rs.getString("cname");
                    String categoryID = rs.getString("categoryID");
                    String pubName = rs.getString("pname");
                    list.add(new BookDTO(isbn, new PublisherDTO(pubID, pubName), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
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

    public List<BookDTO> filterbyCate(String cateID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYCATE);
                ptm.setString(1, cateID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String publisherID = rs.getString("publisherID");
                    String cateName = rs.getString("cname");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(cateID, cateName), name, author, price, img, quantity));
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

    public List<BookDTO> filterByPrice(String min, String max) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERPRICE);
                ptm.setString(1, min);
                ptm.setString(2, max);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
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

    
}
