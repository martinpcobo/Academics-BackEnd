package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.ProfessorRepository;
import edu.ucema.academics.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    // ! Injected Dependencies
    @Autowired
    private ClassRepository class_repository;
    @Autowired
    private ProfessorRepository professor_repository;
    @Autowired
    private StudentRepository student_repository;

    // ! Constructors
    public ClassService() {
    }

    // ! Business Logic

    // * Create a Class
    public ResponseEntity<Class> createClass(Class class_instance) throws Exception {
        return ResponseEntity.status(200).body(class_repository.save(new Class(class_instance)));
    }

    // * Get Class by Id
    public ResponseEntity<?> getClassById(String class_id) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_class.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Class.");
        }
    }

    // * Delete a Class
    public ResponseEntity<?> deleteClass(String class_id) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            class_repository.delete(opt_db_class.get());

            if (class_repository.findById(class_id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected Class was deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected Class could not be deleted. Please try again later.");
            }
        } else {
            throw new Exception("Could not find the Class to be deleted.");
        }
    }

    // * Update the details of a Class
    @Transactional
    public ResponseEntity<?> modifyClassDetails(Class class_instance) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_instance.getIdentifier());
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();

            db_class.setName(class_instance.getName());
            db_class.setDescription(class_instance.getDescription());
            db_class.setEndDate(class_instance.getEndDate());
            db_class.setStartDate(class_instance.getStartDate());
            db_class.setSubject(class_instance.getSubject());

            return ResponseEntity.status(HttpStatus.OK).body(class_repository.save(db_class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Class could not be found. Please try again later.");
        }
    }

    // * Modify the Professors of a Class
    @Transactional
    public ResponseEntity<?> setClassProfessors(String class_id, List<String> professors_ids) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();
            List<Professor> professors_list = new ArrayList<Professor>();

            professors_ids.forEach((prof_id) -> {
                Optional<Professor> opt_db_prof = professor_repository.findById(prof_id);
                if (opt_db_prof.isPresent()) {
                    Professor db_prof = opt_db_prof.get();
                    if (!db_class.getProfessors().contains(db_prof)) {
                        db_prof.getCourses().add(db_class);
                        professor_repository.save(db_prof);
                    }
                    professors_list.add(db_prof);
                }
            });

            db_class.getProfessors().forEach((prof_instance) -> {
                if (!professors_list.contains(prof_instance)) {
                    prof_instance.getCourses().remove(db_class);
                    professor_repository.save(prof_instance);
                }
            });

            db_class.setProfessors(professors_list);

            return ResponseEntity.status(HttpStatus.OK).body(class_repository.save(db_class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Class could not be found. Please try again later.");
        }
    }

    // * Modify the Students of a Class
    @Transactional
    public ResponseEntity<?> setClassStudents(String class_id, List<String> student_ids) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();
            List<Student> student_list = new ArrayList<Student>();

            student_ids.forEach((student_id) -> {
                Optional<Student> opt_db_student = student_repository.findById(student_id);
                if (opt_db_student.isPresent()) {
                    Student db_student = opt_db_student.get();
                    if (!db_class.getStudents().contains(db_student)) {
                        db_student.getCourses().add(db_class);
                        student_repository.save(db_student);
                    }
                    student_list.add(db_student);
                }
            });

            db_class.getStudents().forEach((student_instance) -> {
                if (!student_list.contains(student_instance)) {
                    student_instance.getCourses().remove(db_class);
                    student_repository.save(student_instance);
                }
            });

            db_class.setStudents(student_list);

            return ResponseEntity.status(HttpStatus.OK).body(class_repository.save(db_class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected Class could not be found. Please try again later.");
        }
    }
}
