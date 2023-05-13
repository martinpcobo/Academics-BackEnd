package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.models.courses.Course;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.ProfessorRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    // Attributes
    @Autowired
    private ProfessorRepository professor_repository;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private ClassRepository class_repository;

    // Constructors
    public ProfessorService() {
    }

    // Business Logic
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

    // TODO: Methods to be Tested
    public void addClass(String professor_id, String class_id) throws Exception {
        Optional<Professor> opt_db_user = professor_repository.findById(professor_id);
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_user.isPresent() && opt_db_class.isPresent()) {
            Professor db_professor = opt_db_user.get();
            Class db_class = opt_db_class.get();

            List<Course> professor_courses = db_professor.getCourses();
            professor_courses.add(db_class);
            db_professor.setCourses(professor_courses);
            this.professor_repository.save(db_professor);
        } else {
            throw new Exception("Professor or Class not found.");
        }
    }

    public void removeClass(String professor_id, String class_id) throws Exception {
        Optional<Professor> opt_db_professor = professor_repository.findById(professor_id);
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_professor.isPresent() && opt_db_class.isPresent()) {
            Professor db_professor = opt_db_professor.get();
            Class db_class = opt_db_class.get();

            // Remove the course from the student to course M2M.
            List<Course> professor_courses = db_professor.getCourses();
            professor_courses.remove(db_class);
            db_professor.setCourses(professor_courses);

            this.professor_repository.save(db_professor);
        } else {
            throw new Exception("Professor or Course not found.");
        }
    }
}
