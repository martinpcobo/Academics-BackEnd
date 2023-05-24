package edu.ucema.academics.services.auth;

import edu.ucema.academics.models.auth.Authenticator;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.AuthenticatorRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatorService {

    // ! Injectable Dependencies
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private AuthenticatorRepository authenticator_repository;

    // ! Business Logic
    // * Get Authenticators from Username
    public ResponseEntity<?> getAuthenticatorsFormUser(String user_id) {
        Optional<User> user = user_repository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected User, please try again later.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(authenticator_repository.findAllByCredential(user.get().getCredential()));
        }
    }

    // * Get Authenticator Count
    public ResponseEntity<?> getAuthenticatorCount(String user_id) {
        Optional<User> user = user_repository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected User, please try again later.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(authenticator_repository.countByCredential(user.get().getCredential()));
        }
    }

    // * Modify Authenticator Name
    public ResponseEntity<?> modifyAuthenticatorName(String user_id, String authenticator_id, String new_name) {
        Optional<Authenticator> opt_db_authenticator = authenticator_repository.findById(authenticator_id);
        if (opt_db_authenticator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Authenticator, please try again later.");
        }
        Authenticator db_authenticator = opt_db_authenticator.get();

        if (db_authenticator.getCredential().getUser().getIdentifier().equals(user_id)) {
            db_authenticator.setName(new_name);
            authenticator_repository.save(db_authenticator);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully modified the Authenticator Name.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The selected Authenticator does not belong to the selected User.");
        }
    }

    // * Delete an Authenticator
    public ResponseEntity<?> deleteAuthenticator(String user_id, String authenticator_id) {
        Optional<Authenticator> opt_db_authenticator = authenticator_repository.findById(authenticator_id);
        if (opt_db_authenticator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Authenticator, please try again later.");
        }
        Authenticator db_authenticator = opt_db_authenticator.get();

        if (db_authenticator.getCredential().getUser().getIdentifier().equals(user_id)) {
            authenticator_repository.delete(db_authenticator);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted the Authenticator.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The selected Authenticator does not belong to the selected User.");
        }
    }

    // * Get Authenticator By Id
    public ResponseEntity<?> getAuthenticatorById(String user_id, String authenticator_id) {
        Optional<Authenticator> opt_db_authenticator = authenticator_repository.findById(authenticator_id);
        if (opt_db_authenticator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected Authenticator, please try again later.");
        }
        Authenticator db_authenticator = opt_db_authenticator.get();

        if (db_authenticator.getCredential().getUser().getIdentifier().equals(user_id)) {
            return ResponseEntity.status(HttpStatus.OK).body(db_authenticator);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The selected Authenticator does not belong to the selected User.");
        }
    }
}
