package com.jdbc.dao;

import com.jdbc.model.Skill;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class SkillDAO {

    private SessionFactory sessionFactory;

    public SkillDAO(SessionFactory sessionFactory) {
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
