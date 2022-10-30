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

    private static final String GET_9_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.reviewID LIKE r.reviewID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ?\n"
            + "ORDER BY [status] DESC";
    private static final String DETAIL = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p, tblReview r\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID  AND b.reviewID LIKE r.reviewID AND b.ISBN LIKE ?  AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ?";
    private static final String SEARCH_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM ((tblBook b LEFT JOIN tblCategory c ON  b.categoryID LIKE c.categoryID) LEFT JOIN tblPublisher p\n"
            + "ON b.publisherID LIKE p.publisherID) LEFT JOIN tblReview r ON b.reviewID LIKE r.reviewID\n"
            + "WHERE b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND ( b.[name] LIKE ? OR b.ISBN like ?  OR b.[Author-name] LIKE ?\n"
            + "OR dbo.ufn_removeMark(b.Name) LIKE ? OR  dbo.ufn_removeMark(b.[Author-name]) LIKE ?)";
    private static final String SEARCH_BOOK_GET9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, b.reviewID, b.[description], c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus', r.Rate AS 'rate', r.Times AS 'times', r.[status] AS 'rstatus'\n"
            + "FROM ((tblBook b LEFT JOIN tblCategory c ON  b.categoryID LIKE c.categoryID) LEFT JOIN tblPublisher p\n"
            + "ON b.publisherID LIKE p.publisherID) LEFT JOIN tblReview r ON b.reviewID LIKE r.reviewID\n"
            + "WHERE b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND (b.[name] LIKE ? OR b.ISBN like ? OR b.[Author-name] LIKE ?\n"
            + "OR dbo.ufn_removeMark(b.Name) LIKE ? OR  dbo.ufn_removeMark(b.[Author-name]) LIKE ?)\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
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
    private static final String QUANTITY = "SELECT quantity FROM tblBook b, tblCategory c, tblPublisher p WHERE b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND isbn LIKE ?";
    private static final String COUNT = "SELECT COUNT(*) AS 'number' FROM tblBook b, tblCategory c, tblPublisher p WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ?";
    private static final String UPDATE = "UPDATE tblBook SET isbn = ?, name = ?, [author-name] = ?, publisherID = ?, categoryID = ?, [description] = ? ,quantity = ?, price = ?, [image] = ?, [status] = ? WHERE isbn = ?";
    private static final String DUPLICATE_ISBN = "SELECT * FROM tblBook WHERE isbn NOT LIKE ? AND isbn LIKE ?";
    private static final String CREATE_BOOK = "INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,reviewID,[Author-name],Price,[Image],Quantity,[Status],[Description])\n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUANTITY = "UPDATE tblBook SET quantity = quantity - ? WHERE ISBN LIKE ?";
    private static final String REFUND_QUANTITY = "UPDATE tblBook SET quantity = quantity + ? WHERE ISBN LIKE ?";

    public List<BookDTO> getListBook(int page, String st) throws SQLException {
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
                ptm = conn.prepareStatement(GET_9_BOOK);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setInt(4, (page - 1) * 9);
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

    public BookDTO loadBook(String isbnD, String st) throws SQLException {
        BookDTO book = new BookDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DETAIL);
                ptm.setString(1, isbnD);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, st);
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
                    book = new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), new ReviewDTO(reviewID, rate, times, rstatus), name, author, price, description, img, quantity, status);
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
        return book;
    }
//Ham nay dung de search Book By ISBN/Title/Author-name - Quang Vinh

    public List<BookDTO> searchBook(String txtSearch, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BOOK);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, "%" + txtSearch + "%");
                ptm.setString(6, "%" + txtSearch + "%");
                ptm.setString(7, "%" + txtSearch + "%");
                ptm.setString(8, "%" + txtSearch + "%");
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

    public List<BookDTO> searchBook9(String txtSearch, int page, String st) throws SQLException {
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
                ptm = conn.prepareStatement(SEARCH_BOOK_GET9);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, "%" + txtSearch + "%");
                ptm.setString(6, "%" + txtSearch + "%");
                ptm.setString(7, "%" + txtSearch + "%");
                ptm.setString(8, "%" + txtSearch + "%");
                ptm.setInt(9, (page - 1) * 9);
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

    public int quantityCheck(String isbn, String st) throws SQLException {
        int quantity = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(QUANTITY);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, isbn);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    quantity = rs.getInt("quantity");
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
        return quantity;
    }

    public boolean updateBook(BookDTO book, String isbnN) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE);
                ptm.setString(1, book.getIsbn());
                ptm.setString(2, book.getName());
                ptm.setString(3, book.getAuthorName());
                ptm.setString(4, book.getPublisher().getPublisherID());
                ptm.setString(5, book.getCategory().getCategoryID());
                ptm.setString(6, book.getDescription());
                ptm.setString(7, String.valueOf(book.getQuantity()));
                ptm.setString(8, String.valueOf(book.getPrice()));
                ptm.setString(9, book.getImg());
                ptm.setString(10, book.getStatus());
                ptm.setString(11, isbnN);
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

    public boolean updateQuantity(String isbn, int quantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_QUANTITY);
                ptm.setInt(1, quantity);
                ptm.setString(2, isbn);
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
    public boolean refundQuantity(String isbn, int quantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(REFUND_QUANTITY);
                ptm.setInt(1, quantity);
                ptm.setString(2, isbn);
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

    public List<BookDTO> getAllBook(String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BOOK);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
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

    public int countBook(String st) throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(COUNT);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("number");
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
        return count;
    }

    public boolean checkIsbn(String isbn, String isbnN) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(DUPLICATE_ISBN);
                ptm.setString(1, isbnN);
                ptm.setString(2, isbn);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    flag = true;
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
        return flag;
    }

    public boolean createBook(BookDTO book) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE_BOOK);
                ptm.setString(1, book.getIsbn());
                ptm.setString(2, book.getName());
                ptm.setString(3, book.getPublisher().getPublisherID());
                ptm.setString(4, book.getCategory().getCategoryID());
                ptm.setString(5, book.getReview().getReviewID());
                ptm.setString(6, book.getAuthorName());
                ptm.setDouble(7, book.getPrice());
                ptm.setString(8, book.getImg());
                ptm.setInt(9, book.getQuantity());
                ptm.setString(10, book.getStatus());
                ptm.setString(11, book.getDescription());
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
