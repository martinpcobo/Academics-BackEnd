package edu.ucema.academics.services;

import edu.ucema.academics.authentication.jwt.JwtUtilities;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    // ! Injected Dependencies
    @Autowired
    private JwtUtilities jwt_utilities;
    @Autowired
    private UserRepository user_repository;

    // ! Methods
    // * Authenticate User
    public String authenticate(String email, String password) {
        Optional<User> user = this.user_repository.findByVerifiedEmail(email);
        if (user.isEmpty()) return null;

        User db_user = user.get();

        return jwt_utilities.generateToken(db_user.getVerifiedEmail(), db_user.getIdentifier());
    }
}
