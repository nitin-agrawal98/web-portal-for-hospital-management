/**
 * Authentication Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.jwt.JwtUtils;
import com.hospital.management.hospitalmanagement.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager manager;

    public ResponseEntity<?> login(Login login) {
//        Get authentication object with given username and password
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

//        Check if role is correct
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(login.getRole()))) {
            return ResponseEntity.status(403).body("Access Forbidden");
        }

//        Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Generate jwt token
        String token = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok().body(token);
    }
}
