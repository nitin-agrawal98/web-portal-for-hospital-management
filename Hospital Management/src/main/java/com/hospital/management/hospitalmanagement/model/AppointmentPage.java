package com.hospital.management.hospitalmanagement.model;

public class AppointmentPage {
    private int pageNumber = 0;
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "{\n" +
                "pageNumber: " + getPageNumber() + ",\n" +
                "pageSize: " + getPageSize() + "\n" +
                "}";
    }
}
