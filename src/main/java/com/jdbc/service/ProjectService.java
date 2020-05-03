package com.jdbc.service;

import com.jdbc.dao.CompanyDAOImpl;
import com.jdbc.dao.CustomerDAOImpl;
import com.jdbc.dao.DeveloperDAOImpl;
import com.jdbc.dao.ProjectDAOImpl;
import com.jdbc.model.Company;
import com.jdbc.model.Customer;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class ProjectService {

    private ProjectDAOImpl projectDAO;
    private CompanyDAOImpl companyDAO;
    private CustomerDAOImpl customerDAO;
    private DeveloperDAOImpl developerDAO;

    public ProjectService(ProjectDAOImpl projectDAO, CompanyDAOImpl companyDAO,
                          CustomerDAOImpl customerDAO, DeveloperDAOImpl developerDAO) {
        this.projectDAO = projectDAO;
        this.companyDAO = companyDAO;
        this.customerDAO = customerDAO;
        this.developerDAO = developerDAO;
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

    public List<Company> getAllCompanies() {
        return companyDAO.getAll();
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }

    public List<Developer> getAllDevelopers() {
        return developerDAO.getAll();
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

        if (oldName.equals(newName)) {
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

    public Project link(HttpServletRequest req) {

        String id = req.getParameter("projectID");
        String[] reqCompanies = req.getParameterValues("companies");
        String[] reqCustomers = req.getParameterValues("customers");
        String[] reqDevelopers = req.getParameterValues("developers");

        Project project = get(Integer.parseInt(id));
        List<Company> companies = project.getCompanies();
        List<Customer> customers = project.getCustomers();
        List<Developer> developers = project.getDevelopers();

        if (reqCompanies != null) {
            for (String companyName : reqCompanies) {
                Company company = companyDAO.get(companyName);
                if (!companies.contains(company)) {
                    companies.add(company);
                }
            }
        }
        if (reqCustomers != null) {
            for (String customerName : reqCustomers) {
                Customer customer = customerDAO.get(customerName);
                if (!customers.contains(customer)) {
                    customers.add(customer);
                }
            }
        }
        if (reqDevelopers != null) {
            for (String developerName : reqDevelopers) {
                String developerID = developerName.split("\\.")[0];
                Developer developer = developerDAO.getByID(Integer.parseInt(developerID));
                if (!developers.contains(developer)) {
                    developers.add(developer);
                }
            }
        }

        project.setCompanies(companies);
        project.setCustomers(customers);
        project.setDevelopers(developers);
        update(project);
        return project;
    }

    public Project unlink(HttpServletRequest req) {

        String id = req.getParameter("projectID");
        String[] reqCompanies = req.getParameterValues("companies");
        String[] reqCustomers = req.getParameterValues("customers");
        String[] reqDevelopers = req.getParameterValues("developers");

        Project project = get(Integer.parseInt(id));
        List<Company> companies = project.getCompanies();
        List<Customer> customers = project.getCustomers();
        List<Developer> developers = project.getDevelopers();

        if (reqCompanies != null) {
            for (String companyName : reqCompanies) {
                Company company = companyDAO.get(companyName);
                companies.remove(company);
            }
        }
        if (reqCustomers != null) {
            for (String customerName : reqCustomers) {
                Customer customer = customerDAO.get(customerName);
                customers.remove(customer);

            }
        }
        if (reqDevelopers != null) {
            for (String developerName : reqDevelopers) {
                String developerID = developerName.split("\\.")[0];
                Developer developer = developerDAO.getByID(Integer.parseInt(developerID));
                developers.remove(developer);

            }
        }

        project.setCompanies(companies);
        project.setCustomers(customers);
        project.setDevelopers(developers);
        update(project);
        return project;
    }

    public Project unlinkAll(HttpServletRequest req) {

        String id = req.getParameter("id");
        Project project = get(Integer.parseInt(id));
        project.setCompanies(Collections.emptyList());
        project.setCustomers(Collections.emptyList());
        project.setDevelopers(Collections.emptyList());
        update(project);
        return project;
    }
}
