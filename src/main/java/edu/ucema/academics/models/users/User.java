package edu.ucema.academics.models.users;

import edu.ucema.academics.models.users.interfaces.IUser;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements IUser {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Transient
    private String name;
    @Column(name = "verified_email")
    private String verifiedEmail;
    @Column(name = "unverified_email")
    private String unverifiedEmail;
    @Column(name = "email_verification_code")
    private String emailVerificationCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id", referencedColumnName = "id")
    private Password password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student studentProfile;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professorProfile;


    // Constructors
    protected User() {
    }

    public User(String user_id, String user_name, String user_last_name, String verified_email, String unverified_email, String email_verification_code, Password user_password, Student student_profile, Professor professor_profile) {
        this.setIdentifier(user_id);
        this.setFirstName(user_name);
        this.setLastName(user_last_name);
        this.setVerifiedEmail(verified_email);
        this.setUnverifiedEmail(unverified_email);
        this.setEmailVerificationCode(email_verification_code);
        this.setPassword(user_password);
        this.setProfessorProfile(professor_profile);
        this.setStudentProfile(student_profile);

        this.setName();
    }

    public User(User user_instance) {
        if(user_instance == null) return;

        this.setIdentifier(user_instance.getIdentifier());
        this.setFirstName(user_instance.getFirstName());
        this.setLastName(user_instance.getLastName());
        this.setVerifiedEmail(user_instance.getVerifiedEmail());
        this.setUnverifiedEmail(user_instance.getUnverifiedEmail());
        this.setEmailVerificationCode(user_instance.getEmailVerificationCode());
        this.setPassword(user_instance.getPassword());
        this.setProfessorProfile(user_instance.getProfessorProfile());
        this.setStudentProfile(user_instance.getStudentProfile());

        this.setName();
    }

    // Setter
    public String getIdentifier() {
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
    public String getVerifiedEmail() {
        return this.verifiedEmail;
    }
    public String getUnverifiedEmail() {
        return this.unverifiedEmail;
    }
    public String getEmailVerificationCode() {
        return this.emailVerificationCode;
    }
    public Password getPassword() {
        return this.password;
    }
    public Professor getProfessorProfile() { return this.professorProfile; }
    public Student getStudentProfile() { return this.studentProfile; }


    // Setters
    public void setIdentifier(String user_id) {
        this.id = user_id;
    }
    public void setFirstName(String first_name) {
        this.firstName = first_name;
        this.setName();
    }
    public void setLastName(String last_name) {
        this.lastName = last_name;
        this.setName();
    }
    public void setName() {
        this.name = this.getFirstName() + " " + this.getLastName();
    }
    public void setVerifiedEmail(String verified_email) {
        this.verifiedEmail = verified_email;
    }
    public void setUnverifiedEmail(String unverified_email) {
        this.unverifiedEmail = unverified_email;
    }
    public void setEmailVerificationCode(String email_verification_code) {
        this.emailVerificationCode = email_verification_code;
    }
    public void setPassword(Password password_instance) {
        this.password = password_instance != null ? new Password(password_instance) : null;
    }
    public void setProfessorProfile(Professor professor_profile) { this.professorProfile = professor_profile != null ? new Professor(professor_profile) : null; }
    public void setStudentProfile(Student student_profile) { this.studentProfile = studentProfile != null ? new Student(student_profile) : null; }
}
