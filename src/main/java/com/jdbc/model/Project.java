package com.jdbc.model;

import java.sql.Date;
import java.util.List;

public class Project {

    private int projectID;
    private String projectName;
    private String status;
    private int cost;
    private Date date;
    private List<Developer> developers;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Project {" +
                "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", status='" + status + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                '}';
    }
}
