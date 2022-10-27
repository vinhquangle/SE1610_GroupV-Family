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

    private String detailID;
    private OrderDTO order;
    private BookDTO isbn;
    private double price;
    private int quantity;
    private double total;
    private String status;

    public OrderDetailDTO() {
        this.detailID = "";
        this.order = new OrderDTO();
        this.isbn = new BookDTO();
        this.price = 0;
        this.quantity = 0;
        this.total = 0;
        this.status = "";
    }

    public OrderDetailDTO(String detailID, OrderDTO order, BookDTO isbn, double price, int quantity, double total, String status) {
        this.detailID = detailID;
        this.order = order;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public String getDetailID() {
        return detailID;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public BookDTO getIsbn() {
        return isbn;
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

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public void setIsbn(BookDTO isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
