package com.jdbc.dao;

import com.jdbc.model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {

    private SessionFactory sessionFactory;

    public ProjectDAOImpl(SessionFactory sessionFactory) {
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
    public Project get(String projectName) {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("from Project p where p.projectName=:projectName", Project.class)
                    .setParameter("projectName", projectName).uniqueResult();
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
    public void delete(int id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Project p where p.id=:id")
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
