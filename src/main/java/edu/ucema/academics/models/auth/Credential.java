package edu.ucema.academics.models.auth;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "credential")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Credential {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "credential", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "credential")
    private List<Authenticator> authenticators;

    // ! Constructors
    public Credential() {
    }

    public Credential(User user, String password, List<Authenticator> authenticators) {
        this.setId(user.getId());
        this.setPassword(password);
        this.setUser(user);
        this.setAuthenticators(authenticators);
    }

    public Credential(Credential credential_instance) {
        this.setId(credential_instance.getId());
        this.setPassword(credential_instance.getPassword());
        this.setUser(credential_instance.getUser());
        this.setAuthenticators(credential_instance.getAuthenticators());
    }

    // ! Methods
    // * Getters
    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public User getUser() {
        return this.user;
    }

    public List<Authenticator> getAuthenticators() {
        return this.authenticators;
    }


    // * Setters
    public void setId(String identifier) {
        this.id = identifier;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthenticators(List<Authenticator> authenticators) {
        this.authenticators = authenticators;
    }
}
