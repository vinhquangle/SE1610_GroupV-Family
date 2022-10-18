/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author PCPV
 */
public class ReviewDetailDTO {
    private String reviewDetailID;
    private ReviewDTO review;
    private CustomerDTO customer;
    private String description;
    private double rate;
    private String date;
    private String status;

    public ReviewDetailDTO() {
        this.reviewDetailID = "";
        this.review = new ReviewDTO();
        this.customer = new CustomerDTO();
        this.description = "";
        this.rate = 0.0;
        this.date = "";
        this.status = "";
    }

    public ReviewDetailDTO(String reviewDetailID, ReviewDTO review, CustomerDTO customer, String description, double rate, String date, String status) {
        this.reviewDetailID = reviewDetailID;
        this.review = review;
        this.customer = customer;
        this.description = description;
        this.rate = rate;
        this.date = date;
        this.status = status;
    }

    public String getReviewDetailID() {
        return reviewDetailID;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public String getDescription() {
        return description;
    }

    public double getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setReviewDetailID(String reviewDetailID) {
        this.reviewDetailID = reviewDetailID;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
