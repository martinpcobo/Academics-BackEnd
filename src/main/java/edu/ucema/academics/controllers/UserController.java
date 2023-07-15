package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.PasswordChangeDTO;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.services.UserService;
import io.jsonwebtoken.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    @Autowired
    private UserService user_service;

    public UserController() {
    }

    // Get all Users
    @GetMapping(path = "/")
    public ResponseEntity<?> getAllUsers() {
        try {
            return user_service.getAllUsers();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // User Exists
    @GetMapping(path = "/{username}/exists")
    public ResponseEntity<?> userExistsByUsername(@PathVariable String username) {
        try {
            return user_service.userExists(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Get User Id by Verified Email
    @GetMapping(path = "/{email}/id")
    public ResponseEntity<?> getUserIdByVerifiedEmail(@PathVariable String email) {
        try {
            return user_service.getUserIDByVerifiedEmail(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Get User Information
    @GetMapping(path = "/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable String user_id) {
        try {
            return user_service.getUserInformation(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
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

    // [USER] Modify User Name
    @PutMapping(path = "/{user_id}")
    public ResponseEntity<?> modifyUserSecure(@PathVariable String user_id, @RequestBody User user) {
        try {
            return user_service.modifyUserSecure(user_id, user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // [ADMIN] Modify User Name
    @PutMapping(path = "/{user_id}/admin")
    public ResponseEntity<?> modifyUser(@PathVariable String user_id, @RequestBody User user) {
        try {
            return user_service.modifyUser(user_id, user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Verify User Email
    @PostMapping(path = "/{user_id}/email/verify")
    public ResponseEntity<?> verifyUserEmail(@PathVariable String user_id, @RequestParam(required = true) String email_verification_code) {
        try {
            return user_service.verifyUserEmail(user_id, email_verification_code);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // Change User Credential
    @PutMapping(path = "/{user_id}/password")
    public ResponseEntity<?> changeUserPassword(@PathVariable String user_id, @RequestBody PasswordChangeDTO password_change) {
        try {
            return user_service.changePassword(user_id, password_change);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}