/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import dto.CategoryDTO;
import dto.PublisherDTO;

>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
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
    private static final String FILTERBYPUB = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.publisherID LIKE ?";
    private static final String FILTERBYCATE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.categoryID LIKE ?";
    private static final String FILTERPRICE = "SELECT b.isbn, b.name, b.[author-name], b.publisherID, b.categoryID, b.price, b.quantity, b.image, c.Name AS 'cname', p.Name AS 'pname'\n"
            + "FROM tblBook b, tblCategory c, tblPublisher p\n"
            + "WHERE b.publisherID LIKE p.publisherID AND b.categoryID LIKE c.categoryID AND b.price BETWEEN ? AND ?";
    private static final String QUANTITY = "SELECT quantity FROM tblBook b, tblCategory c, tblPublisher p WHERE b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ? AND isbn LIKE ?";
    private static final String COUNT = "SELECT COUNT(*) AS 'number' FROM tblBook b, tblCategory c, tblPublisher p WHERE b.categoryID LIKE c.categoryID AND b.publisherID LIKE p.publisherID AND b.[status] LIKE ? AND c.[status] LIKE ? AND p.[status] LIKE ?";
    private static final String UPDATE = "UPDATE tblBook SET isbn = ?, name = ?, [author-name] = ?, publisherID = ?, categoryID = ?, [description] = ? ,quantity = ?, price = ?, [image] = ?, [status] = ? WHERE isbn = ?";
    private static final String DUPLICATE_ISBN = "SELECT * FROM tblBook WHERE isbn NOT LIKE ? AND isbn LIKE ?";
    private static final String CREATE_BOOK = "INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,reviewID,[Author-name],Price,[Image],Quantity,[Status],[Description])\n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    public List<BookDTO> getListBook(int page, String st) throws SQLException {
=======

    private static final String BOOK = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook ORDER BY price DESC";
    private static final String DETAIL = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE isbn LIKE ?";
    private static final String SEARCH_BOOK = "SELECT isbn, [name], publisherID, categoryID, [author-name], price, [image], quantity "
            + "FROM tblBook WHERE [name] LIKE ? OR isbn like ? OR [author-name] LIKE ?";
    private static final String FILTERBYPUB = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE publisherID=?";
    private static final String FILTERBYCATE = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE categoryID=?";
    private static final String FILTERPRICE = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE price BETWEEN ? AND ?";
    private static final String SORTPRICEDESC = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, [image] FROM tblBook WHERE categoryID=? ORDER BY price DESC";
    private static final String SORTPRICEASC = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, [image] FROM tblBook WHERE categoryID=? ORDER BY price ASC";
    private static final String SORTALPHABETDESC = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, [image] FROM tblBook WHERE categoryID = ? ORDER BY name DESC";
    private static final String SORTALPHABETASC = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, [image] FROM tblBook WHERE categoryID = ? ORDER BY name ASC";

    public List<BookDTO> getListBook(List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
<<<<<<< HEAD
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
=======
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(BOOK);
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
<<<<<<< HEAD
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
=======
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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

<<<<<<< HEAD
    public BookDTO loadBook(String isbnD, String st) throws SQLException {
=======
    public BookDTO loadBook(List<CategoryDTO> listCate, List<PublisherDTO> listPub, String isbnD) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        BookDTO book = new BookDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DETAIL);
                ptm.setString(1, isbnD);
<<<<<<< HEAD
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, st);
=======
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
<<<<<<< HEAD
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
=======
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    book = new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity);
>>>>>>> origin/Quốc-Phi-Branch
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

<<<<<<< HEAD
    public List<BookDTO> searchBook(String txtSearch, String st) throws SQLException {
=======
    public List<BookDTO> searchBook(String txtSearch, List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BOOK);
<<<<<<< HEAD
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, "%" + txtSearch + "%");
                ptm.setString(5, "%" + txtSearch + "%");
                ptm.setString(6, "%" + txtSearch + "%");
                ptm.setString(7, "%" + txtSearch + "%");
                ptm.setString(8, "%" + txtSearch + "%");
=======
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
<<<<<<< HEAD
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
=======
                    String authorName = rs.getString("author-name");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, authorName, price, image, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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

<<<<<<< HEAD
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
	
    public List<BookDTO> filterbyPub(String pubID) throws SQLException {
>>>>>>> Quang-Vinh-Branch
=======
    public List<BookDTO> filterbyPub(String pubID, String pubName, List<CategoryDTO> listCate) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYPUB);
<<<<<<< HEAD
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, pubID);
=======
                ptm.setString(1, pubID);
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
<<<<<<< HEAD
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
=======
                    int quantity = rs.getInt("quantity");
                    String category = "";
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(pubID, pubName), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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

<<<<<<< HEAD
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
=======
    public List<BookDTO> filterbyCate(String cateID, String cateName, List<PublisherDTO> listPub) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERBYCATE);
<<<<<<< HEAD
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, cateID);
=======
                ptm.setString(1, cateID);
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
<<<<<<< HEAD
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
=======
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(cateID, cateName), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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

<<<<<<< HEAD
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
=======
    public List<BookDTO> filterByPrice(String min, String max, List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(FILTERPRICE);
<<<<<<< HEAD
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, min);
                ptm.setString(5, max);
=======
                ptm.setString(1, min);
                ptm.setString(2, max);
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
<<<<<<< HEAD
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
=======
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD

<<<<<<< HEAD
    public List<BookDTO> filterByPrice9(String min, String max, int page, String st) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
=======
    public int quantityCheck(String isbn, String st) throws SQLException {
        int quantity = 0;
>>>>>>> Quang-Vinh-Branch
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
<<<<<<< HEAD
                ptm = conn.prepareCall(FILTERPRICE_9);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, min);
                ptm.setString(5, max);
                ptm.setInt(6, (page - 1) * 9);
=======
                ptm = conn.prepareCall(QUANTITY);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                ptm.setString(4, isbn);
>>>>>>> Quang-Vinh-Branch
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

<<<<<<< HEAD
=======
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

    public List<BookDTO> getAllBook(String st) throws SQLException {
=======
    
    public List<BookDTO> sortPriceDesc(List<CategoryDTO> listCate, List<PublisherDTO> listPub,String cateID) throws SQLException {
>>>>>>> origin/Quốc-Phi-Branch
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
<<<<<<< HEAD
                ptm = conn.prepareStatement(GET_BOOK);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
=======
                ptm = conn.prepareStatement(SORTPRICEDESC);
                ptm.setString(1,cateID); 
>>>>>>> origin/Quốc-Phi-Branch
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
<<<<<<< HEAD
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
=======
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD

    public int countBook(String st) throws SQLException {
        int count = 0;
=======
    
    public List<BookDTO> sortPriceAsc(List<CategoryDTO> listCate, List<PublisherDTO> listPub,String cateID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
>>>>>>> origin/Quốc-Phi-Branch
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
<<<<<<< HEAD
                ptm = conn.prepareCall(COUNT);
                ptm.setString(1, st);
                ptm.setString(2, st);
                ptm.setString(3, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("number");
=======
                ptm = conn.prepareStatement(SORTPRICEASC);
                ptm.setString(1,cateID); 
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD
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
=======
        return list;
    }
    
    public List<BookDTO> sortAlphabetDESC(List<CategoryDTO> listCate, List<PublisherDTO> listPub,String cateID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SORTALPHABETDESC);
                ptm.setString(1,cateID); 
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
>>>>>>> origin/Quốc-Phi-Branch
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
<<<<<<< HEAD
        return flag;
    }

    public boolean createBook(BookDTO book) throws SQLException{
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
=======
        return list;
    }
    
    public List<BookDTO> sortAlphabetASC(List<CategoryDTO> listCate, List<PublisherDTO> listPub,String cateID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SORTALPHABETASC);
                ptm.setString(1,cateID); 
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
                    list.add(new BookDTO(isbn, new PublisherDTO(publisherID, publisher), new CategoryDTO(categoryID, category), name, author, price, img, quantity));
                }
>>>>>>> origin/Quốc-Phi-Branch
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
<<<<<<< HEAD
=======
            if (rs != null) {
                rs.close();
            }
>>>>>>> origin/Quốc-Phi-Branch
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
<<<<<<< HEAD
        return check;
=======
        return list;
    }
    
    public List<BookDTO> sortPricePublisherDesc(List<CategoryDTO> listCate, List<PublisherDTO> listPub,String pubID) throws SQLException {
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SORTPRICEDESC);
                ptm.setString(1,pubID); 
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
                    String author = rs.getString("author-name");
                    String img = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String publisher = "";
                    String category = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
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
>>>>>>> origin/Quốc-Phi-Branch
    }

    
}
<<<<<<< HEAD
>>>>>>> Quang-Vinh-Branch
=======
>>>>>>> origin/Quốc-Phi-Branch
