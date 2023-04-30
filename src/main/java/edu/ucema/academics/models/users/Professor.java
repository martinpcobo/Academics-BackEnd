package edu.ucema.academics.models.users;

import edu.ucema.academics.models.courses.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;


@Entity
public final class Professor extends User {
    @ManyToMany()
    @JoinTable(
            name = "professor_to_class",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_class")
    )
    private List<Course> courses;
    public Professor() {}
    public Professor(Long user_id, String user_name, String user_last_name, Email email, Password password) {
        super(user_id, user_name, user_last_name, email, password);
    }
    public Professor(Professor professor_instance) {
        super(professor_instance);
    }
}
