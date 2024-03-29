package edu.ucema.academics.models.courses;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    // ! Attributes
    // * Data
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

    // * Relationships
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Professor> professors;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Student> students;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Grade> grades;

    // ! Constructors
    public Course() {
    }

    public Course(String course_id, List<Student> students_list, List<Professor> professors_list, Date course_start_date, Date course_end_date, String course_name, String course_description, List<Grade> grades_list) {
        this.setId(course_id);
        this.setStudents(students_list);
        this.setProfessors(professors_list);
        this.setStartDate(course_start_date);
        this.setEndDate(course_end_date);
        this.setName(course_name);
        this.setDescription(course_description);
        this.setGrades(grades_list);
    }

    public Course(Course course_instance) {
        this.setId(course_instance.getId());
        this.setStudents(course_instance.getStudents());
        this.setProfessors(course_instance.getProfessors());
        this.setEndDate(course_instance.getEndDate());
        this.setStartDate(course_instance.getStartDate());
        this.setDescription(course_instance.getDescription());
        this.setName(course_instance.getName());
        this.setGrades(course_instance.getGrades());
    }

    // ! Methods
    // * Getters
    public String getId() {
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
        return this.professors;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

    // * Setters
    public void setId(String course_id) {
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
        if (this.professors != null && this.professors.equals(professors_list)) return;
        this.professors = professors_list;
    }

    public void setStudents(List<Student> student_list) {
        if (this.students != null && this.students.equals(student_list)) return;
        this.students = student_list;
    }

    public void setGrades(List<Grade> grade_list) {
        if (this.grades != null && this.grades.equals(grade_list)) return;
        this.grades = grade_list;
    }
}