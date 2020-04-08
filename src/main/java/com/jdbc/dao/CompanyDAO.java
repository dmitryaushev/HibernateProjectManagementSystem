package com.jdbc.dao;

import com.jdbc.model.Company;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO implements DataAccessObject<Company> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String unlinkCompanyProject = "DELETE FROM company_project WHERE company_id = ?;";

    public CompanyDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Company company) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public Company getByID(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Company c where c.id=:id", Company.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Company> getAll() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Company ", Company.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void update(Company company) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Company company) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    public void unlinkCompanyProject(int companyID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCompanyProject)) {

            statement.setInt(1, companyID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
