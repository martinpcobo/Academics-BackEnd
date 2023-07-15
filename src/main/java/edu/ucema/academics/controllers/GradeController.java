package edu.ucema.academics.controllers;

import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/grade")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class GradeController {
    // ! Injected Dependencies
    @Autowired
    private GradeService grade_service;

    // ! Business Logic
    // * Record a Grade
    @PostMapping(path = "/")
    public ResponseEntity<?> recordGrade(@RequestBody Grade grade_instance) {
        try {
            return grade_service.recordGrade(grade_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get all Grades from a Class
    @GetMapping(path = "/course/{course_id}")
    public ResponseEntity<?> getGradesFromCourse(@PathVariable String course_id) {
        try {
            return grade_service.getGradesFromCourse(course_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get all Grades from a Student
    @GetMapping(path = "/student/{student_id}")
    public ResponseEntity<?> getGradesFromStudent(@PathVariable String student_id) {
        try {
            return grade_service.getGradesFromStudent(student_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get Grade by Id
    @GetMapping(path = "/{grade_id}")
    public ResponseEntity<?> getGradeById(@PathVariable String grade_id) {
        try {
            return grade_service.getGradeById(grade_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Delete a Grade
    @DeleteMapping(path = "/{grade_id}")
    public ResponseEntity<?> deleteClass(@PathVariable String grade_id) {
        try {
            return grade_service.deleteGrade(grade_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Update Grade Details
    @PutMapping(path = "/")
    public ResponseEntity<?> modifyGrade(@RequestBody Grade grade_instance) {
        try {
            return grade_service.modifyGrade(grade_instance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
