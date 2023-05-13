package edu.ucema.academics.models.users;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identifier")
public final class Student {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String id;

    // * Relationships
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_to_course",
            joinColumns = @JoinColumn(name = "id_student", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id_course", referencedColumnName = "id")
    )
    @JsonManagedReference
    private List<Course> courses;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Grade> grades;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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
    public String getIdentifier() {
        return this.id;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public User getUser() {
        return this.user;
    }

    public List<Grade> getGrades() {
        return this.grades;
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

    public void setGrades(List<Grade> grade_list) {
        this.grades = grade_list;
    }
}
