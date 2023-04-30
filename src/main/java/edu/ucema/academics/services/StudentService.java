package edu.ucema.academics.services;

import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    // Attributes
    @Autowired
    private StudentRepository student_repository;

    // Constructor
    public StudentService() {}

    // Business Logic
    public Student subscribe_user(User user) throws Exception {
        return this.student_repository.save(new Student((Student) user));
    }
}
