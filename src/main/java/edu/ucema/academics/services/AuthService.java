package edu.ucema.academics.services;

import edu.ucema.academics.authentication.jwt.JwtUtilities;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    // ! Injected Dependencies
    @Autowired
    private JwtUtilities jwt_utilities;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private PasswordEncoder password_encoder;

    // ! Methods
    // * Authenticate User
    public String authenticate(String email, String password) {
        Optional<User> user = this.user_repository.findByVerifiedEmail(email);
        if (user.isEmpty()) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        User db_user = user.get();

        if (db_user.getPasswordInstance() == null || !password_encoder.matches(password, db_user.getPasswordInstance().getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        return jwt_utilities.generateToken(db_user.getVerifiedEmail(), db_user.getIdentifier());
    }

    // * Get Authenticated User
    public Optional<User> getUserByUsername(String username) {
        return this.user_repository.findByVerifiedEmail(username);
    }
}
