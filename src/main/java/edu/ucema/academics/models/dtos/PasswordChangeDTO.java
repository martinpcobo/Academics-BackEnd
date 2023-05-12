package edu.ucema.academics.models.dtos;

public class PasswordChangeDTO {
    // ! Attributes
    // * Data
    private String newPassword;
    private String oldPassword;

    // ! Constructors
    public PasswordChangeDTO() {}
    public PasswordChangeDTO(String newPassword, String oldPassword) {
        this.setNewPassword(newPassword);
        this.setOldPassword(oldPassword);
    }
    public PasswordChangeDTO(PasswordChangeDTO password_change_instance) {
        this.setNewPassword(password_change_instance.getNewPassword());
        this.setOldPassword(password_change_instance.getOldPassword());
    }

    // ! Methods

    // * Setters
    public void setNewPassword(String new_password) {
        this.newPassword = new_password;
    }
    public void setOldPassword(String old_password) {
        this.oldPassword = old_password;
    }
    // * Getters
    public String getNewPassword() { return this.newPassword; }
    public String getOldPassword() { return this.oldPassword; }
}
