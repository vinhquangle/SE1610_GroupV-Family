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

    private String requestDetailID;
    private BookRequestDTO request;
    private BookDTO isbn;
    private int quantity;
    private String status;
    private String delete;

    public BRequestDetailDTO() {
        this.requestDetailID = "";
        this.request = new BookRequestDTO();
        this.isbn = new BookDTO();
        this.quantity = 0;
        this.status = "";
        this.delete = "";
    }

    public BRequestDetailDTO(String requestDetailID, BookRequestDTO request, BookDTO isbn, int quantity, String status, String delete) {
        this.requestDetailID = requestDetailID;
        this.request = request;
        this.isbn = isbn;
        this.quantity = quantity;
        this.status = status;
        this.delete = delete;
    }

    public String getStatus() {
        return status;
    }

    public String getDelete() {
        return delete;
    }

    public String getRequestDetailID() {
        return requestDetailID;
    }

    public BookRequestDTO getRequest() {
        return request;
    }

    public BookDTO getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setRequestDetailID(String requestDetailID) {
        this.requestDetailID = requestDetailID;
    }

    public void setRequest(BookRequestDTO request) {
        this.request = request;
    }

    public void setIsbn(BookDTO isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
