package edu.ucema.academics.services;

import edu.ucema.academics.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    // Injectable Repositories
    @Autowired
    private SubjectRepository subject_repositories;

    // Constructor
    public void SubjectRepository() {}

    // Business Logic
}
