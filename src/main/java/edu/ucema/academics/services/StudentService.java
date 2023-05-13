package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.StudentRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class StudentService {
    // ! Injected Dependencies
    @Autowired
    private StudentRepository student_repository;
    @Autowired
    private UserRepository user_repository;

    // ! Constructors
    public StudentService() {
    }

    // ! Business Logic

    // * Create a Student Profile
    @Transactional
    public ResponseEntity<?> subscribeUser(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            Student new_student = new Student();
            new_student.setCourses(new ArrayList<Course>());
            new_student.setGrades(new ArrayList<Grade>());
            new_student.setUser(opt_db_user.get());

            return ResponseEntity.status(HttpStatus.OK).body(this.student_repository.save(new_student));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later.");
        }
    }

    // * Get Student Information
    public ResponseEntity<?> getStudentById(String student_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        if (opt_db_student.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Student could not be found. Please try again later.");
        }
    }

    // * Delete Student Profile
    public ResponseEntity<?> deleteStudentById(String student_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        if (opt_db_student.isPresent()) {
            Student db_student = opt_db_student.get();
            student_repository.delete(db_student);

            if (student_repository.findById(student_id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected User was deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected Student could not be deleted. Please try again later.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Student could not be found. Please try again later.");
        }
    }
}
