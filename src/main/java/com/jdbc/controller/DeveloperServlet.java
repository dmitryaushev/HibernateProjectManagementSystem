package com.jdbc.controller;

import com.jdbc.config.HibernateDatabaseConnector;
import com.jdbc.dao.DeveloperDAOImpl;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Developer;
import com.jdbc.service.DeveloperService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/developer/*")
public class DeveloperServlet extends HttpServlet {

    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        super.init();
        developerService = new DeveloperService(new DeveloperDAOImpl(HibernateDatabaseConnector.getSessionFactory()),
                new SkillDAO(HibernateDatabaseConnector.getSessionFactory()));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/showDevelopers")) {
            List<Developer> developers = developerService.getAll();
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/view/showDevelopers.jsp").forward(req, resp);
        }
        else if (action.startsWith("/findDeveloper")) {
            req.getRequestDispatcher("/view/findDeveloper.jsp").forward(req, resp);
        }
        else if (action.startsWith("/find")) {
            String email = req.getParameter("email").trim();
            Developer developer = developerService.get(email);
            if (developer == null) {
                String message = String.format("Developer with email %s not exist", email);
                req.setAttribute("message", message);
                req.getRequestDispatcher("/view/findDeveloper.jsp").forward(req, resp);
            } else {
                req.setAttribute("developer", developer);
                req.getRequestDispatcher("/view/developerDetails.jsp").forward(req, resp);
            }
        }
        else if (action.startsWith("/createDeveloper")) {
            req.getRequestDispatcher("/view/createDeveloper.jsp").forward(req, resp);
        }
        else if (action.startsWith("/get")) {
            String id = req.getParameter("id");
            Developer developer = developerService.get(Integer.parseInt(id));
            req.setAttribute("developer", developer);
            req.getRequestDispatcher("/view/developerDetails.jsp").forward(req, resp);
        }
        else if (action.startsWith("/edit")) {
            String id = req.getParameter("id");
            Developer developer = developerService.get(Integer.parseInt(id));
            req.setAttribute("developer", developer);
            req.getRequestDispatcher("/view/editDeveloper.jsp").forward(req, resp);
        }
        else if (action.startsWith("/delete")) {
            doDelete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        if (action.startsWith("/createDeveloper")) {
            String validate = developerService.validateDeveloper(req);
            if (!validate.isEmpty()) {
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/createDeveloper.jsp").forward(req, resp);
            } else {
                Developer developer = developerService.mapDeveloper(req);
                developerService.create(developer);
                req.setAttribute("developer", developer);
                req.getRequestDispatcher("/view/developerDetails.jsp").forward(req, resp);
            }
        } else if (action.startsWith("/edit")) {
            String id = req.getParameter("developerID");
            Developer developer = developerService.get(Integer.parseInt(id));
            String validate = developerService.validateEditDeveloper(req);
            if (!validate.isEmpty()) {
                req.setAttribute("developer", developer);
                req.setAttribute("validate", validate);
                req.getRequestDispatcher("/view/editDeveloper.jsp").forward(req, resp);
            } else {
                developer = developerService.mapEditDeveloper(developer, req);
                developerService.update(developer);
                req.setAttribute("developer", developer);
                req.getRequestDispatcher("/view/developerDetails.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Developer developer = developerService.get(Integer.parseInt(id));
        developerService.delete(developer);
        resp.sendRedirect("/ProjectManagementSystem/developer/showDevelopers");
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String requestPathWithServletContext = req.getContextPath() + req.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
