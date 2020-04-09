package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

public class GetSumSalaryByProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetSumSalaryByProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get sum salary by project";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null) {
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));
        }

        int sum = 0;
        for (Developer developer : project.getDevelopers()) {
            sum += developer.getSalary();
        }

        view.write(String.format("Salary of all developers in project %s is %d\n", project.getProjectName(), sum));
        sleep();
    }
}
