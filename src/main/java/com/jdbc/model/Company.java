package com.jdbc.model;

import java.util.List;
import java.util.Objects;

public class Company {

    private int companyID;
    private String companyName;
    private String location;
    private List<Project> projects;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
        return "Company {" +
                "companyID=" + companyID +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyID == company.companyID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyID);
    }
}
