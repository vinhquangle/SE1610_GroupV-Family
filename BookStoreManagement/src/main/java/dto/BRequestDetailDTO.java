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
public class BRequestDetailDTO {

    String requestDetailID;
    String requestID;
    String isbn;
    String publisherID;
    String categoryID;
    String name;
    String authorName;
    int quantity;

    public BRequestDetailDTO() {
        this.requestDetailID = "";
        this.requestID = "";
        this.isbn = "";
        this.publisherID = "";
        this.categoryID = "";
        this.name = "";
        this.authorName = "";
        this.quantity = 0;
    }

    public BRequestDetailDTO(String requestDetailID, String requestID, String isbn, String publisherID, String categoryID, String name, String authorName, int quantity) {
        this.requestDetailID = requestDetailID;
        this.requestID = requestID;
        this.isbn = isbn;
        this.publisherID = publisherID;
        this.categoryID = categoryID;
        this.name = name;
        this.authorName = authorName;
        this.quantity = quantity;
    }

    public String getRequestDetailID() {
        return requestDetailID;
    }

    public String getRequestID() {
        return requestID;
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

    public void setRequestDetailID(String requestDetailID) {
        this.requestDetailID = requestDetailID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
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

}
