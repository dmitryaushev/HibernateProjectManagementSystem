package com.jdbc.model;

import java.util.List;

public class Skill {

    private int skillID;
    private String department;
    private String level;
    private List<Developer> developers;

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Skill {" +
                "skillID=" + skillID +
                ", department='" + department + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
