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
public class BResponseDetailDTO {

    String responseDetailID;
    String responseID;
    String isbn;
    String publisherID;
    String categoryID;
    String name;
    String authorName;
    int quantity;
    double price;

    public BResponseDetailDTO() {
        this.responseDetailID = "";
        this.responseID = "";
        this.isbn = "";
        this.publisherID = "";
        this.categoryID = "";
        this.name = "";
        this.authorName = "";
        this.quantity = 0;
        this.price = 0;
    }

    public BResponseDetailDTO(String responseDetailID, String responseID, String isbn, String publisherID, String categoryID, String name, String authorName, int quantity, double price) {
        this.responseDetailID = responseDetailID;
        this.responseID = responseID;
        this.isbn = isbn;
        this.publisherID = publisherID;
        this.categoryID = categoryID;
        this.name = name;
        this.authorName = authorName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getResponseDetailID() {
        return responseDetailID;
    }

    public String getResponseID() {
        return responseID;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setResponseDetailID(String responseDetailID) {
        this.responseDetailID = responseDetailID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
