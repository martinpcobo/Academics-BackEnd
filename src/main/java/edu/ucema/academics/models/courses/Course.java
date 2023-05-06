package edu.ucema.academics.models.courses;

import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
public abstract class Course {
    // Attributes
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "start_date")
    @Temporal(value = TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(value = TemporalType.DATE)
    private Date endDate;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;


    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Professor> professors;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Student> students;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Grade> grades;

    // Constructors
    public Course() {
    }

    public Course(String course_id, List<Student> students_list, List<Professor> professors_list, Date course_start_date, Date course_end_date, String course_name, String course_description) {
        this.setIdentifier(course_id);
        this.setStudents(students_list);
        this.setProfessors(professors_list);
        this.setStartDate(course_start_date);
        this.setEndDate(course_end_date);
        this.setName(course_name);
        this.setDescription(course_description);
    }

    public Course(Course course_instance) {
        this.setIdentifier(course_instance.getIdentifier());
        this.setStudents(course_instance.getStudents());
        this.setProfessors(course_instance.getProfessors());
        this.setEndDate(course_instance.getEndDate());
        this.setStartDate(course_instance.getStartDate());
        this.setDescription(course_instance.getDescription());
    }

    // * Methods

    // Getters
    public String getIdentifier() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public List<Professor> getProfessors() {
        return this.professors.stream().map((professor) -> new Professor(professor)).toList();
    }

    public List<Student> getStudents() {
        return this.students.stream().map((student) -> new Student(student)).toList();
    }

    // Setters
    public void setIdentifier(String course_id) {
        this.id = course_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }

    public void setEndDate(Date end_date) {
        this.endDate = end_date;
    }

    public void setProfessors(List<Professor> professors_list) {
        this.professors = professors_list.stream().map((professor) -> new Professor(professor)).toList();
    }

    public void setStudents(List<Student> student_list) {
        this.students = student_list.stream().map((student) -> new Student(student)).toList();
    }
}