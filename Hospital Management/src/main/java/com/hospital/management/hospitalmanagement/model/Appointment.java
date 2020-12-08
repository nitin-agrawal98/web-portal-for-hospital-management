/**
 * Appointment Table
 */

package com.hospital.management.hospitalmanagement.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Appointment implements Serializable {
    @ManyToOne
    @JoinColumn
    private Patient patient;
    @ManyToOne
    @JoinColumn
    private Doctor doctor;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String healthProblem;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String prescription;
    @Column
    private String feedback;

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, String date, String time, String healthProblem) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.healthProblem = healthProblem;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Appointment = " + getId();
    }
}
