/**
 * Appointment Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.*;
import com.hospital.management.hospitalmanagement.repository.AppointmentPageRepository;
import com.hospital.management.hospitalmanagement.repository.AppointmentRepository;
import com.hospital.management.hospitalmanagement.repository.DoctorRepository;
import com.hospital.management.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentPageRepository pageRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserRepository userRepository;

    //    Return all upcoming appointments (where prescription is null)
    public List<Appointment> getUpcomingAppointments() {
        return appointmentRepository.findAllByPrescriptionNull();
    }

    //    Return all appointments history (where prescription is not null
    public List<Appointment> getAppointmentsHistory() {
        return appointmentRepository.findAllByPrescriptionNotNull();
    }

    //    Return appointment with a specific id
    public Appointment findById(long id) {
        return appointmentRepository.findById(id);
    }

    /*Return appointment with specific id having current logged in user as patient if present
     * else return a status of 400 */
    public ResponseEntity<?> findByIdAndPatient_LoginName(long id) {
        String loginName = getLoginName();

        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndPatient_LoginName(id, loginName);


        if (optionalAppointment.isEmpty())
            return ResponseEntity.badRequest().body("Requested appointment is not present in database");
        return ResponseEntity.ok().body(optionalAppointment.get());
    }

    /*Return appointment with specific id having current logged in user as doctor if present
     * else return a status of 400 */
    public ResponseEntity<?> findByIdAndDoctor_LoginName(long id) {
        String loginName = getLoginName();

        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndDoctor_LoginName(id, loginName);

        if (optionalAppointment.isEmpty())
            return ResponseEntity.badRequest().body("Invalid request");
        return ResponseEntity.ok().body(optionalAppointment.get());
    }

    //    Return all upcoming appointments having current logged in user as patient
    public List<Appointment> getUpcomingAppointmentsByPatient() {
        String loginName = getLoginName();

        return appointmentRepository.findAllByPatient_LoginNameAndDateGreaterThanEqual(loginName, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).split(" ")[0]);
    }

    //    Return all appointments history having current logged in user as patient
    public List<Appointment> getAppointmentsHistoryByPatient() {
        String loginName = getLoginName();

        return appointmentRepository.findAllByPatient_LoginNameAndDateLessThanEqual(loginName, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).split(" ")[0]);
    }

    //    Get all upcoming appointments with filters
    public ResponseEntity<?> getUpcomingAppointmentsByDoctor(AppointmentPage page, AppointmentSearchCriteria criteria) {
        String loginName = getLoginName();
        String role = getRole();

        return ResponseEntity.ok().body(pageRepository.findAllUpcomingWithFilters(page, criteria, loginName, role));
    }

    //    Return all appointments history having current logged in user as doctor
    public List<Appointment> getAppointmentsHistoryByDoctor() {
        String loginName = getLoginName();

        return appointmentRepository.findAllByDoctor_LoginNameAndPrescriptionNotNull(loginName);
    }

    //    Return all appointments having current logged in user as doctor and given user as patient
    public List<Appointment> findAllByPatient_IdAndDoctor_LoginName(long id) {
        String loginName = getLoginName();
        return appointmentRepository.findAllByPatient_IdAndDoctor_LoginName(id, loginName);
    }

    //    Return feedback of appointment with specific id having current logged in user as doctor
    public String getFeedback(long id) {
        String loginName = getLoginName();

        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndDoctor_LoginName(id, loginName);

        if (optionalAppointment.isPresent()) {
            return optionalAppointment.get().getFeedback();
        } else {
            return "No feedback";
        }
    }

    //    Book next appointment by doctor
    public ResponseEntity<?> bookNext(String date, String time, long id) {
        String loginName = getLoginName();

        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndDoctor_LoginName(id, loginName);

        if (optionalAppointment.isEmpty()) return ResponseEntity.badRequest().body("Invalid request");

        Appointment appointment = optionalAppointment.get();

        Appointment newAppointment = new Appointment();

        newAppointment.setPatient(appointment.getPatient());
        newAppointment.setDoctor(appointment.getDoctor());
        newAppointment.setHealthProblem(appointment.getHealthProblem());

        newAppointment.setDate(date);
        newAppointment.setTime(time);

        appointmentRepository.save(newAppointment);

        return ResponseEntity.ok().body("Appointment Successfully booked");
    }

    //    Add prescription for a specific appointment
    public ResponseEntity<?> addPrescription(String prescription, long id) {
        String loginName = getLoginName();

        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndDoctor_LoginName(id, loginName);

        if (optionalAppointment.isEmpty()) return ResponseEntity.badRequest().body("Invalid request");

        Appointment appointment = optionalAppointment.get();

        appointment.setPrescription(prescription);

        appointmentRepository.save(appointment);

        return ResponseEntity.ok().body("Prescription successfully added");
    }

    //    Get all appointments with filters
    public ResponseEntity<?> getUpcomingAppointmentsWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria) {
        String loginName = getLoginName();
        String role = getRole();

        if (role.equals("doctor") && !appointmentRepository.existsByDoctor_LoginName(loginName))
            return ResponseEntity.badRequest().body("Invalid request");

        if (role.equals("patient") && !appointmentRepository.existsByPatient_LoginName(loginName))
            return ResponseEntity.badRequest().body("Invalid request");

        return ResponseEntity.ok().body(pageRepository.findAllUpcomingWithFilters(page, criteria, loginName, role));
    }

    //    Get all appointments with filters
    public ResponseEntity<?> getHistoryAppointmentsWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria) {
        String loginName = getLoginName();
        String role = getRole();

        if (role.equals("doctor") && !appointmentRepository.existsByDoctor_LoginName(loginName))
            return ResponseEntity.badRequest().body("Invalid request");

        if (role.equals("patient") && !appointmentRepository.existsByPatient_LoginName(loginName))
            return ResponseEntity.badRequest().body("Invalid request");

        return ResponseEntity.ok().body(pageRepository.findAllHistoryWithFilters(page, criteria, loginName, role));
    }

    //    Get patient details
    public ResponseEntity<?> getPatientByAppointmentId(long id) {
        String loginName = getLoginName();

        String role = userRepository.findByUsername(loginName).get().getRole();

        if (!appointmentRepository.existsById(id)) return ResponseEntity.badRequest().body("Invalid request");

        ResponseEntity responseEntity = (role.equals("ADMIN")) ? ResponseEntity.ok().body(findById(id)) : findByIdAndDoctor_LoginName(id);

        if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) return responseEntity;

        Patient patient = ((Appointment) Objects.requireNonNull(responseEntity.getBody())).getPatient();

        Map<String, Object> map = new HashMap<>();

        map.put("firstName", patient.getFirstName());
        map.put("lastName", patient.getLastName());
        map.put("dob", patient.getDOB());
        map.put("email", patient.getEmail());
        map.put("pastIllness", patient.getPastIllness());

        return ResponseEntity.ok().body(map);
    }

    //    Get patient details
    public ResponseEntity<?> getDoctorByAppointmentId(long id) {
        if (!appointmentRepository.existsById(id)) return ResponseEntity.badRequest().body("Invalid request");

        Doctor doctor = findById(id).getDoctor();

        Map<String, Object> map = new HashMap<>();

        map.put("firstName", doctor.getFirstName());
        map.put("lastName", doctor.getLastName());
        map.put("dob", doctor.getDob());
        map.put("email", doctor.getEmail());
        map.put("qualifications", doctor.getQualifications());
        map.put("specialization", doctor.getSpecialization());

        return ResponseEntity.ok().body(map);
    }

    //    Login name of current logged in user
    private String getLoginName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

    //    Role of current logged in user
    private String getRole() {
        return userRepository.findByUsername(getLoginName()).get().getRole();
    }

}
