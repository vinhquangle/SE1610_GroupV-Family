/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
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
public class BookDAO {

    private static final String BOOK = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook";
    private static final String FILTERBYCATE = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE categoryID=?";
    private static final String FILTERBYPUB = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE publisherID=?";
    private static final String FILTERPRICEUNDER = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE price BETWEEN ? AND ?";
    private static final String FILTERPRICEMID = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE price BETWEEN 60000 AND 150000";
    public List<BookDTO> getListBook(List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(BOOK);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    String publisherID = "";
                    String categoryID = "";
                    String tmp1 = rs.getString("publisherID");
                    for (PublisherDTO publisher : listPub) {
                        if (publisher.getPublisherID().equals(tmp1)) {
                            publisherID = publisher.getName();
                        }
                    }
                    String tmp2 = rs.getString("categoryID");
                    for (CategoryDTO category : listCate) {
                        if (category.getCategoryID().equals(tmp2)) {
                            categoryID = category.getName();
                        }
                    }
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");

                    list.add(new BookDTO(isbn, publisherID, categoryID, name, author, price, img, quantity));
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
    // method này để filter book by category nhưng bằng categoryID
    public List<BookDTO> filterbyCate(String cateID, List<CategoryDTO> listCatebyID) throws SQLException {
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
                    String publisherID = rs.getString("publisherID");
                    String categoryID = "";
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String tmp1 = rs.getString("categoryID");
                    for (CategoryDTO category : listCatebyID) {
                        if (category.getCategoryID().equals(tmp1)) {
                            categoryID = category.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, publisherID, categoryID, name, author, price, img, quantity));
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

    public List<BookDTO> filterbyPub(String pubID,List<CategoryDTO> listCate) throws SQLException {
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
                    String categoryID = "";
                    String tmp1 = rs.getString("categoryID");
                    for (CategoryDTO category : listCate) {
                        if (category.getCategoryID().equals(tmp1)) {
                            categoryID = category.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, pubID, categoryID, name, author, price, img, quantity));
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
    public List<BookDTO> getListBookPrice(String min,String max,List<CategoryDTO> listCate) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERPRICEUNDER);
                ptm.setString(1, min);
                ptm.setString(2, max);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    String publisherID = "publisherID";
                    String categoryID = "";
                    String tmp1 = rs.getString("categoryID");
                    for (CategoryDTO category : listCate) {
                        if (category.getCategoryID().equals(tmp1)) {
                            categoryID = category.getName();
                        }
                    }
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");

                    list.add(new BookDTO(isbn, publisherID, categoryID, name, author, price, img, quantity));
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
