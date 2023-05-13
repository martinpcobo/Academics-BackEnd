package edu.ucema.academics.controllers;

import edu.ucema.academics.models.Subject;
import edu.ucema.academics.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/subject")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class SubjectController {
    // Injectable Services
    @Autowired
    SubjectService subject_service;

    // Constructor
    public void SubjectController() {
    }

    // ! Endpoints
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
