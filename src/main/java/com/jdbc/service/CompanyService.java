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

    public void delete(int id) {
        companyDAO.delete(id);
    }

    public Company mapCompany(HttpServletRequest req) {
        String companyName = req.getParameter("companyName").trim();
        String location = req.getParameter("location").trim();

        Company company = new Company();
        company.setCompanyName(companyName);
        company.setLocation(location);
        return company;
    }

    public Company mapEditCompany(Company company, HttpServletRequest req) {
        String companyName = req.getParameter("companyName").trim();
        String location = req.getParameter("location").trim();

        company.setCompanyName(companyName);
        company.setLocation(location);
        return company;
    }

    public String validateCompany(HttpServletRequest req) {
        String companyName = req.getParameter("companyName").trim();
        if (get(companyName) != null) {
            return String.format("Company with title %s already exist", companyName);
        }
        return "";
    }

    public String validateEditCompany(HttpServletRequest req) {
        String oldName = req.getParameter("oldName").trim();
        String newName = req.getParameter("companyName").trim();
        if (oldName.equals(newName)) {
            return "";
        } else if (get(newName) != null) {
            return String.format("Company with title %s already exist", newName);
        }
        return "";
    }
}
