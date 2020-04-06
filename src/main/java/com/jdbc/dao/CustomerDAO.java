package com.jdbc.dao;

import com.jdbc.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO implements DataAccessObject<Customer> {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static String unlinkCustomerProject = "DELETE FROM customer_project WHERE customer_id = ?;";

    public CustomerDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Customer customer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Customer getByID(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Customer c where c.id=:id", Customer.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Customer> getAll() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Customer ", Customer.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void update(Customer customer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Customer customer) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public void unlinkCustomerProject(int customerID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCustomerProject)) {

            statement.setInt(1, customerID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
