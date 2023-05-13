package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Grade;
import edu.ucema.academics.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Grade recordGrade(Grade grade_instance) {
        return this.grade_repository.save(new Grade(grade_instance));
    }

    // * Get Grade By Id
    public Grade getGradeById(String grade_id) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_id);
        if (opt_db_grade.isPresent()) {
            return opt_db_grade.get();
        } else {
            throw new Exception("Selected Grade could not be found. Please try again later. ");
        }
    }

    // * Modify a Grade
    public Grade modifyGrade(Grade grade_instance) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_instance.getIdentifier());
        if (opt_db_grade.isPresent()) {
            Grade db_grade = opt_db_grade.get();
            db_grade.setValue(grade_instance.getValue());
            return grade_repository.save(db_grade);
        } else {
            throw new Exception("Selected Grade could not be found. Please try again later. ");
        }
    }

    // * Delete a Grade
    public Boolean deleteGrade(String grade_id) throws Exception {
        Optional<Grade> opt_db_grade = grade_repository.findById(grade_id);
        if (opt_db_grade.isPresent()) {
            grade_repository.delete(opt_db_grade.get());
            return !grade_repository.existsById(grade_id);
        } else {
            throw new Exception("Selected Grade could not be found. Please try again later. ");
        }
    }
}
