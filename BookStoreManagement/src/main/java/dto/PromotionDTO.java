/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author PCPV
 */
public class PromotionDTO {
    private String promotionID;
    private StaffDTO staff;
    private String dateStart;
    private String dateEnd;
    private String description;
    private double condition;
    private double discount;
    private String status;

    public PromotionDTO() {
        this.promotionID = "";
        this.staff = new StaffDTO();
        this.dateStart = "";
        this.dateEnd = "";
        this.description = "";
        this.condition = 0.0;
        this.discount = 0.0;
        this.status = "";
    }

    public PromotionDTO(String promotionID, StaffDTO staff, String dateStart, String dateEnd, String description, double condition, double discount, String status) {
        this.promotionID = promotionID;
        this.staff = staff;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.condition = condition;
        this.discount = discount;
        this.status = status;
    }

    public String getPromotionID() {
        return promotionID;
    }

    public StaffDTO getStaff() {
        return staff;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public double getCondition() {
        return condition;
    }

    public double getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
