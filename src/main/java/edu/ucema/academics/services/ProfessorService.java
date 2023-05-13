package edu.ucema.academics.services;

import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.ProfessorRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    // * Create a Professor Profile
    @Transactional
    public Professor subscribeProfessor(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            Professor new_professor = new Professor();
            new_professor.setCourses(null);
            new_professor.setUser(opt_db_user.get());

            return this.professor_repository.save(new_professor);
        } else {
            throw new Exception("User not found.");
        }
    }

    // * Get Professor Profile
    @Transactional
    public Professor getProfessorById(String prof_id) throws Exception {
        Optional<Professor> opt_db_prof = professor_repository.findById(prof_id);
        if (opt_db_prof.isPresent()) {
            return opt_db_prof.get();
        } else {
            throw new Exception("Professor not found.");
        }
    }

    // * Delete a Professor Profile
    @Transactional
    public boolean deleteProfessor(String professor_id) throws Exception {
        Optional<Professor> opt_db_professor = professor_repository.findById(professor_id);
        if (opt_db_professor.isPresent()) {
            this.professor_repository.delete(opt_db_professor.get());
            return !this.professor_repository.existsById(professor_id);
        } else {
            throw new Exception("Professor not found.");
        }
    }
}
