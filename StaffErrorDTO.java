/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author vungo
 */
public class StaffErrorDTO {
    private String staffIDError;
    private String nameError;
    private String passwordError;
    private String roleError;
    private String phoneError;
    private String dateOfBirthError;
    private String statusError;
    private String deleteError;
    private String confirmError;

    public StaffErrorDTO() {
        staffIDError = "";
        nameError = "";
        passwordError = "";
        roleError = "";
        phoneError = "";
        dateOfBirthError = "";
        statusError = "";
        deleteError = "";
        confirmError = "";
    }

    public StaffErrorDTO(String staffIDError, String nameError, String passwordError, String roleError, String phoneError, String dateOfBirthError, String statusError, String deleteError, String confirmError) {
        this.staffIDError = staffIDError;
        this.nameError = nameError;
        this.passwordError = passwordError;
        this.roleError = roleError;
        this.phoneError = phoneError;
        this.dateOfBirthError = dateOfBirthError;
        this.statusError = statusError;
        this.deleteError = deleteError;
        this.confirmError = confirmError;
    }

    public String getStaffIDError() {
        return staffIDError;
    }

    public void setStaffIDError(String staffIDError) {
        this.staffIDError = staffIDError;
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

    public String getRoleError() {
        return roleError;
    }

    public void setRoleError(String roleError) {
        this.roleError = roleError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getDateOfBirthError() {
        return dateOfBirthError;
    }

    public void setDateOfBirthError(String dateOfBirthError) {
        this.dateOfBirthError = dateOfBirthError;
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

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

}