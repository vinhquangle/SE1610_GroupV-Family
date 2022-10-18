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
public class StaffDTO {

    private String staffID;
    private String name;
    private String password;
    private String role;
    private String phone;
    private String dateOfBirth;
    private String status;
    private String delete;
    public static final String PHONE_FORMAT = "\\d[9]|\\d{10}";

    public StaffDTO() {
        this.staffID = "";
        this.name = "";
        this.password = "";
        this.role = "";
        this.phone = "";
        this.dateOfBirth = "";
        this.status = "";
        this.delete = "";
    }

    public StaffDTO(String staffID, String name, String password, String role, String phone, String dateOfBirth, String status, String delete) {
        this.staffID = staffID;
        this.name = name;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.delete = delete;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public String getDelete() {
        return delete;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
