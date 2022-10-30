/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Thịnh
 */
public class PromotionErrorDTO {

    private String promotionID;
    private String staff;
    private String dateStart;
    private String dateEnd;
    private String description;
    private String condition;
    private String discount;
    private String status;

    public PromotionErrorDTO() {
        this.promotionID = "";
        this.staff = "";
        this.dateStart = "";
        this.dateEnd = "";
        this.description = "";
        this.condition = "";
        this.discount = "";
        this.status = "";
    }

    public PromotionErrorDTO(String promotionID, String staff, String dateStart, String dateEnd, String description, String condition, String discount, String status) {
        this.promotionID = promotionID;
        this.staff = staff;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.condition = condition;
        this.discount = discount;
        this.status = status;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public void setStaff(String staff) {
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

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromotionID() {
        return promotionID;
    }

    public String getStaff() {
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

    public String getCondition() {
        return condition;
    }

    public String getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status;
    }
    
}
