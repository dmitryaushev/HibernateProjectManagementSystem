package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Customer;
import com.jdbc.model.Project;

import java.util.List;

public class LinkProjectToCustomer implements Command {

    private View view;
    private ProjectDAO projectDAO;
    private CustomerDAO customerDAO;

    public LinkProjectToCustomer(View view, ProjectDAO projectDAO, CustomerDAO customerDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Link project to customer";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null) {
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));
        }

        view.write("Enter a customer id");
        int customerID = Integer.parseInt(view.read());
        Customer customer = customerDAO.getByID(customerID);

        if (customer == null) {
            throw new IllegalArgumentException(String.format("Customer with id %d not exist", customerID));
        }

        String projectName = projectDAO.getByID(projectID).getProjectName();
        String customerName = customerDAO.getByID(customerID).getCustomerName();

        List<Customer> customers = project.getCustomers();
        for (Customer c : customers) {
            if (c.getCustomerID() == customerID) {
                throw new UnsupportedOperationException(String.format(
                        "Customer %s already owns project %s", customerName, projectName));
            }
        }

        view.write(String.format("Connect a project %s with a customer %s? Y|N", projectName, customerName));
        question(view.read());

        customers.add(customer);
        project.setCustomers(customers);
        projectDAO.update(project);
        view.write("Successful");
        sleep();
    }
}
