package edu.ucema.academics.repositories;

import edu.ucema.academics.models.auth.Credential;
import edu.ucema.academics.models.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends CrudRepository<Credential, String> {
    Credential getCredentialByUser(User user);
}
