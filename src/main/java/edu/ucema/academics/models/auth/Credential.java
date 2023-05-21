package edu.ucema.academics.models.auth;

import edu.ucema.academics.models.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "password")
public class Credential {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "credential")
    private User user;

    @OneToMany(mappedBy = "credential")
    private List<Authenticator> athenticators;

    // ! Constructors
    public Credential() {
    }

    public Credential(User user, String password, List<Authenticator> athenticators) {
        this.setIdentifier(user.getIdentifier());
        this.setPassword(password);
        this.setUser(user);
    }

    public Credential(Credential credential_instance) {
        this.setIdentifier(credential_instance.getIdentifier());
        this.setPassword(credential_instance.getPassword());
    }

    // ! Methods
    // * Getters
    public String getIdentifier() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public User getUser() {
        return this.user;
    }

    public List<Authenticator> getAuthenticators() {
        return this.athenticators;
    }


    // * Setters
    public void setIdentifier(String identifier) {
        this.id = identifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthenticators(List<Authenticator> athenticators) {
        this.athenticators = athenticators;
    }
}
