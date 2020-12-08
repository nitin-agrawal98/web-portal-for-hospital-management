/**
 * Rest controller for appointments
 * All api are allowed to be accessed by only authenticated users having specific roles
 * Receptionist role is referred as ADMIN in all places
 */

package com.hospital.management.hospitalmanagement.controller;

import com.hospital.management.hospitalmanagement.model.Appointment;
import com.hospital.management.hospitalmanagement.model.AppointmentPage;
import com.hospital.management.hospitalmanagement.model.AppointmentSearchCriteria;
import com.hospital.management.hospitalmanagement.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired
    AppointmentService service;

    //    Get appointments with filters
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingAppointmentsWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getUpcomingAppointmentsWithFilters(page, criteria);
    }

    //    Get all appointments history
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/history")
    public ResponseEntity<?> getAppointmentsHistoryWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getHistoryAppointmentsWithFilters(page, criteria);
    }

    //    Get appointment with a specific id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("id/{id}")
    public Appointment getAppointment(@PathVariable long id) {
        return service.findById(id);
    }

    //    Get appointment with specific id having current logged in user as patient
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("patient/appointment/{id}")
    public ResponseEntity<?> getAppointmentByPatientAndId(@PathVariable long id) {
        return service.findByIdAndPatient_LoginName(id);
    }

    //    Get appointment with specific id having current logged in user as doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("doctor/appointment/{id}")
    public ResponseEntity<?> getAppointmentByDoctorAndId(@PathVariable long id) {
        return service.findByIdAndDoctor_LoginName(id);
    }

    //    Get all upcoming appointments having current logged in user as patient
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("patient/upcoming")
    public ResponseEntity<?> getUpcomingAppointmentsByPatient(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getUpcomingAppointmentsWithFilters(page, criteria);
    }

    //    Get all appointments history having current logged in user as patient
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("patient/history")
    public ResponseEntity<?> getAppointmentsHistoryByPatient(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getHistoryAppointmentsWithFilters(page, criteria);
    }

    //    Get all appointments having current logged in user as doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("doctor/upcoming")
    public ResponseEntity<?> getUpcomingAppointmentsByDoctor(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getUpcomingAppointmentsWithFilters(page, criteria);
    }

    //    Get all appointments having current logged in user as doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("doctor/history")
    public ResponseEntity<?> getAppointmentsHistoryByDoctor(AppointmentPage page, AppointmentSearchCriteria criteria) {
        return service.getHistoryAppointmentsWithFilters(page, criteria);
    }

    //    Get all appointments having current logged in user as doctor and given user as patient
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("doctor/patient/{id}")
    public List<Appointment> getAppointmentsByPatientAndDoctor(@PathVariable long id) {
        return service.findAllByPatient_IdAndDoctor_LoginName(id);
    }

    //    Get feedback of appointment with specific id having current logged in user as doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("feedback/appointment/{id}")
    public String viewFeedback(@PathVariable long id) {
        return service.getFeedback(id);
    }

    //    Book next appointment by doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("book/old/{id}")
    public ResponseEntity<?> bookNextAppointment(@RequestBody Map<String, String> map, @PathVariable long id) {
        String date = map.get("date");
        String time = map.get("time");

        return service.bookNext(date, time, id);
    }

    //    Add prescription for a specific appointment by doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("add-prescription/id/{id}")
    public ResponseEntity<?> addPrescription(@RequestBody String prescription, @PathVariable long id) {
        return service.addPrescription(prescription, id);
    }

    //    Get patient details by id
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @GetMapping("/patient-details/{id}")
    public ResponseEntity<?> getPatientDetails(@PathVariable long id) {
        return service.getPatientByAppointmentId(id);
    }

    //    Get patient details by id
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/doctor-details/{id}")
    public ResponseEntity<?> getDoctorDetails(@PathVariable long id) {
        return service.getDoctorByAppointmentId(id);
    }
}
