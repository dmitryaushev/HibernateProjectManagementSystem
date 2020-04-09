package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

public class GetCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public GetCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Get company";
    }

    @Override
    public void process() {

        view.write("Enter a company id");
        int companyID = Integer.parseInt(view.read());
        Company company = companyDAO.getByID(companyID);

        if (company == null) {
            throw new IllegalArgumentException(String.format("Company with id %d not exist", companyID));
        }

        view.write(company.toString());
        sleep();
    }
}
