package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


@Entity
@Table(name = "professor")
public final class Professor {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String id;

    // * Relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "professor_to_course",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_course")
    )
    private List<Course> courses;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
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
    public String getIdentifier() { return this.id; }
    public List<Course> getCourses() {
        return this.courses;
    }
    public User getUser() { return this.user; }

    // * Setters
    public void setIdentifier(String id) { this.id = id; }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void setUser(User user) { this.user = new User(user); }
}
