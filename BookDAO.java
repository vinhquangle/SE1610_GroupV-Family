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

    private static final String BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID\n"
            + "ORDER BY price DESC";
    private static final String DETAIL = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.ISBN LIKE ?";
    private static final String SEARCH_BOOK = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM (tblBook b LEFT JOIN tblCategory c ON  b.categoryID LIKE c.categoryID) LEFT JOIN tblPublisher p\n"
            + "ON b.publisherID LIKE p.publisherID\n"
            + "WHERE  b.[name] LIKE ? OR b.ISBN like ? OR b.[Author-name] LIKE ?\n"
            + "OR dbo.ufn_removeMark(b.Name) LIKE ? OR  dbo.ufn_removeMark(b.[Author-name]) LIKE ?";
    private static final String FILTERBYPUB = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.publisherID LIKE ?";
    private static final String FILTERBYCATE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.categoryID LIKE ?";
    private static final String FILTERPRICE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.price BETWEEN ? AND ?";
    private static final String QUANTITY = "SELECT quantity FROM tblBook WHERE isbn LIKE ?";
    private static final String CREATE_BOOK = "INSERT INTO tblBook (ISBN, Name, publisherID, categoryID, [Author-name], Price, [Image], Quantity) "
             + "VALUES (?,?,?,?,?,0,?,0)";
    private static final String CHECK_DUPLICATE_ISBN = "SELECT isbn FROM tblBook WHERE isbn LIKE ?";

    public List<BookDTO> getListBook() throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(BOOK);
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
                    book = new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity);
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
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, authorName, price, image, quantity));
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
    
    //Ham nay dung de them 1 quyen sach moi - Quang Vinh
    public boolean createBook(BookDTO book) throws ClassNotFoundException, SQLException, NamingException {
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
                ptm.setString(5, book.getAuthorName());
                ptm.setDouble(6, book.getPrice());
                ptm.setString(7, book.getImg());
                ptm.setInt(8, book.getQuantity());
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
    
    public boolean checkDuplicateIsbn(String isbn) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE_ISBN);
                ptm.setString(1, isbn);
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
}
