/**
 * Appointment Feedback repository
 */

package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.AppointmentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentFeedbackRepository extends JpaRepository<AppointmentFeedback, Long> {

    Optional<AppointmentFeedback> findByAppointment_Id(long id);

    void deleteByAppointment_Id(long id);

    boolean existsByAppointmentId(long id);
}
