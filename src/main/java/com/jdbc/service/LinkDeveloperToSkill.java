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

        List<Developer> developersList = developerDAO.getAll();
        List<Integer> idList = developersList
                .stream()
                .map(Developer::getDeveloperID)
                .collect(Collectors.toList());
        int developerID;

        Set<String> departmentsSet = skillDAO.getAll()
                .stream()
                .map(Skill::getDepartment)
                .collect(Collectors.toSet());
        String department;

        Set<String> levelsSet = skillDAO.getAll()
                .stream()
                .map(Skill::getLevel)
                .collect(Collectors.toSet());
        String level;

        do {
            view.write("Choose developer id");
            developersList.forEach(System.out::println);
            developerID = Integer.parseInt(view.read());
        } while (!matchInt(developerID, idList));

        do {
            view.write("Choose skill department");
            departmentsSet.forEach(System.out::println);
            department = view.read();
        } while (!matchString(department, departmentsSet));

        do {
            view.write("Choose skill level");
            levelsSet.forEach(System.out::println);
            level = view.read();
        } while (!matchString(level, levelsSet));

        developerDAO.linkDeveloperSkill(developerID, department, level);

        String firstName = developerDAO.getByID(developerID).getFirstName();
        String lastName = developerDAO.getByID(developerID).getLastName();

        view.redWrite(String.format("%s %s is %s %s developer \n", firstName, lastName, department, level));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
