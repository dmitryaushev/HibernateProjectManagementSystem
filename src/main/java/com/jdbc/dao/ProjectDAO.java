package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DataAccessObject<Project> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String getSumSalaryByProject;
    private static String getAllDevelopersByProject;
    private static String getAllProjectsWithDevelopers;

    private static String linkCustomerProject = "INSERT INTO customer_project (customer_id, project_id) " +
                                                "VALUES(?, ?)";
    private static String linkCompanyProject = "INSERT INTO company_project (company_id, project_id) " +
                                               "VALUES(?, ?)";

    private static String unlinkCustomerProject = "DELETE FROM customer_project WHERE project_id = ?;";
    private static String unlinkCompanyProject = "DELETE FROM company_project WHERE project_id = ?;";
    private static String unlinkDeveloperProject = "DELETE FROM developer_project WHERE project_id = ?;";
    private static String getCompanyProjectLink = "SELECT * FROM company_project " +
                                                  "WHERE company_id = ? AND project_id = ?;";
    private static String getCustomerProjectLink = "SELECT * FROM customer_project " +
                                                   "WHERE customer_id = ? AND project_id = ?;";

    public ProjectDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Project project) {

        Session session = null;
        Transaction transaction;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public Project getByID(int id) {

        Session session = null;
        Project project = new Project();

        try {
            session = sessionFactory.openSession();
            project = (Project) session.createQuery("from Project p where p.id=:id")
                    .setParameter("id", id).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return project;
    }

    @Override
    public List<Project> getAll() {

        Session session = null;
        List<Project> projects = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            projects = session.createQuery("from Project").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return projects;
    }

    @Override
    public void update(Project project) {

        Session session = null;
        Transaction transaction;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Project project) {

        Session session = null;
        Transaction transaction;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(project);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    public int getSumSalary(int id) {

        getSumSalaryByProject =
                "SELECT sum(salary)\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_id = ?;";
        int sum = 0;

        try (PreparedStatement statement = connection.prepareStatement(getSumSalaryByProject)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                sum = resultSet.getInt("sum");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public List<Developer> getAllDevelopersByProject(int id) {

        getAllDevelopersByProject =
                "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_id = ?;";

        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllDevelopersByProject)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Developer developer = new Developer();
                developer.setDeveloperID(resultSet.getInt("developer_id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setGender(resultSet.getString("gender"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getInt("salary"));

                developersList.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developersList;
    }

    public List<String> getAllProjectsWithDevelopers() {

        getAllProjectsWithDevelopers =
                "SELECT p.date, p.project_name, count(d) AS amount\n" +
                "FROM projects p \n" +
                "JOIN developer_project dp ON p.project_id = dp.project_id\n" +
                "JOIN developers d ON dp.developer_id = d.developer_id\n" +
                "GROUP BY p.date, p.project_name\n" +
                "ORDER BY amount DESC;";
        List<String> projectsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllProjectsWithDevelopers)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Date date = resultSet.getDate("date");
                String name = resultSet.getString("project_name");
                int amount = resultSet.getInt("amount");

                String s = String.format("%s %s %d", date, name, amount);
                projectsList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectsList;
    }

    public void linkCustomerProject(int customerID, int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(linkCustomerProject)) {

            statement.setInt(1, customerID);
            statement.setInt(2, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkCompanyProject(int companyID, int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(linkCompanyProject)) {

            statement.setInt(1, companyID);
            statement.setInt(2, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkCustomerProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCustomerProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkCompanyProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCompanyProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkDeveloperProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkDeveloperProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCustomerProjectLink(int customerID, int projectID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getCustomerProjectLink)) {
            statement.setInt(1, customerID);
            statement.setInt(2, projectID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkCompanyProjectLink(int companyID, int projectID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getCompanyProjectLink)) {
            statement.setInt(1, companyID);
            statement.setInt(2, projectID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void closeSession(Session session) {
        if (session != null)
            session.close();
    }
}
