package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.AuthnUserDTO;
import edu.ucema.academics.models.dtos.LoginDTO;
import edu.ucema.academics.services.AuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {
    // ! Injectable Dependencies
    @Autowired
    private AuthenticatorService authentication_service;


    // ! Username-Password Endpoints
    // * Login using Username and Password
    @PostMapping(path = "/login")
    public String authenticate(@RequestBody LoginDTO loginDTO) {
        return this.authentication_service.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
    }

    // ! WebAuthn Endpoints
    // * Start Authn Authentication Registration
    @PostMapping(path = "/webauthn/register/start")
    public ResponseEntity<?> startAuthRegistration(@PathVariable String username) {
        try {
            return authentication_service.startAuthnRegistration(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * End Authn Authentication Registration
    @PostMapping(path = "/webauthn/register/end")
    public ResponseEntity<?> endAuthRegistration(@RequestBody AuthnUserDTO authnUserDTO) {
        try {
            return authentication_service.endAuthnRegistration(authnUserDTO.getPublicKey(), authnUserDTO.getUsername(), authnUserDTO.getAuthenticatorName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * Start Authn Authentication Login
    @PostMapping(path = "/webauthn/login/start")
    public ResponseEntity<?> startAuthLogin(@RequestBody AuthnUserDTO authnUserDTO) {
        try {
            return authentication_service.startAuthnLogin(authnUserDTO.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * End Authn Authentication Login
    @PostMapping(path = "/webauthn/login/end")
    public ResponseEntity<?> endAuthLogin(@RequestBody AuthnUserDTO authnUserDTO) {
        try {
            return authentication_service.endAuthnLogin(authnUserDTO.getPublicKey(), authnUserDTO.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}