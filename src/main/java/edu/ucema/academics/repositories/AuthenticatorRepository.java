package edu.ucema.academics.repositories;

import com.yubico.webauthn.data.ByteArray;
import edu.ucema.academics.models.auth.Authenticator;
import edu.ucema.academics.models.auth.Credential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticatorRepository extends CrudRepository<Authenticator, String> {
    Optional<Authenticator> findByCredentialId(ByteArray credentialId);

    List<Authenticator> findAllByCredential(Credential credential);

    List<Authenticator> findAllByCredentialId(ByteArray credentialId);
}