package edu.ucema.academics.services;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.Student;
import edu.ucema.academics.repositories.ClassRepository;
import edu.ucema.academics.repositories.ProfessorRepository;
import edu.ucema.academics.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    // Injectable Repositories
    @Autowired
    private ClassRepository class_repository;
    @Autowired
    private ProfessorRepository professor_repository;
    @Autowired
    private StudentRepository student_repository;

    // Constructors
    public ClassService() {
    }

    // * Business Logic

    // Create a Class
    public Class createClass(Class class_instance) {
        return class_repository.save(new Class(class_instance));
    }

    // Delete a Class
    public Boolean deleteClass(String class_id) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            class_repository.delete(opt_db_class.get());

            return class_repository.findById(class_id).isEmpty();
        } else {
            throw new Exception("Could not find the Class to be deleted.");
        }
    }

    // Modify the date of a Class
    public Class setClassDetails(Class class_instance) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_instance.getIdentifier());
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();

            db_class.setName(class_instance.getName());
            db_class.setDescription(class_instance.getDescription());
            db_class.setEndDate(class_instance.getEndDate());
            db_class.setStartDate(class_instance.getStartDate());

            return class_repository.save(db_class);
        } else {
            throw new Exception("Could not find the Class to be modified.");
        }
    }

    // Modify the Professors of a Class
    public Class setClassProfessors(Class class_instance, List<String> professors_ids) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_instance.getIdentifier());
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();
            db_class.setProfessors(new ArrayList<Professor>());

            professors_ids.forEach((prof_id) -> {
                Optional<Professor> opt_db_prof = professor_repository.findById(prof_id);
                if (opt_db_prof.isPresent()) {
                    Professor db_prof = opt_db_prof.get();

                    List<Professor> prof_list = db_class.getProfessors();
                    prof_list.add(db_prof);
                    db_class.setProfessors(prof_list);
                }
            });

            return class_repository.save(db_class);
        } else {
            throw new Exception("Could not find the Class to be modified.");
        }
    }

    // Modify the Students of a Class
    public Class setClassStudents(Class class_instance, List<String> students_ids) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_instance.getIdentifier());
        if (opt_db_class.isPresent()) {
            Class db_class = opt_db_class.get();
            db_class.setStudents(new ArrayList<Student>());

            students_ids.forEach((student_id) -> {
                Optional<Student> opt_db_student = student_repository.findById(student_id);
                if (opt_db_student.isPresent()) {
                    Student db_student = opt_db_student.get();

                    List<Student> student_list = db_class.getStudents();
                    student_list.add(db_student);
                    db_class.setStudents(student_list);
                }
            });

            return class_repository.save(db_class);
        } else {
            throw new Exception("Could not find the Class to be modified.");
        }
    }

    // Get Class by Id
    public Class getClassById(String class_id) throws Exception {
        Optional<Class> opt_db_class = class_repository.findById(class_id);
        if (opt_db_class.isPresent()) {
            return opt_db_class.get();
        } else {
            throw new Exception("Could not find the Class to be modified.");
        }
    }
}
