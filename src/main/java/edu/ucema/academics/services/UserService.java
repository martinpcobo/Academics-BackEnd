package edu.ucema.academics.services;

import edu.ucema.academics.models.dtos.PasswordChangeDTO;
import edu.ucema.academics.models.users.Password;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.PasswordRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    // Attributes
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private PasswordRepository password_repository;

    // Constructors
    public UserService() {
    }

    // Business Logic
    private User _getSecureUser(User user) {
        user.setPassword(null);
        return user;
    }

    public User createUser(User user) {
        User new_user = new User(user);
        new_user.setPassword(null);

        User db_user = user_repository.save(new_user);

        // Set the User's Password
        Password new_password = new Password(user.getPassword());
        db_user.setPassword(password_repository.save(new_password));

        return this._getSecureUser(user_repository.save(db_user));
    }

    public Boolean deleteUser(String user_id) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            user_repository.delete(opt_db_user.get());
            return user_repository.findById(user_id).isEmpty();
        } else {
            throw new Exception("Could not find the User.");
        }
    }

    public User modifyUserName(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();
            db_user.setLastName(user.getLastName());
            db_user.setFirstName(user.getFirstName());

            return this._getSecureUser(user_repository.save(db_user));
        } else {
            throw new Exception("User not found.");
        }
    }

    public User modifyUserEmail(String user_id, User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();
            db_user.setUnverifiedEmail(user.getUnverifiedEmail());
            db_user.setEmailVerificationCode(UUID.randomUUID().toString());

            return this._getSecureUser(user_repository.save(db_user));
        } else {
            throw new Exception("User not found.");
        }
    }

    public User verifyUserEmail(String user_id, String email_verification_code) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if (db_user.getEmailVerificationCode().equals(email_verification_code)) {
                db_user.setVerifiedEmail(db_user.getUnverifiedEmail());
                db_user.setUnverifiedEmail(null);
                db_user.setEmailVerificationCode(null);

                return this._getSecureUser(user_repository.save(db_user));
            } else {
                throw new Exception("Email verification code not valid.");
            }
        } else {
            throw new Exception("User not found.");
        }
    }

    public Boolean changePassword(String user_id, PasswordChangeDTO password_change) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if (opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if (db_user.getPassword().getPassword().equals(password_change.getOldPassword())) {
                db_user.getPassword().setPassword(password_change.getNewPassword());
                user_repository.save(db_user);
                return true;
            } else {
                throw new Exception("Incorrect password");
            }
        } else {
            throw new Exception("User not found.");
        }
    }
}
