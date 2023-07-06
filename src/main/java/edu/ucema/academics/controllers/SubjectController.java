package edu.ucema.academics.controllers;

import edu.ucema.academics.models.courses.Subject;
import edu.ucema.academics.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/subject")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SubjectController {
    // Injectable Services
    @Autowired
    SubjectService subject_service;

    // ! Endpoints

    // * Get all Subjects
    @GetMapping(path = "/")
    public ResponseEntity<?> getAllSubjects() {
        try {
            return subject_service.getAllSubjects();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Create Subject
    @PostMapping(path = "/")
    public ResponseEntity<?> createSubject(@RequestBody Subject subject_instance) {
        try {
            return subject_service.createSubject(subject_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Delete Subject
    @DeleteMapping(path = "/{subject_id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String subject_id) {
        try {
            return subject_service.deleteSubject(subject_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Modify Subject
    @PutMapping(path = "/")
    public ResponseEntity<?> modifySubject(@RequestBody Subject subject_instance) {
        try {
            return subject_service.modifySubject(subject_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get Subject Details
    @GetMapping(path = "/{subject_id}")
    public ResponseEntity<?> modifySubject(@PathVariable String subject_id) {
        try {
            return subject_service.getSubjectById(subject_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
