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

    private static final String BOOK = "SELECT isbn, [name], publisherID, categoryID, [author-name], price, [image], quantity FROM tblBook";
    
    private static final String SEARCH_BOOK = "SELECT isbn, [name], publisherID, categoryID, [author-name], price, [image], quantity  "
            + "FROM tblBook WHERE [name] like ? or isbn like ? or [author-name] like ?";


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
    
    //Ham nay dung de search Book By ISBN/Title/Author-name - Quang Vinh
    public List<BookDTO> SearchBook(String txtSearch, List<CategoryDTO> listCate, List<PublisherDTO> listPub) throws SQLException{
        List<BookDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(SEARCH_BOOK);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                rs = ptm.executeQuery();               
                while(rs.next()){
                    String isbn = rs.getString("isbn");                                     
                    String name = rs.getString("name");
                    String authorName = rs.getString("author-name");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
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
                    BookDTO book = new BookDTO(isbn, publisherID, categoryID, name, authorName, price, image, quantity);
                    list.add(book);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(rs!=null) rs.close();
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return list;
    }
    
    
}
