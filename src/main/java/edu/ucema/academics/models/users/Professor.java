package edu.ucema.academics.models.users;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.courses.Course;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "professor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identifier")
public final class Professor {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String id;

    // * Relationship
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "professor_to_course",
            joinColumns = @JoinColumn(name = "id_professor", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id_course", referencedColumnName = "id")
    )
    private List<Course> courses;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // ! Constructors
    public Professor() {
    }

    public Professor(Professor professor_instance) {
        this.setIdentifier(professor_instance.getIdentifier());
        this.setCourses(professor_instance.getCourses());
        this.setUser(professor_instance.getUser());
    }

    public Professor(String id, User user_instance, List<Course> courses_list) {
        this.setIdentifier(id);
        this.setCourses(courses_list);
        this.setUser(user_instance);
    }

    // ! Methods
    // * Getters
    public String getIdentifier() {
        return this.id;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public User getUser() {
        return this.user;
    }

    // * Setters
    public void setIdentifier(String id) {
        this.id = id;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setUser(User user) {
        if (this.user != null && this.user.equals(user)) return;

        this.user = user;
    }
}
