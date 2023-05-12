package edu.ucema.academics.models.courses;

import edu.ucema.academics.models.Subject;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.List;

@Entity
public class Class extends Course {
    // ! Attributes
    // * Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // ! Constructors
    public Class() {
    }
    public Class(String class_id, String course_id, List<Student> students_list, List<Professor> professors_list, Date class_start_date, Date class_end_date, Subject subject_instance, String course_name, String course_description) {
        super(course_id, students_list, professors_list, class_start_date, class_end_date, course_name, course_description);
        this.setIdentifier(class_id);
        this.setSubject(subject_instance);
    }
    public Class(Class class_instance) {
        super(class_instance);
        this.setIdentifier(class_instance.getIdentifier());
        this.setSubject(class_instance.getSubject());
    }

    // ! Methods
    // * Getters
    public Subject getSubject() {
        return new Subject(this.subject);
    }

    // * Setters
    public void setSubject(Subject subject_instance) {
        this.subject = subject_instance;
    }
}
