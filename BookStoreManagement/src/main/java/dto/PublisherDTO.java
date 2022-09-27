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

    public PublisherDTO() {
        this.publisherID = "";
        this.name = "";
    }

    public PublisherDTO(String publisherID, String name) {
        this.publisherID = publisherID;
        this.name = name;
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

}
