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
public class BookDTO {
    String isbn;
    String publisherID;
    String categoryID;
    String name;
    String authorName;
    double price;
    String img;
    int quantity;

    public BookDTO() {
         this.isbn = "";
        this.publisherID = "";
        this.categoryID = "";
        this.name = "";
        this.authorName = "";
        this.price = 0;
        this.img = "";
        this.quantity = 0;
    }

    public BookDTO(String isbn, String publisherID, String categoryID, String name, String authorName, double price, String img, int quantity) {
        this.isbn = isbn;
        this.publisherID = publisherID;
        this.categoryID = categoryID;
        this.name = name;
        this.authorName = authorName;
        this.price = price;
        this.img = img;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public int getQuantity() {
        return quantity;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
