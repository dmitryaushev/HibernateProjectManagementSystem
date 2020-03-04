package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;

public class DeleteCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public DeleteCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Delete company";
    }

    @Override
    public void process() {

        view.write("Chose company id");
        companyDAO.getAll().forEach(System.out::println);
        int id = Integer.parseInt(view.read());
        companyDAO.remove(id);
    }
}