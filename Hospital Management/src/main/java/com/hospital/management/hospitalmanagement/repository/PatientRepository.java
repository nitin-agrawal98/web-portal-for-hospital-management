/**
 * Patient Repository
 */

package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.Patient;
import com.hospital.management.hospitalmanagement.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByLoginName(String loginName);

    List<Patient> findAllByStatus(Status status);

    boolean existsById(long id);

    boolean existsByLoginName(String loginName);
}
