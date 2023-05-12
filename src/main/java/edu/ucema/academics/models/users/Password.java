package edu.ucema.academics.models.users;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "password")
public class Password {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "password")
    private String password;

    // ! Constructors
    public Password() {
    }

    public Password(String password, User user) {
        this.setIdentifier(user.getIdentifier());
        this.setPassword(password);
    }

    public Password(Password password_instance) {
        this.setIdentifier(password_instance.getIdentifier());
        this.setPassword(password_instance.getPassword());
    }

    // ! Methods
    // * Getters
    public String getIdentifier() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    // * Setters
    public void setIdentifier(String identifier) { this.id = identifier; }
    public void setPassword(String password) {
        this.password = password;
    }
}
