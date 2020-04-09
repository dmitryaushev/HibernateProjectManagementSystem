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
import java.util.List;

public class DeveloperDAO implements DataAccessObject<Developer> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String linkDeveloperProject = "INSERT INTO developer_project(developer_id, project_id) " +
            "VALUES(?, ?);";
    private static String unlinkDeveloperProject = "DELETE FROM developer_project WHERE developer_id = ?;";
    private static String getDeveloperProjectLink = "SELECT * FROM developer_project " +
            "WHERE developer_id = ? AND project_id = ?;";

    public DeveloperDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Developer developer) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(developer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
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

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(developer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Developer developer) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(developer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    public List<Developer> getAllDevelopersByDepartment(String department) {

        try (Session session = sessionFactory.openSession()) {
            String query = "select d from Developer d " +
                    "join d.skills s " +
                    "where s.department=:department";
            return session.createQuery(query, Developer.class)
                    .setParameter("department", department).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public List<Developer> getAllDevelopersByLevel(String level) {

        try (Session session = sessionFactory.openSession()) {
            String query = "select d from Developer d " +
                    "join d.skills s " +
                    "where s.level=:level " +
                    "group by d.developerID";
            return session.createQuery(query, Developer.class)
                    .setParameter("level", level).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
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

    public void unlinkDeveloperProject(int developerID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkDeveloperProject)) {

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

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
