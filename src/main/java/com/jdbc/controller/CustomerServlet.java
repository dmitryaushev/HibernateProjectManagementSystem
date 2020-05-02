package com.jdbc.controller;

import com.jdbc.config.HibernateDatabaseConnector;
import com.jdbc.dao.CustomerDAOImpl;
import com.jdbc.model.Customer;
import com.jdbc.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        customerService = new CustomerService(new CustomerDAOImpl(HibernateDatabaseConnector.getSessionFactory()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/showCustomers")) {
            List<Customer> customers = customerService.getAll();
            req.setAttribute("customers", customers);
            req.getRequestDispatcher("/view/showCustomers.jsp").forward(req, resp);
        }
        else if (action.startsWith("/findCustomer")) {
            req.getRequestDispatcher("/view/findCustomer.jsp").forward(req, resp);
        }
        else if (action.startsWith("/find")) {
            String customerName = req.getParameter("customerName").trim();
            Customer customer = customerService.get(customerName);
            if (customer == null) {
                String message = String.format("Customer with name %s not exist", customerName);
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/findCustomer.jsp").forward(req, resp);
            } else {
                req.setAttribute("customer", customer);
                req.getRequestDispatcher("/view/customerDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/createCustomer")) {
            req.getRequestDispatcher("/view/createCustomer.jsp").forward(req, resp);
        }
        else if (action.startsWith("/get")) {
            String id = req.getParameter("id");
            Customer customer = customerService.get(Integer.parseInt(id));
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/view/customerDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("id");
            Customer customer = customerService.get(Integer.parseInt(id));
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/view/editCustomer.jsp").forward(req, resp);
        }
        else if (action.startsWith("/delete")) {
            doDelete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/createCustomer")) {
            String validate = customerService.validateCustomer(req);
            if (!validate.isEmpty()) {
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/createCustomer.jsp").forward(req, resp);
            } else {
                Customer customer = customerService.mapCustomer(req);
                customerService.create(customer);
                req.setAttribute("customer", customer);
                req.getRequestDispatcher("/view/customerDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("customerID");
            Customer customer = customerService.get(Integer.parseInt(id));
            String validate = customerService.validateEditCustomer(req);
            if (!validate.isEmpty()) {
                req.setAttribute("customer", customer);
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/editCustomer.jsp").forward(req, resp);
            } else {
                customer = customerService.mapEditCustomer(customer, req);
                customerService.update(customer);
                req.setAttribute("customer", customer);
                req.getRequestDispatcher("/view/customerDetails.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Customer customer = customerService.get(Integer.parseInt(id));
        customerService.delete(customer);
        resp.sendRedirect("/ProjectManagementSystem/customer/showCustomers");
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String requestPathWithServletContext = req.getContextPath() + req.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
