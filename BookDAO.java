/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;
import dto.ReviewDTO;
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
    private static final String FILTERBYPUB_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.publisherID LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERBYCATE_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.categoryID LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERPRICE_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.price BETWEEN ? AND ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERBYPUB = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.publisherID LIKE ?";
    private static final String FILTERBYCATE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.categoryID LIKE ?";
    private static final String FILTERPRICE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND b.price BETWEEN ? AND ?";


    public List<BookDTO> filterbyPub(String pubID, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYPUB);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, pubID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

    public List<BookDTO> filterbyPub9(String pubID, int page, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYPUB_9);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, pubID);
                ptm.setInt(5, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

    public List<BookDTO> filterbyCate(String cateID, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYCATE);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, cateID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

    public List<BookDTO> filterbyCate9(int page, String cateID, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYCATE_9);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, cateID);
                ptm.setInt(5, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

    public List<BookDTO> filterByPrice(String min, String max, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERPRICE);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, min);
                ptm.setString(5, max);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

    public List<BookDTO> filterByPrice9(String min, String max, int page, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERPRICE_9);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, min);
                ptm.setString(5, max);
                ptm.setInt(6, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    String reviewID = rs.getString("reviewID");
                    double rate = rs.getDouble("rate");
                    int times = rs.getInt("times");
                    String rstatus = rs.getString("rstatus");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status));
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

