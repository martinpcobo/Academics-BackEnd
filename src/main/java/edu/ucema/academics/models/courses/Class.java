package edu.ucema.academics.models.courses;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.Subject;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "class")
@PrimaryKeyJoinColumn(name = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identifier")
public class Class extends Course {
    // ! Attributes
    // * Relationships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
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
        if (this.subject != null && this.subject.equals(subject_instance)) return;
        this.subject = subject_instance;
    }
}
