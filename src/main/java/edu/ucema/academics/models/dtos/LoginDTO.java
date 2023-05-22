package edu.ucema.academics.models.dtos;

public class LoginDTO {
    // ! Attributes
    private String username;
    private String password;

    // ! Constructors
    public void LoginDTO() {
    }

    public void LoginDTO(LoginDTO login_dto_instance) {
        this.setPassword(login_dto_instance.getPassword());
        this.setUsername(login_dto_instance.getUsername());
    }

    // ! Methods
    // * Getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    // * Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
