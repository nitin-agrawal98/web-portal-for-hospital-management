/**
 * Rest controller for doctors
 * All api are allowed to be accessed by only authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.model.Doctor;
import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    DoctorService service;

    @Autowired
    BCryptPasswordEncoder encoder;

    //    Get all doctors
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Doctor> getDoctors() {
        return service.findAll();
    }

    //    Get doctor profile
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("name")
    public Doctor getProfile() {
        return service.findByLoginName();
    }

    //    Update doctor profile
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody Doctor doctor) {
        return service.updateProfile(doctor);
    }

    //    Register a new doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("register")
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {

        String pwd = doctor.getPasscode();
        pwd = encoder.encode(pwd);
        doctor.setPasscode(pwd);

        return service.save(doctor);
    }

}
