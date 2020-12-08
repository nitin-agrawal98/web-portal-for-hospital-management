/**
 * Appointment Feedback Service Class
 */

package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.Appointment;
import com.hospital.management.hospitalmanagement.model.AppointmentFeedback;
import com.hospital.management.hospitalmanagement.repository.AppointmentFeedbackRepository;
import com.hospital.management.hospitalmanagement.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AppointmentFeedbackService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentFeedbackRepository feedbackRepository;

    //    Add feedback and appointment id to appointment feedback table
    public ResponseEntity<?> giveFeedback(String feedback, long id) {

        if (feedback == null) ResponseEntity.badRequest().body("Feedback should not be null");

        String loginName = getLoginName();

//        Get optional object of appointment with given id having current logged in user as patient
        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAndPatient_LoginName(id, loginName);

//        If appointment is not present then return a status of 400
        if (optionalAppointment.isEmpty())
            return ResponseEntity.badRequest().body("Requested appointment is not present in database");

        Appointment appointment = optionalAppointment.get();

        /*If feedback has already been forwarded by receptionist then return a status of 200 with
         * a message*/
        if (appointment.getFeedback() != null)
            return ResponseEntity.ok().body("Feedback has already been forwarded");

        /*If feedback has already been provided by patient then return a status of 200 with
         * a message*/
        if (feedbackRepository.existsByAppointmentId(id))
            return ResponseEntity.ok().body("Feedback has already been provided.");


        feedbackRepository.save(new AppointmentFeedback(appointment, feedback));

        return ResponseEntity.ok().body("Successfully sent feedback from patient side");
    }

    //    Return feedback with specific appointment id
    public String viewFeedback(long id) {
        String feedback = null;

        Optional<AppointmentFeedback> optionalAppointmentFeedback = feedbackRepository.findByAppointment_Id(id);
        String s = appointmentRepository.findById(id).getFeedback();

        /*If feedback has been forwarded by receptionist then append "Not Forwarded"
         * else "Forwarded" at the start of the string*/

        if (optionalAppointmentFeedback.isEmpty() && s != null) feedback = "Forwarded" + s;
        else if (optionalAppointmentFeedback.isPresent())
            feedback = "Not Forwarded" + optionalAppointmentFeedback.get().getFeedback();

        return feedback;
    }

    @Transactional
    public String forwardFeedback(long id) {
//        If no feedback is present
        if (!feedbackRepository.existsByAppointmentId(id)) return "No feedback";

//        Get appointment feedback object
        AppointmentFeedback appointmentFeedback = feedbackRepository.findByAppointment_Id(id).get();

//        Delete feedback from appointment feedback table
        feedbackRepository.deleteByAppointment_Id(id);

//        Get corresponding appointment
        Appointment appointment = appointmentFeedback.getAppointment();

//        Save feedback in appointment object
        appointment.setFeedback(appointmentFeedback.getFeedback());

//        Update appointment in appointments table
        appointmentRepository.save(appointment);

        return "Successfully forwarded feedback";
    }

    //    Login Name of current logged in user
    private String getLoginName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }
}
