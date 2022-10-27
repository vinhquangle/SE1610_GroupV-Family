/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CategoryDTO;
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
public class CategoryDAO {

    private static final String CATEGORY = "SELECT categoryID, name, [status] FROM tblCategory WHERE [status] LIKE ?";
    private static final String GET_CATE = "SELECT categoryID, name, [status] FROM tblCategory WHERE categoryID LIKE ? OR name LIKE ?";
    private static final String GET_CATE_ID = "SELECT categoryID, name, [status] FROM tblCategory WHERE categoryID LIKE ?";
    private static final String UPDATE = "UPDATE tblCategory SET categoryID = ?, name = ?, [status] = ? WHERE categoryID = ?";
    private static final String GET_9_CATE = "SELECT categoryID, name, [status]\n"
            + "FROM tblCategory \n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String SEARCH_CATE = "SELECT categoryID, name, [status] FROM tblCategory \n"
            + "WHERE  name LIKE ? OR categoryID LIKE ?\n"
            + "OR dbo.ufn_removeMark(name) LIKE ?";
    private static final String SEARCH_9_CATE = "SELECT categoryID, name, [status] FROM tblCategory \n"
            + "WHERE  name LIKE ? OR categoryID LIKE ?\n"
            + "OR dbo.ufn_removeMark(name) LIKE ?\n"
            + "ORDER BY [status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String COUNT = "SELECT COUNT(*) AS 'number' FROM tblCategory";
    private static final String CREATE = "INSERT INTO tblCategory(categoryID, name, [status]) VALUES(?,?,?)";
    private static final String DUPLICATE_ID = "SELECT categoryID FROM tblCategory WHERE categoryID = ?";
    private static final String DUPLICATE_NAME = "SELECT name FROM tblCategory WHERE name = ?";

    public List<CategoryDTO> getListCategory(String st) throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(CATEGORY);
                ptm.setString(1, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new CategoryDTO(categoryID, name, status));
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

    public CategoryDTO getCategory(String key) throws SQLException {
        CategoryDTO cate = new CategoryDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CATE);
                ptm.setString(1, key);
                ptm.setString(2, key);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    cate = new CategoryDTO(categoryID, name, status);
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
        return cate;
    }

    public List<CategoryDTO> getList9Category(int page) throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_9_CATE);
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new CategoryDTO(categoryID, name, status));
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
    //method nay de load category đang đc update. - Hữu Hiếu

    public CategoryDTO loadCate(String cateIDN) throws SQLException {
        CategoryDTO cate = new CategoryDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CATE_ID);
                ptm.setString(1, cateIDN);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    cate = new CategoryDTO(categoryID, name, status);
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
        return cate;
    }
    //method này dùng để search Category - Hữu Hiếu

    public List<CategoryDTO> searchCate(String txtSearch) throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_CATE);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new CategoryDTO(categoryID, name, status));
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

    // hàm này dùng để search theo phân trang - Hữu Hiếu
    public List<CategoryDTO> searchCate9(String txtSearch, int page) throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_9_CATE);
                ptm.setString(1, "%" + txtSearch + "%");
                ptm.setString(2, "%" + txtSearch + "%");
                ptm.setString(3, "%" + txtSearch + "%");
                ptm.setInt(4, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    list.add(new CategoryDTO(categoryID, name, status));
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

    //hàm này để update category - Hữu Hiếu
    public boolean updateCate(CategoryDTO cate, String cateIDN) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE);
                ptm.setString(1, cate.getCategoryID());
                ptm.setString(2, cate.getName());
                ptm.setString(3, cate.getStatus());
                ptm.setString(4, cateIDN);
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
    //method này dùng để đúng số lượng category có trong database - Hữu Hiếu

    public int countCate() throws SQLException {
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

    //method này dùng tạo category - Hữu Hiếu
    public boolean createCate(CategoryDTO cate) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, cate.getCategoryID());
                ptm.setString(2, cate.getName());
                ptm.setString(3, cate.getStatus());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                ptm.close();
            }
        }
        return check;
    }

    //method này dùng để check trùng ID - Hữu Hiếu
    public boolean checkDuplicateID(String cateID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DUPLICATE_ID);
                ptm.setString(1, cateID);
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

    //method này dùng để check trùng name - Hữu Hiếu
    public boolean checkDuplicateName(String name) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DUPLICATE_NAME);
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
