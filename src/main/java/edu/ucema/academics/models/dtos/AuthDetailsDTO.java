package edu.ucema.academics.models.dtos;

public class AuthDetailsDTO {
    // ! Attributes
    private String username;
    private String authenticatorName;
    private String publicKey;
    private String password;

    // ! Constructors
    public AuthDetailsDTO() {
    }

    // ! Methods
    // * Getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAuthenticatorName() {
        return this.authenticatorName;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    // * Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthenticatorName(String authenticator_name) {
        this.authenticatorName = authenticator_name;
    }

    public void setPublicKey(String public_key) {
        this.publicKey = public_key;
    }
}
