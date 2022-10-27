/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author ownhi
 */
public class CategoryErrorDTO {
    
    private String categoryIDError;
    private String nameError;

    public CategoryErrorDTO(String categoryIDError, String nameError) {
        this.categoryIDError = categoryIDError;
        this.nameError = nameError;
    }
    public CategoryErrorDTO(){
        categoryIDError="";
        nameError = "";
    }

    public String getCategoryIDError() {
        return categoryIDError;
    }

    public void setCategoryIDError(String categoryIDError) {
        this.categoryIDError = categoryIDError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }
    

}