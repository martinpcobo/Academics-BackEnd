package edu.ucema.academics.models.courses;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "grade")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identifier")
public class Grade {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "value")
    private Float value;

    // * Relationships
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Student student;

    // ! Constructors
    public Grade() {
    }

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

    // ! Methods
    // * Getters
    public String getIdentifier() {
        return this.id;
    }

    public Course getCourse() {
        return this.course;
    }

    public Student getStudent() {
        return this.student;
    }

    public Float getValue() {
        return this.value;
    }

    // * Setters
    public void setIdentifier(String id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
