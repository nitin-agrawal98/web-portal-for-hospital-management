/**
 * Rest controller for authentication
 * All api are allowed to be accessed only by authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.model.Login;
import com.hospital.management.hospitalmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return service.login(login);
    }

}
