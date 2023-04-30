package edu.ucema.academics.models.users;

import jakarta.persistence.*;

@Entity
@Table(name = "password")
public class Password {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "password")
    private User user;

    // Constructors
    public Password() {}
    public Password(String password, User user) {
        this.id = user.getIdentifier();
        this.password = password;
        this.user = user;
    }
    public Password(Password password_instance) {
        this.id = password_instance.getIdentifier();
        this.password = password_instance.getPassword();
        this.user = password_instance.getUser();
    }

    // Setters
    public void setPassword(String password) {
        this.password = password;
    }
    // Getters
    public Long getIdentifier() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getPassword() {
        return this.password;
    }
}
