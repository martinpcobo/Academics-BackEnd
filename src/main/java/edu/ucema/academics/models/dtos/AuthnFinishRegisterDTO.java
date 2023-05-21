package edu.ucema.academics.models.dtos;

public class AuthnFinishRegisterDTO {
    // ! Attributes
    private String username;
    private String authenticator_name;
    private String public_key;

    // ! Constructors
    public AuthnFinishRegisterDTO() {
    }

    // ! Methods
    // * Getters
    public String getUsername() {
        return this.username;
    }

    public String getAuthenticatorName() {
        return this.authenticator_name;
    }

    public String getPublicKey() {
        return this.public_key;
    }

    // * Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthenticatorName(String authenticator_name) {
        this.authenticator_name = authenticator_name;
    }

    public void setPublicKey(String public_key) {
        this.public_key = public_key;
    }
}
