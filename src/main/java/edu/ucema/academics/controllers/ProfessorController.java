package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.ClientResponseDTO;
import edu.ucema.academics.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/professor")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class ProfessorController {
    @Autowired
    ProfessorService professor_service;

    // Create Professor
    @PostMapping(path = "/{user_id}")
    public ResponseEntity<?> subscribeProfessor(@PathVariable String user_id) {
        try {
            return ResponseEntity.status(200).body(professor_service.subscribeProfessor(user_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Delete Professor
    @DeleteMapping(path = "/{professor_id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable String professor_id) {
        try {
            if (professor_service.deleteProfessor(professor_id)) {
                return ResponseEntity.status(200).body(new ClientResponseDTO("Professor profile deleted successfully."));
            } else {
                return ResponseEntity.status(500).body(new ClientResponseDTO("Professor profile could not be deleted."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // TODO: Endpoints to be tested.
}
