package com.jdbc.service;

import com.jdbc.dao.DeveloperDAOImpl;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Developer;
import com.jdbc.model.Skill;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class DeveloperService {

    DeveloperDAOImpl developerDAO;
    SkillDAO skillDAO;

    public DeveloperService(DeveloperDAOImpl developerDAO, SkillDAO skillDAO) {
        this.developerDAO = developerDAO;
        this.skillDAO = skillDAO;
    }

    public void create(Developer developer) {
        developerDAO.create(developer);
    }

    public Developer get(int id) {
        return developerDAO.getByID(id);
    }

    public Developer get(String email) {
        return developerDAO.get(email);
    }

    public List<Developer> getAll() {
        return developerDAO.getAll();
    }

    public void update(Developer developer) {
        developerDAO.update(developer);
    }

    public void delete(Developer developer) {
        developerDAO.delete(developer);
    }

    public List<Developer> getAllDevelopersByLevel(String level) {
        return developerDAO.getAllDevelopersByLevel(level);
    }

    public List<Developer> getAllDevelopersByDepartment(String department) {
        return developerDAO.getAllDevelopersByDepartment(department);
    }

    public List<Developer> getAllDeveloperBySkill(String department,String level) {
        return developerDAO.getAllDeveloperBySkill(department, level);
    }

    public String validateDeveloper(HttpServletRequest req) {
        String email = req.getParameter("email").trim();
        if (get(email) != null) {
            return String.format("Developer with email %s already exist", email);
        }
        return "";
    }

    public Developer mapDeveloper(HttpServletRequest req) {

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String gender = req.getParameter("gender");
        String age = req.getParameter("age");
        String salary = req.getParameter("salary");
        String email = req.getParameter("email").trim();

        String department = req.getParameter("department");
        String level = req.getParameter("level");
        Skill skill = skillDAO.get(department, level);

        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setGender(gender);
        developer.setAge(Integer.parseInt(age));
        developer.setSalary(Integer.parseInt(salary));
        developer.setEmail(email);
        developer.setSkills(Arrays.asList(skill));

        return developer;

    }

    public String validateEditDeveloper(HttpServletRequest req) {

        String oldEmail = req.getParameter("oldEmail").trim();
        String email = req.getParameter("email").trim();

        if (oldEmail.equals(email)) {
            return "";
        } else if (get(email) != null) {
            return String.format("Developer with email %s already exist", email);
        }
        return "";
    }

    public Developer mapEditDeveloper(Developer developer, HttpServletRequest req) {

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String gender = req.getParameter("gender");
        String age = req.getParameter("age");
        String salary = req.getParameter("salary");
        String email = req.getParameter("email").trim();
        String department = req.getParameter("department");
        String level = req.getParameter("level");

        List<Skill> skills = developer.getSkills();
        for (Skill skill : skills) {
            if (skill.getDepartment().equals(department)) {
                skills.remove(skill);
                break;
            }
        }
        Skill skill = skillDAO.get(department, level);
        skills.add(skill);

        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setGender(gender);
        developer.setAge(Integer.parseInt(age));
        developer.setSalary(Integer.parseInt(salary));
        developer.setEmail(email);
        developer.setSkills(skills);

        return developer;
    }

    public List<Developer> getDevelopersBySkill(HttpServletRequest req) {
        String department = req.getParameter("department");
        String level = req.getParameter("level");

        if (level == null && department == null) {
            return getAll();
        } else if (department == null) {
            return getAllDevelopersByLevel(level);
        } else if (level == null) {
            return getAllDevelopersByDepartment(department);
        } else {
            return getAllDeveloperBySkill(department, level);
        }
    }
}
