package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String unlinkCustomerProject = "DELETE FROM customer_project WHERE customer_id = ?;";

    public CustomerDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Customer customer) {

        Session session = null;
        Transaction transaction;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public Customer getByID(int id) {

        Session session = null;
        Customer customer = new Customer();

        try {
            session = sessionFactory.openSession();
            customer = (Customer) session.createQuery("from Customer c where c.id=:id")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {

        Session session = null;
        List customers = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            customers = session.createQuery("from Customer ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {

        Session session = null;
        Transaction transaction;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Customer customer) {

        Session session = null;
        Transaction transaction;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    public void unlinkCustomerProject (int customerID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCustomerProject)) {

            statement.setInt(1, customerID);
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
