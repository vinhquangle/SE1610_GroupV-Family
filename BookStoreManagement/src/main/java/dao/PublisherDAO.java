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
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class PublisherDAO {

    private static final String PUBLISHER = "SELECT publisherID, name, [status] FROM tblPublisher WHERE [status] LIKE ?";
    private static final String GET_PUB = "SELECT publisherID, name, [status] FROM tblPublisher WHERE publisherID LIKE ? OR name LIKE ?";
    private static final String GET_PUB_ID = "SELECT publisherID, name, [status] FROM tblPublisher WHERE publisherID LIKE ?";
    private static final String SEARCH_PUB = "SELECT publisherID, Name, [status]\n"
            + "FROM  tblPublisher\n"
            + "WHERE name LIKE ? OR publisherID LIKE ?\n"
            + "OR dbo.ufn_removeMark(name) LIKE ?";
    private static final String SEARCH_9_PUB = "SELECT publisherID, name, [status]\n"
            + "FROM  tblPublisher\n"
            + "WHERE name LIKE ? OR publisherID LIKE ?\n"
            + "OR dbo.ufn_removeMark(name) LIKE ?\n"
            + "            ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_9_PUB = "SELECT publisherID, name, [status]\n"
            + "            FROM  tblPublisher\n"
            + "            ORDER BY [status] DESC\n"
            + "            OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String COUNT = "SELECT COUNT(*) AS 'number' FROM tblPublisher";
    private static final String UPDATE = "UPDATE tblPublisher SET publisherID = ?, name = ?, [status] = ? WHERE publisherID = ?";
    private static final String CREATE = "INSERT INTO tblPublisher(publisherID, name, [status]) VALUES (?,?,?)";
    private static final String CHECK_ID = "SELECT publisherID FROM tblPublisher WHERE publisherID=?";
    private static final String CHECK_NAME = "SELECT name FROM tblPublisher WHERE name LIKE ?";

    public List<PublisherDTO> getListPublisher(String st) throws SQLException {
        List<PublisherDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(PUBLISHER);
                ptm.setString(1, st);
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
    //Ham nay dung de lay publisher theo ID - Quang Vinh

    public PublisherDTO getPublisherByID(String pubB) throws SQLException {
        PublisherDTO pub = new PublisherDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PUB_ID);
                ptm.setString(1, pubB);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String publisherID = rs.getString("publisherID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    pub = new PublisherDTO(publisherID, name, status);
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
        return pub;
    }

    //Ham nay dung de lay 6 Publisher - Quang Vinh
    public List<PublisherDTO> getListPublisher9(int page) throws SQLException {
        List<PublisherDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_9_PUB);
                ptm.setInt(1, (page - 1) * 9);
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

    //Ham nay dung de lay List Publisher khi search by ID hoac Name 
    public PublisherDTO getPublisher(String key) throws SQLException {
        PublisherDTO pub = new PublisherDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PUB);
                ptm.setString(1, key);
                ptm.setString(2, key);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String publisherID = rs.getString("publisherID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    pub = new PublisherDTO(publisherID, name, status);
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
        return pub;
    }

    //Ham nay dung de search publisher by Name - Quang Vinh
    public List<PublisherDTO> searchPub(String txtSearch) throws SQLException {
        List<PublisherDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PUB);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String pubID = rs.getString("publisherID");
                    String pubName = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new PublisherDTO(pubID, pubName, status));
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

    //Ham nay dung de search 6 publisher by Name va ID, thuc hien phan trang - Quang Vinh
    public List<PublisherDTO> searchPub9(String txtSearch, int page) throws SQLException {
        List<PublisherDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_9_PUB);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setInt(4, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String pubID = rs.getString("publisherID");
                    String pubName = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new PublisherDTO(pubID, pubName, status));
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

    //Ham nay dung de dem so luong publisher - Quang Vinh
    public int countPub() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(COUNT);
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

    //Ham nay dung de update thong tin cua publisher - Quang Vinh
    public boolean updatePub(PublisherDTO pub, String pubB) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, pub.getPublisherID());
                ptm.setString(2, pub.getName());
                ptm.setString(3, pub.getStatus());
                ptm.setString(4, pubB);
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

    //Ham nay dung de them 1 publisher - Quang Vinh
    public boolean create(PublisherDTO pub) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, pub.getPublisherID());
                ptm.setString(2, pub.getName());
                ptm.setString(3, pub.getStatus());
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

    //Ham nay dung de kiem tra duplicate publisherID - Quang Vinh
    public boolean checkDuplicateID(String pubID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_ID);
                ptm.setString(1, pubID);
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

    //Ham nay dung de kiem tra duplicate name - Quang Vinh
    public boolean checkDuplicateName(String name) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_NAME);
                ptm.setString(1, name);
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
