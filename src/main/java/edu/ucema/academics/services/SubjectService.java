package edu.ucema.academics.services;

import edu.ucema.academics.models.Subject;
import edu.ucema.academics.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    // Injectable Repositories
    @Autowired
    private SubjectRepository subject_repositories;

    // Constructor
    public void SubjectRepository() {
    }

    // ! Business Logic
    // * Create Subject
    public Subject createSubject(Subject subject_instance) {
        return subject_repositories.save(new Subject(subject_instance));
    }

    // * Delete Subject
    public boolean deleteSubject(String subject_id) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_id);
        if (opt_db_subject.isPresent()) {
            subject_repositories.delete(opt_db_subject.get());
            return !subject_repositories.existsById(subject_id);
        } else {
            throw new Exception("Could not find the selected Subject. Please try again later.");
        }
    }

    // * Update Subject
    public Subject modifySubject(Subject subject_instance) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_instance.getIdentifier());
        if (opt_db_subject.isPresent()) {
            Subject db_subject = opt_db_subject.get();
            db_subject.setName(subject_instance.getName());

            return subject_repositories.save(db_subject);
        } else {
            throw new Exception("Could not find the selected Subject. Please try again later.");
        }
    }

    // * Get a Subject
    public Subject getSubjectById(String subject_id) throws Exception {
        Optional<Subject> opt_db_subject = subject_repositories.findById(subject_id);
        if (opt_db_subject.isPresent()) {
            return opt_db_subject.get();
        } else {
            throw new Exception("Could not find the selected Subject. Please try again later.");
        }
    }
}
