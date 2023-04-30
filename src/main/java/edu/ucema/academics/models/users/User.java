package edu.ucema.academics.models.users;

import edu.ucema.academics.models.users.interfaces.IUser;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements IUser {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;
    @Transient
    private String name;
    @OneToOne
    @JoinColumn(name = "password_id", referencedColumnName = "id")
    private Password password;
    @OneToOne
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private Email email;

    // Constructors
    protected User() {}
    public User(Long user_id, String user_name, String user_last_name, Email user_email, Password user_passowrd) {
        this.id = user_id;
        this.firstName = user_name;
        this.lastName = user_last_name;
        this.name = user_name + ' ' + user_last_name;
        this.email = user_email;
        this.password = user_passowrd;
    }
    public User(User user_instance) {
        this.id = user_instance.getIdentifier();
        this.firstName = user_instance.getFirstName();
        this.lastName = user_instance.getLastName();
        this.name = user_instance.getName();
        this.email = user_instance.getEmail();
        this.password = user_instance.getPassword();
    }

    // Getters
    public Long getIdentifier() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getName() {
        return this.name;
    }
    public Email getEmail() {
        return this.email;
    }
    public Password getPassword() { return this.password; }

    // Setter
    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }
    public void setLastName(String last_name) {
        this.lastName = last_name;
    }
    public void setEmail(Email email_instance) {
        this.email = email_instance;
    }
    public void setPassword(Password password_instance) { this.password = password_instance; }
}
