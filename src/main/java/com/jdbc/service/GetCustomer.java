package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

public class GetCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public GetCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Get customer";
    }

    @Override
    public void process() {

        view.write("Enter a customer id");
        int customerID = Integer.parseInt(view.read());
        Customer customer = customerDAO.getByID(customerID);

        if (customer == null) {
            throw new IllegalArgumentException(String.format("Customer with id %d not exist", customerID));
        }

        view.write(customer.toString());
        sleep();
    }
}
