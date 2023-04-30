package edu.ucema.academics.controllers;

import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.ucema.academics.repositories.StudentRepository;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/api/student")
@CrossOrigin(
        origins = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class StudentController {
    @Autowired
    private StudentService student_service;
    public StudentController() {}

    @PostMapping(path = "/")
    public ResponseEntity<?> createStudent(@RequestBody User user) {
        try {
            return ResponseEntity.status(200).body(student_service.subscribe_user(user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

}
