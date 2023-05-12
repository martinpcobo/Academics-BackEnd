package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.GradeRepository;
import edu.ucema.academics.repositories.StudentRepository;

import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    // Attributes
    @Autowired
    private StudentRepository student_repository;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private ClassRepository class_repository;
    @Autowired
    private GradeRepository grade_repository;

    // Constructor
    public StudentService() {
    }

    // * Business Logic

    // Subscribe a User as a Student
    @Transactional
    public Student subscribeUser(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            Student new_student = new Student();
            new_student.setCourses(new ArrayList<Course>());
            new_student.setGrades(new ArrayList<Grade>());
            new_student.setUser(opt_db_user.get());

            return this.student_repository.save(new_student);
        } else {
            throw new Exception("User not found.");
        }
    }

    // Get Student Information
    public Student getStudentById(String student_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        if (opt_db_student.isPresent()) {
            return opt_db_student.get();
        } else {
            throw new Exception("User not found.");
        }
    }

    // Delete Student
    public Boolean deleteStudentById(String student_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        if (opt_db_student.isPresent()) {
            Student db_student = opt_db_student.get();
            student_repository.delete(db_student);

            return student_repository.findById(student_id).isEmpty();
        } else {
            throw new Exception("Student not found.");
        }
    }

    // Add a Class to a Student
    public void addClass(String student_id, String class_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_student.isPresent() && opt_db_class.isPresent()) {
            Student db_student = opt_db_student.get();
            Class db_class = opt_db_class.get();

            List<Course> student_courses = db_student.getCourses();
            student_courses.add(db_class);
            db_student.setCourses(student_courses);
            this.student_repository.save(db_student);
        } else {
            throw new Exception("Student or Class not found.");
        }
    }
    // Remove a Class from a Student
    public void removeClass(String student_id, String class_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_student.isPresent() && opt_db_class.isPresent()) {
            Student db_student = opt_db_student.get();
            Class db_class = opt_db_class.get();

            // Delete grade if one exists
            /*
            Optional<Grade> opt_db_grade = grade_repository.getGradeByStudentCourse(class_id, student_id);
            if(opt_db_grade.isPresent()) {
                Grade db_grade = opt_db_grade.get();
                grade_repository.delete(db_grade);
            }
            */

            // Remove the course from the student to course M2M.
            List<Course> student_courses = db_student.getCourses();
            student_courses.remove(db_class);
            db_student.setCourses(student_courses);

            this.student_repository.save(db_student);
        } else {
            throw new Exception("Student or Course not found.");
        }
    }

    // Set the Grade for a Student's Class
    /*
    public void setGrade(String student_id, String class_id, Float value) throws Exception{
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_student.isPresent() && opt_db_class.isPresent()) {
            Student db_student = opt_db_student.get();
            Class db_class = opt_db_class.get();

            Optional<Grade> opt_db_grade = grade_repository.getGradeByStudentCourse(class_id, student_id);
            // Modify the grade if it already exists
            if(opt_db_grade.isPresent()) {
                Grade db_grade = opt_db_grade.get();
                db_grade.setValue(value);
                grade_repository.save(db_grade);
            }
            // Create the grade if it does not exist
            else {
                Grade student_grade = new Grade();
                student_grade.setCourse(db_class);
                student_grade.setStudent(db_student);
                student_grade.setValue(value);
                grade_repository.save(student_grade);
            }
        } else {
            throw new Exception("Student or Course not found.");
        }
    }
    */
}
