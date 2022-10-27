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
public class CategoryDTO {

    private String categoryID;
    private String name;
    private String status;

    public CategoryDTO() {
        this.categoryID = "";
        this.name = "";
        this.status = "";
    }

    public CategoryDTO(String categoryID, String name, String status) {
        this.categoryID = categoryID;
        this.name = name;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
