package com.jdbc.controller;

import com.jdbc.config.HibernateDatabaseConnector;
import com.jdbc.dao.CompanyDAOImpl;
import com.jdbc.dao.CustomerDAOImpl;
import com.jdbc.dao.DeveloperDAOImpl;
import com.jdbc.dao.ProjectDAOImpl;
import com.jdbc.model.Company;
import com.jdbc.model.Customer;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;
import com.jdbc.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {

    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        super.init();
        projectService = new ProjectService(
                new ProjectDAOImpl(HibernateDatabaseConnector.getSessionFactory()),
                new CompanyDAOImpl(HibernateDatabaseConnector.getSessionFactory()),
                new CustomerDAOImpl(HibernateDatabaseConnector.getSessionFactory()),
                new DeveloperDAOImpl(HibernateDatabaseConnector.getSessionFactory()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/showProjects")) {
            List<Project> projects = projectService.getAll();
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/view/project/showProjects.jsp").forward(req, resp);
        }
        else if (action.startsWith("/findProject")) {
            req.getRequestDispatcher("/view/project/findProject.jsp").forward(req, resp);
        }
        else if (action.startsWith("/find")) {
            String projectName = req.getParameter("projectName");
            if (projectName.isEmpty()) {
                String message = "Project name is empty";
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/project/findProject.jsp").forward(req, resp);
            }
            Project project = projectService.get(projectName);
            if (project == null) {
                String message = String.format("Project with name %s not exist", projectName);
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/project/findProject.jsp").forward(req, resp);
            } else {
                req.setAttribute("project", project);
                req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/createProject")) {
            req.getRequestDispatcher("/view/project/createProject.jsp").forward(req, resp);
        }
        else if (action.startsWith("/get")) {
            String id = req.getParameter("id");
            Project project = projectService.get(Integer.parseInt(id));
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("id");
            Project project = projectService.get(Integer.parseInt(id));
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/editProject.jsp").forward(req, resp);
        }
        else if (action.startsWith("/delete")) {
            doDelete(req, resp);
        }
        else if (action.startsWith("/link")) {
            String id = req.getParameter("id");
            Project project = projectService.get(Integer.parseInt(id));
            List<Company> companies = projectService.getAllCompanies();
            List<Customer> customers = projectService.getAllCustomers();
            List<Developer> developers = projectService.getAllDevelopers();
            req.setAttribute("project", project);
            req.setAttribute("companies", companies);
            req.setAttribute("customers", customers);
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/view/project/linkProject.jsp").forward(req, resp);
        }
        else if ((action.startsWith("/unlinkAll"))) {
            Project project = projectService.unlinkAll(req);
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/unlink")) {
            String id = req.getParameter("id");
            Project project = projectService.get(Integer.parseInt(id));
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/unlinkProject.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/createProject")) {
            String validate = projectService.validateProject(req);
            if (!validate.isEmpty()) {
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/project/createProject.jsp").forward(req, resp);
            } else {
                Project project = projectService.mapProject(req);
                projectService.create(project);
                req.setAttribute("project", project);
                req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("projectID");
            Project project = projectService.get(Integer.parseInt(id));
            String validate = projectService.validateEditProject(req);
            if (!validate.isEmpty()) {
                req.setAttribute("project", project);
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/project/editProject.jsp").forward(req, resp);
            } else {
                project = projectService.mapEditProject(project, req);
                projectService.update(project);
                req.setAttribute("project", project);
                req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/linkProject")) {
            Project project = projectService.link(req);
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/unlinkProject")){
            Project project = projectService.unlink(req);
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project/projectDetails.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Project project = projectService.get(Integer.parseInt(id));
        projectService.delete(project);
        resp.sendRedirect("/ProjectManagementSystem/project/showProjects");
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String requestPathWithServletContext = req.getContextPath() + req.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
