package com.hospital.management.hospitalmanagement.model;

public class AppointmentSearchCriteria {
    private String filterBy = "patient";
    private String filterValue = "";
    private String healthProblem = "";

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getHealthProblem() {
        return healthProblem;
    }

    public void setHealthProblem(String healthProblem) {
        this.healthProblem = healthProblem;
    }

    @Override
    public String toString() {
        return "{" +
                "filterBy: " + getFilterBy() + ",\n" +
                "filterValue: " + getFilterValue() + ",\n" +
                "healthProblem: " + getHealthProblem() + "\n" +
                "}";
    }
}
