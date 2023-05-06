package edu.ucema.academics.repositories;

import edu.ucema.academics.models.users.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubjectRepository extends CrudRepository<Student, String> {
}
