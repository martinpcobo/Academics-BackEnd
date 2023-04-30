package edu.ucema.academics.models.users;

import jakarta.persistence.*;

@Entity
@Table(name = "email")
public class Email {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "verified_status")
    private Boolean verifiedStatus;
    @Column(name = "verified_email")
    private String verifiedEmail;
    @Column(name = "unverified_email")
    private String unverifiedEmail;
    @OneToOne(mappedBy = "email")
    private User user;

    protected Email(){}
    public Email(Long id, Boolean verifiedStatus, String verifiedEmail, String unverifiedEmail, User user) {
        this.id = id;
        this.verifiedStatus = verifiedStatus;
        this.verifiedEmail = verifiedEmail;
        this.unverifiedEmail = unverifiedEmail;
        this.user = user;
    }
    public Email(Email email_instance) {
        this.id = email_instance.getIdentifier();
        this.verifiedStatus = email_instance.getVerifiedStatus();
        this.verifiedEmail = email_instance.getVerifiedEmail();
        this.unverifiedEmail = email_instance.getUnverifiedEmail();
    }


    // Getters
    public String getVerifiedEmail() {
        return this.verifiedEmail;
    }
    public String getUnverifiedEmail() {
        return this.unverifiedEmail;
    }
    public Boolean getVerifiedStatus() {
        return this.verifiedStatus;
    }
    public Long getIdentifier() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }

    // Setters
    public void setVerifiedEmail(String email) {
        this.verifiedEmail = email;
    }
    public void setUnverifiedEmail(String email) {
        this.unverifiedEmail = email;
    }
    public void setVerifiedStatus(Boolean verified) {
        this.verifiedStatus = verified;
    }
}
