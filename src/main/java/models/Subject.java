package models;

public class Subject {
    // Attributes
    private String identifier;
    private String name;

    // Constructor
    public Subject(String subject_id, String subject_name) {
        this.name = subject_name;
        this.identifier = subject_id;
    }
    public Subject(Subject subject_instance) {
        this.name = subject_instance.getName();
        this.identifier = subject_instance.getIdentifier();
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public String getIdentifier() {
        return this.identifier;
    }
}