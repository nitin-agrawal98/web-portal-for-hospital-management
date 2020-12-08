/**
 * Appointment Request Table
 */

package com.hospital.management.hospitalmanagement.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class AppointmentRequest implements Serializable {
    @ManyToOne
    @JoinColumn
    Patient patient;
    @Column(nullable = false)
    String date;
    @Column(nullable = false)
    String time;
    @Column(nullable = false)
    String healthProblem;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public AppointmentRequest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHealthProblem() {
        return healthProblem;
    }

    public void setHealthProblem(String healthProblem) {
        this.healthProblem = healthProblem;
    }
}
