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

    private String isbn;
    private PublisherDTO publisher;
    private CategoryDTO category;
    private String name;
    private String authorName;
    private double price;
    private String img;
    private int quantity;

    public BookDTO() {
        this.isbn = "";
        this.publisher = new PublisherDTO();
        this.category = new CategoryDTO();
        this.name = "";
        this.authorName = "";
        this.price = 0;
        this.img = "";
        this.quantity = 0;
    }

    public BookDTO(String isbn, PublisherDTO publisher, CategoryDTO category, String name, String authorName, double price, String img, int quantity) {
        this.isbn = isbn;
        this.publisher = publisher;
        this.category = category;
        this.name = name;
        this.authorName = authorName;
        this.price = price;
        this.img = img;
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public CategoryDTO getCategory() {
        return category;
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

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
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
