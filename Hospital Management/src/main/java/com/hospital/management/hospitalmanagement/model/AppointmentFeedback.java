/**
 * Appointment Feedback Table
 */

package com.hospital.management.hospitalmanagement.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class AppointmentFeedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    @JoinColumn
    Appointment appointment;

    @Column(nullable = false)
    private String feedback;

    public AppointmentFeedback() {
    }

    public AppointmentFeedback(Appointment appointment, String feedback) {
        this.appointment = appointment;
        this.feedback = feedback;
    }

    public AppointmentFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
