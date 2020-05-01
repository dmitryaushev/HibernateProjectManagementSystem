package com.jdbc.config;

import com.jdbc.dao.*;
import com.jdbc.service.*;
import org.hibernate.SessionFactory;

import java.util.Arrays;
import java.util.List;

public class MainController {

    private View view;
    private SessionFactory sessionFactory;
    private List<Command> commands;

    private CompanyDAOImpl companyDAO;
    private CustomerDAO customerDAO;
    private DeveloperDAO developerDAO;
    private ProjectDAO projectDAO;
    private SkillDAO skillDAO;

    public MainController() {

        view = new Console();

        HibernateDatabaseConnector.init();
        sessionFactory = HibernateDatabaseConnector.getSessionFactory();

        companyDAO = new CompanyDAOImpl(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        developerDAO = new DeveloperDAO(sessionFactory);
        projectDAO = new ProjectDAO(sessionFactory);
        skillDAO = new SkillDAO(sessionFactory);

        commands = Arrays.asList(
                new CreateCompany(view, companyDAO),
                new CreateCustomer(view, customerDAO),
                new CreateDeveloper(view, developerDAO),
                new CreateProject(view, projectDAO),
                new GetCompany(view, companyDAO),
                new GetCustomer(view, customerDAO),
                new GetDeveloper(view, developerDAO),
                new GetProject(view, projectDAO),
                new GetAllCompanies(companyDAO),
                new GetAllCustomers(customerDAO),
                new GetAllDevelopers(developerDAO),
                new GetAllProjects(projectDAO),
                new UpdateCompany(view, companyDAO),
                new UpdateCustomer(view, customerDAO),
                new UpdateDeveloper(view, developerDAO),
                new UpdateProject(view, projectDAO),
                new DeleteCompany(view, companyDAO),
                new DeleteCustomer(view, customerDAO),
                new DeleteDeveloper(view, developerDAO),
                new DeleteProject(view, projectDAO),
                new LinkDeveloperToProject(view, developerDAO, projectDAO),
                new LinkDeveloperToSkill(view, developerDAO, skillDAO),
                new LinkProjectToCustomer(view, projectDAO, customerDAO),
                new LinkProjectToCompany(view, projectDAO, companyDAO),
                new GetSumSalaryByProject(view, projectDAO),
                new GetAllDevelopersByProject(view, projectDAO),
                new GetAllProjectsWithDevelopers(view, projectDAO),
                new GetAllDevelopersByDepartment(view, developerDAO, skillDAO),
                new GetAllDevelopersByLevel(view, developerDAO, skillDAO)
        );
    }

    public void run() {

        view.write("Welcome");
        while (true) {
            view.write("\nChoose a command. Q to exit\n");
            commands.forEach(x -> view.write(x.command()));
            String input = view.read();
            if (input.equalsIgnoreCase("Q")) {
                break;
            }
            doCommand(input);
        }
    }

    private void doCommand(String input) {

        for (Command command : commands)
            try {
                if (command.canProcess(input)) {
                    command.process();
                    break;
                }
            } catch (Exception e) {
                view.write(e.getMessage());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
    }
}
