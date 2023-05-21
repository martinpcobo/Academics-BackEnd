package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.AuthnFinishRegisterDTO;
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


    // ! Endpoints
    @PostMapping(path = "/login")
    public String authenticate(@RequestBody LoginDTO loginDTO) {
        return this.authentication_service.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
    }

    // * Start Authn Authentication Registration
    @PostMapping(path = "/webauthn/start")
    public ResponseEntity<?> startAuthRegistration(@PathVariable String username) {
        try {
            return authentication_service.startAuthnRegistration(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // * End Authn Authentication Registration
    @PostMapping(path = "/webauthn/end")
    public ResponseEntity<?> endAuthRegistration(@RequestBody AuthnFinishRegisterDTO authnFinishRegisterDTO) {
        try {
            return authentication_service.endAuthnRegistration(authnFinishRegisterDTO.getPublicKey(), authnFinishRegisterDTO.getUsername(), authnFinishRegisterDTO.getAuthenticatorName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}