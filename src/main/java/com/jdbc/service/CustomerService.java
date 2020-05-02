package com.jdbc.service;

import com.jdbc.dao.CustomerDAOImpl;
import com.jdbc.model.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CustomerService {

    private CustomerDAOImpl customerDAO;

    public CustomerService(CustomerDAOImpl customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void create(Customer customer) {
        customerDAO.create(customer);
    }

    public Customer get(int id) {
        return customerDAO.getByID(id);
    }

    public Customer get(String customerName) {
        return customerDAO.get(customerName);
    }

    public List<Customer> getAll() {
        return customerDAO.getAll();
    }

    public void update(Customer customer) {
        customerDAO.update(customer);
    }

    public void delete(Customer customer) {
        customerDAO.delete(customer);
    }

    public String validateCustomer(HttpServletRequest req) {
        String customerName = req.getParameter("customerName").trim();
        if (get(customerName) != null) {
            return String.format("Customer with title %s already exist", customerName);
        }
        return "";
    }

    public Customer mapCustomer(HttpServletRequest req) {

        String customerName = req.getParameter("customerName").trim();
        String location = req.getParameter("location").trim();

        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setLocation(location);

        return customer;
    }

    public String validateEditCustomer(HttpServletRequest req) {

        String oldName = req.getParameter("oldName").trim();
        String customerName = req.getParameter("customerName").trim();

        if (oldName.equals(customerName)) {
            return "";
        } else if (get(customerName) != null) {
            return String.format("Customer with title %s already exist", customerName);
        }
        return "";
    }

    public Customer mapEditCustomer(Customer customer, HttpServletRequest req) {

        String customerName = req.getParameter("customerName").trim();
        String location = req.getParameter("location").trim();

        customer.setCustomerName(customerName);
        customer.setLocation(location);

        return customer;
    }
}
