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
public class OrderDetailDTO {

    String detailID;
    String orderID;
    String isbn;
    String name;
    String publisherID;
    String categoryID;
    String authorName;
    double price;
    int quantity;
    double total;

    public OrderDetailDTO() {
        this.detailID = "";
        this.orderID = "";
        this.isbn = "";
        this.name = "";
        this.publisherID = "";
        this.categoryID = "";
        this.authorName = "";
        this.price = 0;
        this.quantity = 0;
        this.total = 0;
    }

    public OrderDetailDTO(String detailID, String orderID, String isbn, String name, String publisherID, String categoryID, String authorName, double price, int quantity, double total) {
        this.detailID = detailID;
        this.orderID = orderID;
        this.isbn = isbn;
        this.name = name;
        this.publisherID = publisherID;
        this.categoryID = categoryID;
        this.authorName = authorName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getDetailID() {
        return detailID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
