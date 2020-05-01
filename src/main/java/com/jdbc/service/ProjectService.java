package com.jdbc.service;

import com.jdbc.dao.ProjectDAOImpl;
import com.jdbc.model.Project;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

public class ProjectService {

    private ProjectDAOImpl projectDAO;

    public ProjectService(ProjectDAOImpl projectDAO) {
        this.projectDAO = projectDAO;
    }

    public void create(Project project) {
        projectDAO.create(project);
    }

    public Project get(int id) {
        return projectDAO.getByID(id);
    }

    public Project get(String projectName) {
        return projectDAO.get(projectName);
    }

    public List<Project> getAll() {
        return projectDAO.getAll();
    }

    public void update(Project project) {
        projectDAO.update(project);
    }

    public void delete(Project project) {
        projectDAO.delete(project);
    }

    public String validateProject(HttpServletRequest req) {

        String projectName = req.getParameter("projectName").trim();
        if (get(projectName) != null) {
            return String.format("Project with title %s already exist", projectName);
        }
        return "";
    }

    public Project mapProject(HttpServletRequest req) {

        String projectName = req.getParameter("projectName").trim();
        String status = req.getParameter("status").trim();
        String cost = req.getParameter("cost");
        String date = req.getParameter("date");

        Project project = new Project();
        project.setProjectName(projectName);
        project.setStatus(status);
        project.setCost(Integer.parseInt(cost));
        project.setDate(Date.valueOf(date));

        return project;
    }

    public String validateEditProject(HttpServletRequest req) {

        String oldName = req.getParameter("oldName").trim();
        String newName = req.getParameter("projectName").trim();

        if (oldName.equals(newName)){
            return "";
        } else if (get(newName) != null) {
            return String.format("Project with title %s already exist", newName);
        }
        return "";
    }

    public Project mapEditProject(Project project, HttpServletRequest req) {

        String projectName = req.getParameter("projectName").trim();
        String status = req.getParameter("status").trim();
        String cost = req.getParameter("cost");
        String date = req.getParameter("date");

        project.setProjectName(projectName);
        project.setStatus(status);
        project.setCost(Integer.parseInt(cost));
        project.setDate(Date.valueOf(date));

        return project;
    }
}
