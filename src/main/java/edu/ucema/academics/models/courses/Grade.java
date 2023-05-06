package edu.ucema.academics.models.courses;

import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "value")
    private Float value;

    public Grade(String id, Course course, Student student, Float value) {
        this.setIdentifier(id);
        this.setCourse(course);
        this.setStudent(student);
        this.setValue(value);
    }

    public Grade(Grade grade_instance) {
        this.setIdentifier(grade_instance.getIdentifier());
        this.setCourse(grade_instance.getCourse());
        this.setStudent(grade_instance.getStudent());
        this.setValue(grade_instance.getValue());
    }

    public Grade() {
    }

    // Getters
    public String getIdentifier() {
        return this.id;
    }

    // Setters
    public void setIdentifier(String id) {
        this.id = id;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Float getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
