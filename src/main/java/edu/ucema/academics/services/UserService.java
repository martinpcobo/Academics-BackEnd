package edu.ucema.academics.services;

import edu.ucema.academics.models.dtos.PasswordChangeDTO;
import edu.ucema.academics.models.users.Password;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.PasswordRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    // ! Injected Dependencies
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private PasswordRepository password_repository;

    // ! Constructors
    public UserService() {
    }

    // ! Methods
    // * Get Secure User
    private User _getSecureUser(User user) {
        user.setPassword(null);
        return user;
    }

    // ! Business Logic

    // * Create a User
    public ResponseEntity<User> createUser(User user) {
        User new_user = new User(user);
        new_user.setPassword(null);

        User db_user = user_repository.save(new_user);

        // Set the User's Password
        Password new_password = new Password(user.getPassword());
        db_user.setPassword(password_repository.save(new_password));

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

    // * Update User Details by Id
    public ResponseEntity<?> modifyUserName(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();
            db_user.setLastName(user.getLastName());
            db_user.setFirstName(user.getFirstName());

            return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }

    // * Update User Email by Id
    public ResponseEntity<?> modifyUserEmail(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();
            db_user.setUnverifiedEmail(user.getUnverifiedEmail());
            db_user.setEmailVerificationCode(UUID.randomUUID().toString());

            return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
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

    // * Change User Password
    public ResponseEntity<?> changePassword(String user_id, PasswordChangeDTO password_change) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if (db_user.getPassword().getPassword().equals(password_change.getOldPassword())) {
                db_user.getPassword().setPassword(password_change.getNewPassword());
                return ResponseEntity.status(HttpStatus.OK).body(this._getSecureUser(user_repository.save(db_user)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The provided password was incorrect. Please try again later");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The selected User could not be found. Please try again later");
        }
    }
}