package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

public class GetAllProjectsWithDevelopers implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetAllProjectsWithDevelopers(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get all projects with developers";
    }

    @Override
    public void process() {

        StringBuilder result = new StringBuilder();

        for (Project project : projectDAO.getAll()) {
            result.append(project.getDate()).append(" ")
                    .append(project.getProjectName()).append(" ")
                    .append(project.getDevelopers().size()).append("\n");
        }

        view.write(result.toString());
        sleep();
    }
}
