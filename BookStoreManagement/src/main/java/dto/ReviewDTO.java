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
public class ReviewDTO {
    private String reviewID;
    private double rate;
    private int times;
    private String status;

    public ReviewDTO() {
        this.reviewID = "";
        this.rate = 0.0;
        this.times = 0;
        this.status = "";
    }

    public ReviewDTO(String reviewID, double rate, int times, String status) {
        this.reviewID = reviewID;
        this.rate = rate;
        this.times = times;
        this.status = status;
    }

    public String getReviewID() {
        return reviewID;
    }

    public double getRate() {
        return rate;
    }

    public int getTimes() {
        return times;
    }

    public String getStatus() {
        return status;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
