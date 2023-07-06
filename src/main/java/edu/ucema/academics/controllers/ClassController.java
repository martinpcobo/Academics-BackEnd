package edu.ucema.academics.controllers;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.services.ClassService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/class")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ClassController {
    // ! Injected Dependencies
    @Autowired
    private ClassService class_service;

    // ! Business Logic

    // * Get all classes
    @GetMapping(path = "/")
    public ResponseEntity<?> getAllClasses() {
        try {
            return class_service.getAllClasses();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Create a Class
    @PostMapping(path = "/")
    public ResponseEntity<?> createClass(@RequestBody Class class_instance) {
        try {
            return class_service.createClass(class_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get Class by Id
    @GetMapping(path = "/{class_id}")
    public ResponseEntity<?> getClassById(@PathVariable String class_id) {
        try {
            return class_service.getClassById(class_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Delete a class
    @DeleteMapping(path = "/{class_id}")
    public ResponseEntity<?> deleteClass(@PathVariable String class_id) {
        try {
            return class_service.deleteClass(class_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Update Class Details
    @PutMapping(path = "/")
    public ResponseEntity<?> modifyClassDetails(@RequestBody Class class_instance) {
        try {
            return class_service.modifyClassDetails(class_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Modify a Class' Professors
    @PutMapping(path = "/{class_id}/professors")
    public ResponseEntity<?> modifyClassProfessors(@PathVariable String class_id, @RequestBody List<String> professors_ids) {
        try {
            return class_service.setClassProfessors(class_id, professors_ids);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Modify a Class' Students
    @PutMapping(path = "/{class_id}/students")
    public ResponseEntity<?> modifyClassStudents(@PathVariable String class_id, @RequestBody List<String> student_ids) {
        try {
            return class_service.setClassStudents(class_id, student_ids);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
