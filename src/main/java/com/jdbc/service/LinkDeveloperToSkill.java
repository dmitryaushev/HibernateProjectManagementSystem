package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Developer;
import com.jdbc.model.Skill;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LinkDeveloperToSkill implements Command {

    private View view;
    private DeveloperDAO developerDAO;
    private SkillDAO skillDAO;

    public LinkDeveloperToSkill(View view, DeveloperDAO developerDAO, SkillDAO skillDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
        this.skillDAO = skillDAO;
    }

    @Override
    public String command() {
        return "Link developer to skill";
    }

    @Override
    public void process() {

        view.write("Enter a developer id");
        int developerID = Integer.parseInt(view.read());
        Developer developer = developerDAO.getByID(developerID);

        if (developer == null) {
            throw new IllegalArgumentException(String.format("Developer with id %d not exist", developerID));
        }

        Set<String> departmentsSet = skillDAO.getAll()
                .stream()
                .map(Skill::getDepartment)
                .collect(Collectors.toSet());
        String department;

        do {
            view.write("Choose skill department");
            departmentsSet.forEach(System.out::println);
            department = view.read();
        } while (!matchString(department, departmentsSet));

        Set<String> levelsSet = skillDAO.getAll()
                .stream()
                .map(Skill::getLevel)
                .collect(Collectors.toSet());
        String level;

        do {
            view.write("Choose skill level");
            levelsSet.forEach(System.out::println);
            level = view.read();
        } while (!matchString(level, levelsSet));

        String firstName = developer.getFirstName();
        String lastName = developer.getLastName();
        view.write(String.format("%s %s is %s %s developer? Y|N", firstName, lastName, department, level));
        question(view.read());

        List<Skill> skills = developer.getSkills();
        for (Skill skill : skills) {
            if (skill.getDepartment().equals(department)) {
                skills.remove(skill);
                break;
            }
        }

        Skill skill = skillDAO.get(department, level);
        skills.add(skill);
        developer.setSkills(skills);

        developerDAO.linkDeveloperSkill(developer);
        view.write("Successful");
        sleep();
    }
}
