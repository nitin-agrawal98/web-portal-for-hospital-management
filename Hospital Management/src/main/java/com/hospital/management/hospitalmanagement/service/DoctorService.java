/**
 * Doctor Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.Doctor;
import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.model.User;
import com.hospital.management.hospitalmanagement.repository.DoctorRepository;
import com.hospital.management.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserRepository userRepository;

    //    Return all doctors
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    //    Save new doctor
    public ResponseEntity<?> save(Doctor doctor) {
//        Check whether username is already present
        boolean isPresent = userRepository.existsByUsername(doctor.getLoginName());
        if (isPresent) return ResponseEntity.badRequest().body("Username already exists");

//        Save doctor in doctors table
        doctorRepository.save(doctor);

//        Save username and password in user table with role doctor
        userRepository.save(new User(doctor.getLoginName(), doctor.getPasscode(), "DOCTOR"));

        return ResponseEntity.ok().body("Registration request sent successfully");
    }

    //    Return current logged in user
    public Doctor findByLoginName() {
        String loginName = getLoginName();

        return doctorRepository.findByLoginName(loginName);
    }

    //    Return qualifications and specialization of current logged in doctor
    public ResponseEntity<?> updateProfile(Doctor newDoctor) {
        String loginName = getLoginName();

        if (!doctorRepository.existsByLoginName(loginName)) return ResponseEntity.badRequest().body("Invalid request");

        Doctor doctor = doctorRepository.findByLoginName(loginName);

        newDoctor.setId(doctor.getId());
        newDoctor.setLoginName(loginName);
        newDoctor.setPasscode(doctor.getPasscode());

        doctorRepository.save(newDoctor);

        return ResponseEntity.ok().body("Successfully updated doctor");
    }

    //    Login name of current logged in user
    private String getLoginName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

}
