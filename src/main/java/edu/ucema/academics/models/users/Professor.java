package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "professor")
public final class Professor extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "professor_to_course",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_course")
    )
    private List<Course> courses;

    @OneToOne(mappedBy = "professorProfile", fetch = FetchType.LAZY)
    private User user;

    public Professor() {
    }

    public Professor(Professor professor_instance) {
        super(professor_instance);
        this.setCourses(professor_instance.getCourses());
        this.setUser(professor_instance.getUser());
    }
    public Professor(User user_instance, List<Course> courses_list) {
        super(user_instance);
        this.setCourses(courses_list);
        this.setUser(user_instance);
    }

    // Getters
    public List<Course> getCourses() {
        return this.courses;
    }
    public User getUser() { return this.user; }
    // Setters
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void setUser(User user) { this.user = new User(user); }
}
