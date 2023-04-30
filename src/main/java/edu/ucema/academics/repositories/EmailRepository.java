package edu.ucema.academics.repositories;

import edu.ucema.academics.models.users.Email;
import edu.ucema.academics.models.users.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
}
