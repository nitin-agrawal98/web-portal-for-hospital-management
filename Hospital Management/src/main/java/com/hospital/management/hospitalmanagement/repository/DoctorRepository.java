/**
 * Doctor repository
 */

package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByLoginName(String loginName);

    Doctor findByLoginName(String loginName);
}
