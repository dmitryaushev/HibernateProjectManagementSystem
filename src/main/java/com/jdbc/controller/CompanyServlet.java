package com.jdbc.controller;

import com.jdbc.config.HibernateDatabaseConnector;
import com.jdbc.dao.CompanyDAOImpl;
import com.jdbc.model.Company;
import com.jdbc.service.CompanyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/company/*")
public class CompanyServlet extends HttpServlet {

    private CompanyService companyService;

    @Override
    public void init() throws ServletException {
        super.init();
        companyService = new CompanyService(new CompanyDAOImpl(HibernateDatabaseConnector.getSessionFactory()));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/showCompanies")) {
            List<Company> companies = companyService.getAll();
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/view/company/showCompanies.jsp").forward(req, resp);
        }
        else if (action.startsWith("/findCompany")) {
            req.getRequestDispatcher("/view/company/findCompany.jsp").forward(req, resp);
        }
        else if (action.startsWith("/find")) {
            String companyName = req.getParameter("companyName");
            if (companyName.isEmpty()) {
                String message = "Company name is empty";
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/company/findCompany.jsp").forward(req, resp);
            }
            Company company = companyService.get(companyName);
            if (company == null) {
                String message = String.format("Company with name %s not exist", companyName);
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/company/findCompany.jsp").forward(req, resp);
            } else {
                req.setAttribute("company", company);
                req.getRequestDispatcher("/view/company/companyDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/createCompany")) {
            req.getRequestDispatcher("/view/company/createCompany.jsp").forward(req, resp);
        }
        else if (action.startsWith("/get")) {
            String id = req.getParameter("id");
            Company company = companyService.get(Integer.parseInt(id));
            req.setAttribute("company", company);
            req.getRequestDispatcher("/view/company/companyDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("id");
            Company company = companyService.get(Integer.parseInt(id));
            req.setAttribute("company", company);
            req.getRequestDispatcher("/view/company/editCompany.jsp").forward(req, resp);
        }
        else if (action.startsWith("/delete")) {
            doDelete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/createCompany")) {
            String validate = companyService.validateCompany(req);
            if (!validate.isEmpty()) {
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/company/createCompany.jsp").forward(req, resp);
            } else {
                Company company = companyService.mapCompany(req);
                companyService.create(company);
                req.setAttribute("company", company);
                req.getRequestDispatcher("/view/company/companyDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/edit")) {
            String validate = companyService.validateEditCompany(req);
            if (!validate.isEmpty()) {
                Company company = companyService.mapEditCompany(req, "oldName", "oldLocation");
                req.setAttribute("company", company);
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/company/editCompany.jsp").forward(req, resp);
            } else {
                Company company = companyService.mapEditCompany(req, "newName", "newLocation");
                companyService.update(company);
                req.setAttribute("company", company);
                req.getRequestDispatcher("/view/company/companyDetails.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String id = req.getParameter("id");
            Company company = companyService.get(Integer.parseInt(id));
            companyService.delete(company);
            resp.sendRedirect("/ProjectManagementSystem/company/showCompanies");
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String requestPathWithServletContext = req.getContextPath() + req.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
