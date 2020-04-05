package com.jdbc.dao;

import com.jdbc.model.Skill;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SkillDAO {

    private Connection connection;
    private SessionFactory sessionFactory;

    private static final String SELECT_ALL = "SELECT * FROM skills;";
    private static final String SELECT = "SELECT * FROM skills WHERE department = ? AND level = ?;";

    public SkillDAO(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
    }

    public List<Skill> getAll() {

        Session session = null;
        List<Skill> skills = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            skills = session.createQuery("from Skill").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }

        return skills;
    }

    public Skill get(String department, String level) {

        Session session = null;
        Skill skill = new Skill();

        try {
            session = sessionFactory.openSession();
            skill = (Skill) session.createQuery("from Skill s where s.department=:department and s.level=:level")
                    .setParameter("department", department).setParameter("level", level).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }

        return skill;
    }

    private void closeSession(Session session) {
        if (session != null)
            session.close();
    }
}
