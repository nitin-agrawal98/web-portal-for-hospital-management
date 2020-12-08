/**
 * Patient Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.model.Status;
import com.hospital.management.hospitalmanagement.model.User;
import com.hospital.management.hospitalmanagement.repository.PatientRepository;
import com.hospital.management.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    //    Return all patients
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    //    Check whether patient with specific id is present or not
    public boolean notExistsById(long id) {
        return !patientRepository.existsById(id);
    }

    //    Return patient with specific id
    public Patient findById(long id) {
        return patientRepository.findById(id).get();
    }

    //    Return current logged in user
    public Patient findByLoginName() {
        String loginName = getLoginName();

        return patientRepository.findByLoginName(loginName);
    }

    //    Save a new patient
    public ResponseEntity<?> save(Patient patient) {
//        Check whether username is already present in database
        boolean isPresent = userRepository.existsByUsername(patient.getLoginName());
        if (isPresent) return ResponseEntity.badRequest().body("Username already exists");

        patientRepository.save(patient);

        return ResponseEntity.ok().body("Registration request sent successfully");
    }

    //    Convert from string to Status enumeration
    private Status stringToStatus(String statusAsString) {
        switch (statusAsString) {
            case "approved":
                return Status.APPROVED;
            case "cancelled":
                return Status.CANCELLED;
            default:
                return Status.IN_PROGRESS;
        }
    }

    //    Return all patients with given status
    public List<Patient> findAllByStatus(String status) {
        return patientRepository.findAllByStatus(stringToStatus(status));
    }

    //    Approve patient with specific id
    public ResponseEntity<?> approvePatient(long id) {
//        Check whether patient with specific id is present in the database
        if (notExistsById(id)) return ResponseEntity.badRequest().body("Patient not present in database");

        Patient patient;
        patient = findById(id);

//        Check whether patient has already been approved
        if (userRepository.existsByUsername(patient.getLoginName()))
            return ResponseEntity.badRequest().body("Patient is already approved");

//        Change patient status from IN_PROGRESS TO APPROVED
        patient.setStatus(Status.APPROVED);

        patientRepository.save(patient);
        userRepository.save(new User(patient.getLoginName(), patient.getPasscode(), "PATIENT"));

        return ResponseEntity.ok().body("Patient successfully approved");
    }

    public ResponseEntity<?> cancelPatient(long id) {
//        Check whether patient with specific id is present in the database
        if (notExistsById(id)) return ResponseEntity.badRequest().body("Patient not present in database");

        Patient patient;
        patient = findById(id);

//        Check whether patient has already been approved
        if (userRepository.existsByUsername(patient.getLoginName()))
            return ResponseEntity.badRequest().body("Patient is already approved");

//        Change patient status from IN_PROGRESS TO CANCELLED
        patient.setStatus(Status.CANCELLED);

        patientRepository.save(patient);

        return ResponseEntity.ok().body("Patient successfully cancelled");
    }

    //    Return qualifications and specialization of current logged in doctor
    public ResponseEntity<?> updateProfile(Patient newPatient) {
        String loginName = getLoginName();

        if (!patientRepository.existsByLoginName(loginName)) return ResponseEntity.badRequest().body("Invalid request");

        Patient patient = patientRepository.findByLoginName(loginName);

        newPatient.setId(patient.getId());
        newPatient.setLoginName(loginName);
        newPatient.setPasscode(patient.getPasscode());

        patientRepository.save(newPatient);

        return ResponseEntity.ok().body("Successfully updated doctor");
    }

    //    Login name of current logged in user
    private String getLoginName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }
}
