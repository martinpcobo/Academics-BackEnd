package models.users;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import models.courses.Class;

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
    private List<Class> classes;
    public Student() {}
    public Student(Long student_id, String user_name, String user_last_name, String email, List<Class> student_classes) {
        super(student_id, user_name, user_last_name, email);
        this.classes = student_classes;
    }
    public Student(Student student_instance) {
        super(student_instance);
        this.classes = student_instance.classes;
    }

    public List<Class> getClasses() {
        return this.classes;
    }
}
