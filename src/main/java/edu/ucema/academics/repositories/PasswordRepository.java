package edu.ucema.academics.repositories;

import edu.ucema.academics.models.users.Password;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends CrudRepository<Password, String> {
}
