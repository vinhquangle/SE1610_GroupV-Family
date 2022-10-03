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

    private static final String BOOK = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook ORDER BY price DESC";
    private static final String DETAIL = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE isbn LIKE ?";
    private static final String SEARCH_BOOK = "SELECT isbn, [name], publisherID, categoryID, [author-name], price, [image], quantity "
            + "FROM tblBook WHERE [name] LIKE ? OR isbn like ? OR [author-name] LIKE ?";
    private static final String FILTERBYPUB = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE publisherID=?";
    private static final String FILTERBYCATE = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE categoryID=?";
    private static final String FILTERPRICE = "SELECT isbn, name, [author-name], publisherID, categoryID, price, quantity, image FROM tblBook WHERE price BETWEEN ? AND ?";

    public List<BookDTO> getListBook(List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
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
    }

    public BookDTO loadBook(List<CategoryDTO> listCate, List<PublisherDTO> listPub, String isbnD) throws SQLException {
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

    public List<BookDTO> searchBook(String txtSearch, List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
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
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String name = rs.getString("name");
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

    public List<BookDTO> filterbyPub(String pubID, String pubName, List<CategoryDTO> listCate) throws SQLException {
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
                    String category = "";
                    String categoryID = rs.getString("categoryID");
                    for (CategoryDTO cate : listCate) {
                        if (cate.getCategoryID().equals(categoryID)) {
                            category = cate.getName();
                        }
                    }
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

    public List<BookDTO> filterbyCate(String cateID, String cateName, List<PublisherDTO> listPub) throws SQLException {
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
                    String publisher = "";
                    String publisherID = rs.getString("publisherID");
                    for (PublisherDTO pub : listPub) {
                        if (pub.getPublisherID().equals(publisherID)) {
                            publisher = pub.getName();
                        }
                    }
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

    public List<BookDTO> filterByPrice(String min, String max, List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException {
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
