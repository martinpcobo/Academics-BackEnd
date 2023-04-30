package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public final class Student extends User {
    @ManyToMany()
    @JoinTable(
            name = "student_to_class",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_course")
    )
    private List<Course> courses;
    public Student() {}
    public Student(Long student_id, String user_name, String user_last_name, Email email, Password password, List<Course> student_courses) {
        super(student_id, user_name, user_last_name, email, password);
        this.courses = student_courses;
    }
    public Student(Student student_instance) {
        super(student_instance);
        this.courses = student_instance.courses;
    }

    public List<Course> getClasses() {
        return this.courses;
    }
}
