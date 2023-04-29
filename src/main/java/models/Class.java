package models;

import models.users.Professor;
import models.users.Student;
import models.users.User;

import java.util.Date;
import java.util.List;

public class Class {
    // Attributes
    private Long identifier;
    private Subject subject;
    private List<Professor> professors;
    private List<Student> students;
    private Date start_date;
    private Date end_date;


    // Constructors
    Class(Long class_id, Subject subject_instance, List<Student> students_list, List<Professor> professors_list, Date class_start_date, Date class_end_date) {
        this.identifier = class_id;
        this.subject = subject_instance;
        this.students = students_list;
        this.professors = professors_list;
        this.start_date = class_start_date;
        this.end_date = class_end_date;
    }
    Class(Class class_instance) {
        this.identifier = class_instance.getIdentifier();
        this.subject = class_instance.getSubject();
        this.students = class_instance.getStudents();
        this.professors = class_instance.getProfessors();
        this.end_date = class_instance.getEndDate();
        this.start_date = class_instance.getStartDate();
    }

    // Getters
    public Long getIdentifier() {
        return this.identifier;
    }
    public Subject getSubject() {
        return new Subject(this.subject);
    }
    public List<Student> getStudents() {
        return this.students.stream().map((student) -> new Student(student)).toList();
    }
    public List<Professor> getProfessors() {
        return this.professors.stream().map((professor) -> new Professor(professor)).toList();
    }
    public Date getStartDate() {
        return this.start_date;
    }
    public Date getEndDate() {
        return this.end_date;
    }

    // Student Methods
    private Student getStudentById(Long student_id) {
        List<Student> users_results = this.students.stream().filter(student -> student.getIdentifier().equals(student_id)).toList();
        return users_results.size() == 1 ? users_results.get(0) : null;
    }
    public boolean isStudentSubscribed(User student) {
        return this.getStudentById(student.getIdentifier()) != null;
    }
    public void addStudent(Student student) {
        student = new Student(student);
        if (!this.isStudentSubscribed(student)) {
            this.students.add(student);
        }
    }
    public void removeStudent(Student student) {
        student = new Student(student);
        if (this.isStudentSubscribed(student)) {
            this.students.remove(this.getStudentById(student.getIdentifier()));
        }
    }

    // Professor Methods
    private Professor getProfessorById(Long professor_id) {
        List<Professor> users_results = this.professors.stream().filter(user -> user.getIdentifier().equals(professor_id)).toList();
        return users_results.size() == 1 ? users_results.get(0) : null;
    }
    public boolean isProfessorSubscribed(User professor) {
        return this.getProfessorById(professor.getIdentifier()) != null;
    }
    public void addProfessor(Professor professor) {
        professor = new Professor(professor);
        if (!this.isProfessorSubscribed(professor)) {
            this.professors.add(professor);
        }
    }
    public void removeProfessor(Professor professor) {
        professor = new Professor(professor);
        if (this.isProfessorSubscribed(professor)) {
            this.professors.remove(this.getProfessorById(professor.getIdentifier()));
        }
    }
}
