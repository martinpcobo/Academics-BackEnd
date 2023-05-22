package edu.ucema.academics.repositories;

import com.yubico.webauthn.data.ByteArray;
import edu.ucema.academics.models.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByVerifiedEmail(String email);

    Optional<User> findByHandle(ByteArray handle);
}
