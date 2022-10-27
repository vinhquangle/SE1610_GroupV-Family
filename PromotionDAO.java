/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import utils.DBUtils;

/**
 *
 * @author vungo
 */
//Toàn bộ là function mới của Thy
public class PromotionDAO {

public List<PromotionDTO> searchPromotion(String search) throws SQLException {
        String SEARCH_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n"
                + "promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n"
                + "promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n"
                + "promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n"
                + "s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n"
                + "s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n"
                + "FROM tblPromotion promo \n"
                + "LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n"
                + "WHERE promo.[promotionID] LIKE ? OR promo.[staffID] LIKE ? OR promo.[Date-start] \n"
                + "LIKE ? OR promo.[Date-end] LIKE ? OR promo.[Description] LIKE ? OR promo.[Condition] LIKE ?\n"
                + "OR promo.[Discount] LIKE ? OR promo.[Status] LIKE ?";
        List<PromotionDTO> listSearch = new ArrayList<>();
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
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String promotionID = rs.getString("promoID");
                    String dateStart = rs.getString("startpromo");
                    String dateEnd = rs.getString("endpromo");
                    String description = rs.getString("despromo");
                    String condition = rs.getString("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = rs.getString("passS");
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

public List<PromotionDTO> loadPromotion() throws SQLException {
        String LOAD_PROMOTION = "SELECT promo.[promotionID] AS promoID, promo.[staffID] AS staffpromo,\n" +
"                promo.[Date-start] AS startpromo,promo.[Date-end] AS endpromo,\n" +
"                promo.[Description] AS despromo,promo.[Condition] AS condipromo,\n" +
"               promo.[Discount] AS dispromo,promo.[Status] AS statuspromo,\n" +
"                s.[staffID] AS staffS, s.[Name] AS nameS,s.[Password] AS passS,s.[Role] AS roleS,\n" +
"                s.Phone AS phoneS,s.[Date-of-birth] AS dobS,s.[Status] AS statusS,s.[Delete] AS deleteS\n" +
"                FROM tblPromotion promo LEFT JOIN tblStaff s ON s.staffID=promo.staffID\n" +
"				ORDER By promo.[Status]";
        List<PromotionDTO> listSearch = new ArrayList<>();
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
                    String condition = rs.getString("condipromo");
                    float discount = rs.getFloat("dispromo");
                    String status = rs.getString("statuspromo");
                    String staffID = rs.getString("staffS");
                    String name = rs.getString("nameS");
                    String password = rs.getString("passS");
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

public boolean updateStatusPromotion(String promoID) throws SQLException {
String UPDATE_STATUS = "UPDATE tblPromotion SET [status] = ? WHERE promotionID = ?";
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String status = "1";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, promoID);
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
