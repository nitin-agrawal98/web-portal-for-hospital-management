/**
 * Rest controller for patient
 * All api are allowed to be accessed by only authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    PatientService service;

    @Autowired
    BCryptPasswordEncoder encoder;

    //    Get all patients
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Patient> getPatients() {
        return service.findAll();
    }

    //    Get all patients having
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("status/{status}")
    public List<Patient> getPatientsByStatus(@PathVariable String status) {
        return service.findAllByStatus(status);
    }

    //    Get current logged in user as patient
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @GetMapping("name")
    public Patient getPatientByLoginName() {
        return service.findByLoginName();
    }

    //    Register new patient with status IN_PROGRESS initially
    @PostMapping("register")
    public ResponseEntity<?> addPatientRequest(@RequestBody Patient patient) {

        String pwd = patient.getPasscode();
        pwd = encoder.encode(pwd);
        patient.setPasscode(pwd);

        return service.save(patient);
    }

    //    Approve patient by receptionist i.e. change status to APPROVED
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("approve")
    public ResponseEntity<?> approvePatient(@RequestBody long id) {
        return service.approvePatient(id);
    }

    //    Approve patient by receptionist i.e. change status to CANCELLED
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("cancel")
    public ResponseEntity<?> cancelPatient(@RequestBody long id) {
        return service.cancelPatient(id);
    }

    //    Update patient profile
    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody Patient patient) {
        return service.updateProfile(patient);
    }

}
