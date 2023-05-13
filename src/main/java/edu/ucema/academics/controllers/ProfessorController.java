package edu.ucema.academics.controllers;

import edu.ucema.academics.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/professor")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class ProfessorController {
    // ! Injected Dependencies
    @Autowired
    ProfessorService professor_service;

    // ! Endpoints
    // * Create Professor
    @PostMapping(path = "/{user_id}")
    public ResponseEntity<?> subscribeProfessor(@PathVariable String user_id) {
        try {
            return professor_service.subscribeProfessor(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Delete Professor
    @DeleteMapping(path = "/{professor_id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable String professor_id) {
        try {
            return professor_service.deleteProfessor(professor_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get Professor By Id
    @GetMapping(path = "/{professor_id}")
    public ResponseEntity<?> getProfessorProfile(@PathVariable String professor_id) {
        try {
            return professor_service.getProfessorById(professor_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
