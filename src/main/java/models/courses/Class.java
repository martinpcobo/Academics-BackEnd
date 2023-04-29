package models.courses;

import jakarta.persistence.*;
import models.Subject;
import models.users.Professor;
import models.users.Student;

import java.util.Date;
import java.util.List;

@Entity
public class Class extends Course {
    // Attributes
    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    // Constructors
    public Class() {}
    public Class(Long class_id, List<Student> students_list, List<Professor> professors_list, Date class_start_date, Date class_end_date, Subject subject_instance) {
        super(class_id, students_list, professors_list, class_start_date, class_end_date);
        this.subject = subject_instance;

    }
    public Class(Class class_instance) {
        super(class_instance);
        this.subject = class_instance.getSubject();
    }

    // Getters
    public Subject getSubject() {
        return new Subject(this.subject);
    }
}
