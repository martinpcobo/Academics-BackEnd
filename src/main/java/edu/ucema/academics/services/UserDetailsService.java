package edu.ucema.academics.services;

import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository user_repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return user_repository.findByVerifiedEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
    }
}
