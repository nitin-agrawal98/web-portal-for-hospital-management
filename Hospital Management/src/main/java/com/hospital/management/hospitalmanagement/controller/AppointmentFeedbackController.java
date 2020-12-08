/**
 * Rest controller for appointment feedback
 * All api are allowed to be accessed only by authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.service.AppointmentFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("feedback")
public class AppointmentFeedbackController {

    @Autowired
    AppointmentFeedbackService service;

    //    Add feedback for a specific appointment in appointment feedback table
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/add/appointment/{id}")
    public ResponseEntity<?> addFeedback(@RequestBody String feedback, @PathVariable long id) {
        return service.giveFeedback(feedback, id);
    }

    //    Get feedback of a specific appointment
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("view/appointment/{id}")
    public String viewFeedback(@PathVariable long id) {
        return service.viewFeedback(id);
    }

    //    Delete row with specific appointment id and forward the feedback to appointment table
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("forward/appointment/{id}")
    public String forwardFeedback(@PathVariable long id) {
        return service.forwardFeedback(id);
    }
}
