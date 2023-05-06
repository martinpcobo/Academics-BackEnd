package edu.ucema.academics.controllers;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/class")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class ClassController {
    // * Injected Services
    @Autowired
    private ClassService class_service;

    // * Business Logic

    // Create a Class
    @PostMapping(path = "/")
    public ResponseEntity<?> createClass(@RequestBody Class class_instance) {
        try {
            return ResponseEntity.status(200).body(class_service.createClass(class_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Get Class by Id
    @GetMapping(path = "/{class_id}")
    public ResponseEntity<?> getClassById(@PathVariable String class_id) {
        try {
            return ResponseEntity.status(200).body(class_service.getClassById(class_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Delete a class
    @DeleteMapping(path = "/{class_id}")
    public ResponseEntity<?> deleteClass(@PathVariable String class_id) {
        try {
            return ResponseEntity.status(200).body(class_service.deleteClass(class_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Modify the Date of a Class
    @PutMapping(path = "/details")
    public ResponseEntity<?> setClassDate(@RequestBody Class class_instance) {
        try {
            return ResponseEntity.status(200).body(class_service.setClassDetails(class_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Modify the Professors of a Class
    @PutMapping(path = "/professors")
    public ResponseEntity<?> modifyClassProfessors(@RequestBody Class class_instance, @RequestBody List<String> professors_ids) {
        try {
            return ResponseEntity.status(200).body(class_service.setClassProfessors(class_instance, professors_ids));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Modify the Students of a Class
    @PutMapping(path = "/students")
    public ResponseEntity<?> modifyClassStudents(@RequestBody Class class_instance, @RequestBody List<String> student_ids) {
        try {
            return ResponseEntity.status(200).body(class_service.setClassStudents(class_instance, student_ids));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
}
