/**
 * Status of registration of patient
 * Default value is IN_PROGRESS when patient registers
 * which changes to approved or cancelled according to
 * receptionist response
 */

package com.hospital.management.hospitalmanagement.model;

public enum Status {
    IN_PROGRESS, APPROVED, CANCELLED
}
