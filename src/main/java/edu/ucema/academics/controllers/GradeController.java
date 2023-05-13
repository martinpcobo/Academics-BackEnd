package edu.ucema.academics.controllers;

import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.models.dtos.ClientResponseDTO;
import edu.ucema.academics.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/grade")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class GradeController {
    // ! Injected Dependencies
    @Autowired
    private GradeService grade_service;

    // ! Business Logic
    // Record a Grade
    @PostMapping(path = "/")
    public ResponseEntity<?> recordGrade(@RequestBody Grade grade_instance) {
        try {
            return ResponseEntity.status(200).body(grade_service.recordGrade(grade_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Get Grade by Id
    @GetMapping(path = "/{grade_id}")
    public ResponseEntity<?> getGradeById(@PathVariable String grade_id) {
        try {
            return ResponseEntity.status(200).body(grade_service.getGradeById(grade_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Delete a Grade
    @DeleteMapping(path = "/{grade_id}")
    public ResponseEntity<?> deleteClass(@PathVariable String grade_id) {
        try {
            if (grade_service.deleteGrade(grade_id)) {
                return ResponseEntity.status(200).body(new ClientResponseDTO("Selected Grade was deleted successfully."));
            } else {
                return ResponseEntity.status(500).body(new ClientResponseDTO("Selected Grade could not be deleted. Please try again later."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Update Grade Details
    @PutMapping(path = "/")
    public ResponseEntity<?> modifyGrade(@RequestBody Grade grade_instance) {
        try {
            return ResponseEntity.status(200).body(grade_service.modifyGrade(grade_instance));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
}
