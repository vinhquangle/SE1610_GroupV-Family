/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Quang Vinh
 */
public class PublisherErrorDTO {
    private String publisherIDError;
    private String nameError;
    private String statusError;

    public PublisherErrorDTO() {
        this.publisherIDError = "";
        this.nameError = "";
        this.statusError = "";
    }

    public PublisherErrorDTO(String publisherIDError, String nameError, String statusError) {
        this.publisherIDError = publisherIDError;
        this.nameError = nameError;
        this.statusError = statusError;
    }

    public String getPublisherIDError() {
        return publisherIDError;
    }

    public void setPublisherIDError(String publisherIDError) {
        this.publisherIDError = publisherIDError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getStatusError() {
        return statusError;
    }

    public void setStatusError(String statusError) {
        this.statusError = statusError;
    }
    
    
}