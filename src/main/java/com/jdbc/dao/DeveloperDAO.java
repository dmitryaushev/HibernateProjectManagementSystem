package com.jdbc.dao;

import com.jdbc.model.Developer;

import java.util.List;

public interface DeveloperDAO extends DataAccessObject<Developer> {

    Developer get(String email);
    List<Developer> getAllDevelopersByDepartment(String department);
    List<Developer> getAllDevelopersByLevel(String level);
}
