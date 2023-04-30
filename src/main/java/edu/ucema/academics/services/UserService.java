package edu.ucema.academics.services;

import edu.ucema.academics.models.users.Email;
import edu.ucema.academics.models.users.Password;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.EmailRepository;
import edu.ucema.academics.repositories.PasswordRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    // Attributes
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private EmailRepository email_repository;
    @Autowired
    private PasswordRepository password_repository;

    // Constructors
    public UserService() {}

    // Methods (Business Logic)
    public User create_user(User user) {
        Email user_email = user.getEmail();
        user_email.setVerifiedStatus(false);
        user_email.setVerifiedEmail(null);
        email_repository.save(user_email);

        Password user_password = user.getPassword();
        password_repository.save(user_password);

        return user_repository.save(user);
    }
    public Boolean remove_user(User user) {
        user_repository.delete(user);
        return user_repository.findById(user.getIdentifier()).isEmpty();
    }
    public User modify_user_name(User user) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user.getIdentifier());
        if(opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();
            db_user.setLastName(user.getLastName());
            db_user.setFirstName(user.getFirstName());
            return user_repository.save(db_user);
        } else {
            throw new Exception("User not found.");
        }
    }
    public User set_user_email(Long user_id, String unverified_email) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if(opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            Email user_email = db_user.getEmail();
            user_email.setUnverifiedEmail(unverified_email);
            user_email.setVerifiedStatus(false);
            db_user.setEmail(this.email_repository.save(new Email(user_email)));

            return user_repository.save(db_user);
        } else {
            throw new Exception("User not found.");
        }
    }
    public User verify_user_email (Long user_id, Long email_verification_code) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if(opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            Email user_email = db_user.getEmail();
            if(user_email.getIdentifier().equals(email_verification_code)) {
                user_email.setVerifiedEmail(user_email.getUnverifiedEmail());
                user_email.setUnverifiedEmail(null);
                user_email.setVerifiedStatus(true);
                db_user.setEmail(new Email(user_email));

                return user_repository.save(db_user);
            } else {
                throw new Exception("Email verification code not valid.");
            }
        } else {
            throw new Exception("User not found.");
        }
    }
    public Boolean change_password(Long user_id, String old_password, String new_password) throws Exception {
        Optional<User> opt_db_user = user_repository.findById(user_id);
        if(opt_db_user.isPresent()) {
            User db_user = opt_db_user.get();

            if(db_user.getPassword().getPassword().equals(new_password)) {
                db_user.getPassword().setPassword(new_password);
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
