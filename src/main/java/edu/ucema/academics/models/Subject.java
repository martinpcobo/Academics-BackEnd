package edu.ucema.academics.models;

import jakarta.persistence.*;
import edu.ucema.academics.models.courses.Class;

import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    // Attributes
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "subject")
    private List<Class> classes;

    // Constructor
    public Subject() {}
    public Subject(Long subject_id, String subject_name) {
        this.name = subject_name;
        this.id = subject_id;
    }
    public Subject(Subject subject_instance) {
        this.name = subject_instance.getName();
        this.id = subject_instance.getIdentifier();
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public Long getIdentifier() {
        return this.id;
    }
}