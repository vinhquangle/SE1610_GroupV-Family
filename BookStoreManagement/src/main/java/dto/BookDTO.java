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
    private ReviewDTO review;
    private String name;
    private String authorName;
    private double price;
    private String description;
    private String img;
    private int quantity;
    private String status;

    public BookDTO() {
        this.isbn = "";
        this.publisher = new PublisherDTO();
        this.category = new CategoryDTO();
         this.review = new ReviewDTO();
        this.name = "";
        this.authorName = "";
        this.price = 0;
        this.description="";
        this.img = "";
        this.quantity = 0;
        this.status = "";
    }

    public BookDTO(String isbn, PublisherDTO publisher, CategoryDTO category, ReviewDTO review, String name, String authorName, double price, String description, String img, int quantity, String status) {
        this.isbn = isbn;
        this.publisher = publisher;
        this.category = category;
        this.review = review;
        this.name = name;
        this.authorName = authorName;
        this.price = price;
        this.description = description;
        this.img = img;
        this.quantity = quantity;
        this.status = status;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
