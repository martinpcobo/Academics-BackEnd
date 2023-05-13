package edu.ucema.academics.controllers;

import edu.ucema.academics.models.Subject;
import edu.ucema.academics.models.dtos.ClientResponseDTO;
import edu.ucema.academics.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResponseEntity.status(200).body(subject_service.createSubject(subject_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // * Delete Subject
    @DeleteMapping(path = "/{subject_id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String subject_id) {
        try {
            if (subject_service.deleteSubject(subject_id)) {
                return ResponseEntity.status(200).body(new ClientResponseDTO("The selected Class was deleted successfully."));
            } else {
                return ResponseEntity.status(500).body(new ClientResponseDTO("The selected Class could not be deleted. Please try again later."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // * Modify Subject
    @PutMapping(path = "/")
    public ResponseEntity<?> modifySubject(@RequestBody Subject subject_instance) {
        try {
            return ResponseEntity.status(200).body(subject_service.modifySubject(subject_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // * Get Subject Details
    @GetMapping(path = "/{subject_id}")
    public ResponseEntity<?> modifySubject(@PathVariable String subject_id) {
        try {
            return ResponseEntity.status(200).body(subject_service.getSubjectById(subject_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

}
