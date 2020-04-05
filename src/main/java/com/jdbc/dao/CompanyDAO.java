package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO extends DataAccessObject<Company> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String unlinkCompanyProject = "DELETE FROM company_project WHERE company_id = ?;";

    public CompanyDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Company company) {

        Session session = null;
        Transaction transaction;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public Company getByID(int id) {

        Session session = null;
        Company company = new Company();

        try {
            session = sessionFactory.openSession();
            company = (Company) session.createQuery("from Company c where c.id=:id")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return company;
    }

    @Override
    public List<Company> getAll() {

        Session session = null;
        List companies = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            companies = session.createQuery("from Company ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return companies;
    }

    @Override
    public void update(Company company) {

        Session session = null;
        Transaction transaction;

        try  {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Company company) {

        Session session = null;
        Transaction transaction;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
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

    private void closeSession(Session session) {
        if (session != null)
            session.close();
    }
}
