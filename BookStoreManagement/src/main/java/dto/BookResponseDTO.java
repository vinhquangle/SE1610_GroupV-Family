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

    private String responseID;
    private BookRequestDTO request;
    private StaffDTO staff;
    private String date;
    private String status;
    private String delete;

    public BookResponseDTO() {
        this.responseID = "";
        this.request = new BookRequestDTO();
        this.staff = new StaffDTO();
        this.date = "";
        this.status = "";
        this.delete = "";
    }

    public BookResponseDTO(String responseID, BookRequestDTO request, StaffDTO staff, String date, String status, String delete) {
        this.responseID = responseID;
        this.request = request;
        this.staff = staff;
        this.date = date;
        this.status = status;
        this.delete = delete;
    }

    public String getDelete() {
        return delete;
    }

    public String getResponseID() {
        return responseID;
    }

    public BookRequestDTO getRequest() {
        return request;
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

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public void setRequest(BookRequestDTO request) {
        this.request = request;
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

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
