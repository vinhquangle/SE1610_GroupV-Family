/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author vungo
 */
//Locate at dto
public class CustomerErrorDTO {

    private String customerIDError;
    private String nameError;
    private String passwordError;
    private String confirmError;
    private String emailError;
    private String addressError;
    private String phoneError;
    private String pointError;
    private String statusError;
    private String deleteError;

    public CustomerErrorDTO() {
        this.customerIDError = "";
        this.nameError = "";
        this.passwordError = "";
        this.confirmError = "";
        this.emailError = "";
        this.addressError = "";
        this.phoneError = "";
        this.pointError = "";
        this.statusError = "";
        this.deleteError = "";
    }

    public CustomerErrorDTO(String customerIDError, String nameError, String passwordError, String confirmError, String emailError, String addressError, String phoneError, String pointError, String statusError, String deleteError) {
        this.customerIDError = customerIDError;
        this.nameError = nameError;
        this.passwordError = passwordError;
        this.confirmError = confirmError;
        this.emailError = emailError;
        this.addressError = addressError;
        this.phoneError = phoneError;
        this.pointError = pointError;
        this.statusError = statusError;
        this.deleteError = deleteError;
    }

    public String getCustomerIDError() {
        return customerIDError;
    }

    public void setCustomerIDError(String customerIDError) {
        this.customerIDError = customerIDError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getPointError() {
        return pointError;
    }

    public void setPointError(String pointError) {
        this.pointError = pointError;
    }

    public String getStatusError() {
        return statusError;
    }

    public void setStatusError(String statusError) {
        this.statusError = statusError;
    }

    public String getDeleteError() {
        return deleteError;
    }

    public void setDeleteError(String deleteError) {
        this.deleteError = deleteError;
    }
}
