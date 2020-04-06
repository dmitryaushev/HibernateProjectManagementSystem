package com.jdbc.dao;

import com.jdbc.model.Skill;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.List;

public class SkillDAO {

    private Connection connection;
    private SessionFactory sessionFactory;

    public SkillDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    public List<Skill> getAll() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Skill", Skill.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public Skill get(String department, String level) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Skill s where s.department=:department and s.level=:level", Skill.class)
                    .setParameter("department", department).setParameter("level", level).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }
}
