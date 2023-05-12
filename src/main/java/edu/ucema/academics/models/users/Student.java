package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "student")
public final class Student {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "user_id")
    private String id;

    // * Relationships
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_to_course", joinColumns = @JoinColumn(name = "id_student"), inverseJoinColumns = @JoinColumn(name = "id_course"))
    private List<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Grade> grades;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // ! Constructors
    public Student() {
    }

    public Student(Student student_instance) {
        this.setIdentifier(student_instance.getIdentifier());
        this.setCourses(student_instance.getCourses());
        this.setUser(student_instance.getUser());
        this.setGrades(student_instance.getGrades());
    }

    public Student(String id, User user, List<Course> student_courses, List<Grade> grades_list) {
        this.setIdentifier(id);
        this.setUser(user);
        this.setCourses(student_courses);
        this.setGrades(grades_list);
    }

    // ! Methods
    // * Getters
    public String getIdentifier() { return this.id; }
    public List<Course> getCourses() {
        return this.courses;
    }
    public User getUser() { return this.user; }
    public List<Grade> getGrades() { return this.grades; }

    // * Setters
    public void setIdentifier(String id) { this.id = id; }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void setUser(User user) { this.user = new User(user); }
    public void setGrades(List<Grade> grade_list) { this.grades = grade_list; }
}
