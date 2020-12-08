/**
 * Appointment Repository
 */

package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient_LoginNameAndDateGreaterThanEqual(String loginName, String date);

    List<Appointment> findAllByPatient_LoginNameAndDateLessThanEqual(String loginName, String date);

    List<Appointment> findAllByPrescriptionNull();

    List<Appointment> findAllByPrescriptionNotNull();

    Appointment findById(long id);

    Optional<Appointment> findByIdAndPatient_LoginName(long id, String loginName);

    Optional<Appointment> findByIdAndDoctor_LoginName(long id, String loginName);

    List<Appointment> findAllByPatient_LoginNameAndPrescriptionNull(String loginName);

    List<Appointment> findAllByPatient_LoginNameAndPrescriptionNotNull(String loginName);

    List<Appointment> findAllByDoctor_LoginNameAndPrescriptionNull(String loginName);

    List<Appointment> findAllByDoctor_LoginNameAndPrescriptionNotNull(String loginName);

    List<Appointment> findAllByPatient_IdAndDoctor_LoginName(long id, String loginName);

    boolean existsByDoctor_LoginName(String loginName);

    boolean existsByPatient_LoginName(String loginName);
}
