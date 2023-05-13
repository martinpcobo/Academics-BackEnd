package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GradeService {
    // ! Injected Dependencies
    @Autowired
    private GradeRepository grade_repository;

    // ! Constructor
    public GradeService() {
    }

    // ! Business Logic
    // * Record new Grade
    public ResponseEntity<?> recordGrade(Grade grade_instance) {
        return ResponseEntity.status(HttpStatus.OK).body(this.grade_repository.save(new Grade(grade_instance)));
    }

    // * Get Grade By Id
    public ResponseEntity<?> getGradeById(String grade_id) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_id);
        if (opt_db_grade.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_grade.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Grade could not be found. Please try again later.");
        }
    }

    // * Modify a Grade
    public ResponseEntity<?> modifyGrade(Grade grade_instance) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_instance.getIdentifier());
        if (opt_db_grade.isPresent()) {
            Grade db_grade = opt_db_grade.get();
            db_grade.setValue(grade_instance.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(grade_repository.save(db_grade));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Grade could not be found. Please try again later.");
        }
    }

    // * Delete a Grade
    public ResponseEntity<?> deleteGrade(String grade_id) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_id);
        if (opt_db_grade.isPresent()) {
            grade_repository.delete(opt_db_grade.get());
            if (!grade_repository.existsById(grade_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected Grade was deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected Grade could not be deleted. Please try again later.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Grade could not be found. Please try again later.");
        }
    }
}
