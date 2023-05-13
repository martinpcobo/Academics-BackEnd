package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.StudentRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // * Get Student Information
    public Student getStudentById(String student_id) throws Exception {
        Optional<Student> opt_db_student = student_repository.findById(student_id);
        if (opt_db_student.isPresent()) {
            return opt_db_student.get();
        } else {
            throw new Exception("User not found.");
        }
    }

    // * Delete Student Profile
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
}
