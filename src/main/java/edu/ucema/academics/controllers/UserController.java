package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.PasswordChangeDTO;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class UserController {
    @Autowired
    private UserService user_service;

    public UserController() {
    }

    // Create a User
    @PostMapping(path = "/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return user_service.createUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Delete a User
    @DeleteMapping(path = "/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable String user_id) {
        try {
            return user_service.deleteUser(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Modify User Name
    @PutMapping(path = "/name")
    public ResponseEntity<?> modifyUserName(@RequestParam(required = true) String user_id, @RequestBody User user) {
        try {
            return user_service.modifyUserName(user_id, user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Modify User Email
    @PutMapping(path = "/email")
    public ResponseEntity<?> modifyUserEmail(@RequestParam(required = true) String user_id, @RequestBody User user) {
        try {
            return user_service.modifyUserEmail(user_id, user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    // Verify User Email
    @PostMapping(path = "/email/verify")
    public ResponseEntity<?> verifyUserEmail(@RequestParam(required = true) String user_id, @RequestParam(required = true) String email_verification_code) {
        try {
            return user_service.verifyUserEmail(user_id, email_verification_code);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Change User Credential
    @PutMapping(path = "/password")
    public ResponseEntity<?> changeUserPassword(@RequestParam String user_id, @RequestBody PasswordChangeDTO password_change) {
        try {
            return user_service.changePassword(user_id, password_change);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}