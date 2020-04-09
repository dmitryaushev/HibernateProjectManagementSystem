package com.jdbc.model;

import java.util.List;

public class Customer {

    private int customerID;
    private String customerName;
    private String location;
    List<Project> projects;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
