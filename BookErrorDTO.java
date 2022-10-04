/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author PC
 */
public class BookErrorDTO {
    private String isbnError;
    private PublisherDTO publisherError;
    private CategoryDTO categoryError;
    private String nameError;
    private String authorNameError;
    private double priceError;
    private String imgError;
    private int quantityError;

    public BookErrorDTO() {
        this.isbnError = "";
        this.publisherError = new PublisherDTO();
        this.categoryError = new CategoryDTO();
        this.nameError = "";
        this.authorNameError = "";
        this.priceError = 0;
        this.imgError = "";
        this.quantityError = 0;
    }

    public BookErrorDTO(String isbnError, PublisherDTO publisherError, CategoryDTO categoryError, String nameError, String authorNameError, double priceError, String imgError, int quantityError) {
        this.isbnError = isbnError;
        this.publisherError = publisherError;
        this.categoryError = categoryError;
        this.nameError = nameError;
        this.authorNameError = authorNameError;
        this.priceError = priceError;
        this.imgError = imgError;
        this.quantityError = quantityError;
    }

    public String getIsbnError() {
        return isbnError;
    }

    public void setIsbnError(String isbnError) {
        this.isbnError = isbnError;
    }

    public PublisherDTO getPublisherError() {
        return publisherError;
    }

    public void setPublisherError(PublisherDTO publisherError) {
        this.publisherError = publisherError;
    }

    public CategoryDTO getCategoryError() {
        return categoryError;
    }

    public void setCategoryError(CategoryDTO categoryError) {
        this.categoryError = categoryError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getAuthorNameError() {
        return authorNameError;
    }

    public void setAuthorNameError(String authorNameError) {
        this.authorNameError = authorNameError;
    }

    public double getPriceError() {
        return priceError;
    }

    public void setPriceError(double priceError) {
        this.priceError = priceError;
    }

    public String getImgError() {
        return imgError;
    }

    public void setImgError(String imgError) {
        this.imgError = imgError;
    }

    public int getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(int quantityError) {
        this.quantityError = quantityError;
    }
    
    
}
