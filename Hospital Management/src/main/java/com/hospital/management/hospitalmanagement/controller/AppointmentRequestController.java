/**
 * Rest controller for appointment request
 * All api are allowed to be accessed only by authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.model.Appointment;
import com.hospital.management.hospitalmanagement.model.AppointmentRequest;
import com.hospital.management.hospitalmanagement.service.AppointmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("requests")
public class AppointmentRequestController {

    @Autowired
    AppointmentRequestService service;

    //    Get all requests
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AppointmentRequest> getRequests() {
        return service.findAll();
    }

    //    Get request with specific id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("id/{id}")
    public AppointmentRequest getRequest(@PathVariable long id) {
        return service.findById(id);
    }

    //    Book appointment request
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("book")
    public String bookRequest(@RequestBody AppointmentRequest request) {
        return service.save(request);
    }

    //    Get all requests having current logged in user as patient
    @PreAuthorize("hasAnyRole('PATIENT')")
    @GetMapping("patient")
    public List<AppointmentRequest> getRequestsByPatient() {
        return service.findAllByPatient_LoginName();
    }

    //    Approve a specific request by receptionist
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveRequest(@RequestBody Appointment appointment, @PathVariable long id) {
        return service.approve(appointment, id);
    }

    //    Delete a specific request
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        return service.deleteById(id);
    }

}
