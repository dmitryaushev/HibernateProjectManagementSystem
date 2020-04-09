package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;

public class GetAllDevelopersByProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetAllDevelopersByProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get all developers by project";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());

        if (projectDAO.getByID(projectID) == null) {
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));
        }

        projectDAO.getByID(projectID).getDevelopers().forEach(System.out::println);
        sleep();
    }
}
