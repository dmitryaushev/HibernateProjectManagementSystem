package com.jdbc.dao;

import com.jdbc.model.Developer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAO implements DataAccessObject<Developer> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String getAllDevelopersByDepartment;
    private static String getAllDevelopersByLevel;

    private static String linkDeveloperProject = "INSERT INTO developer_project(developer_id, project_id) " +
            "VALUES(?, ?);";
    private static String linkDeveloperSkill = "INSERT INTO developer_skill(developer_id, skill_id) " +
            "VALUES(?, ?);";
    private static String unlinkDeveloperProject = "DELETE FROM developer_project WHERE developer_id = ?;";
    private static String unlinkDeveloperSkill = "DELETE FROM developer_skill WHERE developer_id = ?;";
    private static String getDeveloperProjectLink = "SELECT * FROM developer_project " +
            "WHERE developer_id = ? AND project_id = ?;";
    private static String getDeveloperSkillLink = "SELECT * FROM developer_skill " +
            "WHERE developer_id = ? AND skill_id = ?;";

    public DeveloperDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Developer developer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(developer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Developer getByID(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Developer d where d.id=:id", Developer.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Developer> getAll() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Developer", Developer.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void update(Developer developer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(developer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Developer developer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(developer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public List<Developer> getAllDevelopersByDepartment(String department) {

        getAllDevelopersByDepartment =
                "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                        "FROM developers d\n" +
                        "JOIN developer_skill ds ON d.developer_id = ds.developer_id\n" +
                        "JOIN skills s ON ds.skill_id = s.skill_id\n" +
                        "WHERE s.department = ?;";
        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllDevelopersByDepartment)) {
            statement.setString(1, department);

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

    public List<Developer> getAllDevelopersByLevel(String level) {

        getAllDevelopersByLevel =
                "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                        "FROM developers d\n" +
                        "JOIN developer_skill ds ON d.developer_id = ds.developer_id\n" +
                        "JOIN skills s ON ds.skill_id = s.skill_id\n" +
                        "WHERE s.level = ?" +
                        "GROUP BY d.developer_id;";
        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllDevelopersByLevel)) {
            statement.setString(1, level);

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

    public void linkDeveloperProject(int developerID, int projectId) {

        try (PreparedStatement statement = connection.prepareStatement(linkDeveloperProject)) {

            statement.setInt(1, developerID);
            statement.setInt(2, projectId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkDeveloperSkill(int developerID, int skillID) {

        try (PreparedStatement statement = connection.prepareStatement(linkDeveloperSkill)) {

            statement.setInt(1, developerID);
            statement.setInt(2, skillID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkDeveloperProject(int developerID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkDeveloperProject)) {

            statement.setInt(1, developerID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkDeveloperSkill(int developerID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkDeveloperSkill)) {

            statement.setInt(1, developerID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDeveloperProjectLink(int developerID, int projectID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getDeveloperProjectLink)) {
            statement.setInt(1, developerID);
            statement.setInt(2, projectID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkDeveloperSkillLink(int developerID, int skillID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getDeveloperSkillLink)) {
            statement.setInt(1, developerID);
            statement.setInt(2, skillID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
