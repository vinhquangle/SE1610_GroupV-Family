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
public class CustomerDTO {

    private String customerID;
    private String name;
    private String password;
    private String email;
    private String address;
    private String phone;
    private int point;
    private String status;
    private String delete;
    public static final String PHONE_FORMAT = "\\d[9]|\\d{10}";

    public CustomerDTO() {
        this.customerID = "";
        this.name = "";
        this.password = "";
        this.email = "";
        this.address = "";
        this.phone = "";
        this.point = 0;
        this.status = "";
        this.delete = "";
    }

    public CustomerDTO(String customerID, String name, String password, String email, String address, String phone, int point, String status, String delete) {
        this.customerID = customerID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.point = point;
        this.status = status;
        this.delete = delete;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoint() {
        return point;
    }

    public String getStatus() {
        return status;
    }

    public String getDelete() {
        return delete;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
