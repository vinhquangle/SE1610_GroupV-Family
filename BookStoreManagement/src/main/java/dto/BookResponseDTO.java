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
public class BookResponseDTO {

    String responseID;
    String requestID;
    String staffID;
    String date;
    String status;

    public BookResponseDTO() {
        this.responseID = "";
        this.requestID = "";
        this.staffID = "";
        this.date = "";
        this.status = "";
    }

    public BookResponseDTO(String responseID, String requestID, String staffID, String date, String status) {
        this.responseID = responseID;
        this.requestID = requestID;
        this.staffID = staffID;
        this.date = date;
        this.status = status;
    }

    public String getResponseID() {
        return responseID;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
