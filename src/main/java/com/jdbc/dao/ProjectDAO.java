package com.jdbc.dao;

import com.jdbc.model.Developer;
import com.jdbc.model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements DataAccessObject<Project> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String getSumSalaryByProject;
    private static String getAllDevelopersByProject;
    private static String getAllProjectsWithDevelopers;

    private static String linkCustomerProject = "INSERT INTO customer_project (customer_id, project_id) " +
            "VALUES(?, ?)";

    private static String unlinkCustomerProject = "DELETE FROM customer_project WHERE project_id = ?;";
    private static String getCustomerProjectLink = "SELECT * FROM customer_project " +
            "WHERE customer_id = ? AND project_id = ?;";

    public ProjectDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Project project) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public Project getByID(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Project p where p.id=:id", Project.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Project> getAll() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Project", Project.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void update(Project project) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Project project) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(project);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
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

    public void unlinkCustomerProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCustomerProject)) {

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

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
