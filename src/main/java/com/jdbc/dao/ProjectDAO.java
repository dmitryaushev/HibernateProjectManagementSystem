package com.jdbc.dao;

import com.jdbc.model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public interface ProjectDAO extends DataAccessObject<Project> {

        Project get(String projectName);
}
