/**
 * Appointment Request Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.Appointment;
import com.hospital.management.hospitalmanagement.model.AppointmentRequest;
import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.repository.AppointmentRepository;
import com.hospital.management.hospitalmanagement.repository.AppointmentRequestRepository;
import com.hospital.management.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentRequestService {

    @Autowired
    AppointmentRequestRepository requestRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    //    Return all requests
    public List<AppointmentRequest> findAll() {
        return requestRepository.findAll();
    }

    //    Return request with specific id
    public AppointmentRequest findById(long id) {
        return requestRepository.findById(id);
    }

    //    Save request in appointment requests table
    public String save(AppointmentRequest request) {
        String loginName = getLoginName();

//        Get current logged in user as patient and insert it into request object
        Patient patient = patientRepository.findByLoginName(loginName);
        request.setPatient(patient);

//        Save the updated request object into appointment requests table
        requestRepository.save(request);

        return "Successfully added request";
    }

    //    Return all appointments having current logged in user as patient
    public List<AppointmentRequest> findAllByPatient_LoginName() {
        String loginName = getLoginName();

        return requestRepository.findAllByPatient_LoginName(loginName);
    }

    //    Approval of a specific request by receptionist
    public ResponseEntity<?> approve(Appointment appointment, long id) {
//        Check whether request with id equal to given id is present in database
        if (!requestRepository.existsById(id))
            return ResponseEntity.badRequest().body("Requested with id =" + id + "not found in database");

//        Delete request from table
        AppointmentRequest request = requestRepository.deleteById(id);


//        Save current appointment in appointments table
        appointmentRepository.save(appointment);

        return ResponseEntity.ok().body("Successfully approved appointment");
    }

    //    Delete a specific request
    public String deleteById(long id) {
        requestRepository.deleteById(id);

        return "Successfully deleted";
    }

    //    Login Name of current logged in user
    private String getLoginName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

}
