package models.courses;

import jakarta.persistence.*;
import models.users.Professor;
import models.users.Student;
import models.users.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
public abstract class Course {
    // Attributes
    @Id
    private Long id;
    @ManyToMany(mappedBy = "professors")
    private List<Professor> professors;
    @ManyToMany(mappedBy = "students")
    private List<Student> students;
    @Column(name = "start_date")
    @Temporal(value = TemporalType.DATE)
    private Date start_date;
    @Column(name = "end_date")
    @Temporal(value = TemporalType.DATE)
    private Date end_date;

    // Constructors
    public Course() {}
    public Course(Long course_id, List<Student> students_list, List<Professor> professors_list, Date course_start_date, Date course_end_date) {
        this.id = course_id;
        this.students = students_list;
        this.professors = professors_list;
        this.start_date = course_start_date;
        this.end_date = course_end_date;
    }
    public Course(Course course_instance) {
        this.id = course_instance.getIdentifier();
        this.students = course_instance.getStudents();
        this.professors = course_instance.getProfessors();
        this.end_date = course_instance.getEndDate();
        this.start_date = course_instance.getStartDate();
    }

    // * Methods
    // Getters
    public Long getIdentifier() {
        return this.id;
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
    // Setters
    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }
    public void setEndDate(Date end_date) {
        this.end_date = end_date;
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
