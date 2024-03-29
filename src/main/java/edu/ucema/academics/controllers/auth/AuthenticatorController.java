package edu.ucema.academics.controllers.auth;

import edu.ucema.academics.models.auth.Authenticator;
import edu.ucema.academics.services.auth.AuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth/authenticator/")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthenticatorController {
    // ! Injectable Dependencies
    @Autowired
    private AuthenticatorService authenticator_service;

    // ! Endpoints
    // * Get User's Authenticators
    @GetMapping(path = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Authenticator>> getAuthenticators(@PathVariable String user_id) {
        try {
            return authenticator_service.getAuthenticatorsFormUser(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // * Get Authenticator Count
    @GetMapping(path = "/{user_email}/count")
    public ResponseEntity<?> getAuthenticatorByUsername(@PathVariable String user_email) {
        try {
            return authenticator_service.getAuthenticatorCount(user_email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Get Authenticator by ID
    @GetMapping(path = "/{user_id}/{authenticator_id}")
    public ResponseEntity<?> getAuthenticatorById(@PathVariable String user_id, @PathVariable String authenticator_id) {
        try {
            return authenticator_service.getAuthenticatorById(user_id, authenticator_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Modify the Name of an Authenticator
    @PutMapping(path = "/{user_id}/{authenticator_id}")
    public ResponseEntity<?> modifyAuthenticatorName(@PathVariable String user_id, @PathVariable String authenticator_id, @RequestBody String new_authenticator_name) {
        try {
            return authenticator_service.modifyAuthenticatorName(user_id, authenticator_id, new_authenticator_name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Remove an Authenticator from a User
    @DeleteMapping(path = "/{user_id}/{authenticator_id}")
    public ResponseEntity<?> deleteAuthenticator(@PathVariable String user_id, @PathVariable String authenticator_id) {
        try {
            return authenticator_service.deleteAuthenticator(user_id, authenticator_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
