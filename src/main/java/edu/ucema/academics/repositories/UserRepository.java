package edu.ucema.academics.repositories;

import edu.ucema.academics.models.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
