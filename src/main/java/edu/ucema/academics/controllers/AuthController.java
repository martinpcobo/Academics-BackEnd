package edu.ucema.academics.controllers;

import edu.ucema.academics.models.dtos.LoginDTO;
import edu.ucema.academics.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private AuthService authentication_service;

    @PostMapping(path = "/login")
    public String authenticate(@RequestBody LoginDTO loginDTO) {
        return this.authentication_service.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @PostMapping(path = "/logout")
    public String logout(@RequestBody LoginDTO loginDTO) {
        return this.authentication_service.logout(loginDTO.getUsername());
    }
}