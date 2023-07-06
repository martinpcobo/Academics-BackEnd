package edu.ucema.academics.services;

import com.yubico.webauthn.data.ByteArray;
import edu.ucema.academics.models.auth.Credential;
import edu.ucema.academics.models.dtos.PasswordChangeDTO;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.CredentialRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    // ! Injected Dependencies
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private CredentialRepository credential_repository;
    @Autowired
    private PasswordEncoder password_encoder;

    // ! Methods
    // * Get Secure User
    private User _getSecureUser(User user) {
        user.setCredential(null);
        return user;
    }

    // ! Business Logic

    // * Get all users
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(user_repository.findAll());
    }

    // * User Exists
    public ResponseEntity<Boolean> userExists(String username) {
        return ResponseEntity.status(HttpStatus.OK).body(user_repository.existsUserByVerifiedEmail(username));
    }

    // * Get User by Verified Email
    public ResponseEntity<String> getUserIDByVerifiedEmail(String email) {
        Optional<User> opt_db_user = user_repository.findByVerifiedEmail(email);
        if (opt_db_user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opt_db_user.get().getIdentifier());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // * Get User Information
    public ResponseEntity<User> getUserInformation(String user_id) {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(opt_db_user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // * Create a User
    public ResponseEntity<User> createUser(User user) {
        User new_user = new User(user);
        new_user.setCredential(null);
        new_user.setHandle(new ByteArray(new byte[64]));

        User db_user = user_repository.save(new_user);

        // Set the User's Credential
        Credential new_credential = new Credential();
        new_credential.setIdentifier(db_user.getIdentifier());
        new_credential.setPassword(password_encoder.encode(user.getCredential().getPassword()));
        new_credential.setUser(db_user);

        db_user.setCredential(credential_repository.save(new_credential));
        return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
    }

    // * Delete a User
    public ResponseEntity<?> deleteUser(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            user_repository.delete(opt_db_user.get());
            if (user_repository.findById(user_id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("The selected User was deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The selected User could not be deleted. Please try again later");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }

    // * [USER] Update User Information by Id
    public ResponseEntity<?> modifyUserSecure(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if(!db_user.getFirstName().equals(user.getFirstName())) {
                db_user.setFirstName(user.getFirstName());
            }

            if(!db_user.getLastName().equals(user.getLastName())) {
                db_user.setLastName(user.getLastName());
            }

            if(!db_user.getUnverifiedEmail().equals(user.getUnverifiedEmail())) {
                db_user.setUnverifiedEmail(user.getUnverifiedEmail());
                db_user.setEmailVerificationCode(UUID.randomUUID().toString());
            }

            return ResponseEntity.status(HttpStatus.OK).body("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }

    // * [ADMIN] Update User Information by Id
    public ResponseEntity<?> modifyUser(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if(!db_user.getFirstName().equals(user.getFirstName())) {
                db_user.setFirstName(user.getFirstName());
            }

            if(!db_user.getLastName().equals(user.getLastName())) {
                db_user.setLastName(user.getLastName());
            }

            if((db_user.getUnverifiedEmail() == null && user.getUnverifiedEmail() != null) || (db_user.getUnverifiedEmail() != null && user.getUnverifiedEmail() != null && !db_user.getUnverifiedEmail().equals(user.getUnverifiedEmail()))) {
                db_user.setUnverifiedEmail(user.getUnverifiedEmail());
                db_user.setEmailVerificationCode(UUID.randomUUID().toString());
            }

            if(!db_user.getVerifiedEmail().equals(user.getVerifiedEmail())) {
                db_user.setVerifiedEmail(user.getVerifiedEmail());
            }

            user_repository.save(db_user);

            return ResponseEntity.status(HttpStatus.OK).body("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }

    // * Verify User Email by Id
    public ResponseEntity<?> verifyUserEmail(String user_id, String email_verification_code) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if (db_user.getEmailVerificationCode().equals(email_verification_code)) {
                db_user.setVerifiedEmail(db_user.getUnverifiedEmail());
                db_user.setUnverifiedEmail(null);
                db_user.setEmailVerificationCode(null);

                return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The provided email verification code is not correct. Please try again later.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }

    // * Change User Credential
    public ResponseEntity<?> changePassword(String user_id, PasswordChangeDTO password_change) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();


            if(password_encoder.matches(password_change.getOldPassword(), db_user.getCredential().getPassword())) {
                db_user.getCredential().setPassword(password_encoder.encode(password_change.getNewPassword()));
                return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The provided password was incorrect. Please try again later");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }
}
