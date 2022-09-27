/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Administrator
 */
public class BookRequestDTO {

    private String requestID;
    private StaffDTO staff;
    private String date;
    private String status;

    public BookRequestDTO() {
        this.requestID = "";
        this.staff = new StaffDTO();
        this.date = "";
        this.status = "";
    }

    public BookRequestDTO(String requestID, StaffDTO staff, String date, String status) {
        this.requestID = requestID;
        this.staff = staff;
        this.date = date;
        this.status = status;
    }

    public String getRequestID() {
        return requestID;
    }

    public StaffDTO getStaff() {
        return staff;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
