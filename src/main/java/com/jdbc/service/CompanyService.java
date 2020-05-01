package com.jdbc.service;

import com.jdbc.dao.CompanyDAOImpl;
import com.jdbc.model.Company;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CompanyService {

    private CompanyDAOImpl companyDAO;

    public CompanyService(CompanyDAOImpl companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void create(Company company) {
        companyDAO.create(company);
    }

    public Company get(int id) {
        return companyDAO.getByID(id);
    }

    public Company get(String companyName) {
        return companyDAO.get(companyName);
    }

    public List<Company> getAll() {
        return companyDAO.getAll();
    }

    public void update(Company company) {
        companyDAO.update(company);
    }

    public void delete(Company company) {
        companyDAO.delete(company);
    }

    public Company mapCompany(HttpServletRequest req) {
        String companyName = req.getParameter("companyName");
        String location = req.getParameter("location");

        Company company = new Company();
        company.setCompanyName(companyName);
        company.setLocation(location);
        return company;
    }

    public Company mapEditCompany(HttpServletRequest req, String nameParameter, String locationParameter) {

        String companyID = req.getParameter("companyID");
        String companyName = req.getParameter(nameParameter);
        String location = req.getParameter(locationParameter);

        Company company = get(Integer.parseInt(companyID));
        company.setCompanyName(companyName);
        company.setLocation(location);
        return company;
    }

    public String validateCompany(HttpServletRequest req) {

        String result = "";
        String companyName = req.getParameter("companyName").trim();
        String location = req.getParameter("location").trim();
        if (companyName.isEmpty() || location.isEmpty()) {
            return "Company name and location must not be empty";
        }
        Company company = get(companyName);
        if (company != null) {
            return String.format("Company with title %s already exist", companyName);
        }
        return result;
    }

    public String validateEditCompany(HttpServletRequest req) {

        String result = "";
        String oldName = req.getParameter("oldName").trim();
        String newName = req.getParameter("newName").trim();
        String location = req.getParameter("newLocation").trim();
        if (newName.isEmpty() || location.isEmpty()) {
            return "Company name and location must not be empty";
        } else if (oldName.equals(newName)) {
            return result;
        }
        Company company = get(newName);
        if (company != null) {
            return String.format("Company with title %s already exist", newName);
        }
        return result;
    }
}
