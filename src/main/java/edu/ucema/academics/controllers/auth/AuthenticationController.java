package edu.ucema.academics.controllers.auth;

import edu.ucema.academics.models.dtos.AuthDetailsDTO;
import edu.ucema.academics.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthenticationController {
    // ! Injectable Dependencies
    @Autowired
    private AuthenticationService authentication_service;


    // ! Username-Password Endpoints
    // * Login using Username and Password
    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthDetailsDTO authDetailsDTO) {
        return this.authentication_service.authenticate(authDetailsDTO.getUsername(), authDetailsDTO.getPassword());
    }

    // ! WebAuthn Endpoints
    // * Start Authn Authentication Registration
    @PostMapping(path = "/webauthn/register/start")
    public ResponseEntity<?> startAuthRegistration(@RequestBody AuthDetailsDTO authDetailsDTO) {
        try {
            return authentication_service.startAuthnRegistration(authDetailsDTO.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * End Authn Authentication Registration
    @PostMapping(path = "/webauthn/register/end")
    public ResponseEntity<?> endAuthRegistration(@RequestBody AuthDetailsDTO authDetailsDTO) {
        try {
            return authentication_service.endAuthnRegistration(authDetailsDTO.getPublicKey(), authDetailsDTO.getUsername(), authDetailsDTO.getAuthenticatorName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Start Authn Authentication Login
    @PostMapping(path = "/webauthn/login/start")
    public ResponseEntity<?> startAuthLogin(@RequestBody AuthDetailsDTO authDetailsDTO) {
        try {
            return authentication_service.startAuthnLogin(authDetailsDTO.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * End Authn Authentication Login
    @PostMapping(path = "/webauthn/login/end")
    public ResponseEntity<?> endAuthLogin(@RequestBody AuthDetailsDTO authDetailsDTO) {
        try {
            return authentication_service.endAuthnLogin(authDetailsDTO.getPublicKey(), authDetailsDTO.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}