package com.hospital.management.hospitalmanagement;

import com.hospital.management.hospitalmanagement.model.User;
import com.hospital.management.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HospitalManagementApplication {

    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }

    @PostConstruct
    public void addAdmin() {
        String username = "admin";
        String password = "admin";
        if (!repository.existsByUsername(username))
            repository.save(new User(username, encoder.encode(password), "ADMIN"));
    }
}
