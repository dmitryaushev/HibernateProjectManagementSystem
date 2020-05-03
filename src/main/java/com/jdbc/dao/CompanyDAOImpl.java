package com.jdbc.dao;

import com.jdbc.model.Company;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    private SessionFactory sessionFactory;

    public CompanyDAOImpl(SessionFactory sessionFactory) {
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
            return session.get(Company.class, id);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Company get(String companyName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Company c where c.companyName=:companyName", Company.class)
                    .setParameter("companyName", companyName).uniqueResult();
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
    public void delete(int id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Company c where c.id=:id")
                    .setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
