
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
 * @author vqPhi
 */
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
                    float condition = rs.getFloat("condipromo");
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
                    float condition = rs.getFloat("condipromo");
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

public boolean createPromotion(PromotionDTO promo) throws SQLException{
    String CREATE_PROMOTION = "INSERT INTO tblPromotion( staffID, Date-start, Date-end, Description, Condition, Discount, Status) VALUE(?,?,?,?,?,?,0)";
    boolean check = false;
    Connection conn = null;
    PreparedStatement ptm = null;
    try{
        conn = DBUtils.getConnection();
        if(conn!=null){
            ptm=conn.prepareStatement(CREATE_PROMOTION);
            ptm.setString(1, promo.getStaff().getStaffID());
            ptm.setString(2, promo.getDateStart());
            ptm.setString(3, promo.getDateEnd());
            ptm.setString(4, promo.getDescription());
            ptm.setDouble(5, promo.getCondition());
            ptm.setDouble(6, promo.getDiscount());
            ptm.setString(7, "0");
            
        }
    }catch(Exception e){
        
    }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
            }
    return check;
}

public boolean ModifyPromotion(PromotionDTO promo) throws SQLException{
    String UPDATE_PROMOTION = "UPDATE tblPromotion SET [Date-start]=?, [Date-end]=?, [Description]=?, [Condition]=?, [Discount]=? WHERE promotionID=?";
    boolean check = false;
    Connection conn = null;
    PreparedStatement ptm = null;
    try{
        conn=DBUtils.getConnection();
        if(conn!=null){
            ptm = conn.prepareStatement(UPDATE_PROMOTION);
            ptm.setString(1, promo.getDateStart());
            ptm.setString(2, promo.getDateEnd());
            ptm.setString(3, promo.getDescription());
            ptm.setDouble(4, promo.getCondition());
            ptm.setDouble(5, promo.getDiscount());
            
            check = ptm.executeUpdate() > 0? true : false;
        }
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(ptm!=null){ptm.close();}
        if(conn!=null){conn.close();}
    }
    return check;
}
 

}