/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.PublisherDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class PublisherDAO {

    private static final String PUBLISHER = "SELECT publisherID, name, [status] FROM tblPublisher";
    private static final String GET_PUB = "SELECT publisherID, name, [status] FROM tblPublisher WHERE publisherID LIKE ? OR name LIKE ?";
   
    
    
    //Ham nay dung de lay list Publisher
    public List<PublisherDTO> getListPublisher() throws SQLException {
        List<PublisherDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(PUBLISHER);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String publisherID = rs.getString("publisherID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new PublisherDTO(publisherID, name, status));
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
