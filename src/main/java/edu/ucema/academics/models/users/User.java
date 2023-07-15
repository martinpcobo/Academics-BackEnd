package edu.ucema.academics.models.users;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yubico.webauthn.data.ByteArray;
import edu.ucema.academics.models.auth.Credential;
import edu.ucema.academics.models.users.enums.EUserRoles;
import edu.ucema.academics.utilities.ByteArrayAttributeConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
    // ! Attributes
    // * Data
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
    @Column(name = "handle")
    @Convert(converter = ByteArrayAttributeConverter.class)
    private ByteArray handle;

    // * Relationships
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id")
    @JsonIgnore
    private Credential credential;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Student studentProfile;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Professor professorProfile;

    // ! Constructors
    protected User() {
        this.setName();
    }

    public User(String user_id, String user_name, String user_last_name, String verified_email, String unverified_email, String email_verification_code, Credential user_credential, Student student_profile, Professor professor_profile, ByteArray handle) {
        this.setId(user_id);
        this.setFirstName(user_name);
        this.setLastName(user_last_name);
        this.setVerifiedEmail(verified_email);
        this.setUnverifiedEmail(unverified_email);
        this.setEmailVerificationCode(email_verification_code);
        this.setCredential(user_credential);
        this.setProfessorProfile(professor_profile);
        this.setStudentProfile(student_profile);
        this.setHandle(handle);

        this.setName();
    }

    public User(User user_instance) {
        if (user_instance == null) return;

        this.setId(user_instance.getId());
        this.setFirstName(user_instance.getFirstName());
        this.setLastName(user_instance.getLastName());
        this.setVerifiedEmail(user_instance.getVerifiedEmail());
        this.setUnverifiedEmail(user_instance.getUnverifiedEmail());
        this.setEmailVerificationCode(user_instance.getEmailVerificationCode());
        this.setCredential(user_instance.getCredential());
        this.setProfessorProfile(user_instance.getProfessorProfile());
        this.setStudentProfile(user_instance.getStudentProfile());
        this.setHandle(user_instance.getHandle());

        this.setName();
    }

    // ! Methods
    // * Getters
    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        this.setName();
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

    public Credential getCredential() {
        return this.credential;
    }

    public Professor getProfessorProfile() {
        return this.professorProfile;
    }

    public Student getStudentProfile() {
        return this.studentProfile;
    }

    public List<EUserRoles> getRoles() {
        List<EUserRoles> user_roles = new ArrayList<>();
        if (this.getProfessorProfile() != null) user_roles.add(EUserRoles.PROFESSOR);
        if (this.getStudentProfile() != null) user_roles.add(EUserRoles.STUDENT);
        if (user_roles.size() == 0) user_roles.add(EUserRoles.NONE);
        return user_roles;
    }

    public ByteArray getHandle() {
        return this.handle;
    }


    // * Setters
    public void setId(String user_id) {
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

    private void setName() {
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

    @JsonProperty("credential")
    public void setCredential(Credential credential_instance) {
        this.credential = credential_instance != null ? new Credential(credential_instance) : null;
    }

    public void setProfessorProfile(Professor professor_profile) {
        this.professorProfile = professor_profile != null ? new Professor(professor_profile) : null;
    }

    public void setStudentProfile(Student student_profile) {
        this.studentProfile = studentProfile != null ? new Student(student_profile) : null;
    }

    public void setHandle(ByteArray handle) {
        this.handle = handle;
    }

    // * Auth Methods
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getVerifiedEmail();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        if (this.getCredential() == null) return null;
        return this.getCredential().getPassword();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRoles().toString()));
        return authorities;
    }
}
