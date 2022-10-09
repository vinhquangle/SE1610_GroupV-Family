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

    private static final String GET_9_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID\n"
            + "ORDER BY [status] DESC";
    private static final String DETAIL = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.ISBN LIKE ?";
    private static final String SEARCH_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM (tblBook b LEFT JOIN tblCategory c ON  b.categoryID LIKE c.categoryID) LEFT JOIN tblPublisher p\n"
            + "ON b.publisherID LIKE p.publisherID\n"
            + "WHERE  b.[name] LIKE ? OR b.ISBN like ? OR b.[Author-name] LIKE ?\n"
            + "OR dbo.ufn_removeMark(b.Name) LIKE ? OR  dbo.ufn_removeMark(b.[Author-name]) LIKE ?";
    private static final String SEARCH_BOOK_GET9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM (tblBook b LEFT JOIN tblCategory c ON  b.categoryID LIKE c.categoryID) LEFT JOIN tblPublisher p\n"
            + "ON b.publisherID LIKE p.publisherID\n"
            + "WHERE  b.[name] LIKE ? OR b.ISBN like ? OR b.[Author-name] LIKE ?\n"
            + "OR dbo.ufn_removeMark(b.Name) LIKE ? OR  dbo.ufn_removeMark(b.[Author-name]) LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERBYPUB_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.publisherID LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERBYCATE_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.categoryID LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERPRICE_9 = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.price BETWEEN ? AND ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String FILTERBYPUB = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.publisherID LIKE ?";
    private static final String FILTERBYCATE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.categoryID LIKE ?";
    private static final String FILTERPRICE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, b.status, c.Name AS 'cname', c.[status] AS 'cstatus', p.Name AS 'pname', p.[status] AS 'pstatus'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.price BETWEEN ? AND ?";
    private static final String QUANTITY = "SELECT quantity FROM tblBook WHERE isbn LIKE ?";
    private static final String COUNT = "SELECT COUNT(*) AS 'number' FROM tblBook";
    private static final String UPDATE = "UPDATE tblBook SET isbn = ?, name = ?, [author-name] = ?, publisherID = ?, categoryID = ?, quantity = ?, price = ?, [image] = ?, [status] = ? WHERE isbn = ?";

    public List<BookDTO> getListBook(int page) throws SQLException {
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
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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

    public BookDTO loadBook(String isbnD) throws SQLException {
        BookDTO book = new BookDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DETAIL);
                ptm.setString(1, isbnD);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    book = new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status);
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

    public List<BookDTO> searchBook(String txtSearch) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BOOK);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, "%" + txtSearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String authorName = rs.getString("author-name");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, authorName, price, image, quantity, status));
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

    public List<BookDTO> searchBook_9(String txtSearch, int page) throws SQLException {
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
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, "%" + txtSearch + "%");
                ptm.setInt(6, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String authorName = rs.getString("author-name");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, authorName, price, image, quantity, status));
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
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(pubID, pubName, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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

    public List<BookDTO> filterbyPub_9(String pubID, int page) throws SQLException {
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
                ptm.setString(1, pubID);
                ptm.setInt(2, (page - 1) * 9);
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
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(pubID, pubName, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(cateID, cateName, cstatus), name, author, price, img, quantity, status));
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

    public List<BookDTO> filterbyCate_9(int page, String cateID) throws SQLException {
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
                ptm.setString(1, cateID);
                ptm.setInt(2, (page - 1) * 9);
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
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(cateID, cateName, cstatus), name, author, price, img, quantity, status));
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
                    String publisherID = rs.getString("publisherID");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String categoryID = rs.getString("categoryID");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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

    public List<BookDTO> filterByPrice_9(String min, String max, int page) throws SQLException {
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
                ptm.setString(1, min);
                ptm.setString(2, max);
                ptm.setInt(3, (page - 1) * 9);
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
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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

    public int quantityCheck(String isbn) throws SQLException {
        int quantity = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(QUANTITY);
                ptm.setString(1, isbn);
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
                ptm.setString(6, String.valueOf(book.getQuantity()));
                ptm.setString(7, String.valueOf(book.getPrice()));
                ptm.setString(8, book.getImg());
                ptm.setString(9, book.getStatus());
                ptm.setString(10, isbnN);
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

    public List<BookDTO> getAllBook() throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BOOK);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = rs.getString("pname");
                    String category = rs.getString("cname");
                    String publisherID = rs.getString("publisherID");
                    String categoryID = rs.getString("categoryID");
                    String pstatus = rs.getString("pstatus");
                    String cstatus = rs.getString("cstatus");
                    String status = rs.getString("status");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher, pstatus), new CategoryDTO(categoryID, category, cstatus), name, author, price, img, quantity, status));
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

    public int countBook() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(COUNT);
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
}
