package edu.ucema.academics.services;

import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.ProfessorRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfessorService {
    // ! Injected Dependencies
    @Autowired
    private ProfessorRepository professor_repository;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private ClassRepository class_repository;

    // ! Constructors
    public ProfessorService() {
    }

    // ! Business Logic
    // * Get all Professors
    public ResponseEntity<?> getAllProfessors() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(professor_repository.findAll());
    }

    // * Create a Professor Profile
    @Transactional
    public ResponseEntity<?> subscribeProfessor(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            Professor new_professor = new Professor();
            new_professor.setCourses(null);
            new_professor.setUser(opt_db_user.get());

            return ResponseEntity.status(HttpStatus.OK).body(this.professor_repository.save(new_professor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Plase try again later.");
        }
    }

    // * Get Professor Profile
    @Transactional
    public ResponseEntity<?> getProfessorById(String prof_id) throws Exception {
        Optional<Professor> opt_db_prof = professor_repository.findById(prof_id);
        if (opt_db_prof.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_prof.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Professor could not be found. Plase try again later.");
        }
    }

    // * Delete a Professor Profile
    @Transactional
    public ResponseEntity<?> deleteProfessor(String professor_id) throws Exception {
        Optional<Professor> opt_db_professor = professor_repository.findById(professor_id);
        if (opt_db_professor.isPresent()) {
            this.professor_repository.delete(opt_db_professor.get());
            if (!this.professor_repository.existsById(professor_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected Professor was deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected Professor could not be deleted. Please try again later.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Professor could not be found. Plase try again later.");
        }
    }
}
