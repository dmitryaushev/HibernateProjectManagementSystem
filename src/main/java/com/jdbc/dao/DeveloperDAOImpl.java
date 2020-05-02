package com.jdbc.dao;

import com.jdbc.model.Developer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DeveloperDAOImpl implements DeveloperDAO {

    private SessionFactory sessionFactory;

    public DeveloperDAOImpl(SessionFactory sessionFactory) {
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
    public Developer get(String email) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Developer d where d.email=:email", Developer.class)
                    .setParameter("email", email).uniqueResult();
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

    @Override
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

    @Override
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

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
