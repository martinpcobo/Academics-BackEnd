package edu.ucema.academics.controllers;

import edu.ucema.academics.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/student")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class StudentController {
    @Autowired
    private StudentService student_service;

    public StudentController() {
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllStudents() {
        try {
            return student_service.getAllStudents();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(path = "/{user_id}")
    public ResponseEntity<?> subscribeStudent(@PathVariable String user_id) {
        try {
            return student_service.subscribeUser(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping(path = "/{student_id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String student_id) {
        try {
            return student_service.deleteStudentById(student_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // TODO: Endpoints to be tested.
    @GetMapping(path = "/{student_id}")
    public ResponseEntity<?> getStudentById(@PathVariable String student_id) {
        try {
            return student_service.getStudentById(student_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
