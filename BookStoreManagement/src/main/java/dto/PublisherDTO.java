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
public class PublisherDTO {

    private String publisherID;
    private String name;
    private String status;

    public PublisherDTO() {
        this.publisherID = "";
        this.name = "";
        this.status = "";
    }

    public PublisherDTO(String publisherID, String name, String status) {
        this.publisherID = publisherID;
        this.name = name;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public String getName() {
        return name;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
