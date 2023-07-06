package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Subject;
import edu.ucema.academics.repositories.SubjectRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    // ! Injected Dependencies
    @Autowired
    private SubjectRepository subject_repositories;

    // ! Constructors
    public void SubjectRepository() {
    }

    // ! Business Logic

    // * Get all Subjects
    public ResponseEntity<Iterable<Subject>> getAllSubjects() {
        return ResponseEntity.status(HttpStatus.OK).body(subject_repositories.findAll());
    }

    // * Create Subject
    public ResponseEntity<Subject> createSubject(Subject subject_instance) {
        return ResponseEntity.status(HttpStatus.OK).body(subject_repositories.save(new Subject(subject_instance)));
    }

    // * Delete Subject
    public ResponseEntity<?> deleteSubject(String subject_id) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_id);
        if (opt_db_subject.isPresent()) {
            subject_repositories.delete(opt_db_subject.get());
            if (!subject_repositories.existsById(subject_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected Subject was deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected Subject could not be deleted. Please try again later.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Subject. Please try again later.");
        }
    }

    // * Update Subject
    public ResponseEntity<?> modifySubject(Subject subject_instance) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_instance.getIdentifier());
        if (opt_db_subject.isPresent()) {
            Subject db_subject = opt_db_subject.get();
            db_subject.setName(subject_instance.getName());

            return ResponseEntity.status(HttpStatus.OK).body(subject_repositories.save(db_subject));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Subject. Please try again later.");
        }
    }

    // * Get a Subject
    public ResponseEntity<?> getSubjectById(String subject_id) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_id);
        if (opt_db_subject.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_subject.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Subject. Please try again later.");
        }
    }
}
