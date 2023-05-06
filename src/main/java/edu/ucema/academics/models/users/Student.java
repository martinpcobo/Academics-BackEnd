package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
public final class Student extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_to_course", joinColumns = @JoinColumn(name = "id_student"), inverseJoinColumns = @JoinColumn(name = "id_course"))
    private List<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Grade> grades;

    @OneToOne(mappedBy = "studentProfile", fetch = FetchType.LAZY)
    private User user;

    public Student() {
    }

    public Student(Student student_instance) {
        super(student_instance.getUser());
        this.setCourses(student_instance.getCourses());
        this.setUser(student_instance.getUser());
        this.setGrades(student_instance.getGrades());
    }

    public Student(User user, List<Course> student_courses, List<Grade> grades_list) {
        super(user);
        this.setUser(user);
        this.setCourses(student_courses);
        this.setGrades(grades_list);
    }

    // Getters
    public List<Course> getCourses() {
        return this.courses;
    }
    public User getUser() { return this.user; }
    public List<Grade> getGrades() { return this.grades; }

    // Setters
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void setUser(User user) { this.user = new User(user); }
    public void setGrades(List<Grade> grade_list) { this.grades = grade_list; }
}
