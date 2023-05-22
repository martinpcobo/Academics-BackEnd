package edu.ucema.academics.models.dtos;

public class AuthnUserDTO {
    // ! Attributes
    private String username;
    private String authenticatorName;
    private String publicKey;

    // ! Constructors
    public AuthnUserDTO() {
    }

    // ! Methods
    // * Getters
    public String getUsername() {
        return this.username;
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

    public void setAuthenticatorName(String authenticator_name) {
        this.authenticatorName = authenticator_name;
    }

    public void setPublicKey(String public_key) {
        this.publicKey = public_key;
    }
}
