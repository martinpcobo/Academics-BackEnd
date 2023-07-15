package edu.ucema.academics.models.courses;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "subject")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Subject {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Class> classes;

    // Constructor
    public Subject() {
    }

    public Subject(String subject_id, String subject_name, List<Class> classes_list) {
        this.setName(subject_name);
        this.setId(subject_id);
        this.setClasses(classes_list);
    }

    public Subject(Subject subject_instance) {
        this.setName(subject_instance.getName());
        this.setId(subject_instance.getId());
        this.setClasses(subject_instance.getClasses());
    }

    // Getters
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Class> getClasses() {
        return this.classes;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}