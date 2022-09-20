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

    String orderID;
    String customerID;
    String staffID;
    double deliveryCost;
    String date;
    double total;
    String status;

    public OrderDTO() {
        this.orderID = "";
        this.customerID = "";
        this.staffID = "";
        this.deliveryCost = 0;
        this.date = "";
        this.total = 0;
        this.status = "";
    }

    public OrderDTO(String orderID, String customerID, String staffID, double deliveryCost, String date, double total, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.staffID = staffID;
        this.deliveryCost = deliveryCost;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getStaffID() {
        return staffID;
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

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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

}
