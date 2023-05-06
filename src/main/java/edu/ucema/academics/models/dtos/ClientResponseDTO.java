package edu.ucema.academics.models.dtos;

public class ClientResponseDTO {
    // Attributes
    private String message;

    // Constructors
    public ClientResponseDTO(String message) {
        this.setMessage(message);
    }

    // Methods

    // Setters
    public void setMessage(String message_details) {
        this.message = message_details;
    }

    // Getters
    public String getMessage() { return this.message; }

}
