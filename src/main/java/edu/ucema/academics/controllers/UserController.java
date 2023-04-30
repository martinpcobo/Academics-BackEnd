package edu.ucema.academics.controllers;

import edu.ucema.academics.models.users.User;
import edu.ucema.academics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(
        origins = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class UserController {
    @Autowired
    private UserService user_service;

    public UserController() {}

    @PostMapping(path = "/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(200).body(user_service.create_user(user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
}
