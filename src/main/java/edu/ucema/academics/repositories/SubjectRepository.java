package edu.ucema.academics.repositories;

import edu.ucema.academics.models.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String> {
}
