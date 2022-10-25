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

    private String responseDetailID;
    private BookResponseDTO response;
    private BookDTO isbn;
    private int quantity;
    private double price;
    private String status;
    private String delete;

    public BResponseDetailDTO() {
        this.responseDetailID = "";
        this.response = new BookResponseDTO();
        this.isbn = new BookDTO();
        this.quantity = 0;
        this.price = 0;
        this.status = "";
        this.delete = "";
    }

    public BResponseDetailDTO(String responseDetailID, BookResponseDTO response, BookDTO isbn, int quantity, double price, String status, String delete) {
        this.responseDetailID = responseDetailID;
        this.response = response;
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.delete = delete;
    }

    public String getStatus() {
        return status;
    }

    public String getDelete() {
        return delete;
    }

    public String getResponseDetailID() {
        return responseDetailID;
    }

    public BookResponseDTO getResponse() {
        return response;
    }

    public BookDTO getIsbn() {
        return isbn;
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

    public void setResponse(BookResponseDTO response) {
        this.response = response;
    }

    public void setIsbn(BookDTO isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
