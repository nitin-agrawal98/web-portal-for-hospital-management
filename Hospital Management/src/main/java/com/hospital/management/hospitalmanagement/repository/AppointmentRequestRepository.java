/**
 * Appointment Request Repository
 */

package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.AppointmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
    AppointmentRequest findById(long id);

    AppointmentRequest deleteById(long id);

    List<AppointmentRequest> findAllByPatient_LoginName(String name);
}
