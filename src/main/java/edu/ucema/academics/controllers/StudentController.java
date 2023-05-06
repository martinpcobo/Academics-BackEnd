package edu.ucema.academics.controllers;

import edu.ucema.academics.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/{user_id}")
    public ResponseEntity<?> subscribeStudent(@PathVariable String user_id) {
        try {
            return ResponseEntity.status(200).body(student_service.subscribeUser(user_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    @GetMapping(path = "/{student_id}")
    public ResponseEntity<?> getStudentById(@PathVariable String student_id) {
        try {
            return ResponseEntity.status(200).body(student_service.getStudentById(student_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    @DeleteMapping(path = "/{student_id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String student_id) {
        try {
            if(student_service.deleteStudentById(student_id)) {
                return ResponseEntity.status(200).body(true);
            } else {
                return ResponseEntity.status(500).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
}
