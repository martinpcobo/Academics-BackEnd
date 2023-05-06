package edu.ucema.academics.models;

import jakarta.persistence.*;
import edu.ucema.academics.models.courses.Class;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Class> classes;

    // Constructor
    public Subject() {
    }

    public Subject(String subject_id, String subject_name) {
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

    public String getIdentifier() {
        return this.id;
    }
}