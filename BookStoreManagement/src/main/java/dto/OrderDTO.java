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
public class OrderDTO {

    private String orderID;
    private CustomerDTO customer;
    private StaffDTO staff;
    private double deliveryCost;
    private String date;
    private double total;
    private String description;
    private String status;
    private String delete;

    public OrderDTO() {
        this.orderID = "";
        this.customer = new CustomerDTO();
        this.staff = new StaffDTO();
        this.deliveryCost = 0;
        this.date = "";
        this.total = 0;
        this.description = "";
        this.status = "";
        this.delete = "";
    }

    public OrderDTO(String orderID, CustomerDTO customer, StaffDTO staff, double deliveryCost, String date, double total, String description, String status, String delete) {
        this.orderID = orderID;
        this.customer = customer;
        this.staff = staff;
        this.deliveryCost = deliveryCost;
        this.date = date;
        this.total = total;
        this.description = description;
        this.status = status;
        this.delete = delete;
    }

    public String getDescription() {
        return description;
    }

    public String getDelete() {
        return delete;
    }

    public String getOrderID() {
        return orderID;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public StaffDTO getStaff() {
        return staff;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public String getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
