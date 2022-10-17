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
public class StaffErrorDTO {
    
    private String staffIDEr;
    private String nameEr;
    private String passwordEr;
    private String roleEr;
    private String phoneEr;
    private String dateOfBirthEr;
    private String statusEr;
    private String deleteEr;

    public StaffErrorDTO() {
        this.staffIDEr = "";
        this.nameEr = "";
        this.passwordEr = "";
        this.roleEr = "";
        this.phoneEr = "";
        this.dateOfBirthEr = "";
        this.statusEr = "";
        this.deleteEr = "";
    }

    public StaffErrorDTO(String staffIDEr, String nameEr, String passwordEr, String roleEr, String phoneEr, String dateOfBirthEr, String statusEr, String deleteEr) {
        this.staffIDEr = staffIDEr;
        this.nameEr = nameEr;
        this.passwordEr = passwordEr;
        this.roleEr = roleEr;
        this.phoneEr = phoneEr;
        this.dateOfBirthEr = dateOfBirthEr;
        this.statusEr = statusEr;
        this.deleteEr = deleteEr;
    }

    public String getStaffIDEr() {
        return staffIDEr;
    }

    public void setStaffIDEr(String staffIDEr) {
        this.staffIDEr = staffIDEr;
    }

    public String getNameEr() {
        return nameEr;
    }

    public void setNameEr(String nameEr) {
        this.nameEr = nameEr;
    }

    public String getPasswordEr() {
        return passwordEr;
    }

    public void setPasswordEr(String passwordEr) {
        this.passwordEr = passwordEr;
    }

    public String getRoleEr() {
        return roleEr;
    }

    public void setRoleEr(String roleEr) {
        this.roleEr = roleEr;
    }

    public String getPhoneEr() {
        return phoneEr;
    }

    public void setPhoneEr(String phoneEr) {
        this.phoneEr = phoneEr;
    }

    public String getDateOfBirthEr() {
        return dateOfBirthEr;
    }

    public void setDateOfBirthEr(String dateOfBirthEr) {
        this.dateOfBirthEr = dateOfBirthEr;
    }

    public String getStatusEr() {
        return statusEr;
    }

    public void setStatusEr(String statusEr) {
        this.statusEr = statusEr;
    }

    public String getDeleteEr() {
        return deleteEr;
    }

    public void setDeleteEr(String deleteEr) {
        this.deleteEr = deleteEr;
    }
    
}
