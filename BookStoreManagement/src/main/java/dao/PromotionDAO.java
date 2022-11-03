/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.PromotionDTO;
import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author PCPV
 */
public class PromotionDAO {
    
    private static final String SEARCH_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo\n"
            + "LEFT JOIN tblStaff s ON s.staffID = promo.staffID\n"
            + "WHERE promo.promotionID LIKE ? OR promo.[Description] LIKE ?\n"
            + "OR promo.Discount LIKE ? OR s.Name LIKE ? OR s.staffID LIKE ?\n"
            + "OR promo.[Date-start] LIKE ? OR promo.[Date-end] LIKE ?\n"
            + "OR dbo.ufn_removeMark(promo.promotionID) LIKE ?\n"
            + "OR dbo.ufn_removeMark(promo.[Description]) LIKE ?\n"
            + "OR dbo.ufn_removeMark(s.Name) LIKE ?";
    private static final String SEARCH_9_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo\n"
            + "LEFT JOIN tblStaff s ON s.staffID = promo.staffID\n"
            + "WHERE promo.promotionID LIKE ? OR promo.[Description] LIKE ?\n"
            + "OR promo.Discount LIKE ? OR s.Name LIKE ? OR s.staffID LIKE ? \n"
            + "OR promo.[Date-start] LIKE ? OR promo.[Date-end] LIKE ?\n"
            + "OR dbo.ufn_removeMark(promo.promotionID) LIKE ?\n"
            + "OR dbo.ufn_removeMark(promo.[Description]) LIKE ?\n"
            + "OR dbo.ufn_removeMark(s.Name) LIKE ?\n"
            + "ORDER BY promo.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String LOAD_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n"
            + "ORDER By promo.[Status] DESC";
    private static final String LOAD_9_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n"
            + "ORDER BY promo.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String GET_PROMOTION_BY_ID = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n"
            + "WHERE promo.[promotionID] LIKE ?\n"
            + "ORDER By promo.[Status] DESC";
    private static final String UPDATE_PROMOTION = "UPDATE [tblPromotion] SET [Date-start]=?, [Date-end]=?, [Description]=?, [Condition]=?, [Discount]=?, [Status]=?\n"
            + "WHERE promotionID=?";
    private static final String CREATE_PROMOTION = "INSERT INTO [tblPromotion]( staffID, [Date-start], [Date-end], [Description], [Condition], [Discount], [Status])\n"
            + "VALUES(?,?,?,?,?,?,?)";
    private static final String GET_PROMOTION_LIST = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
            + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
            + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
            + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
            + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
            + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
            + "FROM tblPromotion promo LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n"
            + "WHERE promo.[Status] LIKE ?\n"
            + "ORDER BY promo.[status] DESC";
    
    public List<PromotionDTO> loadAvailablePromotion(String st) throws SQLException {
        List<PromotionDTO> listPromotion = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PROMOTION_LIST);
                ptm.setString(1, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    listPromotion.add(new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status));
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
        return listPromotion;
    }

    public List<PromotionDTO> loadPromotion() throws SQLException {
        List<PromotionDTO> listPromotion = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_PROMOTION);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    listPromotion.add(new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status));
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
        return listPromotion;
    }
    
    public List<PromotionDTO> searchPromotion(String search) throws SQLException {
        List<PromotionDTO> listPromotion = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PROMOTION);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                ptm.setString(3, "%" + search + "%");
                ptm.setString(4, "%" + search + "%");
                ptm.setString(5, "%" + search + "%");
                ptm.setString(6, "%" + search + "%");
                ptm.setString(7, "%" + search + "%");
                ptm.setString(8, "%" + search + "%");
                ptm.setString(9, "%" + search + "%");
                ptm.setString(10, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    listPromotion.add(new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status));
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
        return listPromotion;
    }
    
    public List<PromotionDTO> load9Promotion(int page) throws SQLException {
        List<PromotionDTO> listSearch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_9_PROMOTION);
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    listSearch.add(new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status));
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
        return listSearch;
    }
    
    public List<PromotionDTO> search9Promotion(String search, int page) throws SQLException {
        List<PromotionDTO> listSearch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_9_PROMOTION);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                ptm.setString(3, "%" + search + "%");
                ptm.setString(4, "%" + search + "%");
                ptm.setString(5, "%" + search + "%");
                ptm.setString(6, "%" + search + "%");
                ptm.setString(7, "%" + search + "%");
                ptm.setString(8, "%" + search + "%");
                ptm.setString(9, "%" + search + "%");
                ptm.setString(10, "%" + search + "%");
                ptm.setInt(11, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    listSearch.add(new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status));
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
        return listSearch;
    }
    
    public PromotionDTO getPromotionById(String proID) throws SQLException {
        PromotionDTO promotion = new PromotionDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PROMOTION_BY_ID);
                ptm.setString(1, proID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    double condition = rs.getDouble("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = "***";
                    String roleID = rs.getString("roleS");
                    String phone = rs.getString("phoneS");
                    String dateOfBirth = rs.getString("dobS");
                    String statusS = rs.getString("statusS");
                    String deleteS = rs.getString("deleteS");
                    promotion = new PromotionDTO(promotionID,
                            new StaffDTO(staffID, name, password, roleID, phone, dateOfBirth, statusS, deleteS),
                            dateStart, dateEnd, description, condition, discount, status);
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
        return promotion;
    }
    
    public boolean updatePromotion(PromotionDTO promo) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_PROMOTION);
                ptm.setString(1, promo.getDateStart());
                ptm.setString(2, promo.getDateEnd());
                ptm.setString(3, promo.getDescription());
                ptm.setDouble(4, promo.getCondition());
                ptm.setDouble(5, promo.getDiscount());
                ptm.setString(6, promo.getStatus());
                ptm.setString(7, promo.getPromotionID());
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
    
    public boolean createPromotion(PromotionDTO promo) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(CREATE_PROMOTION);
                ptm.setString(1, promo.getStaff().getStaffID());
                ptm.setString(2, promo.getDateStart());
                ptm.setString(3, promo.getDateEnd());
                ptm.setString(4, promo.getDescription());
                ptm.setDouble(5, promo.getCondition());
                ptm.setDouble(6, promo.getDiscount());
                ptm.setString(7, promo.getStatus());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            
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
